package com.github.feiyongjing.service.spring.core.mvc.handler;

import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;

public interface RequestHandler {
    /**
     * 处理不同Http请求，返回http响应
     * @param fullHttpRequest 不同的http请求
     * @return 返回http响应
     */
    FullHttpResponse handle(FullHttpRequest fullHttpRequest);
}
