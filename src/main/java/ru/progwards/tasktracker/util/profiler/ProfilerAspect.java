package ru.progwards.tasktracker.util.profiler;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.util.StopWatch;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Oleg Kiselev
 */
//@Aspect
//@Component
public class ProfilerAspect {

    @Pointcut("within(ru.progwards.tasktracker.service.impl.*) " +
            "|| within(ru.progwards.tasktracker.repository.*) " +
            "|| within(ru.progwards.tasktracker.controller.*)" +
            "|| within(ru.progwards.tasktracker.dto.converter.impl.*)")
    public void profilerPointcut() {
    }

    private Map<String, Long> durationMap = new HashMap<>();
    private Map<String, Long> countMap = new HashMap<>();
    static long interval = System.currentTimeMillis();
   // private final Logger loggerProfiler = LoggerFactory.getLogger(ProfilerAspect.class);

    @Around("profilerPointcut()")
    public Object profilerAround(ProceedingJoinPoint joinPoint) throws Throwable {

        StopWatch stopWatch = new StopWatch(joinPoint.toString());

        try {
            stopWatch.start(joinPoint.toShortString());
            return joinPoint.proceed();
        } finally {
            stopWatch.stop();
            //System.out.println("Method execution time: " + stopWatch.getTotalTimeMillis() + " milliseconds");
            if (durationMap.containsKey(joinPoint.toString())){
                durationMap.put(joinPoint.toString(), durationMap.get(joinPoint.toString())+stopWatch.getTotalTimeMillis());
                countMap.put(joinPoint.toString(), countMap.get(joinPoint.toString())+1L);
            } else{
                durationMap.put(joinPoint.toString(), stopWatch.getTotalTimeMillis());
                countMap.put(joinPoint.toString(), 1L);
            }
            printProfiler();
        }
    }

    public void printProfiler(){
        if((System.currentTimeMillis() - interval) > 3600000){
            for(Map.Entry<String, Long> entry : durationMap.entrySet()) {
                System.out.println(entry.getKey() + ": " +  entry.getValue() + "ms" + countMap.get(entry.getKey()) + " execs.");
            }
            interval=System.currentTimeMillis();
        }
    }
}
