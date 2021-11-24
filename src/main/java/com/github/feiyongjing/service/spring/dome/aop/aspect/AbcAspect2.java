package com.github.feiyongjing.service.spring.dome.aop.aspect;

import com.github.feiyongjing.service.spring.annotation.aop.After;
import com.github.feiyongjing.service.spring.annotation.aop.Aspect;
import com.github.feiyongjing.service.spring.annotation.aop.Before;
import com.github.feiyongjing.service.spring.annotation.aop.Pointcut;
import com.github.feiyongjing.service.spring.core.aop.lang.JoinPoint;

@Aspect
public class AbcAspect2 {
    @Pointcut("com.github.feiyongjing.service.spring.dome.aop.service.*2*")
    public void oneAspect2() {
    }

    /**
     * 测试注解Before拦截
     */
    @Before("@annotation(com.github.feiyongjing.service.spring.dome.aop.annotation.Log)")
    public void beforeAction2(JoinPoint joinPoint) {
        System.out.println("Service2的go2方法执行前拦截成功");
    }

    /**
     * 测试注解After拦截
     */
    @After("@annotation(com.github.feiyongjing.service.spring.dome.aop.annotation.Log)")
    public void aftereAction2(JoinPoint joinPoint) {
        System.out.println("Service2的go2方法执行后拦截成功");
    }
}
