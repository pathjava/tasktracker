package ru.progwards.tasktracker.util.profiler;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

/**
 * @author Oleg Kiselev
 */
@Aspect
@Component
public class ProfilerAspect {

    @Pointcut("within(ru.progwards.tasktracker.service.impl.*) " +
            "|| within(ru.progwards.tasktracker.repository.*) " +
            "|| within(ru.progwards.tasktracker.controller.*)" +
            "|| within(ru.progwards.tasktracker.dto.converter.impl.*)")
    public void profilerPointcut() {
    }

    @Around("profilerPointcut()")
    public Object profilerAround(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch stopWatch = new StopWatch(joinPoint.toString());

        try {
            stopWatch.start(joinPoint.toShortString());
            return joinPoint.proceed();
        } finally {
            stopWatch.stop();
            System.out.println("Method execution time: " + stopWatch.getTotalTimeMillis() + " milliseconds");
        }
    }
}
