//package com.appreciate.educationweb.student.aop;
//
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.springframework.stereotype.Component;
//
//import java.util.Arrays;
//
//@Aspect
//@Component
//public class LoggingAspect {
//
//    @Around("execution(* com.appreciate.educationweb.student.controller.StudentRestController.getAll(..))")
//    public Object logInputOutput(ProceedingJoinPoint joinPoint) throws Throwable {
//        Object[] args = joinPoint.getArgs();
//        System.out.println("INPUTS: " + Arrays.toString(args));
//
//        Object result = joinPoint.proceed();
//
//        System.out.println("OUTPUT: " + result);
//
//        return result;
//    }
//
//}
