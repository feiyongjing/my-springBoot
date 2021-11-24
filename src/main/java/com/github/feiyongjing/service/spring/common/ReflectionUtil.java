package com.github.feiyongjing.service.spring.common;

import com.github.feiyongjing.service.spring.core.aop.lang.JoinPoint;

import javax.annotation.*;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.annotation.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

public class ReflectionUtil {
    public static Set<Class<?>> scanAnnotatedClass(String[] packageNames, Class<?> annotationClass) {
        Set<Class<?>> result = new HashSet<>();
        for (String packageName : packageNames) {
            result.addAll(getScanAnnotationClass(annotationClass, packageName));
        }
        return result;
    }

    private static boolean isInstanceClass(Class<?> clazz) {
        return !(clazz.isInterface() || clazz.isAnnotation() || Modifier.isAbstract(clazz.getModifiers()));
    }


    private static Set<Class<?>> getScanAnnotationClass(Class<?> annotationClass, String packageName) {
        return getPackageAllClass(packageName)
                .stream()
                .filter(ReflectionUtil::isInstanceClass)
                .filter(clazz -> isContainsAnnotation(clazz, annotationClass))
                .collect(Collectors.toSet());
    }

    public static Object newInstance(Class<?> cls) {
        try {
            return cls.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    private static boolean isContainsAnnotation(Class<?> classz1, Class<?> classz2) {
        Annotation[] annotations = classz1.getAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation.annotationType() != Deprecated.class &&
                    annotation.annotationType() != SuppressWarnings.class &&
                    annotation.annotationType() != Override.class &&
                    annotation.annotationType() != PostConstruct.class &&
                    annotation.annotationType() != PreDestroy.class &&
                    annotation.annotationType() != Resource.class &&
                    annotation.annotationType() != Resources.class &&
                    annotation.annotationType() != Generated.class &&
                    annotation.annotationType() != Target.class &&
                    annotation.annotationType() != Retention.class &&
                    annotation.annotationType() != Documented.class &&
                    annotation.annotationType() != Inherited.class
            ) {
                if (annotation.annotationType() == classz2) {
                    return true;
                } else {
                    if (isContainsAnnotation(annotation.annotationType(), classz2)) {
                        return true;
                    }
                }
            }

        }
        return false;
    }

