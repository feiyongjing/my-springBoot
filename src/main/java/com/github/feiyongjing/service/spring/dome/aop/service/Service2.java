package com.github.feiyongjing.service.spring.dome.aop.service;

import com.github.feiyongjing.service.spring.annotation.ioc.Component;
import com.github.feiyongjing.service.spring.dome.aop.annotation.Log;

@Component
public class Service2 {

    @Log
    public int go2(){
        System.out.println("执行go2方法");
        return 100;
    }
}
