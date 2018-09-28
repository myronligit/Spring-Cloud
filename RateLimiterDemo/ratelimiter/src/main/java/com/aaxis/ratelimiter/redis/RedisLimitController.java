package com.aaxis.ratelimiter.redis;

import com.aaxis.limiter.redis.annotation.RequestLimiter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class RedisLimitController {
    private static final AtomicInteger ATOMIC_INTEGER = new AtomicInteger();

    @RequestLimiter(period = 30, count = 3)
    @GetMapping("/r_limit")
    public int testLimiter() {
        return ATOMIC_INTEGER.incrementAndGet();
    }
}
