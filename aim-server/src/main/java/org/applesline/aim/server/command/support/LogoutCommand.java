package org.applesline.aim.server.command.support;

import io.netty.channel.Channel;
import org.applesline.aim.common.constants.AimConstants;
import org.applesline.aim.common.constants.MessageType;
import org.applesline.aim.common.constants.SupportedCmd;
import org.applesline.aim.common.req.AimRequest;
import org.applesline.aim.common.resp.AimResponse;
import org.applesline.aim.common.util.AimUtils;
import org.applesline.aim.server.command.AbstractCommand;
import org.applesline.aim.server.command.ICommand;
import org.applesline.aim.server.command.annotation.Command;
import org.applesline.aim.server.storage.DataCenter;

/**
 * @author liuyaping
 * 创建时间：2020年04月30日
 */
@Command(SupportedCmd.LOGOUT_CMD)
public class LogoutCommand extends AbstractCommand implements ICommand {
    @Override
    public AimResponse execute(Channel channel,AimRequest aimRequest) {
        DataCenter.remove(aimRequest.getSessionId());

        String logoutMsg = "【系统消息】["+aimRequest.getAttactments().get("from")+"] 退出聊天室 ";
        log.info(logoutMsg);
        return new AimResponse.Builder()
                .code(200)
                .type(MessageType.Command.code)
                .attactments(AimUtils.attachments(AimConstants.COMMAND, SupportedCmd.LOGOUT_CMD))
                .sessionId(aimRequest.getSessionId())
                .body(logoutMsg)
                .build();
    }
}
