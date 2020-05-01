package org.applesline.aim.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import org.applesline.aim.common.codec.AimRequestDecoder;
import org.applesline.aim.server.handler.AimBaseHandler;
import org.applesline.aim.server.handler.AimServerHandler;

/**
 * @author liuyaping
 * 创建时间：2020年04月27日
 */
public class AimServer {

    public static void main(String[] args) {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup,workGroup)
                .option(ChannelOption.SO_BACKLOG,2014)
                .channel(NioServerSocketChannel.class)
                .childOption(ChannelOption.SO_KEEPALIVE,true)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    public void initChannel(NioSocketChannel ch){
                        ch.pipeline().addLast(new LineBasedFrameDecoder(4096));
                        ch.pipeline().addLast(new StringDecoder());
                        ch.pipeline().addLast(new AimRequestDecoder());
                        ch.pipeline().addLast(new AimBaseHandler());
                        ch.pipeline().addLast(new AimServerHandler());
                    }
                });
        try {
            System.out.println("server stared...");
            serverBootstrap.bind(8080).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
