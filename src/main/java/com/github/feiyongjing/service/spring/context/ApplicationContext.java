package com.github.feiyongjing.service.spring.context;

import com.github.feiyongjing.service.spring.annotation.ioc.Autowired;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ApplicationContext implements SpringContext {
    public static SpringContext springContext;
    Map<String, Object> IOCcontext = new ConcurrentHashMap<>();

    /**
     * key存放class的全限定类名
     * value 如果class是接口就存放IOC容器中bean实现了该接口的全部全限定类名，
     * 否则就存放IOC容器中bean是class父类的全部全限定类名
     */
    Map<String, String[]> SINGLE_BEAN_NAMES_TYPE_MAP = new ConcurrentHashMap<>(128);

    public ApplicationContext() {
        springContext=this;
    }

    public Map<String, Object> getIOCcontext() {
        return IOCcontext;
    }

    public Map<String, String[]> getSINGLE_BEAN_NAMES_TYPE_MAP() {
        return SINGLE_BEAN_NAMES_TYPE_MAP;
    }

    /**
     * 获取IOC容器指定类型的所有beanName到bean的映射
     * @param type 指定类型
     * @return beanName到bean的映射
     */
    public <T> Map<String, T> getBeansOfType(Class<T> type) {
        Map<String, T> result = new HashMap<>();
        String[] beanNames = getBeanNamesForType(type);
        for (String beanName : beanNames) {
            Object beanInstance = IOCcontext.get(beanName);
            if (!type.isInstance(beanInstance)) {
                throw new RuntimeException("not fount bean implement，the bean :" + type.getName());
            }
            result.put(beanName, type.cast(beanInstance));
        }
        return result;

    }

    /**
     * 获取IOC容器指定类型的所有beanName
     * @param type 指定类型
     * @return beanNames
     */
    private String[] getBeanNamesForType(Class<?> type) {
        String beanName = type.getName();
        String[] beanNames = SINGLE_BEAN_NAMES_TYPE_MAP.get(beanName);
        if (beanNames == null) {
            List<String> beanNamesList = new ArrayList<>();
            for (Map.Entry<String, Object> beanEntry : IOCcontext.entrySet()) {
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
            SINGLE_BEAN_NAMES_TYPE_MAP.put(beanName, beanNames);
        }
        return beanNames;
    }

    @Override
    public Object getBean(String beanName) {
        return IOCcontext.get(beanName);
    }

    @Override
    public Object getBean(Class<?> clazz) {
//        if (clazz.isAnnotation()) {
//            new RuntimeException(clazz.getName() + "是注解");
//        }
//        if (Stream.of(clazz.getAnnotations()).anyMatch(annotation -> annotation.equals(Component.class))){
//            new RuntimeException(clazz.getName() + "类不是Spring组件");
//        }
//
//        // 判断是否是原型Bean的获取，是就创建原型Bean
//        if (Stream.of(clazz.getAnnotations()).anyMatch(annotation -> annotation.equals(Scope.class) &&
//                clazz.getAnnotation(Scope.class).scopeName().equals("SCOPE_PROTOTYPE")
//        )) {
//            return newObjectByClass(clazz);
//        }

        // 判断是容器是否有Bean，是就直接返回Bean
        Object bean = IOCcontext.get(clazz.getName());
//        if (bean != null) {
        return bean;
//        }

        // 查找所有符合类型的Bean
//        List<Object> instances = new ArrayList<>();
//        for (Map.Entry<String, Object> entry : IOCcontext.entrySet()) {
//            if (clazz.isInstance(entry.getValue())) {
//                instances.add(entry.getValue());
//            }
//        }
//        // 查找到类型完全一致的Bean就直接返回
//        for (Object instance : instances) {
//            if (clazz.equals(instance.getClass())) {
//                return instance;
//            }
//        }
//        // 找不到完全一致的就找符合目标类的子类返回
//        for (Object instance : instances) {
//            if (clazz.equals(instance.getClass())) {
//                return instance;
//            }
//        }
//        return newObjectByClass(clazz);
    }

    private Object newObjectByClass(Class<?> clazz) {
        Object bean = null;
        // TODO 需要简略try抓住的写法
        try {
            bean = clazz.getConstructor().newInstance();
            for (Field field : clazz.getFields()) {
                if (field.isAnnotationPresent(Autowired.class)) {
                    Object fieldObj = getBean(field.getType());
                    if (fieldObj != null) {
                        field.set(bean, fieldObj);
                    } else {
                        new RuntimeException("创建" + clazz.getName() + "的bean时自动注入" + field.getName() + "字段失败");
                    }
                }
            }
            setBean(clazz, bean);

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return bean;
    }

    @Override
    public void setBean(String beanName, Object bean) {
        IOCcontext.put(beanName, bean);
    }

    @Override
    public void setBean(Class<?> clazz, Object bean) {
        IOCcontext.put(clazz.getSimpleName(), bean);
    }

    @Override
    public void dispose() {
        IOCcontext.clear();
    }
}
