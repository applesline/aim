package org.applesline.aim.server.handler.http.support;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import org.applesline.aim.common.constants.AimConstants;
import org.applesline.aim.common.constants.MessageType;
import org.applesline.aim.common.resp.AimResponse;
import org.applesline.aim.common.util.AimUtils;
import org.applesline.aim.server.handler.http.RouteHandler;
import org.applesline.aim.server.handler.http.annotation.Router;
import org.applesline.aim.server.storage.DataCenter;


/**
 * @author liuyaping
 * 创建时间：2020年05月08日
 */
@Router("/chat")
public class ChatHandler implements RouteHandler {
    @Override
    public void handle(ChannelHandlerContext ctx, FullHttpRequest request) {
        ByteBuf byteBuf = request.content();
        byte[] bts = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bts);

        for (Channel ch : DataCenter.USER_CHANNEL_MAP.values()) {
           try {
               ByteBuf byteBuf1 = ch.alloc().directBuffer();
               AimResponse aimResponse = new AimResponse.Builder()
                       .code(200)
                       .type(MessageType.Onchat.code)
                       .attactments(AimUtils.attachments(AimConstants.LOGIN_NAME,"admin"))
                       .body(new String(bts,"utf8"))
                       .build();
               byteBuf1.writeBytes(AimUtils.toBytes(aimResponse));
               ch.writeAndFlush(byteBuf1);
           } catch (Exception ex) {}
        }

        FullHttpResponse response = new DefaultFullHttpResponse(
                HttpVersion.HTTP_1_1,
                HttpResponseStatus.OK);
        response.content().writeBytes(byteBuf);
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html; charset=UTF-8");
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }
}
