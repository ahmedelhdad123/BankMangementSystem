package com.fawry.bankmangementsystem.aop;

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
public class AuthenticationLoggingAspect {

    @Pointcut("execution(* com.fawry.bankmangementsystem.service.serviceIpml.AuthenticationServiceImpl.*(..))")
    public void authenticationMethods() {}

    @Before("authenticationMethods()")
    public void logBeforeAuthentication(JoinPoint joinPoint) {
        log.info("Entering method: {}", joinPoint.getSignature().getName());
        Object[] args = joinPoint.getArgs();
        if (args.length > 0) {
            log.debug("Arguments: {}", args);
        } else {
            log.debug("No arguments provided.");
        }
    }

    @AfterReturning(value = "authenticationMethods()", returning = "result")
    public void logAfterAuthentication(JoinPoint joinPoint, Object result) {
        log.info("Exiting method: {}", joinPoint.getSignature().getName());
        if (result != null) {
            log.debug("Result: {}", result);
        } else {
            log.debug("No result returned.");
        }
    }

    @AfterThrowing(value = "authenticationMethods()", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable exception) {
        log.error("Exception in method: {} with message: {}", joinPoint.getSignature().getName(), exception.getMessage());
        log.error("Exception details: ", exception);
    }
}