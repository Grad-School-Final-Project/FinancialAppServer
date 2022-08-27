package com.jared.financialappserver.apis.controllers;

import com.jared.financialappserver.apis.handlers.HelloWorldHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.AccessToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
    private static final Logger logger = LogManager.getLogger(HelloWorldController.class);

    @GetMapping("/hello")
    public String helloWorld(){
        logger.trace("Hello world api called, calling handler");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        KeycloakPrincipal principal = (KeycloakPrincipal)auth.getPrincipal();


        KeycloakSecurityContext session = principal.getKeycloakSecurityContext();
        AccessToken accessToken = session.getToken();
        String username = accessToken.getPreferredUsername();
        logger.trace(username);
        return HelloWorldHandler.handleRequest(username);
    }

    @GetMapping("/twoPlusTwo")
    public int twoPlusTwo(){
        return 4;
    }

    @PutMapping("/moneyApi/convertCurrency")
    public int convertCurrency(){
        return 8;
    }

}
