package com.github.feiyongjing.service.spring.dome.aop.service;

import com.github.feiyongjing.service.spring.annotation.ioc.Service;

@Service
public class Service4 {
    public int go4(){
//        System.out.println("执行go4方法失败，抛出异常");
        throw new RuntimeException("执行go4方法失败，抛出异常");
    }
}
