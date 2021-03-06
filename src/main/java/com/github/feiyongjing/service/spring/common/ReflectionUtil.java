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
        // ?????????class????????????
        Set<Class<?>> classes = new LinkedHashSet<>();
        // ??????????????????
        boolean recursive = true;
        // ???????????????????????????
        String packageDirName = packageName.replace('.', '/');
        // ??????????????????????????? ??????????????????????????????????????????things
        Enumeration<URL> dirs;
        try {
            dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
            // ??????????????????
            while (dirs.hasMoreElements()) {
                // ?????????????????????
                URL url = dirs.nextElement();
                // ?????????????????????
                String protocol = url.getProtocol();
                // ????????????????????????????????????????????????
                if ("file".equals(protocol)) {
                    // ????????????????????????
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                    // ????????????????????????????????????????????? ?????????????????????
                    findAndAddClassesInPackageByFile(packageName, filePath, recursive, classes);
                } else if ("jar".equals(protocol)) {
                    // ?????????jar?????????
                    // ????????????JarFile
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
        // ????????????????????? ????????????File
        File dir = new File(packagePath);
        // ????????????????????? ??????????????????????????????
        if (!dir.exists() || !dir.isDirectory()) {
            // log.warn("?????????????????? " + packageName + " ?????????????????????");
            return;
        }
        // ???????????? ?????????????????????????????? ????????????
        File[] dirfiles = dir.listFiles(new FileFilter() {
            // ????????????????????? ??????????????????(???????????????) ????????????.class???????????????(????????????java?????????)
            public boolean accept(File file) {
                return (recursive && file.isDirectory()) || (file.getName().endsWith(".class"));
            }
        });
        // ??????????????????
        for (File file : dirfiles) {
            // ??????????????? ???????????????
            if (file.isDirectory()) {
                findAndAddClassesInPackageByFile(packageName + "." + file.getName(), file.getAbsolutePath(), recursive,
                        classes);
            } else {
                // ?????????java????????? ???????????????.class ???????????????
                String className = file.getName().substring(0, file.getName().length() - 6);
                try {
                    // ?????????????????????
                    // classes.add(Class.forName(packageName + '.' +
                    // className));
                    // ???????????????????????????????????????forName???????????????????????????static?????????????????????classLoader???load??????
                    classes.add(
                            Thread.currentThread().getContextClassLoader().loadClass(packageName + '.' + className));
                } catch (ClassNotFoundException e) {
                    // log.error("???????????????????????????????????? ??????????????????.class??????");
                    e.printStackTrace();
                }
            }
        }
    }

    private static String getJarClass(Set<Class<?>> classes, boolean recursive, String packageName, String packageDirName, URL url) {
        JarFile jar;
        try {
            // ??????jar
            jar = ((JarURLConnection) url.openConnection()).getJarFile();
            // ??????jar??? ?????????????????????
            Enumeration<JarEntry> entries = jar.entries();
            // ???????????????????????????
            while (entries.hasMoreElements()) {
                // ??????jar?????????????????? ??????????????? ?????????jar????????????????????? ???META-INF?????????
                JarEntry entry = entries.nextElement();
                String name = entry.getName();
                // ????????????/?????????
                if (name.charAt(0) == '/') {
                    // ????????????????????????
                    name = name.substring(1);
                }
                // ??????????????????????????????????????????
                if (name.startsWith(packageDirName)) {
                    int idx = name.lastIndexOf('/');
                    // ?????????"/"?????? ????????????
                    if (idx != -1) {
                        // ???????????? ???"/"?????????"."
                        packageName = name.substring(0, idx).replace('/', '.');
                    }
                    // ???????????????????????? ??????????????????
                    if ((idx != -1) || recursive) {
                        // ???????????????.class?????? ??????????????????
                        if (name.endsWith(".class") && !entry.isDirectory()) {
                            // ???????????????".class" ?????????????????????
                            String className = name.substring(packageName.length() + 1, name.length() - 6);
                            try {
                                // ?????????classes
                                classes.add(Class.forName(packageName + '.' + className));
                            } catch (ClassNotFoundException e) {
                                // log
                                // .error("????????????????????????????????????
                                // ??????????????????.class??????");
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            // log.error("?????????????????????????????????jar?????????????????????");
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
