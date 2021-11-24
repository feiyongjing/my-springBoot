package com.github.feiyongjing.service.spring.core.mvc.resolver;

import com.github.feiyongjing.service.spring.core.mvc.entity.MethodDetail;

import java.lang.reflect.Parameter;

public interface ParameterResolver {
    /**
     * Process method parameters
     *
     * @param methodDetail Target method related information
     * @param parameter    The parameter of the target method
     * @return Specific values ​​corresponding to the parameters of the target method
     */
    Object resolve(MethodDetail methodDetail, Parameter parameter);
}
