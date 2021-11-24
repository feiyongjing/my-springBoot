package com.github.feiyongjing.service.spring.core.mvc.handler;

import com.github.feiyongjing.service.spring.context.ApplicationContext;
import com.github.feiyongjing.service.spring.core.mvc.factory.FullHttpResponseFactory;
import com.github.feiyongjing.service.spring.core.mvc.factory.ParameterResolverFactory;
import com.github.feiyongjing.service.spring.core.mvc.factory.RouteMethodMapper;
import com.github.feiyongjing.service.spring.core.mvc.resolver.ParameterResolver;
import com.github.feiyongjing.service.spring.factory.BeanFactoryImpl;
import com.github.feiyongjing.service.spring.core.mvc.entity.MethodDetail;
import com.github.feiyongjing.service.spring.core.mvc.util.UrlUtil;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpMethod;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GetRequestHandler implements RequestHandler {
    /**
     * 处理Get状态的Http请求，返回http响应
     * @param fullHttpRequest Get状态的Http请求
     * @return 返回http响应，若程序找不到处理请求的方法则返回null
     */
    @Override
    public FullHttpResponse handle(FullHttpRequest fullHttpRequest) {
        MethodDetail methodDetail=getMethDetail(fullHttpRequest);
        Method targetMethod=methodDetail.getMethod();
        if (targetMethod == null) {
            return null;
        }
        Parameter[] targetMethodParameters = targetMethod.getParameters();
        List<Object> targetMethodParams = getTargetMethodParams(methodDetail, targetMethodParameters);

        String beanName = BeanFactoryImpl.getBeanName(methodDetail.getMethod().getDeclaringClass());
        Object targetObject = ApplicationContext.springContext.getIOCcontext().get(beanName);
        return FullHttpResponseFactory.getSuccessResponse(targetMethod, targetMethodParams, targetObject);
    }

    /**
     * 获取响应Http请求的方法和请求的实际参数
     */
    private MethodDetail getMethDetail(FullHttpRequest fullHttpRequest){
        String requestUri = fullHttpRequest.uri();
        Map<String, String> queryParameterMappings = UrlUtil.getQueryParams(requestUri);
        // get http request path，such as "/user"
        String requestPath = UrlUtil.getRequestPath(requestUri);
        // get target method
        MethodDetail methodDetail = RouteMethodMapper.getMethodDetail(requestPath, HttpMethod.GET);
        methodDetail.setQueryParameterMappings(queryParameterMappings);
        return methodDetail;
    }

    /**
     * 根据Method参数的注解来解析和验证实际的参数
     * @param methodDetail 方法和各种参数
     * @param targetMethodParameters 方法的形参
     * @return 方法接收到实际的参数
     */
    private List<Object> getTargetMethodParams(MethodDetail methodDetail, Parameter[] targetMethodParameters) {
        List<Object> targetMethodParams = new ArrayList<>();
        for (Parameter parameter : targetMethodParameters) {
            ParameterResolver parameterResolver = ParameterResolverFactory.get(parameter);
            if (parameterResolver != null) {
                Object param = parameterResolver.resolve(methodDetail, parameter);
                targetMethodParams.add(param);
            }
        }
        return targetMethodParams;
    }
}
