package com.weison.provider.log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Component
public class LogManager {

    private static Logger logger = LoggerFactory.getLogger(LogAspect.class);

    private static final Boolean enableWatch = true;

    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

        if (!enableWatch) {
            return joinPoint.proceed();
        }

        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        String completeMethodName = className + "." + methodName;

        // 得到类名、方法名和参数
        Object[] args = joinPoint.getArgs();

        StopWatch stopWatch = new StopWatch(completeMethodName);
        stopWatch.start();

        Object proceedResult;

        try {
            proceedResult = joinPoint.proceed(args);
        } finally {
            stopWatch.stop();
        }

        logger.info("Service: {}; Spend: {}; Args: {}",  completeMethodName, stopWatch.getLastTaskTimeMillis(), args);

        return proceedResult;
    }
}
