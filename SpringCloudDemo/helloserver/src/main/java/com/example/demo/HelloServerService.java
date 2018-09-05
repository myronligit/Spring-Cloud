package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class HelloServerService {

    @Value("${server.port}")
    String port;

    public String hello(){
        System.out.println("hello service");
        return "Hello Spring Cloud, Service Port : " + port;
    }

    @RequestLimit
    public String limit(){
        System.out.println("hello limit");
        return "Hello Spring Cloud, Service Port : " + port;
    }
}
