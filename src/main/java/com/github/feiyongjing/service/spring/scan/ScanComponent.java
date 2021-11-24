package com.github.feiyongjing.service.spring.scan;

import com.github.feiyongjing.service.spring.annotation.ioc.Autowired;
import com.github.feiyongjing.service.spring.annotation.config.Bean;
import com.github.feiyongjing.service.spring.annotation.ComponentScan;
import com.github.feiyongjing.service.spring.annotation.config.Value;
import com.github.feiyongjing.service.spring.common.util.ObjectUtil;
import com.github.feiyongjing.service.spring.context.ApplicationContext;
import com.github.feiyongjing.service.spring.context.SpringContext;
import com.github.feiyongjing.service.spring.core.aop.factory.InterceptorFactory;
import com.github.feiyongjing.service.spring.core.boot.ApplicationRunner;
import com.github.feiyongjing.service.spring.core.config.Configuration;
import com.github.feiyongjing.service.spring.core.config.ConfigurationManager;
import com.github.feiyongjing.service.spring.core.mvc.factory.RouteMethodMapper;
import com.github.feiyongjing.service.spring.dome.Application;
import com.github.feiyongjing.service.spring.factory.BeanFactoryImpl;
import com.github.feiyongjing.service.spring.factory.ClassFactory;
import com.github.feiyongjing.service.spring.server.HttpServer;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class ScanComponent {
    public static SpringContext run(Class<Application> applicationClass, String[] args) {

        // 创建容器类
        SpringContext applicationContext = new ApplicationContext();

        // 获取全部的扫描路径
        String[] packageNames = getScanPackageNames(applicationClass);
        // 加载所有的Class，并进行过滤分类
        ClassFactory.loadClass(packageNames);

        // 获取所有Controller中url，并按照请求方式进行分类
        RouteMethodMapper.loadRoutes();

        // 加载所有的Bean
        BeanFactoryImpl.loadBeans(applicationContext);

        // 加载配置文件，放入ConfigurationManager这个Bean中进行管理
        loadResources(applicationClass,applicationContext);

        // 加载所有的拦截器
        InterceptorFactory.loadInterceptors(packageNames);

        // 对所有的Bean判断是否需要动态代理
        BeanFactoryImpl.applyBeanPostProcessors(applicationContext);

        // 刷新IOC容器，填充Bean的字段属性
        refreshContext(applicationContext);

        callRunners((ApplicationContext) applicationContext);

        return applicationContext;
    }

    /**
     * 加载Configuration.DEFAULT_CONFIG_NAMES指定的配置文件
     * 加载的结果会缓存到 configurationManager 这个Bean的 configuration 字段中
     *
     * @param applicationClass
     * @param applicationContext
     */
    private static void loadResources(Class<?> applicationClass, SpringContext applicationContext) {
        ClassLoader classLoader = applicationClass.getClassLoader();
        List<Path> filePaths = new ArrayList<>();
        for (String configName : Configuration.DEFAULT_CONFIG_NAMES) {
            URL url = classLoader.getResource(configName);
            if (!Objects.isNull(url)) {
                try {
                    filePaths.add(Paths.get(url.toURI()));
                } catch (URISyntaxException ignored) {
                }
            }
        }
        ConfigurationManager configurationManager = BeanFactoryImpl.getBean(ConfigurationManager.class,applicationContext);
        configurationManager.loadResources(filePaths);
    }

    /**
     * 获取需要扫描的全部包名
     * 判断传入的clazz是否包含@ComponentScan注解，
     * 包含就直接返回@Component注解中的value属性,否则返回clazz的包名
     *
     * @param clazz
     * @return 需要扫描的全部包名
     */
    private static String[] getScanPackageNames(Class<Application> clazz) {
        ComponentScan componentScan = clazz.getAnnotation(ComponentScan.class);
        return !Objects.isNull(componentScan) ? componentScan.value()
                : new String[]{clazz.getPackage().getName()};
    }


    private static void refreshContext(SpringContext context) {
        for (String beanName : context.getIOCcontext().keySet()) {
            Object bean = context.getBean(beanName);
            processBeanAnnotationMethod(context, bean);
            processAllAnnotationField(context, bean);
        }
    }

    /**
     * 处理bean的字段包含的注解
     * 目前处理@Autowired、@Value
     * @param context
     * @param bean
     */
    private static void processAllAnnotationField(SpringContext context, Object bean) {
        for (Field field : bean.getClass().getFields()) {
            processAutowiredAnnotationField(context,bean,field);
            processValueAnnotationField(context,bean,field);
        }
    }

    /**
     * 对bean中的所有方法过滤，判断是否标注@Bean注解，如果是就调用该方法返回值放入IOC容器
     * @param context IOC容器
     * @param bean 正在进行过滤方法的Bean
     */
    private static void processBeanAnnotationMethod(SpringContext context, Object bean) {
        for (Method method : bean.getClass().getMethods()) {
            if (method.isAnnotationPresent(Bean.class)) {
                try {
                    Object[] args = Stream.of(method.getParameterTypes())
                            .map(context::getBean)
                            .toArray(Object[]::new);
                    Object returnBean = method.invoke(bean, args);
                    String beamName=method.getName();
                    if(method.getDeclaredAnnotation(Bean.class).value().length>0){
                        beamName=method.getDeclaredAnnotation(Bean.class).value()[0];
                    }
                    context.setBean(beamName, returnBean);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 判断field是否标注@AutoWired,如果是则从IOC容器中获取对应的Bean，
     * 对获取的Bean进行后置处理，即判断该Bean是否存在切点，存在则进行动态代理。
     * 最后将获取的Bean进行字段注入
     * @param context IOC容器
     * @param bean 正在进行字段注入的Bean
     * @param field Bean的字段
     */
    private static void processAutowiredAnnotationField(SpringContext context, Object bean,Field field) {
        if (field.isAnnotationPresent(Autowired.class)) {
            try {
                Object beanFieldInstance = context.getBean(field.getType());
                if(beanFieldInstance==null){
                    throw new RuntimeException("can not determine target bean of" + field.getType().getName());
                }
                field.set(bean, beanFieldInstance);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 判断field是否标注@Value,如果是则从配置文件中读取对应的value进行注入。
     * 配置文件内容的value从IOC中的ConfigurationManager对应的Bean获取
     * @param context IOC容器
     * @param bean 正在进行字段注入的Bean
     * @param field Bean的字段
     */
    private static void processValueAnnotationField(SpringContext context, Object bean,Field field) {
        if (field.isAnnotationPresent(Value.class)) {
            try {
                String key = field.getDeclaredAnnotation(Value.class).value();
                ConfigurationManager configurationManager = (ConfigurationManager) context.getIOCcontext().get(ConfigurationManager.class.getName());
                String value = configurationManager.getString(key);
                if (value == null) {
                    throw new IllegalArgumentException("can not find target value for property:{" + key + "}");
                }
                field.set(bean, ObjectUtil.convert(field.getType(), value));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private static void callRunners(ApplicationContext applicationContext) {
        List<ApplicationRunner> runners = new ArrayList<>(applicationContext.getBeansOfType(ApplicationRunner.class).values());
        //The last step is to start web application
        runners.add(() -> {
            HttpServer httpServer = new HttpServer();
            httpServer.start();
        });
        for (Object runner : new LinkedHashSet<>(runners)) {
            ((ApplicationRunner) runner).run();
        }
    }

}
