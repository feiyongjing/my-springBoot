package com.github.feiyongjing.service.spring.dome.aop.aspect;

import com.github.feiyongjing.service.spring.annotation.aop.*;
import com.github.feiyongjing.service.spring.core.aop.lang.JoinPoint;
import com.github.feiyongjing.service.spring.core.aop.lang.MethodInvocationProceedingJoinPoint;

@Aspect
public class AbcAspect4 {
    @Pointcut("com.github.feiyongjing.service.spring.dome.aop.service.*4*")
    public void oneAspect4() {
    }

    /**
     * 测试切点表达式Around拦截
     */
    @Around("oneAspect4()")
    public void aroundAction41(MethodInvocationProceedingJoinPoint joinPoint) {
        System.out.println("Service4的go4方法执行前Around拦截成功");
        joinPoint.proceed();
        System.out.println("Service4的go4方法执行后Around拦截成功");
    }

    /**
     * 测试切点表达式Around中Before拦截
     */
    @Before("oneAspect4()")
    public void aroundAction42(JoinPoint joinPoint) {
        System.out.println("Service4的go4方法执行前Before拦截成功");
    }

    /**
     * 测试切点表达式Around后After拦截
     */
    @After("oneAspect4()")
    public void aroundAction43(JoinPoint joinPoint) {
        System.out.println("Service4的go4方法执行后After拦截成功");
    }

    /**
     * 测试切点表达式Around后AfterThrowing拦截
     */
    @AfterThrowing("oneAspect4()")
    public void aroundAction44(JoinPoint joinPoint,Throwable throwable) {
        System.out.println("Service4的go4方法执行后AfterThrowing拦截成功");
    }
}
