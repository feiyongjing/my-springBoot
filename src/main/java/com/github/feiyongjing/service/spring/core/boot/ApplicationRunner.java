package com.github.feiyongjing.service.spring.core.boot;

@FunctionalInterface
public interface ApplicationRunner {

    /**
     * After initialization is complete, perform some things
     */
    void run();
}

