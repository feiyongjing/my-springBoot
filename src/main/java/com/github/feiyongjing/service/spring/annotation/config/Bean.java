package com.github.feiyongjing.service.spring.annotation.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Bean {
    /**
     * bean的名字，可以有多个名字
     * 如果不写默认是被标注的方法名字
     * @return
     */
    String[] value() default {};
}
