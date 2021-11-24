package com.github.feiyongjing.service.spring.factory;

public interface BeanFactory {

    /**
     * 根据名字或者别名获取Bean
     * @param name Bean的名字或者是别名
     * @return Bean
     */
    Object getBean(String name);

    /**
     * 获取对应名字并且继承或者实现了限制类的Bean
     * @param name Bean的名字或者是别名
     * @param requiredType 限制类
     * @return Bean
     */
    <T> T getBean(String name, Class<T> requiredType);

    /**
     * 只在创建Bean时使用，而不是获取Bean时使用
     * 根据名字或者别名、以及指定Bean的构造参数或者工厂方法参数获取Bean
     * @param name Bean的名字或者是别名
     * @param args Bean的构造参数或者工厂方法参数
     * @return Bean
     */
    Object getBean(String name, Object... args);

    /**
     * 返回与给定对象类型(如果有的话)唯一匹配的bean实例。
     * @param requiredType 限制类
     * @return Bean
     */
    <T> T getBean(Class<T> requiredType);

    /**
     * Bean工厂是否包含指定名称的Bean
     * @param name Bean的名字或者是别名
     * @return 包含指定名称的Bean返回true
     */
    boolean containsBean(String name);

    /**
     * 该名称的Bean是否是单例的
     * @param name Bean的名字或者是别名
     * @return 是单例的Bean返回true，返回false不一定是原型多例Bean
     */
    boolean isSingleton(String name);

    /**
     * 该名称的Bean是否是原型多例的
     * @param name Bean的名字或者是别名
     * @return 是原型多例的Bean返回true，返回false不一定是单例Bean
     */
    boolean isPrototype(String name);

    /**
     * 检查具有指定名称的bean是否与指定的类型匹配。
     * @param name Bean的名字或者是别名
     * @param typeToMatch 指定的类型
     * @return 指定名称的bean与指定的类型匹配返回true
     */
    boolean isTypeMatch(String name, Class<?> typeToMatch);





}
