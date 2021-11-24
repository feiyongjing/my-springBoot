package com.github.feiyongjing.service.spring.core.aop.factory;

import com.github.feiyongjing.service.spring.annotation.aop.Aspect;
import com.github.feiyongjing.service.spring.annotation.aop.Order;
import com.github.feiyongjing.service.spring.core.aop.intercept.BeanValidationInterceptor;
import com.github.feiyongjing.service.spring.core.aop.intercept.Interceptor;
import com.github.feiyongjing.service.spring.core.aop.intercept.InternallyAspectInterceptor;
import com.github.feiyongjing.service.spring.common.ReflectionUtil;
import com.github.feiyongjing.service.spring.factory.ClassFactory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class InterceptorFactory {
    /**
     * 存放了所有的拦截器
     */
    private static List<Interceptor> interceptors = new ArrayList<>();

    /**
     * 加载多种拦截器
     * 第一种拦截器：加载的是扫描路径中的Interceptor类和它的子类
     * 第二种拦截器：根据标注了@Aspect的类创建切面实例，然后使用切面实例创建InternallyAspectInterceptor
     * 即在内部切面拦截器
     * 第三种拦截器：BeanValidationInterceptor 即在Bean验证拦截器
     *
     * @param packageName
     */
    public static void loadInterceptors(String[] packageName) {
        // 获取指定包中实现了 Interceptor 接口的类
        Set<Class<?>> interceptorClasses = ReflectionUtil.getSubClass(packageName, Interceptor.class);
        // 获取被 @Aspect 标记的类
        Set<Class<?>> aspects = ClassFactory.CLASSES.get(Aspect.class);
        // 遍历所有拦截器类
        interceptorClasses.forEach(interceptorClass -> {
            try {
                interceptors.add((Interceptor) interceptorClass.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException("not init constructor , the interceptor name :" + interceptorClass.getSimpleName());
            }
        });
        aspects.forEach(aClass -> {
            Object obj = ReflectionUtil.newInstance(aClass);
            Interceptor interceptor = new InternallyAspectInterceptor(obj);
            if (aClass.isAnnotationPresent(Order.class)) {
                Order order = aClass.getAnnotation(Order.class);
                interceptor.setOrder(order.value());
            }
            interceptors.add(interceptor);
        });
        // 添加Bean验证拦截器
        interceptors.add(new BeanValidationInterceptor());
        // 根据 order 为拦截器排序
        interceptors = interceptors.stream().sorted(Comparator.comparing(Interceptor::getOrder)).collect(Collectors.toList());
    }

    /**
     * 获取所有的拦截器
     *
     * @return 所有的拦截器
     */
    public static List<Interceptor> getInterceptors() {
        return interceptors;
    }
}
