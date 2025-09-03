package com.study.springboot.common.aop;


import com.study.springboot.domain.PerformanceLog;
import com.study.springboot.service.PerformanceLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Aspect
@RequiredArgsConstructor
public class TimeCheckerAdvice {

    private final PerformanceLogService service;

    @Around("execution(* com.study.springboot.service.*Service*.*(..))")
    public Object timeLog(ProceedingJoinPoint joinPoint) throws Throwable {

        long start = System.currentTimeMillis();

        Signature signature = joinPoint.getSignature();
        Object target = joinPoint.getTarget();

        log.info("signature.getName() : {}", signature.getName());
        log.info("signature.getDeclaringTypeName() : {}", signature.getDeclaringTypeName());
        log.info("target : {}", target);

        String signatureName = signature.getName();
        String signatureDeclaringTypeName = signature.getDeclaringTypeName();

        Object result = joinPoint.proceed();

        long end = System.currentTimeMillis();

        long durationTime = end - start;

        PerformanceLog performanceLog = new PerformanceLog();
        performanceLog.setSignatureName(signatureName);
        performanceLog.setSignatureTypeName(signatureDeclaringTypeName);
        performanceLog.setDurationTime(durationTime);

        service.register(performanceLog);

        return result;
    }
}
