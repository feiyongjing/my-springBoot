package com.github.feiyongjing.service.spring.server;


import com.github.feiyongjing.service.spring.core.mvc.factory.FullHttpResponseFactory;
import com.github.feiyongjing.service.spring.core.mvc.factory.RequestHandlerFactory;
import com.github.feiyongjing.service.spring.core.mvc.handler.RequestHandler;
import com.github.feiyongjing.service.spring.core.mvc.util.UrlUtil;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.util.AsciiString;

public class HttpServerHandler extends SimpleChannelInboundHandler<FullHttpRequest>
{
    private static final String FAVICON_ICO = "/favicon.ico";
    private static final AsciiString CONNECTION = AsciiString.cached("Connection");
    private static final AsciiString KEEP_ALIVE = AsciiString.cached("keep-alive");

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest fullHttpRequest) {
//        log.info("Handle http request:{}", fullHttpRequest);
        String uri = fullHttpRequest.uri();
        if (uri.equals(FAVICON_ICO)) {
            return;
        }
        RequestHandler requestHandler = RequestHandlerFactory.get(fullHttpRequest.method());
        FullHttpResponse fullHttpResponse;
        try {
            // 处理请求
            fullHttpResponse = requestHandler.handle(fullHttpRequest);
        } catch (Throwable e) {
            // 处理请求失败，返回错误响应
            String requestPath = UrlUtil.getRequestPath(fullHttpRequest.uri());
            fullHttpResponse = FullHttpResponseFactory.getErrorResponse(requestPath, e.toString(), HttpResponseStatus.INTERNAL_SERVER_ERROR);
        }
        boolean keepAlive = HttpUtil.isKeepAlive(fullHttpRequest);
        if (!keepAlive) {
            ctx.write(fullHttpResponse).addListener(ChannelFutureListener.CLOSE);
        } else {
            fullHttpResponse.headers().set(CONNECTION, KEEP_ALIVE);
            ctx.write(fullHttpResponse);
        }
    }

    /**
     * 管道出错后关闭程序上下文连接
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    /**
     * 刷新程序上下文连接
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }


}
