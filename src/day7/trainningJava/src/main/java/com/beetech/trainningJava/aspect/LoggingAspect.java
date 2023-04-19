package com.beetech.trainningJava.aspect;

import com.beetech.trainningJava.aspect.interfaces.IIntroduction;
import com.beetech.trainningJava.aspect.interfaces.imp.IntroductionImp;
import com.beetech.trainningJava.controller.mvc.user.CartController;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {



//    // this
//    @Before(value = "this(cartProductService)", argNames = "joinPoint,cartProductService")
//    public void cartProductServiceImplThis(JoinPoint joinPoint, CartProductServiceImp cartProductService) {
//        System.out.println("Before " + cartProductService);
//        System.out.println("Before " + cartProductService.findByCartProductId(32));
//        System.out.println("Before " + joinPoint.getSignature().getName());
//    }
//
//    // target
//    @Before(value = "target(cartProductService)", argNames = "joinPoint,cartProductService")
//    public void cartProductServiceImplTarget(JoinPoint joinPoint, CartProductServiceImp cartProductService) {
//        System.out.println("Before " + cartProductService);
//        System.out.println("Before " + cartProductService.findByCartProductId(32));
//        System.out.println("Before " + joinPoint.getSignature().getName());
//    }
//
//    @Before(value = "target(cartProductService)", argNames = "joinPoint,cartProductService")
//    public void iCartProductServiceTarget(JoinPoint joinPoint, ICartProductService cartProductService) {
//        System.out.println("Before " + cartProductService);
//        System.out.println("Before " + cartProductService.findByCartProductId(32));
//        System.out.println("Before " + joinPoint.getSignature().getName());
//    }
////    @target
//    @Before(value = "@target(com.beetech.trainningJava.aspect.annotation.Loggable)", argNames = "joinPoint")
//    public void controllerTarget(JoinPoint joinPoint) {
//        System.out.println("Before " + joinPoint.getSignature().getName());
//    }
//
////     pointcut execution and within (mvc) controller ( after, before, around, afterThrowing, afterReturning)
//    @Pointcut("execution(* com.beetech.trainningJava.controller.mvc.user.*.*(..))")
////    @Pointcut("within(com.beetech.trainningJava.controller.mvc.user.*)")
//    public void mvcUserExecution() {
//    }
//
//    @Pointcut("within(com.beetech.trainningJava.controller.mvc..*)") // == w
//    public void mvcUserWithin() {
//    }
    // @within
//    @Before(value = "@within(com.beetech.trainningJava.aspect.annotation.Loggable)", argNames = "joinPoint")
//    public void controllerTarget(JoinPoint joinPoint) {
//        System.out.println("Before " + joinPoint.getSignature().getName());
//    }
    // @annotation
//    @Before(value = "@annotation(com.beetech.trainningJava.aspect.annotation.Loggable)", argNames = "joinPoint")
//    public void controllerTarget(JoinPoint joinPoint) {
//        System.out.println("Before " + joinPoint.getSignature().getName());
//    }
    // bean
//    @Before(value = "bean(*ServiceImp)")
//    public void cartProductServiceImplTarget(JoinPoint joinPoint) {
//        System.out.println("Before " + joinPoint.getSignature().getName());
//    }
    // Declare parents
//    @DeclareParents(value = "com.beetech.trainningJava.controller.mvc.user.CartController", defaultImpl = IntroductionImp.class)
//    public static IIntroduction introduction1;
//
//    @Before(value = "this(cartController))", argNames = "joinPoint, cartController")
//    public void introductionThis(JoinPoint joinPoint, IIntroduction cartController) {
//        System.out.println("Before " + joinPoint.getSignature().getName());
//    }
    //


//
//
//    @Before(value = "mvcUserExecution() || mvcUserWithin()")
//    public void mvcUser(JoinPoint joinPoint) {
//        System.out.println("Before " + joinPoint.getSignature().getName());
//    }
//
//    @After(value = "mvcUserExecution() || mvcUserWithin()")
//    public void logControllerAccessAfter(JoinPoint joinPoint) {
//        System.out.println("After " + joinPoint.getSignature().getName());
//    }
//
//    @Around(value = "mvcUserExecution() || mvcUserWithin()")
//    public Object logControllerAccessAround(ProceedingJoinPoint pjp) throws Throwable {
//        System.out.println("Before Around " + pjp.getSignature().getName());
//        Object value = null;
//        try {
//            value = pjp.proceed();
//        } catch (Exception e) {
//            System.out.println("Around Exception " + e.getMessage());
//        }
//        System.out.println("After Around " + pjp.getSignature().getName() + ". Return value=" + value);
//        return value;
//    }
//
//    @AfterReturning(value = "mvcUserExecution() || mvcUserWithin()", returning = "value")
//    public Object logControllerAccessAfterReturning(JoinPoint joinPoint, Object value) {
//        System.out.println("After Returning " + joinPoint.getSignature().getName() + ". Return value=" + value);
//        return value;
//    }
//
//    @AfterThrowing(value = "mvcUserExecution() || mvcUserWithin()", throwing = "value")
//    public void logControllerAccessAfterThrowing(JoinPoint joinPoint, Exception value) {
//        System.out.println("After Throw " + value.getMessage() + joinPoint.getSignature().getName());
//    }

//    // @args
//    @Before(value = "@args(com.beetech.trainningJava.aspect.annotation.Loggable)")
//    public void controllerArgs(JoinPoint joinPoint) {
////        System.out.println("loggable " + loggable.toString());
//        System.out.println("Before " + joinPoint.getSignature().getName());
//    }



}
