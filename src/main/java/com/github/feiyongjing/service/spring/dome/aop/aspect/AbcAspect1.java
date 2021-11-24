package com.github.feiyongjing.service.spring.dome.aop.aspect;

import com.github.feiyongjing.service.spring.annotation.aop.After;
import com.github.feiyongjing.service.spring.annotation.aop.Aspect;
import com.github.feiyongjing.service.spring.annotation.aop.Before;
import com.github.feiyongjing.service.spring.annotation.aop.Pointcut;
import com.github.feiyongjing.service.spring.core.aop.lang.JoinPoint;

@Aspect
public class AbcAspect1 {
    @Pointcut("com.github.feiyongjing.service.spring.ioc.dome.aop.service.*1*")
    public void oneAspect1() {
    }

    /**
     * 测试切点表达式Before拦截
     */
    @Before("oneAspect1()")
    public void beforeAction1(JoinPoint joinPoint) {
        System.out.println("Service1的go1方法执行前拦截成功1");
    }

    /**
     * 测试切点表达式Before拦截
     */
    @Before("oneAspect1()")
    public void beforeAction11(JoinPoint joinPoint) {
        System.out.println("Service1的go1方法执行前拦截成功2");
    }

    /**
     * 测试切点表达式After拦截
     */
    @After("oneAspect1()")
    public void afterAction1(JoinPoint joinPoint) {
        System.out.println("Service1的go1方法执行后拦截成功1");
    }

    /**
     * 测试切点表达式After拦截
     */
    @After("oneAspect1()")
    public void afterAction11(JoinPoint joinPoint) {
        System.out.println("Service1的go1方法执行后拦截成功2");
    }





}
