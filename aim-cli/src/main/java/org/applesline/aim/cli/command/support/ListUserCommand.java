package org.applesline.aim.cli.command.support;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import org.applesline.aim.cli.command.AbstractCliCommand;
import org.applesline.aim.cli.command.AimCommand;
import org.applesline.aim.cli.command.annotation.Cmd;
import org.applesline.aim.common.constants.AimConstants;
import org.applesline.aim.common.constants.ClientType;
import org.applesline.aim.common.constants.MessageType;
import org.applesline.aim.common.constants.SupportedCmd;
import org.applesline.aim.common.req.AimRequest;
import org.applesline.aim.common.util.AimUtils;

import java.util.Map;

/**
 * @author liuyaping
 * 创建时间：2020年05月10日
 */
@Cmd(value = "listUser",description = "list all current online users,see [listUser] command for details")
public class ListUserCommand extends AbstractCliCommand implements AimCommand {
    @Override
    public void execute(String[] params) {

        Map<String,String> attachments = AimUtils.attachments(AimConstants.COMMAND, SupportedCmd.USERLIST_CMD);
        attachments.put(AimConstants.CLIENT_TYPE, ClientType.AIM_CLI);

        AimRequest aimRequest = new AimRequest.Builder()
                .type(MessageType.Command.code)
                .sessionId((String)holder.get().get(AimConstants.SESSION_ID))
                .attactments(attachments)
                .build();
        Channel channel = (Channel) holder.get().get(AimConstants.CHANNEL);
        ByteBuf byteBuf = channel.alloc().directBuffer();
        byteBuf.writeBytes(AimUtils.toBytes(aimRequest));
        channel.writeAndFlush(byteBuf);
    }
}
