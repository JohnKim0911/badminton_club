package com.club.badminton.config.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


/**
 * 모든 Public 클래스에 AOP를 통해서 실행로그 남기기
 */
@Aspect
@Component
@Slf4j
public class TraceLoggingAspect {

    @Pointcut("within(@org.springframework.stereotype.Service *) || " +
            "within(@org.springframework.stereotype.Controller *) || " +
            "within(@org.springframework.web.bind.annotation.RestController *) || " +
            "within(@org.springframework.stereotype.Component *)")
    public void applicationBeans() {}

    @Around("applicationBeans()")
    public Object logMethodExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        Class<?> targetClass = joinPoint.getTarget().getClass();
        Logger log = LoggerFactory.getLogger(targetClass);

        String className = targetClass.getSimpleName();
        String methodName = joinPoint.getSignature().getName();

        log.trace("{}.{}", className, methodName);
//        log.trace("{}.{} - called", className, methodName);

        try {
            Object result = joinPoint.proceed();
//            log.trace("{}.{} - completed", className, methodName);
            return result;
        } catch (Throwable ex) {
            log.trace("{}.{} - exception: {}", className, methodName, ex.getMessage());
            throw ex;
        }
    }

}
