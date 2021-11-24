package com.github.feiyongjing.service.spring.core.mvc.factory;

import com.github.feiyongjing.service.spring.core.mvc.handler.GetRequestHandler;
import com.github.feiyongjing.service.spring.core.mvc.handler.PostRequestHandler;
import com.github.feiyongjing.service.spring.core.mvc.handler.RequestHandler;
import io.netty.handler.codec.http.HttpMethod;

import java.util.HashMap;
import java.util.Map;

public class RequestHandlerFactory {
    /**
     * key Http方法状态
     * value Http方法状态对应的Http请求处理器
     */
    public static final Map<HttpMethod, RequestHandler> REQUEST_HANDLERS = new HashMap<>();

    static {
        REQUEST_HANDLERS.put(HttpMethod.GET, new GetRequestHandler());
        REQUEST_HANDLERS.put(HttpMethod.POST, new PostRequestHandler());
    }

    /**
     * 根据http请求方法状态获取对应的Http请求处理器
     * @param httpMethod http请求方法状态
     * @return 对应的Http请求处理器
     */
    public static RequestHandler get(HttpMethod httpMethod) {
        return REQUEST_HANDLERS.get(httpMethod);
    }
}
