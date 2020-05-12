package org.applesline.aim.cli.command;

import io.netty.channel.socket.SocketChannel;

/**
 * @author liuyaping
 * 创建时间：2020年05月10日
 */
public class ChannelHolder {

    ThreadLocal<SocketChannel> holder = new ThreadLocal<>();

    public void set(SocketChannel socketChannel) {
        holder.set(socketChannel);
    }

    public SocketChannel get() {
        return holder.get();
    }

}
