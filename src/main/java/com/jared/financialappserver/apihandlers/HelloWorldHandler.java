package com.jared.financialappserver.apihandlers;

public class HelloWorldHandler {

    public static String handleRequest(String username)
    {
        return "Hello " + username + " from the handler static";
    }
}
