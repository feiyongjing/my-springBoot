package com.github.feiyongjing.service.spring.core.aop.lang;

import com.github.feiyongjing.service.spring.common.ReflectionUtil;
import com.github.feiyongjing.service.spring.core.aop.intercept.MethodInvocation;

import java.lang.reflect.Method;
import java.util.List;

public class MethodInvocationProceedingJoinPoint implements JoinPoint {
    private final Object adviceBean;
    private MethodInvocation methodInvocation;
    private List<Method> beforeJoinPointMethods;
    private List<Method> afterJoinPointMethods;

    public MethodInvocationProceedingJoinPoint(Object adviceBean, MethodInvocation methodInvocation, List<Method> beforeJoinPointMethods, List<Method> afterJoinPointMethods) {
        this.adviceBean = adviceBean;
        this.methodInvocation = methodInvocation;
        this.beforeJoinPointMethods = beforeJoinPointMethods;
        this.afterJoinPointMethods = afterJoinPointMethods;
    }

    /**
     * 被标注@Around的切面方法调用，用于调用被拦截的原方法
     * @return 被拦截的原方法的结果
     */
    public Object proceed() {
        JoinPoint joinPoint = new JoinPointImpl(adviceBean, methodInvocation.getTargetObject(),
                methodInvocation.getArgs());
        try {
            beforeJoinPointMethods
                    .forEach(method -> ReflectionUtil.executeTargetMethod(adviceBean, method, joinPoint));
            return methodInvocation.proceed();
        } catch (Throwable e) {
            afterJoinPointMethods
                    .forEach(method -> ReflectionUtil.executeTargetMethod(adviceBean, method, joinPoint));
            throw e;
        }
    }

    @Override
    public Object getAdviceBean() {
        return adviceBean;
    }

    @Override
    public Object getTarget() {
        return methodInvocation.getTargetObject();
    }

    @Override
    public Object[] getArgs() {
        return methodInvocation.getArgs();
    }

    public MethodInvocation getMethodInvocation() {
        return methodInvocation;
    }

    public void setMethodInvocation(MethodInvocation methodInvocation) {
        this.methodInvocation = methodInvocation;
    }

    public List<Method> getBeforeJoinPointMethods() {
        return beforeJoinPointMethods;
    }

    public void setBeforeJoinPointMethods(List<Method> beforeJoinPointMethods) {
        this.beforeJoinPointMethods = beforeJoinPointMethods;
    }
}
