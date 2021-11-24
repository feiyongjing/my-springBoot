package com.github.feiyongjing.service.spring.core.mvc.resolver;


import com.github.feiyongjing.service.spring.annotation.mvc.PathVariable;
import com.github.feiyongjing.service.spring.common.util.ObjectUtil;
import com.github.feiyongjing.service.spring.core.mvc.entity.MethodDetail;

import java.lang.reflect.Parameter;
import java.util.Map;

/**
 * process @PathVariable annotation
 **/
public class PathVariableParameterResolver implements ParameterResolver {
    /**
     * 处理 @PathVariable 注释的参数
     * @param methodDetail 方法以及各种参数
     * @param parameter    注释的形参
     * @return 处理后的实际参数
     */
    @Override
    public Object resolve(MethodDetail methodDetail, Parameter parameter) {
        PathVariable pathVariable = parameter.getDeclaredAnnotation(PathVariable.class);
        String requestParameter = pathVariable.value();
        Map<String, String> urlParameterMappings = methodDetail.getUrlParameterMappings();
        String requestParameterValue = urlParameterMappings.get(requestParameter);
        return ObjectUtil.convert(parameter.getType(), requestParameterValue);
    }

}
