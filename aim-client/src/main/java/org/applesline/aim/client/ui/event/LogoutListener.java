package org.applesline.aim.client.ui.event;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import org.applesline.aim.client.ClientFrame;
import org.applesline.aim.common.constants.MessageType;
import org.applesline.aim.common.req.AimRequest;
import org.applesline.aim.common.util.AimUtils;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author liuyaping
 * 创建时间：2020年04月30日
 */
public class LogoutListener extends MouseAdapter {

    private ClientFrame aimChatHome;

    public LogoutListener(ClientFrame aimChatHome, Channel channel) {
        this.aimChatHome = aimChatHome;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (aimChatHome.getSessionId() == null) {
            return;
        }
        ByteBuf byteBuf = aimChatHome.getChannel().alloc().directBuffer();
        AimRequest request = new AimRequest.Builder()
                .type(MessageType.Logout.code)
                .sessionId(aimChatHome.getSessionId())
                .attactments(AimUtils.attachments("from",aimChatHome.getLoginName().getText()))
                .build();
        byteBuf.writeBytes(AimUtils.toBytes(request));
        try {
            ChannelFuture future = aimChatHome.getChannel().writeAndFlush(byteBuf).await();
        } catch (Exception ex){} finally {
            System.exit(0);
        }

    }
}
