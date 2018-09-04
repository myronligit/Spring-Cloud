package com.example.redislimit;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class RedisLimitController {
    private static final AtomicInteger ATOMIC_INTEGER = new AtomicInteger();

    @RequestLimit(period = 30, count = 3)
    @GetMapping("/test")
    public int testLimiter() {
        return ATOMIC_INTEGER.incrementAndGet();
    }
}
