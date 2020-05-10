package org.applesline.aim.client.connect;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.applesline.aim.client.handler.AimClientHandler;
import org.applesline.aim.client.ClientFrame;
import org.applesline.aim.common.codec.AimResponseDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author liuyaping
 * 创建时间：2020年04月27日
 */
public class AimClient {

    private static final Logger log = LoggerFactory.getLogger(AimClient.class);

    private ClientFrame aimChatHome;

    public AimClient(ClientFrame aimChatHome) {
        this.aimChatHome = aimChatHome;
    }

    ExecutorService executor = Executors.newSingleThreadExecutor();

    public void connect(String host,int port,String nickname,int retrytimes) throws Exception {
        try {
            EventLoopGroup workerGroup = new NioEventLoopGroup();
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new LineBasedFrameDecoder(4096));
                    ch.pipeline().addLast(new StringDecoder(Charset.forName("utf-8")));
                    ch.pipeline().addLast(new AimResponseDecoder());
                    ch.pipeline().addLast(new AimClientHandler(ch,aimChatHome,nickname));
                }
            });
            ChannelFuture f = b.connect(host, port).sync();
            log.info("client started...");
        } finally {
//
        }
    }

}
