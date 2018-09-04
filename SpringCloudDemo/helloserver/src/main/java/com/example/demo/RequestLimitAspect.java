package com.example.demo;

import com.google.common.util.concurrent.RateLimiter;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RequestLimitAspect {
    @Value("${server.requestLimit}")
    private int requestLimit = 1;
    private RateLimiter rateLimiter = RateLimiter.create(requestLimit);
    private Logger logger = LoggerFactory.getLogger(RequestLimitAspect.class);


    @Pointcut("@annotation(com.example.demo.RequestLimit)")
    private void serviceAspect(){

    }

    @Around("serviceAspect()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws RequestLimitException{
        boolean token = rateLimiter.tryAcquire();
        System.out.println("token =============== " + token + "time : " + System.currentTimeMillis() + "limit : " + requestLimit);
        Object result = null;
        try {
            if (token) {
                result = joinPoint.proceed();
            } else {
                throw new RequestLimitException();
            }
        } catch (RequestLimitException e) {
            throw e;
        } catch (Throwable t) {
            logger.error(t.getMessage(), t);
        }
        return result;
    }
}
