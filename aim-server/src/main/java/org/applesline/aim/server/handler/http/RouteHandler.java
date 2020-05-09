package org.applesline.aim.server.handler.http;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

/**
 * @author liuyaping
 * 创建时间：2020年05月08日
 */
public interface RouteHandler {

    void handle(ChannelHandlerContext ctx, FullHttpRequest request);
}
