package com.jared.financialappserver.apis.controllers.secured.unsecured;

import com.jared.financialappserver.apis.handlers.UserHandler;
import com.jared.financialappserver.apis.requests.CreateUserRequest;
import com.jared.financialappserver.models.dao.UserDAO;
import com.jared.financialappserver.models.dto.UserDTO;
import com.jared.financialappserver.util.KeycloakUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    UserDAO userDao;

    @Autowired
    KeycloakUtil keycloakUtil;
    private static final Logger logger = LogManager.getLogger(UserController.class);

    @PostMapping("/createUser")
    public ResponseEntity<String> createUser(@RequestBody CreateUserRequest userRequest){
        logger.trace("In create user, request was " + userRequest);

        boolean status = UserHandler.createUser(userRequest, userDao, keycloakUtil);
        if(status){
            return ResponseEntity.status(HttpStatus.OK).body("OK");
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Request Failed");
        }
    }

    @GetMapping("/secured/getUser")
    public ResponseEntity<UserDTO> getUser(){
        // Get the username from the auth token
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = KeycloakUtil.getUsernameFromJWT(auth);

        logger.trace("In get user, getting user data for: " + username );

        UserDTO user = UserHandler.getUser(username, userDao);
        if(user != null){
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
