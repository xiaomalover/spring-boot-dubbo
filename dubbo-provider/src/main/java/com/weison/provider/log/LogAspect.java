package com.weison.provider.log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(-999) //越小越先执行
@Aspect
public class LogAspect {

    //@Autowired 官方不推荐在属性上注解，所以换成了下面构造器注解
    private LogManager logManager;

    @Autowired
    public LogAspect(LogManager logManager) {
        this.logManager = logManager;
    }

    //Pointcut表示式
    @Pointcut("execution(public * com.weison.base.api..*(..))")
    //Point签名
    private void log() {}

    /*@Before("log()")
    private void before(JoinPoint point) throws Throwable {
        System.out.println("Before service method.");
    }

    @AfterReturning("log()")
    private void afterReturning() {
        System.out.println("After return service method.");
    }

    @AfterThrowing("log()")
    private void afterThrowing() {
        System.out.println("After throwing service method.");
    }

    @After("log()")
    private void after() {
        System.out.println("After service method.");
    }*/

    @Around("log()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        return logManager.around(pjp);
    }
}
