package com.aaxis.ratelimiter.guava.annotation;

import com.aaxis.limiter.exception.RequestLimiterException;
import com.google.common.util.concurrent.RateLimiter;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * The implementation of customized annotation RequestLimiter by Guava.
 */
@Aspect
@Component
public class GuavaRequestLimiterInterceptor {
    @Value("${server.requestLimit}")
    private int requestLimit = 1;
    private RateLimiter rateLimiter = RateLimiter.create(requestLimit);
    private Logger logger = LoggerFactory.getLogger(GuavaRequestLimiterInterceptor.class);


    @Pointcut("@annotation(com.aaxis.limiter.guava.annotation.RequestLimiter)")
    private void serviceAspect(){

    }

    @Around("serviceAspect()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws RequestLimiterException {
        boolean token = rateLimiter.tryAcquire();
        logger.debug("The token current request get is {}", token);
        Object result = null;
        try {
            if (token) {
                result = joinPoint.proceed();
            } else {
                throw new RequestLimiterException();
            }
        } catch (RequestLimiterException e) {
            throw e;
        } catch (Throwable t) {
            logger.error(t.getMessage(), t);
        }
        return result;
    }
}
