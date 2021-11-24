package com.github.feiyongjing.service.spring.annotation.config;

import com.github.feiyongjing.service.spring.annotation.ioc.Component;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface Configuration {
}
