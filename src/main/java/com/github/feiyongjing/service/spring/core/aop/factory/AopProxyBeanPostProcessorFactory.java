package com.github.feiyongjing.service.spring.core.aop.factory;

import com.github.feiyongjing.service.spring.core.aop.intercept.BeanPostProcessor;
import com.github.feiyongjing.service.spring.core.aop.intercept.CglibAopProxyBeanPostProcessor;
import com.github.feiyongjing.service.spring.core.aop.intercept.JdkAopProxyBeanPostProcessor;

public class AopProxyBeanPostProcessorFactory {

    /**
     * @param beanClass 目标类
     * @return beanClass 实现了接口就返回jdk动态代理bean后置处理器,否则返回 cglib动态代理bean后置处理器
     */
    public static BeanPostProcessor get(Class<?> beanClass) {
        if (beanClass.isInterface() || beanClass.getInterfaces().length > 0) {
            return new JdkAopProxyBeanPostProcessor();
        } else {
            return new CglibAopProxyBeanPostProcessor();
        }
    }

}
