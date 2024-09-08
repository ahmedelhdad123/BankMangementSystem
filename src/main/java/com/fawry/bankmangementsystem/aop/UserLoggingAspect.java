package com.fawry.bankmangementsystem.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class UserLoggingAspect {

    @Pointcut("execution(* com.fawry.bankmangementsystem.service.serviceIpml.UserServiceImpl.*(..))")
    public void userMethod() {}

    @Before("userMethod()")
    public void logBeforeUserMethod(JoinPoint joinPoint) {
        log.info("Entering method: {}", joinPoint.getSignature().getName());
        Object[] args = joinPoint.getArgs();
        if (args.length > 0) {
            log.debug("Arguments: {}", args);
        } else {
            log.debug("No arguments provided.");
        }
    }

    @AfterReturning(value = "userMethod()", returning = "result")
    public void logAfterUserMethod(JoinPoint joinPoint, Object result) {
        log.info("Exiting method: {}", joinPoint.getSignature().getName());
        if (result != null) {
            log.debug("Result: {}", result);
        } else {
            log.debug("No result returned.");
        }
    }

    @AfterThrowing(value = "userMethod()", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable exception) {
        log.error("Exception in method: {} with message: {}", joinPoint.getSignature().getName(), exception.getMessage());
        log.error("Exception details: ", exception);
    }
}