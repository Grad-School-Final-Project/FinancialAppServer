package com.jared.financialappserver.apis;
import com.jared.financialappserver.apihandlers.HelloWorldHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

@RestController
public class HelloWorldController {
    private static final Logger logger = LogManager.getLogger(HelloWorldController.class);

    @GetMapping("/hello")
    public String helloWorld(){
        logger.trace("Hello world api called, calling handler");
        return HelloWorldHandler.handleRequest();
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
