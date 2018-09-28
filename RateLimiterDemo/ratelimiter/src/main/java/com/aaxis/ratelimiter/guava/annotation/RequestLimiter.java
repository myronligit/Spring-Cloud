package com.aaxis.ratelimiter.guava.annotation;

import java.lang.annotation.*;

/**
 * Customized annotation to implement rate limit using Guava
 */
@Target({ ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestLimiter {

}
