package org.applesline.aim.client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.SocketChannel;
import org.applesline.aim.client.ClientFrame;
import org.applesline.aim.common.constants.MessageType;
import org.applesline.aim.common.handler.AimHandler;
import org.applesline.aim.common.req.AimRequest;
import org.applesline.aim.common.resp.AimResponse;
import org.applesline.aim.common.util.AimUtils;

import javax.swing.*;
import java.util.List;

/**
 * @author liuyaping
 * 创建时间：2020年04月28日
 */
public class AimClientHandler extends AimHandler {

    private ClientFrame aimFrame;
    private String nickname;
    private SocketChannel channel;

    public AimClientHandler(SocketChannel channel, ClientFrame aimFrame, String nickname) {
        this.channel = channel;
        this.aimFrame = aimFrame;
        this.nickname = nickname;
        aimFrame.setChannel(channel);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        AimRequest aimRequest = new AimRequest.Builder()
                .type(MessageType.Login.code)
                .attactments(AimUtils.attachments("loginName", nickname))
                .build();
        writeRequest(ctx,aimRequest);
        ctx.fireChannelActive();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        AimResponse aimResponse = (AimResponse)msg;
        log.info(GSON.toJson(aimResponse));
        switch (MessageType.getType(aimResponse.getType())) {
            case Login:

                aimFrame.getChatArea().append(aimResponse.getBody().toString()+"\n");
                if (aimFrame.getSessionId() == null) {
                    aimFrame.setSessionId(aimResponse.getSessionId());
                }
                break;
            case Command:
                List<String> users = (List)aimResponse.getBody();
                JComboBox comboBox = aimFrame.getComboBox();
                comboBox.removeAllItems();
                comboBox.addItem("ALL");
                for (String user : users) {
                    comboBox.addItem(user);
                }
                break;
            case Onchat:
                String name = aimResponse.getFrom().equalsIgnoreCase(aimFrame.getLoginName().getText())? "我": aimResponse.getFrom();
                aimFrame.getChatArea().append("【"+name+"】\n"+aimResponse.getBody().toString()+"\n");
                break;
            case Heartbeat:
                break;
            case Logout:
                aimFrame.getChatArea().append(aimResponse.getBody().toString()+"\n");
                break;
        }
        ctx.fireChannelRead(msg);

    }

}
