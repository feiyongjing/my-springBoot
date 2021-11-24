package com.github.feiyongjing.service.spring.dome.aop.service;

import com.github.feiyongjing.service.spring.annotation.ioc.Component;

@Component
public class Service1 {

    public int go1(){
        System.out.println("执行go1方法");
        return 100;
    }
}
