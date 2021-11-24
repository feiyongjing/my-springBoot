package com.github.feiyongjing.service.spring.core.mvc.entity;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class MethodDetail {
    // url 调用的目标方法
    private Method method;
    // url 路径变量参数映射
    private Map<String, String> urlParameterMappings;
    // url 表单参数映射
    private Map<String, String> queryParameterMappings;
    // json type http post request data
    private String json;

    /**
     * urlPath满足程序实际匹配的url就初始化urlPath调用的目标方法和urlPath中的路径参数
     * @param requestPath 请求的url路径
     * @param requestMappings 格式化后的url -> 对应url调用的方法method
     * @param urlMappings 格式化后的url -> 代码注解上的url
     */
    public void build(String requestPath, Map<String, Method> requestMappings, Map<String, String> urlMappings) {
        requestMappings.forEach((key, value) -> {
            Pattern pattern = Pattern.compile(key);
            boolean b = pattern.matcher(requestPath).find();
            if (b) {
                this.setMethod(value);
                String url = urlMappings.get(key);
                Map<String, String> urlParameterMappings = getUrlParameterMappings(requestPath, url);
                this.setUrlParameterMappings(urlParameterMappings);
            }
        });
    }

    /**
     * Match the request path parameter to the URL parameter
     * <p>
     * eg: requestPath="/user/1" url="/user/{id}"
     * this method will return:{"id" -> "1","user" -> "user"}
     * </p>
     */
    private Map<String, String> getUrlParameterMappings(String requestPath, String url) {
        String[] requestParams = requestPath.split("/");
        String[] urlParams = url.split("/");
        Map<String, String> urlParameterMappings = new HashMap<>();
        for (int i = 1; i < urlParams.length; i++) {
            urlParameterMappings.put(urlParams[i].replace("{", "").replace("}", ""), requestParams[i]);
        }
        return urlParameterMappings;
    }

    public MethodDetail(Method method, Map<String, String> urlParameterMappings, Map<String, String> queryParameterMappings, String json) {
        this.method = method;
        this.urlParameterMappings = urlParameterMappings;
        this.queryParameterMappings = queryParameterMappings;
        this.json = json;
    }

    public MethodDetail() {
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Map<String, String> getUrlParameterMappings() {
        return urlParameterMappings;
    }

    public void setUrlParameterMappings(Map<String, String> urlParameterMappings) {
        this.urlParameterMappings = urlParameterMappings;
    }

    public Map<String, String> getQueryParameterMappings() {
        return queryParameterMappings;
    }

    public void setQueryParameterMappings(Map<String, String> queryParameterMappings) {
        this.queryParameterMappings = queryParameterMappings;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }
}
