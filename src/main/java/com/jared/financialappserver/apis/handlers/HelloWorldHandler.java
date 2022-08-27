package com.jared.financialappserver.apis.handlers;

public class HelloWorldHandler {

    public static String handleRequest(String username)
    {
        return "Hello " + username + " from the handler static";
    }
}
