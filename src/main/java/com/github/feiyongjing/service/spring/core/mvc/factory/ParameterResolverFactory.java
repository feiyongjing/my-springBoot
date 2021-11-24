package com.github.feiyongjing.service.spring.core.mvc.factory;

import com.github.feiyongjing.service.spring.annotation.mvc.PathVariable;
import com.github.feiyongjing.service.spring.annotation.mvc.RequestBody;
import com.github.feiyongjing.service.spring.annotation.mvc.RequestParam;
import com.github.feiyongjing.service.spring.core.mvc.resolver.ParameterResolver;
import com.github.feiyongjing.service.spring.core.mvc.resolver.PathVariableParameterResolver;
import com.github.feiyongjing.service.spring.core.mvc.resolver.RequestBodyParameterResolver;
import com.github.feiyongjing.service.spring.core.mvc.resolver.RequestParamParameterResolver;

import java.lang.reflect.Parameter;

public class ParameterResolverFactory {
    /**
     * 获取形参的注解处理器
     * @param parameter 形参
     * @return 形参的注解处理器
     */
    public static ParameterResolver get(Parameter parameter) {
        if (parameter.isAnnotationPresent(RequestParam.class)) {
            return new RequestParamParameterResolver();
        }
        if (parameter.isAnnotationPresent(PathVariable.class)) {
            return new PathVariableParameterResolver();
        }
        if (parameter.isAnnotationPresent(RequestBody.class)) {
            return new RequestBodyParameterResolver();
        }
        return null;
    }
}
