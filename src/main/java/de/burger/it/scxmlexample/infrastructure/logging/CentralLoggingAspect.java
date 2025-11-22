package de.burger.it.scxmlexample.infrastructure.logging;


import de.burger.it.scxmlexample.infrastructure.logging.correlation.CorrelationIdManager;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class CentralLoggingAspect {

    private final LevelLoggerRegistry levelLoggerRegistry;

    public CentralLoggingAspect(LevelLoggerRegistry levelLoggerRegistry) {
        this.levelLoggerRegistry = levelLoggerRegistry;
    }

    @Around("@annotation(de.burger.it.scxmlexample.infrastructure.logging.Loggable) || @within(de.burger.it.scxmlexample.infrastructure.logging.Loggable)")
    public Object logMethodInvocation(ProceedingJoinPoint pjp) throws Throwable {

        // --- correlation id lifecycle start ---
        CorrelationIdManager.initCorrelationId();
        // we do not yet use the id in messages here, it is only placed in MDC for now

        Class<?> targetClass = pjp.getTarget().getClass();
        Logger logger = LoggerFactory.getLogger(targetClass);

        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();

        Loggable loggable = resolveLoggable(targetClass, method);
        Level level = loggable.value();

        LevelLogger levelLogger = levelLoggerRegistry.get(level);

        String methodName = targetClass.getSimpleName() + "." + method.getName();
        Object[] args = pjp.getArgs();

        long start = System.currentTimeMillis();

        try {
            levelLogger.logEntry(logger, methodName, args);

            Object result = pjp.proceed();
            long duration = System.currentTimeMillis() - start;

            levelLogger.logExit(logger, methodName, duration, result);
            return result;

        } catch (Throwable ex) {
            long duration = System.currentTimeMillis() - start;

            levelLogger.logException(logger, methodName, duration, ex);
            throw ex;

        } finally {
            // --- correlation id lifecycle end ---
            CorrelationIdManager.clearIfOwned();
        }
    }

    private Loggable resolveLoggable(Class<?> targetClass, Method method) {
        Loggable anno = method.getAnnotation(Loggable.class);
        if (anno != null) {
            return anno;
        }
        anno = targetClass.getAnnotation(Loggable.class);
        if (anno != null) {
            return anno;
        }
        // Defensive fallback
        return new Loggable() {
            @Override
            public Class<? extends java.lang.annotation.Annotation> annotationType() {
                return Loggable.class;
            }
            @Override
            public org.slf4j.event.Level value() {
                return org.slf4j.event.Level.DEBUG;
            }
        };
    }
}
