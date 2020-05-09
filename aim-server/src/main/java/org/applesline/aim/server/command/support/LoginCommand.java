package org.applesline.aim.server.command.support;

import io.netty.channel.Channel;
import org.applesline.aim.common.constants.MessageType;
import org.applesline.aim.common.req.AimRequest;
import org.applesline.aim.common.resp.AimResponse;
import org.applesline.aim.common.util.AimUtils;
import org.applesline.aim.server.command.AbstractCommand;
import org.applesline.aim.server.command.ICommand;
import org.applesline.aim.server.storage.DataCenter;

/**
 * @author liuyaping
 * 创建时间：2020年04月30日
 */
public class LoginCommand extends AbstractCommand implements ICommand {

    @Override
    public AimResponse execute(Channel channel,AimRequest aimRequest) {
        String uuid = AimUtils.uuid();
        String loginName = aimRequest.getAttactments().get("loginName");
        String welcome = "【系统消息】欢迎 ["+loginName+"] 进入聊天室 ";
        log.info(welcome);
        DataCenter.add(uuid,loginName,channel);
        AimResponse aimResponse = new AimResponse.Builer()
                .code(200)
                .type(MessageType.Login.code)
                .sessionId(uuid)
                .body(welcome)
                .build();
        return aimResponse;
    }
}
