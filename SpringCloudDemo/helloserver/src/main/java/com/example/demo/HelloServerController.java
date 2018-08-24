package com.example.demo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloServerController {

    @Value("${server.port}")
    String port;

    @RequestMapping(name = "hello", method = RequestMethod.GET)
    public String index(){
        throw new RuntimeException("Testing throw exception!");
        //return "Hello Spring Cloud, Service Port : " + port;
    }
}
