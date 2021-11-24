package com.github.feiyongjing.service.spring.core.mvc.factory;

import com.github.feiyongjing.service.spring.exception.ErrorResponse;
import com.github.feiyongjing.service.spring.common.ReflectionUtil;
import com.github.feiyongjing.service.spring.serialize.impl.JacksonSerializer;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.util.AsciiString;

import java.lang.reflect.Method;
import java.util.List;

import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

public class FullHttpResponseFactory {
    private static final AsciiString CONTENT_TYPE = AsciiString.cached("Content-Type");
    private static final AsciiString CONTENT_LENGTH = AsciiString.cached("Content-Length");
    private static final JacksonSerializer JSON_SERIALIZER = new JacksonSerializer();

    /**
     * 获取请求成功的响应
     * @param targetMethod 目标方法
     * @param targetMethodParams 目标参数
     * @param targetObject 目标对象
     * @return http成功响应
     */
    public static FullHttpResponse getSuccessResponse(Method targetMethod, List<Object> targetMethodParams, Object targetObject) {
        //the return type of targetMethod is void
        if (targetMethod.getReturnType() == void.class) {
            ReflectionUtil.executeTargetMethodNoResult(targetObject, targetMethod, targetMethodParams.toArray());
            return buildSuccessResponse();
        } else {
            Object result = ReflectionUtil.executeTargetMethod(targetObject, targetMethod, targetMethodParams.toArray());
            return buildSuccessResponse(result);
        }
    }

    /**
     * 获取请求失败的响应
     * @param url URL
     * @param message 错误响应消息
     * @param httpResponseStatus Http响应状态码
     * @return http失败响应
     */
    public static FullHttpResponse getErrorResponse(String url, String message, HttpResponseStatus httpResponseStatus) {
        ErrorResponse errorResponse = new ErrorResponse(httpResponseStatus.code(), httpResponseStatus.reasonPhrase(), message, url);
        byte[] content = JSON_SERIALIZER.serialize(errorResponse);
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, httpResponseStatus, Unpooled.wrappedBuffer(content));
        response.headers().set(CONTENT_TYPE, "application/json");
        response.headers().setInt(CONTENT_LENGTH, response.content().readableBytes());
        return response;
    }


    private static FullHttpResponse buildSuccessResponse(Object o) {
        byte[] content = JSON_SERIALIZER.serialize(o);
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(content));
        response.headers().set(CONTENT_TYPE, "application/json");
        response.headers().setInt(CONTENT_LENGTH, response.content().readableBytes());
        return response;
    }

    private static FullHttpResponse buildSuccessResponse() {
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK);
        response.headers().set(CONTENT_TYPE, "application/json");
        response.headers().setInt(CONTENT_LENGTH, response.content().readableBytes());
        return response;
    }
}
