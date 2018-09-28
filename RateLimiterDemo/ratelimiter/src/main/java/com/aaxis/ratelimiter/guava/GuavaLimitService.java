package com.aaxis.ratelimiter.guava;

import com.aaxis.limiter.guava.annotation.RequestLimiter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GuavaLimitService {

    @Value("${server.port}")
    String port;

    public String hello(){
        System.out.println("hello service");
        return "Hello Spring Cloud, Service Port : " + port;
    }

    @RequestLimiter
    public String limit(){
        System.out.println("hello limit");
        return "Hello Spring Cloud, Service Port : " + port;
    }
}
