package org.applesline.aim.common.codec;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.applesline.aim.common.req.AimRequest;
import org.applesline.aim.common.resp.AimResponse;

import java.util.List;

/**
 * @author liuyaping
 * 创建时间：2020年04月29日
 */
@Sharable
public class AimResponseDecoder extends MessageToMessageDecoder<String> {

    private static final Gson GSON = new GsonBuilder().create();

    @Override
    protected void decode(ChannelHandlerContext ctx, String msg, List<Object> out) throws Exception {
        out.add(GSON.fromJson(msg.toString(), AimResponse.class));
    }
}
