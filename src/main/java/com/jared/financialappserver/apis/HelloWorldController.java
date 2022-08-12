package com.jared.financialappserver.apis;
import com.jared.financialappserver.apihandlers.HelloWorldHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
    private static final Logger logger = LogManager.getLogger(HelloWorldController.class);

    @RequestMapping("/hello")
    public String helloWorld(){
        logger.trace("Hello world api called, calling handler");
        return HelloWorldHandler.handleRequest();
    }

}
