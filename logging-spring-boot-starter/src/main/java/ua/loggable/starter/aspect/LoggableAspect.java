package ua.loggable.starter.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.util.Arrays;

@Aspect
@Slf4j
public class LoggableAspect {
    @Pointcut("@annotation(ua.loggable.starter.aspect.Loggable)")
    private void loggableMethods(){}


    @Around("loggableMethods()")
    public Object aroundServiceMethod(ProceedingJoinPoint pjp) throws Throwable {
        log.info("LOGGABLE: Before executing {} with params {}", pjp.getSignature(), Arrays.toString(pjp.getArgs()));

        Object returnValue = pjp.proceed();

        log.info("LOGGABLE: After executing {}, returned value: {}", pjp.getSignature(), returnValue);

        return returnValue;
    }

    @AfterThrowing(pointcut = "loggableMethods()", throwing = "e")
    public void afterThrowingFromServiceMethod(Exception e) {
        log.error("LOGGABLE: Exception thrown: {}", e.getMessage());
    }

}