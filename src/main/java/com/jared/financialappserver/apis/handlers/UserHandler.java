package com.jared.financialappserver.apis.handlers;

import com.jared.financialappserver.models.dto.UserDTO;
import com.jared.financialappserver.models.dao.UserDAO;
import com.jared.financialappserver.apis.requests.CreateUserRequest;
import com.jared.financialappserver.util.KeycloakUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import javax.ws.rs.core.Response;
import java.util.Optional;

public class UserHandler {
    private static final Logger logger = LogManager.getLogger(UserHandler.class);
    public static boolean createUser(CreateUserRequest userRequest, UserDAO userDao, KeycloakUtil keycloakUtil){
        if(userDao.existsById(userRequest.getUsername()))
        {
            return false;
        }

        if(createKeycloakUser(userRequest, keycloakUtil)){
            // add the user to our database
            UserDTO newUser = UserDTO.builder()
                    .username(userRequest.getUsername())
                    .firstName(userRequest.getFirstname())
                    .lastName(userRequest.getLastname())
                    .build();

            userDao.save(newUser);
            return true;
        }
        return false;
    }

    public static UserDTO getUser(String username, UserDAO userDAO){

        Optional<UserDTO> optional = userDAO.findById(username);
        if(optional.isEmpty()){
            // user not found
            return null;
        }
        else {
            return optional.get();
        }

    }

    private static boolean createKeycloakUser(CreateUserRequest request, KeycloakUtil keycloakUtil){
        boolean success = false;

        Keycloak keycloak = keycloakUtil.getInstance();

        RealmResource realmResource = keycloak.realm(keycloakUtil.getRealm());
        UsersResource userResource = realmResource.users();

        UserRepresentation keycloakUser = new UserRepresentation();
        keycloakUser.setEnabled(true);
        keycloakUser.setUsername(request.getUsername());
        keycloakUser.setFirstName(request.getFirstname());
        keycloakUser.setLastName(request.getLastname());
        keycloakUser.setEmail(request.getEmail());

        Response response = userResource.create(keycloakUser);
        if (response.getStatus() == 201) {
            success = true;
            // successfully created the user, now set the password.
            logger.info("Successfully created user with username: " + request.getUsername());
            String userId = CreatedResponseUtil.getCreatedId(response);

            // create password credential
            CredentialRepresentation passwordCred = new CredentialRepresentation();
            passwordCred.setTemporary(false);
            passwordCred.setType(CredentialRepresentation.PASSWORD);
            passwordCred.setValue(request.getPassword());

            UserResource user = realmResource.users().get(userId);
            user.resetPassword(passwordCred);
        }

        return success;
    }

}
