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
@Command(SupportedCmd.USERLIST_CMD)
public class UserListCommand extends AbstractCommand implements ICommand {
    @Override
    public AimResponse execute(Channel channel, AimRequest aimRequest) {
        return new AimResponse.Builder()
                .code(200)
                .type(MessageType.Command.code)
                .attactments(AimUtils.attachments(AimConstants.COMMAND, SupportedCmd.USERLIST_CMD))
                .sessionId(aimRequest.getSessionId())
                .body(DataCenter.USER_CHANNEL_MAP.keySet())
                .build();
    }
}
