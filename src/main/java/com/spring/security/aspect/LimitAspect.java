package com.spring.security.aspect;

import com.google.common.util.concurrent.RateLimiter;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @Description limit aspect to controller interface
 * @Author xy
 * @Date 2020/4/15 12:16
 * @Version 1.0
 * @Since JDK 1.8
 */
@Component
@Scope
@Aspect
public class LimitAspect {
    ////每秒只发出30个令牌，此处是单进程服务的限流,内部采用令牌捅算法实现
    private static RateLimiter rateLimiter = RateLimiter.create(30.0);

    //Service层切点  限流
    @Pointcut("@annotation(com.spring.security.aspect.ServiceLimit)")
    public void ServiceAspect() {

    }

    @Around("ServiceAspect()")
    public  Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Boolean flag = rateLimiter.tryAcquire();
        Object obj = null;
        if(flag){
            obj = joinPoint.proceed();
        }
        return obj;
    }
}
