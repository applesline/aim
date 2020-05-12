package org.applesline.aim.cli.initializer;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import org.applesline.aim.cli.handler.AimCliHandler;
import org.applesline.aim.common.codec.AimResponseDecoder;

import java.nio.charset.Charset;
import java.util.Map;

/**
 * @author liuyaping
 * 创建时间：2020年05月10日
 */
public class AimChannelInitializer extends ChannelInitializer<SocketChannel> {

    private String loginName;
    private SocketChannel channel;
    private Map<String,Object> holder;

    public AimChannelInitializer(Map<String,Object> holder,String loginName) {
        this.holder = holder;
        this.loginName = loginName;
    }

    public SocketChannel getChannel() {
        return channel;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast(new LineBasedFrameDecoder(4096));
        ch.pipeline().addLast(new StringDecoder(Charset.forName("utf-8")));
        ch.pipeline().addLast(new AimResponseDecoder());
        ch.pipeline().addLast(new AimCliHandler(holder,loginName));
        this.channel = ch;

    }
}
