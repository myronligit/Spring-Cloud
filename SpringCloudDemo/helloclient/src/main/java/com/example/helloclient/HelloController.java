package com.example.helloclient;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @Autowired
    HelloService helloService;

    @RequestMapping("/hello1")
    @HystrixCommand(fallbackMethod = "fallBackIndex")
    public String hello1(){
        return helloService.helloService();
    }

    @RequestMapping("/hello2")
    @HystrixCommand(fallbackMethod = "fallBackIndex",commandProperties = {
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "10"),
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "30000")
    })
    public String hello2(){
        return helloService.helloService();
    }

    @RequestMapping("/hello3")
    @HystrixCommand(fallbackMethod = "fallBackIndex",commandProperties = {
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "10"),
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "30000"),
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1")
    })
    public String hello3(){
        return helloService.helloService();
    }

    @RequestMapping("/hello4")
    @HystrixCommand(fallbackMethod = "fallBackIndex",commandProperties = {
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "30000"),
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")
    }, threadPoolKey = "hello4ThreadPool", threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "10")
    })
    public String hello4(){
        return helloService.helloService();
    }

    @RequestMapping("/hello5")
    public String hello5(){
        for (int i = 0; i < 10; i++) {
            new Thread(() -> System.out.println("Thread Pool Test === " + hello4())).start();
        }
        return "Test Thread!";
    }


    public String fallBackIndex(Throwable t) {
        System.out.println(t.getMessage());
        return "Fall Back Response!";
    }

}
