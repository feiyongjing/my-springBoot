package com.github.feiyongjing.service.spring.core.aop.intercept;

import com.github.feiyongjing.service.spring.core.aop.factory.InterceptorFactory;

public abstract class AbstractAopProxyBeanPostProcessor implements BeanPostProcessor {
    /**
     * 获取所有的拦截器，判断bean是否需要拦截代理，如果是就生成代理Bean返回
     */
    @Override
    public Object postProcessAfterInitialization(Object bean) {
        Object wrapperProxyBean = bean;
        //链式包装目标类
        for (Interceptor interceptor : InterceptorFactory.getInterceptors()) {
            if (interceptor.supports(bean)) {
                wrapperProxyBean = wrapBean(wrapperProxyBean, interceptor);
            }
        }
        return wrapperProxyBean;
    }

    /**
     * 根据interceptor拦截器对target进行动态代理，返回动态代理对象
     */
    public abstract Object wrapBean(Object target, Interceptor interceptor);
}
