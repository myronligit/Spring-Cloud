package com.aaxis.ratelimiter.redis.annotation;

import java.lang.annotation.*;

/**
 * Customized annotation to implement rate limit using Redis
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface RequestLimiter {

    int period();

    int count();

}
