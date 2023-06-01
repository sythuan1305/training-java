package com.beetech.trainningJava.aspect;

import com.beetech.trainningJava.aspect.interfaces.IIntroduction;
import com.beetech.trainningJava.aspect.interfaces.imp.IntroductionImp;
import com.beetech.trainningJava.utils.Utils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Aspect này dùng để log các hàm được gọi
 */
@Aspect
@Component
public class LoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    // Khai báo các pointcut
    @Pointcut("execution(* com.beetech.trainningJava.service.I*.*(..))")
    public void serviceExecution() {
    }

    @Pointcut("within(com.beetech.trainningJava.controller.*..*)")
    public void controllerWithin() {
    }

    @Pointcut("@within(com.beetech.trainningJava.aspect.annotation.Loggable)")
    public void loggableWithinAnnotation() {
    }

    @Pointcut("target(com.beetech.trainningJava.controller.HomeController)")
    public void homeControllerTarget() {
    }

    @Pointcut("@target(com.beetech.trainningJava.aspect.annotation.Loggable) && within(com.beetech.trainningJava.controller.HomeController)")
    public void loggableTargetAnnotationPcDAndHomeControllerWithinPcd() {
    }

    @Pointcut("this(com.beetech.trainningJava.controller.HomeController)")
    public void homeControllerThisPcD() {
    }

    @Pointcut("@annotation(com.beetech.trainningJava.aspect.annotation.Loggable)")
    public void loggableAnnotation() {
    }

    @Pointcut("bean(Ho*Controller)")
    public void homeControllerBeanServiceImp() {
    }

    @Pointcut("args(String) && within(com.beetech.trainningJava.controller.HomeController)")
    public void argsAndHomeControllerWithin() {
    }

    @Pointcut("@annotation(com.beetech.trainningJava.aspect.annotation.LogMemoryAndCpu)")
    public void logMemoryAndCpuAnnotation() {
    }

//    @Pointcut("@args(com.beetech.trainningJava.aspect.annotation.Loggable)")
//    public void loggableArgsAnnotation() {
//    }

    // Khai báo các pointcut dùng chung
    @Pointcut(value = ""
//          +  "serviceExecution() || controllerWithin()" // Pointcut
//            + "homeControllerThisPcD()" // this
//            + "|| homeControllerTarget()" // target
//            + "|| loggableWithinAnnotation()" // @within productController
            + "loggableAnnotation()" // @annotation product information
////            + "|| beanServiceImp()"// bean
////            + "|| loggableArgsAnnotation()" // @args
//            + "|| argsAndHomeControllerWithin()"// args
//            + "|| loggableTargetAnnotationPcDAndHomeControllerWithinPcd()" + // @target
            + ""
    )
    public void logAllPcD() {
    }

    // Before
    @Before(value = "logAllPcD()")
    public void logControllerAccessBefore(JoinPoint joinPoint) {
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

    // After
    @After(value = "logAllPcD()")
    public void logControllerAccessAfter(JoinPoint joinPoint) {
        System.out.println("After " + "\n" +
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

    // Around
    @Around(value = "logAllPcD()")
    public Object logControllerAccessAround(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("Before Around " + "\n" +
//                "this: " + pjp.getThis() + "\n" +
//                "target: " + pjp.getTarget() + "\n" +
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
        System.out.println("-------------------------------------------------");
        return value;
    }

    // AfterReturning
    @AfterReturning(value = "logAllPcD()", returning = "value")
    public Object logControllerAccessAfterReturning(JoinPoint joinPoint, Object value) {
        System.out.println("After Returning " +
//                "this: " + joinPoint.getThis() + "\n" +
//                "target: " + joinPoint.getTarget() + "\n" +
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
//                "this: " + joinPoint.getThis() + "\n" +
//                "target: " + joinPoint.getTarget() + "\n" +
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
    @DeclareParents(value = "com.beetech.trainningJava.controller.HomeController", defaultImpl = IntroductionImp.class)
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

//    @Around("execution(* com.beetech.trainningJava.service.imp.*.*(..))")
    public Object logMemoryAndCpuAnnotation(ProceedingJoinPoint pjp) {
        System.out.println("Before Around " + "\n" +
                "signature: " + pjp.getSignature().getName() + "\n" );
        // Get start time
        long startTime = System.currentTimeMillis();
        // Get start memory
        long startMemory = Utils.memoryUsed();
        Object value = null;
        try {
            value = pjp.proceed();
            // Get end time
            long endTime = System.currentTimeMillis();
            // Get end memory
            long endMemory = Utils.memoryUsed();
            // Print log
            logger.info("Time: " + (endTime - startTime) + " ms");
            logger.info("Memory: " + (startMemory - endMemory) + " bytes");
            System.out.println("Time: " + (endTime - startTime) + " ms");
            System.out.println("Memory used: " + (endMemory - startMemory) + " bytes");
//            Utils.performGC();
        } catch (Throwable e) {
            System.out.println("Around Exception " + e.getMessage());
        }
        System.out.println("-------------------------------------------------");
        return value;
    }

}
