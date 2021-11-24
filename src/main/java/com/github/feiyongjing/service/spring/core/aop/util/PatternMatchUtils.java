package com.github.feiyongjing.service.spring.core.aop.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class PatternMatchUtils {
    /**
     * Match a String against the given pattern, supporting the following simple
     * pattern styles: "xxx*", "*xxx", "*xxx*" and "xxx*yyy" matches (with an
     * arbitrary number of pattern parts), as well as direct equality.
     *
     * @param pattern the pattern to match against
     * @param str     the String to match
     * @return whether the String matches the given pattern
     */
    public static boolean simpleMatch(String pattern, String str) {
        if (pattern == null || str == null) {
            return false;
        }
        int firstIndex = pattern.indexOf('*');
        if (firstIndex == -1) {
            return pattern.equals(str);
        }
        if (firstIndex == 0) {
            if (pattern.length() == 1) {
                return true;
            }
            int nextIndex = pattern.indexOf('*', firstIndex + 1);
            if (nextIndex == -1) {
                return str.endsWith(pattern.substring(1));
            }
            String part = pattern.substring(1, nextIndex);
            if ("".equals(part)) {
                return simpleMatch(pattern.substring(nextIndex), str);
            }
            int partIndex = str.indexOf(part);
            while (partIndex != -1) {
                if (simpleMatch(pattern.substring(nextIndex), str.substring(partIndex + part.length()))) {
                    return true;
                }
                partIndex = str.indexOf(part, partIndex + 1);
            }
            return false;
        }
        return (str.length() >= firstIndex
                && pattern.substring(0, firstIndex).equals(str.substring(0, firstIndex))
                && simpleMatch(pattern.substring(firstIndex), str.substring(firstIndex)));
    }

    /**
     * Match a String against the given patterns, supporting the following simple
     * pattern styles: "xxx*", "*xxx", "*xxx*" and "xxx*yyy" matches (with an
     * arbitrary number of pattern parts), as well as direct equality.
     *
     * @param patterns the patterns to match against
     * @param str      the String to match
     * @return whether the String matches any of the given patterns
     */
    public static boolean simpleMatch(String[] patterns, String str) {
        if (patterns != null) {
            for (String pattern : patterns) {
                if (simpleMatch(pattern, str)) {
                    return true;
                }
            }
        }
        return false;
    }


    public static boolean isMatch(String url, Class<?> targetClass, HashMap<String, List<Method>> expressionUrlAndMethod) {
        String s = "()";
        if (url.endsWith(s)) {
            String a = url.substring(0, url.length() - s.length());
            for (Map.Entry<String, List<Method>> entry : expressionUrlAndMethod.entrySet()) {
                for (Method method : entry.getValue()) {
                    if (method.getName().equals(a)){
                        return simpleMatch(entry.getKey(), targetClass.getName());
                    }
                }
            }
        }

        Boolean isMatchAnnotationAdvise = isMatchAnnotationAdvise(url, targetClass);
        if (isMatchAnnotationAdvise) return true;

        return simpleMatch(url, targetClass.getName());
    }

    @SuppressWarnings("unchecked")
    private static Boolean isMatchAnnotationAdvise(String url, Class<?> aClass) {
        if (url.startsWith("@annotation")) {
            String b = url.substring(url.indexOf("(") + 1, url.lastIndexOf(")"));
            try {
                Class<? extends Annotation> annotationClass = (Class<? extends Annotation>) Class.forName(b);
                return Stream.of(aClass.getMethods())
                        .anyMatch(method -> method.isAnnotationPresent(annotationClass));
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("找不到这个类：" + b);
            }
        }
        return false;
    }
}
