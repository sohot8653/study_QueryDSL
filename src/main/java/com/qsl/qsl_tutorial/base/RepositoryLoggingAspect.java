package com.qsl.qsl_tutorial.base;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class RepositoryLoggingAspect {

    // 모든 Spring Data Repository 메소드
    @Around("execution(* org.springframework.data.repository.Repository+.*(..))")
    public Object logRepoCall(ProceedingJoinPoint pjp) throws Throwable {
        long t0 = System.currentTimeMillis();
        String type = pjp.getSignature().getDeclaringTypeName();
        String method = pjp.getSignature().getName();
        Object[] args = pjp.getArgs();

        log.info("[Repo call] {}.{}(args={})", type, method, java.util.Arrays.toString(args));
        try {
            Object result = pjp.proceed();
            log.info("[Repo done] {}.{}() in {}ms", type, method, (System.currentTimeMillis() - t0));
            return result;
        } catch (Throwable ex) {
            log.warn("[Repo fail] {}.{}() -> {}", type, method, ex.toString());
            throw ex;
        }
    }
}