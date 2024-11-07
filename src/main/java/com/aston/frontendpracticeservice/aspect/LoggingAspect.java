package com.aston.frontendpracticeservice.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Pointcut("execution(* com.aston.frontendpracticeservice.service..*(..))")
    public void applicationServiceMethods() {
    }

    @Before("applicationServiceMethods()")
    public void logMethodStart(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        log.info("Method execution started:{}", methodName);
    }

    @Before("applicationServiceMethods()")
    public void logMethodArgs(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        Object[] methodArgs = joinPoint.getArgs();
        log.debug("Method {} called with arguments: {}", methodName, methodArgs);
    }

    @AfterReturning(pointcut = "applicationServiceMethods()", returning = "result")
    public void logMethodReturn(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        String resultString =  result != null ? result.toString() : null;
            log.debug("Method {} execution finished with result: {}", methodName, resultString);
    }

    @AfterThrowing(pointcut = "applicationServiceMethods()", throwing = "exception")
    public void logMethodException(JoinPoint joinPoint, Exception exception) {
        String methodName = joinPoint.getSignature().getName();
        log.error("Method {} threw an exception: {}", methodName, exception.getMessage());
    }
}
