package com.beetech.trainningJava.aspect;

import com.beetech.trainningJava.aspect.interfaces.IIntroduction;
import com.beetech.trainningJava.aspect.interfaces.imp.IntroductionImp;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Aspect này dùng để log các hàm được gọi
 */
@Aspect
@Component
public class LoggingAspect {
    // Khai báo các pointcut
    @Pointcut("execution(* com.beetech.trainningJava.controller.mvc.user.*.*(..))")
    public void mvcUserExecution() {
    }

    @Pointcut("within(com.beetech.trainningJava.controller.mvc..*)") // == w
    public void mvcUserWithin() {
    }

    @Pointcut("@within(com.beetech.trainningJava.aspect.annotation.Loggable)")
    public void loggableWithinAnnotation() {
    }

    @Pointcut("target(com.beetech.trainningJava.service.imp.ProductServiceImp)")
    public void productServiceTarget() {
    }

    @Pointcut("@target(com.beetech.trainningJava.aspect.annotation.Loggable) && within(com.beetech.trainningJava.controller..*)")
    public void loggableTargetAnnotation() {
    }

    @Pointcut("this(com.beetech.trainningJava.service.ICartProductService)")
    public void cartProductServiceThis() {
    }

    @Pointcut("@annotation(com.beetech.trainningJava.aspect.annotation.Loggable)")
    public void loggableAnnotation() {
    }

    @Pointcut("bean(*ServiceImp)")
    public void beanServiceImp() {
    }

    @Pointcut("args(Integer) && within(com.beetech.trainningJava.service..*)")
    public void argsWithIntegerSingleParamOfServiceFunction() {
    }

//    @Pointcut("@args(com.beetech.trainningJava.aspect.annotation.Loggable)")
//    public void loggableArgsAnnotation() {
//    }

    // Khai báo các pointcut dùng chung
    @Pointcut(value = "mvcUserExecution() || mvcUserWithin()" // Pointcut
            + "|| cartProductServiceThis()" // this
            + "|| productServiceTarget()" // target
            + "|| loggableWithinAnnotation()" // @within productController
            + "|| loggableAnnotation()" // @annotation product information
//            + "|| beanServiceImp()"// bean
//            + "|| loggableArgsAnnotation()" // @args
            + "|| argsWithIntegerSingleParamOfServiceFunction()"// args
            + "|| loggableTargetAnnotation()" // @target
    )
    public void logAllPcD() {
    }

    // Before
    @Before(value = "logAllPcD()")
    public void logControllerAccessBefore(JoinPoint joinPoint) {
        System.out.println("Before " + "\n" +
                "this: " + joinPoint.getThis() + "\n" +
                "target: " + joinPoint.getTarget() + "\n" +
                "signature: " + joinPoint.getSignature().getName() + "\n" +
                "kind: " + joinPoint.getKind() + "\n" +
                "sourceLocation: " + joinPoint.getSourceLocation() + "\n" +
                "staticPart: " + joinPoint.getStaticPart() + "\n" +
                "args: " + Arrays.toString(joinPoint.getArgs()) + "\n" +
                "-----------------------------------------------"
        );
    }

    // After
    @After(value = "logAllPcD()")
    public void logControllerAccessAfter(JoinPoint joinPoint) {
        System.out.println("After " + "\n" +
                "this: " + joinPoint.getThis() + "\n" +
                "target: " + joinPoint.getTarget() + "\n" +
                "signature: " + joinPoint.getSignature().getName() + "\n" +
                "kind: " + joinPoint.getKind() + "\n" +
                "sourceLocation: " + joinPoint.getSourceLocation() + "\n" +
                "staticPart: " + joinPoint.getStaticPart() + "\n" +
                "args: " + Arrays.toString(joinPoint.getArgs()) + "\n" +
                "-----------------------------------------------"
        );
    }

    // Around
    @Around(value = "logAllPcD()")
    public Object logControllerAccessAround(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("Before Around " + "\n" +
                "this: " + pjp.getThis() + "\n" +
                "target: " + pjp.getTarget() + "\n" +
                "signature: " + pjp.getSignature().getName() + "\n" +
                "kind: " + pjp.getKind() + "\n" +
                "sourceLocation: " + pjp.getSourceLocation() + "\n" +
                "staticPart: " + pjp.getStaticPart() + "\n" +
                "args: " + Arrays.toString(pjp.getArgs()) + "\n" +
                "-----------------------------------------------"
        );
        Object value = null;
        try {
            value = pjp.proceed();
        } catch (Exception e) {
            System.out.println("Around Exception " + e.getMessage());
        }
        System.out.println("After Around " + pjp.getSignature().getName() + ". Return value=" + value);
        return value;
    }

    // AfterReturning
    @AfterReturning(value = "logAllPcD()", returning = "value")
    public Object logControllerAccessAfterReturning(JoinPoint joinPoint, Object value) {
        System.out.println("After Returning " +
                "this: " + joinPoint.getThis() + "\n" +
                "target: " + joinPoint.getTarget() + "\n" +
                "signature: " + joinPoint.getSignature().getName() + "\n" +
                "kind: " + joinPoint.getKind() + "\n" +
                "sourceLocation: " + joinPoint.getSourceLocation() + "\n" +
                "staticPart: " + joinPoint.getStaticPart() + "\n" +
                "args: " + Arrays.toString(joinPoint.getArgs()) + "\n" +
                ". Return value=" + value + "\n" +
                "-----------------------------------------------"
        );
        return value;
    }

    // AfterThrowing
    @AfterThrowing(value = "logAllPcD()", throwing = "value")
    public void logControllerAccessAfterThrowing(JoinPoint joinPoint, Exception value) {
        System.out.println("After Throwing " +
                "this: " + joinPoint.getThis() + "\n" +
                "target: " + joinPoint.getTarget() + "\n" +
                "signature: " + joinPoint.getSignature().getName() + "\n" +
                "kind: " + joinPoint.getKind() + "\n" +
                "sourceLocation: " + joinPoint.getSourceLocation() + "\n" +
                "staticPart: " + joinPoint.getStaticPart() + "\n" +
                "args: " + Arrays.toString(joinPoint.getArgs()) + "\n" +
                ". Throwing value=" + value + "\n" +
                "-----------------------------------------------"
        );
    }

    // Declare parents
    @DeclareParents(value = "com.beetech.trainningJava.controller.mvc.user.CartController", defaultImpl = IntroductionImp.class)
    public static IIntroduction introduction;

    @Before(value = "this(com.beetech.trainningJava.aspect.interfaces.IIntroduction))", argNames = "joinPoint")
    public void introductionThis(JoinPoint joinPoint) {
        System.out.println("Before " + "\n" +
//                "this: " + joinPoint.getThis() + "\n" +
//                "target: " + joinPoint.getTarget() + "\n" +
                        "signature: " + joinPoint.getSignature().getName() + "\n" +
                        "kind: " + joinPoint.getKind() + "\n" +
                        "sourceLocation: " + joinPoint.getSourceLocation() + "\n" +
                        "staticPart: " + joinPoint.getStaticPart() + "\n" +
                        "args: " + Arrays.toString(joinPoint.getArgs()) + "\n" +
                        "-----------------------------------------------"
        );
    }
}
