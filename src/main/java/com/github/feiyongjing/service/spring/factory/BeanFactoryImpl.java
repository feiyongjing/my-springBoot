package com.github.feiyongjing.service.spring.factory;

import com.github.feiyongjing.service.spring.context.ApplicationContext;
import com.github.feiyongjing.service.spring.context.SpringContext;
import com.github.feiyongjing.service.spring.core.config.ConfigurationFactory;
import com.github.feiyongjing.service.spring.core.config.ConfigurationManager;
import com.github.feiyongjing.service.spring.annotation.ioc.Component;
import com.github.feiyongjing.service.spring.core.aop.factory.AopProxyBeanPostProcessorFactory;
import com.github.feiyongjing.service.spring.core.aop.intercept.BeanPostProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BeanFactoryImpl {
    public static void loadBeans(SpringContext context) {
        initContext(context);
    }

    private static void initContext(SpringContext context) {
        for (Class<?> clazz : ClassFactory.CLASSES.get(Component.class)) {
            String beanName = getBeanName(clazz);
            Object bean = newBeanInstance(clazz);
            context.setBean(beanName, bean);
        }
        // 创建读取和管理resources资源文件的Bean
        context.getIOCcontext().put(ConfigurationManager.class.getName(), new ConfigurationManager(ConfigurationFactory.getDefaultConfiguration()));
    }

    private static Object newBeanInstance(Class<?> clazz) {
        try {
            return clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    /**
     * 获取bean的名字
     *
     * @param clazz
     * @return 默认是参数clazz的全限定类名，如果有自定义就返回自定义的名字
     */
    public static String getBeanName(Class<?> clazz) {
        String beanName = clazz.getName();
        if (clazz.isAnnotationPresent(Component.class)) {
            Component component = clazz.getAnnotation(Component.class);
            beanName = component.name().equals("") ? clazz.getName() : component.name();
        }
        return beanName;
    }

    public static <T> T getBean(Class<T> clazz,SpringContext context) {
        String[] beanNames = getBeanNamesForType(clazz,(ApplicationContext) context);
        if (beanNames.length == 0) {
            throw new RuntimeException("not fount bean implement，the bean :" + clazz.getName());
        }
        Object beanInstance = context.getIOCcontext().get(beanNames[0]);
        if (!clazz.isInstance(beanInstance)) {
            throw new RuntimeException("not fount bean implement，the bean :" + clazz.getName());
        }
        return clazz.cast(beanInstance);
    }

    private static String[] getBeanNamesForType(Class<?> type,ApplicationContext context) {
        String beanName = type.getName();
        String[] beanNames = context.getSINGLE_BEAN_NAMES_TYPE_MAP().get(beanName);
        if (beanNames == null) {
            List<String> beanNamesList = new ArrayList<>();
            for (Map.Entry<String, Object> beanEntry : context.getIOCcontext().entrySet()) {
                Class<?> beanClass = beanEntry.getValue().getClass();
                if (type.isInterface()) {
                    Class<?>[] interfaces = beanClass.getInterfaces();
                    for (Class<?> c : interfaces) {
                        if (type.getName().equals(c.getName())) {
                            beanNamesList.add(beanEntry.getKey());
                            break;
                        }
                    }
                } else if (beanClass.isAssignableFrom(type)) {
                    beanNamesList.add(beanEntry.getKey());
                }
            }
            beanNames = beanNamesList.toArray(new String[0]);
            context.getSINGLE_BEAN_NAMES_TYPE_MAP().put(beanName, beanNames);
        }
        return beanNames;
    }

    public static void applyBeanPostProcessors(SpringContext context) {
        Map<String, Object> IOCcontext = context.getIOCcontext();
        for (String beanName : IOCcontext.keySet()) {
            Object beanInstance = IOCcontext.get(beanName);
            BeanPostProcessor beanPostProcessor = AopProxyBeanPostProcessorFactory.get(beanInstance.getClass());
            beanInstance = beanPostProcessor.postProcessAfterInitialization(beanInstance);
            IOCcontext.put(beanName,beanInstance);
        }
    }
}
