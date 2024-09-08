package com.fawry.bankmangementsystem.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class AccountLoggingAspect {

    @Pointcut("execution(* com.fawry.bankmangementsystem.service.serviceIpml.AccountServiceImpl.*(..))")
    public void accountMethod() {}

    @Before("accountMethod()")
    public void accountLogging(JoinPoint joinPoint) {
        log.info("Entering method: {}",joinPoint.getSignature().getName());
        Object[] args = joinPoint.getArgs();
        if (args.length > 0) {
            log.debug("Arguments {} ",args);
        }else {
            log.debug("Arguments is empty");
        }
    }

    @AfterReturning(value = "accountMethod()" , returning = "result")
    public void accountLogging(JoinPoint joinPoint, Object result) {
        log.info("Exiting method: {}",joinPoint.getSignature().getName());
        if (result != null) {
            log.debug("Result: {}", result);
        }else{
            log.debug("Result is empty");
        }
    }

    @AfterThrowing(value = "accountMethod()" , throwing = "exception")
    public void accountLogging(JoinPoint joinPoint, Exception exception) {
        log.error("Exiting method: {} with message: {}",joinPoint.getSignature().getName(),exception.getMessage());
        log.error("Exception details", exception);
    }
}
