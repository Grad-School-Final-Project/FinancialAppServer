package com.jared.financialappserver.apihandlers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class HelloWorldHandlerTest {

    @Test
    public void handleRequestTest(){
        assertTrue(HelloWorldHandler.handleRequest().contains("Hello"));

    }
}
