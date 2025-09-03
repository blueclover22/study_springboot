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

    // 서비스 메서드 실행 시간 측정 (단, PerformanceLogService는 제외하여 무한 루프 방지)
    @Around("execution(* com.study.springboot.service.*Service*.*(..)) && " +
            "!execution(* com.study.springboot.service.PerformanceLogService.*(..))")
    public Object timeLog(ProceedingJoinPoint joinPoint) throws Throwable {

        long start = System.currentTimeMillis();

        Signature signature = joinPoint.getSignature();
        Object target = joinPoint.getTarget();

        log.debug("signature.getName() : {}", signature.getName());
        log.debug("signature.getDeclaringTypeName() : {}", signature.getDeclaringTypeName());
        log.debug("target : {}", target);

        String signatureName = signature.getName();
        String signatureDeclaringTypeName = signature.getDeclaringTypeName();

        Object result = joinPoint.proceed();

        long end = System.currentTimeMillis();

        long durationTime = end - start;

        // 성능 임계값 (100ms) 이상인 경우만 로그 저장
        if (durationTime >= 100) {
            PerformanceLog performanceLog = new PerformanceLog();
            performanceLog.setSignatureName(signatureName);
            performanceLog.setSignatureTypeName(signatureDeclaringTypeName);
            performanceLog.setDurationTime(durationTime);

            try {
                service.register(performanceLog);
            } catch (Exception e) {
                log.error("Failed to save performance log: {}", e.getMessage(), e);
            }
        }

        log.debug("Method [{}] executed in {} ms", signatureName, durationTime);

        return result;
    }
}
