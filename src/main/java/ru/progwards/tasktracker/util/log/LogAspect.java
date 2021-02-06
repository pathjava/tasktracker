package ru.progwards.tasktracker.util.log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @author Oleg Kiselev
 */
@Aspect
@Component
public class LogAspect {

    private final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Pointcut("execution(!void ru.progwards.tasktracker.service.impl.*.*(..)) " +
            "|| execution(!void ru.progwards.tasktracker.repository.*.*(..)) " +
            "|| execution(!void ru.progwards.tasktracker.controller.*.*(..))" +
            "|| execution(!void ru.progwards.tasktracker.dto.converter.impl.*.*(..))")
    public void returnPointcut() {
    }

    @Pointcut("execution(void ru.progwards.tasktracker.service.impl.*.*(..)) " +
            "|| execution(void ru.progwards.tasktracker.repository.*.*(..)) " +
            "|| execution(void ru.progwards.tasktracker.controller.*.*(..))" +
            "|| execution(void ru.progwards.tasktracker.dto.converter.impl.*.*(..))")
    public void voidPointcut() {
    }

    @AfterThrowing(value = "returnPointcut() || voidPointcut()", throwing = "e")
    public void logMethodWithThrows(JoinPoint joinPoint, Exception e) {
        logger.info("Reason Exception: {} with message = {}", joinPoint.getSignature().toString(), e.getMessage());
    }

    @Around("returnPointcut() || voidPointcut()")
    public Object logMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("Enter: {} with argument[s] = {}", joinPoint.getSignature().toString(),
                Arrays.toString(joinPoint.getArgs())
        );
        try {
            Object result = joinPoint.proceed();
            logger.info("Exit: {} with result = {}", joinPoint.getSignature().toString(), result);
            return result;
        } catch (IllegalArgumentException ex) {
            logger.error("Illegal argument: {} in {}.{}()", Arrays.toString(joinPoint.getArgs()),
                    joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName()
            );
            throw ex;
        }
    }


    @Pointcut("within(@org.springframework.stereotype.Repository *)" +
            "|| within(@org.springframework.stereotype.Service *)" +
            "|| within(@org.springframework.web.bind.annotation.RestController *)")
    public void springBeanPointcut() {
    }

//    @Around("springBeanPointcut()")
//    public Object logSpringBeanAround(ProceedingJoinPoint joinPoint) throws Throwable {
//        if (logger.isDebugEnabled()) {
//            logger.debug("Enter: {}.{}() with argument[s] = {}", joinPoint.getSignature().getDeclaringTypeName(),
//                    joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs())
//            );
//        }
//        try {
//            Object result = joinPoint.proceed();
//            if (logger.isDebugEnabled()) {
//                logger.debug("Exit: {}.{}() with result = {}", joinPoint.getSignature().getDeclaringTypeName(),
//                        joinPoint.getSignature().getName(), result
//                );
//            }
//            return result;
//        } catch (IllegalArgumentException ex) {
//            logger.error("Illegal argument: {} in {}.{}()", Arrays.toString(joinPoint.getArgs()),
//                    joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName()
//            );
//            throw ex;
//        }
//    }

}
