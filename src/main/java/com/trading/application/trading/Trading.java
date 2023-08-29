package com.trading.application.trading;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController


public class Trading{


    @RequestMapping(path="api/v1/student")
    public String testApplication(){

        return "Hello World";
    }







}
