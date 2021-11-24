package com.github.feiyongjing.service.spring.dome.aop.aspect;

import com.github.feiyongjing.service.spring.annotation.aop.*;
import com.github.feiyongjing.service.spring.core.aop.lang.JoinPoint;
import com.github.feiyongjing.service.spring.core.aop.lang.MethodInvocationProceedingJoinPoint;

@Aspect
public class AbcAspect5 {
    @Pointcut("com.github.feiyongjing.service.spring.dome.aop.service.*3*")
    public void oneAspect3() {
    }

    /**
     * 测试切点表达式Around拦截
     */
    @Around("oneAspect3()")
    public void aroundAction31(MethodInvocationProceedingJoinPoint joinPoint) {
        System.out.println("Service5的go3方法执行前Around拦截成功");
        joinPoint.proceed();
        System.out.println("Service5的go3方法执行后Around拦截成功");
    }

    /**
     * 测试切点表达式Around中Before拦截
     */
    @Before("oneAspect3()")
    public void aroundAction32(JoinPoint joinPoint) {
        System.out.println("Service5的go3方法执行前Before拦截成功");
    }

    /**
     * 测试切点表达式Around后After拦截
     */
    @After("oneAspect3()")
    public void aroundAction33(JoinPoint joinPoint) {
        System.out.println("Service5的go3方法执行后After拦截成功");
    }

    /**
     * 测试切点表达式Around后AfterReturning拦截
     */
    @AfterReturning("oneAspect3()")
    public void aroundAction34(JoinPoint joinPoint) {
        System.out.println("Service5的go3方法执行后AfterReturning拦截成功");
    }

}
