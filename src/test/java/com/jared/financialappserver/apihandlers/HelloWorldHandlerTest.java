package com.jared.financialappserver.apihandlers;

import com.jared.financialappserver.apis.handlers.HelloWorldHandler;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class HelloWorldHandlerTest {

    @Test
    public void handleRequestTest(){
        assertTrue(HelloWorldHandler.handleRequest("john").contains("Hello"));
    }
}
