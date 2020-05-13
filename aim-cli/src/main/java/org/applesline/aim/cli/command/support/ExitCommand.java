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
@Cmd(value = "exit",description = "exit aim-cli,see exit command for details")
public class ExitCommand extends AbstractCliCommand implements AimCommand {
    @Override
    public void execute(String[] params) {
        Map<String,String> attachments = AimUtils.attachments(AimConstants.FROM,(String)holder.get().get(AimConstants.LOGIN_NAME));
        attachments.put(AimConstants.CLIENT_TYPE, ClientType.AIM_CLI);
        attachments.put(AimConstants.COMMAND, SupportedCmd.LOGOUT_CMD);

        Channel channel = (Channel) holder.get().get("channel");
        ByteBuf byteBuf = channel.alloc().directBuffer();
        AimRequest request = new AimRequest.Builder()
                .type(MessageType.Command.code)
                .sessionId((String)holder.get().get("sessionId"))
                .attactments(attachments)
                .build();
        byteBuf.writeBytes(AimUtils.toBytes(request));
        channel.writeAndFlush(byteBuf);
        System.exit(0);
    }
}
