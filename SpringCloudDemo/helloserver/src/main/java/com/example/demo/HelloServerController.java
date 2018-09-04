package com.example.demo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloServerController {

    @Autowired
    private HelloServerService service;

    @RequestMapping("/hello")
    public String index(){
        //throw new RuntimeException("Testing throw exception!");
        service.hello();
        return "Hello Spring Cloud";
    }

    @RequestMapping("/limit")
    public String limit(){
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                service.hello();
            }).start();
        }
        return "Test Limit";
    }
}
