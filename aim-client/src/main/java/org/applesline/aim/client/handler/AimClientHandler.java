package org.applesline.aim.client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.SocketChannel;
import org.applesline.aim.client.ClientFrame;
import org.applesline.aim.common.constants.AimConstants;
import org.applesline.aim.common.constants.MessageType;
import org.applesline.aim.common.constants.SupportedCmd;
import org.applesline.aim.common.handler.AimHandler;
import org.applesline.aim.common.req.AimRequest;
import org.applesline.aim.common.resp.AimResponse;
import org.applesline.aim.common.util.AimUtils;

import javax.swing.*;
import java.util.List;
import java.util.Map;

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
        Map<String,String> attachments = AimUtils.attachments(AimConstants.LOGIN_NAME, nickname);
        attachments.put(AimConstants.COMMAND,SupportedCmd.LOGIN_CMD);
        AimRequest aimRequest = new AimRequest.Builder()
                .type(MessageType.Command.code)
                .attactments(attachments)
                .build();
        writeRequest(ctx,aimRequest);
        ctx.fireChannelActive();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        AimResponse aimResponse = (AimResponse)msg;
        log.info(GSON.toJson(aimResponse));
        Map<String,String> attachments = aimResponse.getAttactments();
        switch (MessageType.getType(aimResponse.getType())) {
            case Command:
                handleCommand(ctx,aimResponse);
                break;
            case Onchat:
                String from = attachments.get(AimConstants.FROM);
                String name = from.equalsIgnoreCase(aimFrame.getLoginName().getText())? "我": from;
                aimFrame.getChatArea().append("【"+name+"】\n"+aimResponse.getBody().toString()+"\n");
                break;
            case Heartbeat:
                break;
        }
        ctx.fireChannelRead(msg);

    }

    private void handleCommand(ChannelHandlerContext ctx,AimResponse aimResponse) {
        switch (aimResponse.getAttactments().get(AimConstants.COMMAND)) {
            case SupportedCmd.LOGIN_CMD:
                aimFrame.getChatArea().append(aimResponse.getBody().toString()+"\n");
                aimFrame.setSessionId(aimResponse.getSessionId());

                AimRequest aimRequest = new AimRequest.Builder()
                        .type(MessageType.Command.code)
                        .sessionId(aimResponse.getSessionId())
                        .attactments(AimUtils.attachments(AimConstants.COMMAND, SupportedCmd.USERLIST_CMD))
                        .build();
                writeRequest(ctx,aimRequest);
                break;
            case SupportedCmd.USERLIST_CMD:
                List<String> users = (List)aimResponse.getBody();
                JComboBox comboBox = aimFrame.getComboBox();
                comboBox.removeAllItems();
                comboBox.addItem("ALL");
                for (String user : users) {
                    comboBox.addItem(user);
                }
                break;
            case SupportedCmd.LOGOUT_CMD:
                aimFrame.getChatArea().append(aimResponse.getBody().toString()+"\n");
                break;
        }
    }

}
