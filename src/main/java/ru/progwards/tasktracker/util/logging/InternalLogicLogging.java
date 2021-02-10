package ru.progwards.tasktracker.util.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Логика логирования методов, определенных в @Pointcut с помощью Spring AOP
 *
 * @author Oleg Kiselev
 */
@Profile("dev")
@Aspect
@Component
public class InternalLogicLogging {

    private static final Logger LOGGER = LoggerFactory.getLogger(InternalLogicLogging.class);

    /**
     * Pointcut — срез, запрос точек присоединения для return методов
     */
    @Pointcut("execution(!void ru.progwards.tasktracker.service.impl.*.*(..)) " +
            "|| execution(!void ru.progwards.tasktracker.repository.*.*(..)) " +
            "|| execution(!void ru.progwards.tasktracker.controller.*.*(..))" +
            "|| execution(!void ru.progwards.tasktracker.dto.converter.impl.*.*(..))")
    public void returnPointcut() {
    }

    /**
     * Pointcut — срез, запрос точек присоединения для void методов
     */
    @Pointcut("execution(void ru.progwards.tasktracker.service.impl.*.*(..)) " +
            "|| execution(void ru.progwards.tasktracker.repository.*.*(..)) " +
            "|| execution(void ru.progwards.tasktracker.controller.*.*(..))" +
            "|| execution(void ru.progwards.tasktracker.dto.converter.impl.*.*(..))")
    public void voidPointcut() {
    }

    /**
     * Метод запускается после выхода из метода (совпадающего с выражением pointcut) с генерацией исключения.
     *
     * @param joinPoint представляет собой точку в приложении, где можно подключить аспект АОП.
     *                  Можно сказать, что это фактическое место в приложении,
     *                  где будет выполняться действие с использованием инфраструктуры Spring AOP.
     *                  Не обязательный параметр, который предоставляет дополнительную информацию,
     *                  но если он используется, то он должен быть первым.
     *                  Используется при @Before,@AfterThrowing,@AfterReturning
     * @param e         исключение, переданное из метода
     */
    @AfterThrowing(value = "returnPointcut() || voidPointcut()", throwing = "e")
    public void logMethodWithThrows(JoinPoint joinPoint, Exception e) {
        LOGGER.info("Reason Exception: {} with message = {}", joinPoint.getSignature().toString(), e.getMessage());
    }

    /**
     * Метод, который может выполняться до и после выполнения анализируемого метода.
     * Можно получить информацию о входящих и исходящих данных/параметрах анализируемого метода.
     *
     * @param joinPoint представляет собой точку в приложении, где можно подключить аспект АОП.
     *                  Можно сказать, что это фактическое место в приложении,
     *                  где будет выполняться действие с использованием инфраструктуры Spring AOP.
     *                  Используется при @Around
     * @return результат joinPoint.proceed()
     * @throws Throwable исключение, возникшее при выполнении
     */
    @Around("returnPointcut() || voidPointcut()")
    public Object logMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.info("Enter: {} with argument[s] = {}", joinPoint.getSignature().toString(),
                    Arrays.toString(joinPoint.getArgs())
            );
        }
        try {
            Object result = joinPoint.proceed();
            if (LOGGER.isDebugEnabled()) {
                LOGGER.info("Exit: {} with result = {}", joinPoint.getSignature().toString(), result);
            }
            return result;
        } catch (IllegalArgumentException ex) {
            LOGGER.error("Illegal argument: {} in {}.{}()", Arrays.toString(joinPoint.getArgs()),
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
