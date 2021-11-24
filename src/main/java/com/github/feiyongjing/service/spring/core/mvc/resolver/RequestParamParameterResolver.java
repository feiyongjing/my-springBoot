package com.github.feiyongjing.service.spring.core.mvc.resolver;

import com.github.feiyongjing.service.spring.common.util.ObjectUtil;
import com.github.feiyongjing.service.spring.annotation.mvc.RequestParam;
import com.github.feiyongjing.service.spring.core.mvc.entity.MethodDetail;

import java.lang.reflect.Parameter;

/**
 * 处理 @RequestParam 注解
 **/
public class RequestParamParameterResolver implements ParameterResolver {
    /**
     * 处理 @RequestParam 注释的参数
     * @param methodDetail 方法以及各种参数
     * @param parameter    注释的形参
     * @return 处理后的实际参数
     */
    @Override
    public Object resolve(MethodDetail methodDetail, Parameter parameter) {
        RequestParam requestParam = parameter.getDeclaredAnnotation(RequestParam.class);
        String requestParameter = requestParam.value();
        String requestParameterValue = methodDetail.getQueryParameterMappings().get(requestParameter);
        if (requestParameterValue == null) {
            if (requestParam.require() && requestParam.defaultValue().isEmpty()) {
                throw new IllegalArgumentException("The specified parameter " + requestParameter + " can not be null!");
            } else {
                requestParameterValue = requestParam.defaultValue();
            }
        }
        // convert the parameter to the specified type
        return ObjectUtil.convert(parameter.getType(), requestParameterValue);

    }
}
