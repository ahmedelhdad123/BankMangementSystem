package com.fawry.bankmangementsystem.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class TransactionLoggingAspect {

    @Pointcut("execution(* com.fawry.bankmangementsystem.service.serviceIpml.TransactionServiceImpl.*(..))")
    public void transactionMethods() {}


    @Before("transactionMethods()")
    public void logBeforeTransaction(JoinPoint joinPoint) {
        log.info("Entering method: {}", joinPoint.getSignature().getName());
        Object[] args = joinPoint.getArgs();
        if (args.length > 0) {
            log.debug("Arguments: {}", args);
        }else{
            log.debug("Args is empty");
        }
    }


    @AfterReturning(value = "transactionMethods()", returning = "result")
    public void logAfterTransaction(JoinPoint joinPoint, Object result) {
        log.info("Exiting method: {}", joinPoint.getSignature().getName());
        if (result != null) {
            log.debug("Result: {}", result);
        } else {
            log.debug("No result returned.");
        }
    }

    @AfterThrowing(value = "transactionMethods()",throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Exception exception) {
        log.error("Exception in method: {} with message: {}", joinPoint.getSignature().getName(), exception.getMessage());
        log.error("Exception details: ", exception);
    }
}
