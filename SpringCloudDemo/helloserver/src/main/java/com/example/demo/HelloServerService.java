package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class HelloServerService {

    @Value("${server.port}")
    String port;

    @RequestLimit
    public String hello(){
        System.out.println("hello service");
        //throw new RuntimeException("Testing throw exception!");
        return "Hello Spring Cloud, Service Port : " + port;
    }
}
