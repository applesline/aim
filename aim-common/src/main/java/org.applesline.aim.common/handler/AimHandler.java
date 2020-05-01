package org.applesline.aim.common.handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.applesline.aim.common.req.AimRequest;
import org.applesline.aim.common.resp.AimResponse;
import org.applesline.aim.common.util.AimUtils;

/**
 * @author liuyaping
 * 创建时间：2020年04月29日
 */
public class AimHandler extends ChannelInboundHandlerAdapter {

    protected static final String USER_DELIMITER = ",";

    protected static final Gson GSON = new GsonBuilder().create();

    protected void writeRequest(ChannelHandlerContext ctx, AimRequest aimRequest) {
        this.writeRequest(ctx,null,aimRequest);
    }

    protected void writeRequest(ChannelHandlerContext ctx,Channel ch,AimRequest aimRequest) {
        ByteBuf byteBuf = ctx.alloc().directBuffer();
        byteBuf.writeBytes(AimUtils.toBytes(aimRequest));
        if (ch != null) {
            ch.writeAndFlush(byteBuf);
        } else {
            ctx.channel().writeAndFlush(byteBuf);
        }
    }

    protected void writeResponse(ChannelHandlerContext ctx, AimResponse aimResponse) {
        this.writeResponse(ctx,null,aimResponse);
    }

    protected void writeResponse(ChannelHandlerContext ctx,Channel ch,AimResponse aimResponse) {
        ByteBuf byteBuf = ctx.alloc().directBuffer();
        byteBuf.writeBytes(AimUtils.toBytes(aimResponse));
        if (ch != null) {
            ch.writeAndFlush(byteBuf);
        } else {
            ctx.channel().writeAndFlush(byteBuf);
        }
    }

}