    private static Set<Class<?>> getPackageAllClass(String packageName) {
        // 第一个class类的集合
        Set<Class<?>> classes = new LinkedHashSet<>();
        // 是否循环迭代
        boolean recursive = true;
        // 对包的名字进行替换
        String packageDirName = packageName.replace('.', '/');
        // 定义一个枚举的集合 并进行循环来处理这个目录下的things
        Enumeration<URL> dirs;
        try {
            dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
            // 循环迭代下去
            while (dirs.hasMoreElements()) {
                // 获取下一个元素
                URL url = dirs.nextElement();
                // 得到协议的名称
                String protocol = url.getProtocol();
                // 如果是以文件的形式保存在服务器上
                if ("file".equals(protocol)) {
                    // 获取包的物理路径
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                    // 以文件的方式扫描整个包下的文件 并添加到集合中
                    findAndAddClassesInPackageByFile(packageName, filePath, recursive, classes);
                } else if ("jar".equals(protocol)) {
                    // 如果是jar包文件
                    // 定义一个JarFile
                    packageName = getJarClass(classes, recursive, packageName, packageDirName, url);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return classes;
    }

    public static void findAndAddClassesInPackageByFile(String packageName, String packagePath, final boolean recursive,
                                                        Set<Class<?>> classes) {
        // 获取此包的目录 建立一个File
        File dir = new File(packagePath);
        // 如果不存在或者 也不是目录就直接返回
        if (!dir.exists() || !dir.isDirectory()) {
            // log.warn("用户定义包名 " + packageName + " 下没有任何文件");
            return;
        }
        // 如果存在 就获取包下的所有文件 包括目录
        File[] dirfiles = dir.listFiles(new FileFilter() {
            // 自定义过滤规则 如果可以循环(包含子目录) 或则是以.class结尾的文件(编译好的java类文件)
            public boolean accept(File file) {
                return (recursive && file.isDirectory()) || (file.getName().endsWith(".class"));
            }
        });
        // 循环所有文件
        for (File file : dirfiles) {
            // 如果是目录 则继续扫描
            if (file.isDirectory()) {
                findAndAddClassesInPackageByFile(packageName + "." + file.getName(), file.getAbsolutePath(), recursive,
                        classes);
            } else {
                // 如果是java类文件 去掉后面的.class 只留下类名
                String className = file.getName().substring(0, file.getName().length() - 6);
                try {
                    // 添加到集合中去
                    // classes.add(Class.forName(packageName + '.' +
                    // className));
                    // 经过回复同学的提醒，这里用forName有一些不好，会触发static方法，没有使用classLoader的load干净
                    classes.add(
                            Thread.currentThread().getContextClassLoader().loadClass(packageName + '.' + className));
                } catch (ClassNotFoundException e) {
                    // log.error("添加用户自定义视图类错误 找不到此类的.class文件");
                    e.printStackTrace();
                }
            }
        }
    }

    private static String getJarClass(Set<Class<?>> classes, boolean recursive, String packageName, String packageDirName, URL url) {
        JarFile jar;
        try {
            // 获取jar
            jar = ((JarURLConnection) url.openConnection()).getJarFile();
            // 从此jar包 得到一个枚举类
            Enumeration<JarEntry> entries = jar.entries();
            // 同样的进行循环迭代
            while (entries.hasMoreElements()) {
                // 获取jar里的一个实体 可以是目录 和一些jar包里的其他文件 如META-INF等文件
                JarEntry entry = entries.nextElement();
                String name = entry.getName();
                // 如果是以/开头的
                if (name.charAt(0) == '/') {
                    // 获取后面的字符串
                    name = name.substring(1);
                }
                // 如果前半部分和定义的包名相同
                if (name.startsWith(packageDirName)) {
                    int idx = name.lastIndexOf('/');
                    // 如果以"/"结尾 是一个包
                    if (idx != -1) {
                        // 获取包名 把"/"替换成"."
                        packageName = name.substring(0, idx).replace('/', '.');
                    }
                    // 如果可以迭代下去 并且是一个包
                    if ((idx != -1) || recursive) {
                        // 如果是一个.class文件 而且不是目录
                        if (name.endsWith(".class") && !entry.isDirectory()) {
                            // 去掉后面的".class" 获取真正的类名
                            String className = name.substring(packageName.length() + 1, name.length() - 6);
                            try {
                                // 添加到classes
                                classes.add(Class.forName(packageName + '.' + className));
                            } catch (ClassNotFoundException e) {
                                // log
                                // .error("添加用户自定义视图类错误
                                // 找不到此类的.class文件");
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            // log.error("在扫描用户定义视图时从jar包获取文件出错");
            e.printStackTrace();
        }
        return packageName;
    }

    public static Object executeTargetMethod(Object targetObject, Method targetMethod, Object... args) {
        try {
            return targetMethod.invoke(targetObject, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> Set<Class<?>> getSubClass(String[] packageNames, Class<T> clazz) {
        Set<Class<?>> result = new HashSet<>();
        for (String packageName : packageNames) {
            result.addAll(getScanSubClass(clazz, packageName));
        }
        return result;
    }

    private static <T> Collection<Class<?>> getScanSubClass(Class<T> clazz, String packageName) {
        return getPackageAllClass(packageName)
                .stream()
                .filter(ReflectionUtil::isInstanceClass)
                .filter(clazz::isAssignableFrom)
                .collect(Collectors.toSet());
    }

    private static boolean isNotInstanceClass(Class<?> aClass) {
        return !isInstanceClass(aClass);
    }


    public static void executeTargetMethodNoResult(Object targetObject, Method method, Object... args) {
        try {
            // invoke target method through reflection
            method.invoke(targetObject, args);
        } catch (IllegalAccessException | InvocationTargetException ignored) {
            new RuntimeException(ignored);
        }
    }

    public static void executeTargetMethodNoResult(Object targetObject, List<Method> methods, JoinPoint joinPoint) {
        methods.forEach(method -> executeTargetMethodNoResult(targetObject, method, joinPoint));
    }
}
