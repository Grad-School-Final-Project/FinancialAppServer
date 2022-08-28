package com.jared.financialappserver.util;

import lombok.Getter;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;


@Configuration
@Getter
public class KeycloakUtil {

    static Keycloak keycloak = null;
    @Value("${keycloak.auth-server-url}")
    String serverUrl;
    @Value("${keycloak.realm}")
    String realm ;
    @Value("${keycloak.resource}")
    String clientId ;

    @Value("${keycloakClientSecret}")
    String clientSecret;


    public KeycloakUtil() {
    }

    public Keycloak getInstance(){
        if(keycloak == null){


            keycloak = KeycloakBuilder.builder()
                    .serverUrl(serverUrl)
                    .realm(realm)
                    .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                    .clientId(clientId)
                    .clientSecret(clientSecret)
                    .resteasyClient(new ResteasyClientBuilder()
                                    .connectionPoolSize(10)
                                    .build()
                                   )
                    .build();
        }
        return keycloak;
    }

    public static String getUsernameFromJWT(Authentication auth){
        KeycloakPrincipal principal = (KeycloakPrincipal)auth.getPrincipal();
        KeycloakSecurityContext session = principal.getKeycloakSecurityContext();
        AccessToken accessToken = session.getToken();

        return accessToken.getPreferredUsername();
    }
}
