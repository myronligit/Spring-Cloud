package com.aaxis.ratelimiter.redis.annotation;

import com.aaxis.limiter.exception.RequestLimiterException;
import com.google.common.collect.ImmutableList;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;

import java.io.Serializable;
import java.lang.reflect.Method;


/**
 * The implementation of customized annotation RequestLimiter by Redis.
 */
@Aspect
@Configuration
public class RedisRequestLimiterInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(RedisRequestLimiterInterceptor.class);

    private final RedisTemplate<String, Serializable> limitRedisTemplate;

    @Autowired
    public RedisRequestLimiterInterceptor(RedisTemplate<String, Serializable> limitRedisTemplate) {
        this.limitRedisTemplate = limitRedisTemplate;
    }


    @Around("@annotation(com.aaxis.limiter.redis.annotation.RequestLimiter)")
    public Object interceptor(ProceedingJoinPoint pjp) throws RequestLimiterException {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        RequestLimiter limitAnnotation = method.getAnnotation(RequestLimiter.class);
        int limitPeriod = limitAnnotation.period();
        int limitCount = limitAnnotation.count();
        String key = method.getName();
        ImmutableList<String> keys = ImmutableList.of(key);
        try {
            String luaScript = buildLuaScript();
            RedisScript<Number> redisScript = new DefaultRedisScript<>(luaScript, Number.class);
            Number count = limitRedisTemplate.execute(redisScript, keys, limitCount, limitPeriod);
            logger.info("Access try count is {} for key = {}", count, key);
            if (count != null && count.intValue() <= limitCount) {
                return pjp.proceed();
            } else {
                throw new RequestLimiterException();
            }
        } catch (Throwable e) {
            if (e instanceof RequestLimiterException) {
                throw new RequestLimiterException(e.getLocalizedMessage());
            }
            throw new RuntimeException("server exception");
        }
    }

    public String buildLuaScript() {
        StringBuilder lua = new StringBuilder();
        lua.append("local c");
        lua.append("\nc = redis.call('get',KEYS[1])");
        lua.append("\nif c and tonumber(c) > tonumber(ARGV[1]) then");
        lua.append("\nreturn c;");
        lua.append("\nend");
        lua.append("\nc = redis.call('incr',KEYS[1])");
        lua.append("\nif tonumber(c) == 1 then");
        lua.append("\nredis.call('expire',KEYS[1],ARGV[2])");
        lua.append("\nend");
        lua.append("\nreturn c;");
        return lua.toString();
    }

}