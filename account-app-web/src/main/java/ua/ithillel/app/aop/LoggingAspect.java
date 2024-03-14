package ua.ithillel.app.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class LoggingAspect {
    @Pointcut("execution(* ua.ithillel.app.service..*.*(..))")
    private void serviceMethods(){}

    @Pointcut("execution(* ua.ithillel.app.repo..*.*(..))")
    private void repoMethods(){}

    @Pointcut("execution(* ua.ithillel.app.model.mapper.*.*(..))")
    private void mapperMethods(){}

    @Around("serviceMethods()")
    public Object aroundServiceMethod(ProceedingJoinPoint pjp) throws Throwable {
        log.debug("Before executing {} with params {}", pjp.getSignature(), Arrays.toString(pjp.getArgs()));
        System.out.println("from aspect");
        Object returnValue = pjp.proceed();

        log.debug("After executing {}, returned value: {}", pjp.getSignature(), returnValue);
        System.out.println("after method");
        return returnValue;
    }

    @AfterThrowing(pointcut = "serviceMethods()", throwing = "e")
    public void afterThrowingFromServiceMethod(Exception e) {
        log.error("Exception thrown: {}", e.getMessage());
    }

    @Around("repoMethods()")
    public Object aroundRepoMethod(ProceedingJoinPoint pjp) throws Throwable {
        log.debug("Before executing {} with params {}", pjp.getSignature(), Arrays.toString(pjp.getArgs()));

        Object returnValue = pjp.proceed();

        log.debug("After executing {}, returned value: {}", pjp.getSignature(), returnValue);

        return returnValue;
    }

    @AfterThrowing(pointcut = "repoMethods()", throwing = "e")
    public void afterThrowingFromRepoMethod(Exception e) {
        log.error("Exception thrown: {}", e.getMessage());
    }

    @Around("mapperMethods()")
    public Object aroundMapperMethod(ProceedingJoinPoint pjp) throws Throwable {
        log.debug("Before executing {} with params {}", pjp.getSignature(), Arrays.toString(pjp.getArgs()));

        Object returnValue = pjp.proceed();

        log.debug("After executing {}, returned value: {}", pjp.getSignature(), returnValue);

        return returnValue;
    }
}
