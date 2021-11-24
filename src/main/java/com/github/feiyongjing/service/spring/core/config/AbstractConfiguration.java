package com.github.feiyongjing.service.spring.core.config;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AbstractConfiguration implements Configuration {
    /**
     * key是存放全部配置文件中内容的key
     * value是存放全部配置文件内容的value
     * 该缓存会存放到ConfigurationManager这个Bean中管理
     */
    private static final Map<String, String> CONFIGURATION_CACHE = new ConcurrentHashMap<>(64);
    @Override
    public int getInt(String id) {
        return Integer.parseInt(CONFIGURATION_CACHE.get(id));
    }

    @Override
    public String getString(String id) {
        return CONFIGURATION_CACHE.get(id);
    }

    @Override
    public Boolean getBoolean(String id) {
        return Boolean.parseBoolean(CONFIGURATION_CACHE.get(id));
    }

    @Override
    public void put(String id, String content) {
        CONFIGURATION_CACHE.put(id, content);
    }

    @Override
    public void putAll(Map<String, String> maps) {
        CONFIGURATION_CACHE.putAll(maps);
    }


}
