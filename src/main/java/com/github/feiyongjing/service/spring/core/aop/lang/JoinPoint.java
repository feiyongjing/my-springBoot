package com.github.feiyongjing.service.spring.core.aop.lang;

public interface JoinPoint {
    /**
     * 切面Bean
     */
    Object getAdviceBean();

    /**
     * 被拦截的Bean
     */
    Object getTarget();

    /**
     * 拷贝方法拦截时的参数并返回
     */
    Object[] getArgs();
}
