package org.applesline.aim.server.handler.http;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author liuyaping
 * 创建时间：2020年05月08日
 */
public class AimHttpHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private static final Logger log = LoggerFactory.getLogger(AimHttpHandler.class);

    private HttpRouter httpRouter;

    public AimHttpHandler(HttpRouter httpRouter) {
        this.httpRouter = httpRouter;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        log.info("request method {},uri {}",request.method(),request.uri());
        if (request.method()== HttpMethod.POST) {
            httpRouter.getRouteHandler(request.uri()).handle(ctx,request);
        } else {
            FullHttpResponse response = new DefaultFullHttpResponse(
                    HttpVersion.HTTP_1_1,
                    HttpResponseStatus.BAD_REQUEST);
            ByteBuf byteBuf = ctx.alloc().directBuffer();
            byteBuf.writeBytes(("unsupport method ["+request.method().name()+"]").getBytes());
            response.content().writeBytes(byteBuf);
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html; charset=UTF-8");
            ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
        }
    }
}
