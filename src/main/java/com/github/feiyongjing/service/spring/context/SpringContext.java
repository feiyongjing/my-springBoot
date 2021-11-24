package com.github.feiyongjing.service.spring.context;

import java.util.Map;

public interface SpringContext {

    Object getBean(String beanName);
    Object getBean(Class<?> clazz);

    void setBean(String beanName, Object bean);
    void setBean(Class<?> clazz, Object bean);

    void dispose();

    Map<String, Object> getIOCcontext();
}
