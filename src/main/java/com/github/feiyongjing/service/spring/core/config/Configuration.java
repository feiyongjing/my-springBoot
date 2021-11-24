package com.github.feiyongjing.service.spring.core.config;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public interface Configuration {

    String[] DEFAULT_CONFIG_NAMES = {"application.properties", "application.yaml"};

    int getInt(String id);

    String getString(String id);

    Boolean getBoolean(String id);

    default void put(String id, String content) {
    }

    default void putAll(Map<String, String> maps) {
    }

    /**
     * 加载resources中的资源文件
     * @param resourcePaths 资源文件路径
     */
    default void loadResources(List<Path> resourcePaths) {
    }
}
