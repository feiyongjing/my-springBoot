package com.github.feiyongjing.service.spring.dome;

import com.github.feiyongjing.service.spring.annotation.ioc.Component;
import com.github.feiyongjing.service.spring.context.SpringContext;
import com.github.feiyongjing.service.spring.scan.ScanComponent;

@Component
public class Application {
    public static void main(String[] args) {
        SpringContext applicationContext = ScanComponent.run(Application.class, args);
//        ((Service1)(applicationContext.getIOCcontext()
//                .get("com.github.feiyongjing.service.spring.ioc.dome.aop.service.Service1")))
//                .go1();
//        ((Service2)(applicationContext.getIOCcontext()
//                .get("com.github.feiyongjing.service.spring.ioc.dome.aop.service.Service2")))
//                .go2();
//        ((Service3)(applicationContext.getIOCcontext()
//                .get("com.github.feiyongjing.service.spring.ioc.dome.aop.service.Service3")))
//                .go3();
//        ((Service4)(applicationContext.getIOCcontext()
//                .get("com.github.feiyongjing.service.spring.ioc.dome.aop.service.Service4")))
//                .go4();
    }

}
