package com.weison.provider.log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author xiaomalover <xiaomalover@gmail.com>
 */
@Component
@Order(-999)
@Aspect
public class LogAspect {

    private LogManager logManager;

    @Autowired
    public LogAspect(LogManager logManager) {
        this.logManager = logManager;
    }

    /**
     * Pointcut表示式
     * Point签名
     */
    @Pointcut("execution(public * com.weison.base.api..*(..))")
    private void log() {}

    @Around("log()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        return logManager.around(pjp);
    }
}
