package com.github.feiyongjing.service.spring.factory;

import com.github.feiyongjing.service.spring.annotation.Controller;
import com.github.feiyongjing.service.spring.annotation.aop.Aspect;
import com.github.feiyongjing.service.spring.common.ReflectionUtil;
import com.github.feiyongjing.service.spring.annotation.ioc.Component;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ClassFactory {
    /**
     * key是注解类
     * value是标注了该注解的class集合
     */
    public static final Map<Class<? extends Annotation>, Set<Class<?>>> CLASSES = new ConcurrentHashMap<>();
    public static void loadClass(String[] packageNames) {

        Set<Class<?>> restControllers = ReflectionUtil.scanAnnotatedClass(packageNames, Controller.class);
        Set<Class<?>> components = ReflectionUtil.scanAnnotatedClass(packageNames, Component.class);
        Set<Class<?>> aspects = ReflectionUtil.scanAnnotatedClass(packageNames, Aspect.class);
        CLASSES.put(Controller.class, restControllers);
        CLASSES.put(Component.class, components);
        CLASSES.put(Aspect.class, aspects);
    }

}
