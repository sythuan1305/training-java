package com.beetech.trainningJava.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

@Aspect
@Component
public class LoggingAspect {

    @Before("execution(* com.beetech.trainningJava.controller.mvc.user.*.*(..))")
    public void logControllerAccessBefore(JoinPoint joinPoint) {
        System.out.println("logControllerAccessBefore" + joinPoint.getSignature());
    }

    @After("execution(* com.beetech.trainningJava.controller.mvc.user.*.*(..))")
    public void logControllerAccessAfter(JoinPoint joinPoint) {
        System.out.println("logControllerAccessAfter" + joinPoint.getSignature());
    }

    @Around(value = "execution(* com.beetech.trainningJava.controller.mvc.user.*.*(..))", argNames = "pjp")
    public Object logControllerAccessAround(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("Before logControllerAccessAround");
        Object value = null;
        try {
            value = pjp.proceed();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("After logControllerAccessAround. Return value="+value);
        return value;
    }

    @AfterReturning(value = "execution(* com.beetech.trainningJava.controller.mvc.user.*.*(..))", argNames = "joinPoint")
    public void logControllerAccessAfterReturning(JoinPoint joinPoint) {
        System.out.println("logControllerAccessAfterReturning" + joinPoint.getSignature());
    }

    @AfterThrowing(value = "execution(* com.beetech.trainningJava.controller.mvc.user.*.*(..))", argNames = "joinPoint")
    public void logControllerAccessAfterThrowing(JoinPoint joinPoint) {
        System.out.println("logControllerAccessAfterThrowing" + joinPoint.getSignature());
    }



}
