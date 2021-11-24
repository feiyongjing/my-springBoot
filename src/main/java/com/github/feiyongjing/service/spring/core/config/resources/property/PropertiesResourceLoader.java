package com.github.feiyongjing.service.spring.core.config.resources.property;

import com.github.feiyongjing.service.spring.core.config.resources.ResourceLoader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertiesResourceLoader implements ResourceLoader {
    /**
     * 加载Properties后缀的资源文件
     * @param path 资源路径
     * @return
     * @throws IOException
     */
    @Override
    public Map<String, String> loadResource(Path path) throws IOException {
        Properties properties = new Properties();
        try (InputStream stream = Files.newInputStream(path); Reader reader = new InputStreamReader(stream)) {
            properties.load(reader);
        }
        Map<String, String> resource = new HashMap<>(properties.size());
        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            resource.put(entry.getKey().toString(), entry.getValue().toString());
        }
        return resource;
    }
}
