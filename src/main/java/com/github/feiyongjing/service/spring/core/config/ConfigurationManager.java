package com.github.feiyongjing.service.spring.core.config;

import com.github.feiyongjing.service.spring.core.config.resources.property.PropertiesResourceLoader;
import com.github.feiyongjing.service.spring.core.config.resources.ResourceLoader;
import com.github.feiyongjing.service.spring.core.config.resources.yaml.YamlResourceLoader;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class ConfigurationManager implements Configuration {
    private static final String PROPERTIES_FILE_EXTENSION = ".properties";

    private static final String YAML_FILE_EXTENSION = ".yaml";

    private static final String YML_FILE_EXTENSION = ".yaml";


    private final Configuration configuration;

    public ConfigurationManager(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public int getInt(String id) {
        return configuration.getInt(id);
    }

    @Override
    public String getString(String id) {
        return configuration.getString(id);
    }

    @Override
    public Boolean getBoolean(String id) {
        return configuration.getBoolean(id);
    }

    /**
     * 按照资源文件的后缀加载不同的资源文件
     * 只支持.properties .yaml .yaml这三种资源文件的加载
     * @param resourcePaths 资源文件路径集合
     */
    @Override
    public void loadResources(List<Path> resourcePaths) {
        try {
            for (Path resourcePath : resourcePaths) {
                String fileName = resourcePath.getFileName().toString();
                if (fileName.endsWith(PROPERTIES_FILE_EXTENSION)) {
                    ResourceLoader resourceLoader = new PropertiesResourceLoader();
                    configuration.putAll(resourceLoader.loadResource(resourcePath));
                } else if (fileName.endsWith(YML_FILE_EXTENSION) || fileName.endsWith(YAML_FILE_EXTENSION)) {
                    ResourceLoader resourceLoader = new YamlResourceLoader();
                    configuration.putAll(resourceLoader.loadResource(resourcePath));
                }
            }
        } catch (IOException ex) {
//            log.error("not load resources.");
//            System.exit(-1);
            throw new RuntimeException("资源加载出错，错误："+ex);
        }
    }
}
