package com.github.feiyongjing.service.spring.core.config.resources;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

public interface ResourceLoader {
    /**
     * 加载resource资源
     * @param path 资源路径
     * @return
     * @throws IOException
     */
    Map<String, String> loadResource(Path path) throws IOException;
}
