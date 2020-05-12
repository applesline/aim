package org.applesline.aim.server.command.support;

import io.netty.channel.Channel;
import org.applesline.aim.common.constants.AimConstants;
import org.applesline.aim.common.constants.ClientType;
import org.applesline.aim.common.constants.MessageType;
import org.applesline.aim.common.constants.SupportedCmd;
import org.applesline.aim.common.req.AimRequest;
import org.applesline.aim.common.resp.AimResponse;
import org.applesline.aim.common.util.AimUtils;
import org.applesline.aim.server.command.AbstractCommand;
import org.applesline.aim.server.command.ICommand;
import org.applesline.aim.server.command.annotation.Command;
import org.applesline.aim.server.storage.DataCenter;

import java.nio.charset.Charset;
import java.util.Map;

/**
 * @author liuyaping
 * 创建时间：2020年04月30日
 */
@Command(SupportedCmd.LOGIN_CMD)
public class LoginCommand extends AbstractCommand implements ICommand {

    @Override
    public AimResponse execute(Channel channel,AimRequest aimRequest) {
        String uuid = AimUtils.uuid();
        String loginName = aimRequest.getAttactments().get("loginName");
        String welcome = "【系统消息】欢迎 ["+loginName+"] 进入聊天室 ";
        boolean isCliUser = ClientType.AIM_CLI.equals(aimRequest.getAttactments().get(AimConstants.CLIENT_TYPE));
        DataCenter.add(isCliUser,uuid,loginName,channel);

        Map<String,String> attachments = AimUtils.attachments(AimConstants.COMMAND, SupportedCmd.LOGIN_CMD);
        attachments.put(AimConstants.LOGIN_NAME,loginName);

        AimResponse aimResponse = new AimResponse.Builder()
                .code(200)
                .type(MessageType.Command.code)
                .attactments(attachments)
                .sessionId(uuid)
                .body(welcome)
                .build();
        return aimResponse;
    }
}
