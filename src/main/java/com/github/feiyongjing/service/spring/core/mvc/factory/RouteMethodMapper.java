package com.github.feiyongjing.service.spring.core.mvc.factory;

import com.github.feiyongjing.service.spring.annotation.Controller;
import com.github.feiyongjing.service.spring.annotation.mvc.GetMapping;
import com.github.feiyongjing.service.spring.annotation.mvc.PostMapping;
import com.github.feiyongjing.service.spring.core.mvc.entity.MethodDetail;
import com.github.feiyongjing.service.spring.factory.ClassFactory;
import io.netty.handler.codec.http.HttpMethod;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class RouteMethodMapper {
    public static final HttpMethod[] HTTP_METHODS = {HttpMethod.GET, HttpMethod.POST};

    // key : Http方法状态
    // value : 格式化后的url -> 对应url调用的方法method
    private static final Map<HttpMethod, Map<String, Method>> REQUEST_METHOD_MAP = new HashMap<>(2);
    // key : Http方法状态
    // value : 格式化后的url -> 代码注解上的url
    private static final Map<HttpMethod, Map<String, String>> REQUEST_URL_MAP = new HashMap<>(2);

    static {
        for (HttpMethod httpMethod : HTTP_METHODS) {
            REQUEST_METHOD_MAP.put(httpMethod, new HashMap<>(128));
            REQUEST_URL_MAP.put(httpMethod, new HashMap<>(128));
        }
    }
    public static void loadRoutes() {
        Set<Class<?>> classes = ClassFactory.CLASSES.get(Controller.class);
        for (Class<?> aClass : classes) {
            Controller controller = aClass.getAnnotation(Controller.class);
            Method[] methods = aClass.getDeclaredMethods();
            String baseUrl = controller.value();
            for (Method method : methods) {
                if (method.isAnnotationPresent(GetMapping.class)) {
                    String url=baseUrl + method.getAnnotation(GetMapping.class).value();
                    mapUrlToMethod(url, method, HttpMethod.GET);
                }
                if (method.isAnnotationPresent(PostMapping.class)) {
                    String url=baseUrl + method.getAnnotation(PostMapping.class).value();
                    mapUrlToMethod(url, method, HttpMethod.POST);
                }
            }
        }
    }

    /**
     * correspond url to method
     */
    private static void mapUrlToMethod(String url, Method method, HttpMethod httpMethod) {
        String formattedUrl = formatUrl(url);
        Map<String, Method> urlToMethodMap = REQUEST_METHOD_MAP.get(httpMethod);
        Map<String, String> formattedUrlToUrlMap = REQUEST_URL_MAP.get(httpMethod);
        if (urlToMethodMap.containsKey(formattedUrl)) {
            throw new IllegalArgumentException(String.format("duplicate url: %s", url));
        }
        urlToMethodMap.put(formattedUrl, method);
        formattedUrlToUrlMap.put(formattedUrl, url);
        REQUEST_METHOD_MAP.put(httpMethod, urlToMethodMap);
        REQUEST_URL_MAP.put(httpMethod, formattedUrlToUrlMap);
    }

    /**
     * format the url
     * for example : "/user/{name}" -> "^/user/[\u4e00-\u9fa5_a-zA-Z0-9]+/?$"
     */
    private static String formatUrl(String url) {
        // replace {xxx} placeholders with regular expressions matching Chinese, English letters and numbers, and underscores
        String originPattern = url.replaceAll("(\\{\\w+})", "[\\\\u4e00-\\\\u9fa5_a-zA-Z0-9]+");
        String pattern = "^" + originPattern + "/?$";
        return pattern.replaceAll("/+", "/");
    }

    public static MethodDetail getMethodDetail(String requestPath, HttpMethod httpMethod) {
        MethodDetail methodDetail = new MethodDetail();
        methodDetail.build(requestPath, REQUEST_METHOD_MAP.get(httpMethod), REQUEST_URL_MAP.get(httpMethod));
        return methodDetail;
    }


}
