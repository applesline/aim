package org.applesline.aim.client.ui.event;

import io.netty.buffer.ByteBuf;
import org.applesline.aim.client.ClientFrame;
import org.applesline.aim.common.constants.AimConstants;
import org.applesline.aim.common.constants.MessageType;
import org.applesline.aim.common.req.AimRequest;
import org.applesline.aim.common.util.AimUtils;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;

/**
 * @author liuyaping
 * 创建时间：2020年04月28日
 */
public class ChatListener extends MouseAdapter {

    private ClientFrame aimFrame;

    public ChatListener(ClientFrame aimFrame) {
        this.aimFrame = aimFrame;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(aimFrame.getSessionId() == null) {
            return;
        }
        String msg = aimFrame.getInputArea().getText();
        if (AimUtils.isEmptyParam(msg)) {
            return;
        }
        String to = aimFrame.getComboBox().getSelectedItem().toString();
        if (aimFrame.getLoginName().getText().equalsIgnoreCase(to)) {
            JOptionPane.showMessageDialog(aimFrame,"不能给自己发信息");
            return;
        }
        ByteBuf byteBuf = aimFrame.getChannel().alloc().directBuffer();
        Map<String,String> attachments = AimUtils.attachments(AimConstants.TO,to);
        attachments.put(AimConstants.FROM,aimFrame.getLoginName().getText());
        AimRequest requestParam = new AimRequest
                .Builder()
                .type(MessageType.Onchat.code)
                .sessionId(aimFrame.getSessionId())
                .attactments(attachments)
                .body(msg)
                .build();
        byteBuf.writeBytes(AimUtils.toBytes(requestParam));
        aimFrame.getChannel().writeAndFlush(byteBuf);
    }
}
