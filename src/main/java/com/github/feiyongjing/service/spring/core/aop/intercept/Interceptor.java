package com.github.feiyongjing.service.spring.core.aop.intercept;

public abstract class Interceptor {
    private int order = -1;

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    /**
     * 判断传入的bean是否需要进行动态代理
     */
    public boolean supports(Object bean) {
        return false;
    }

    /**
     * 方法被拦截器拦截后动态代理进入这里
     * @param methodInvocation 方法引用，参数对象内部包含方法的调用者、方法自己和方法实参
     * @return 方法调用结果
     */
    public abstract Object intercept(MethodInvocation methodInvocation);
}
