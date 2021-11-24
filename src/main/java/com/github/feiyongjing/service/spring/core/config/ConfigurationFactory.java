package com.github.feiyongjing.service.spring.core.config;

public class ConfigurationFactory {
    public static Configuration getDefaultConfiguration() {
        return SingleConfigurationHolder.INSTANCE;
    }

    private static class SingleConfigurationHolder {

        private static final Configuration INSTANCE = buildConfiguration();

        private static Configuration buildConfiguration() {
            return new DefaultConfiguration();
        }
    }
}
