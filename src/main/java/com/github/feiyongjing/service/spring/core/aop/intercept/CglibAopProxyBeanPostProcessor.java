package com.github.feiyongjing.service.spring.core.aop.intercept;

import com.github.feiyongjing.service.spring.core.aop.proxy.CglibAspectProxy;

public class CglibAopProxyBeanPostProcessor extends AbstractAopProxyBeanPostProcessor {
    /**
     * 根据interceptor拦截器对target进行cglib的动态代理，返回动态代理对象
     * @param target 原有的bean对象
     * @param interceptor 对应的拦截器
     * @return 返回动态代理对象
     */
    @Override
    public Object wrapBean(Object target, Interceptor interceptor) {
        return CglibAspectProxy.wrap(target, interceptor);
    }
}
