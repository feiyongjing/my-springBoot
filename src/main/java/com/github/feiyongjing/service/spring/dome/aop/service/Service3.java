package com.github.feiyongjing.service.spring.dome.aop.service;

import com.github.feiyongjing.service.spring.annotation.ioc.Service;

@Service
public class Service3 {

    public int go3(){
        System.out.println("执行go3方法");
        return 100;
    }
}
