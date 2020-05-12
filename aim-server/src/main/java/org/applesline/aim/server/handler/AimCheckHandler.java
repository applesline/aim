package org.applesline.aim.server.handler;

import io.netty.channel.ChannelHandlerContext;
import org.applesline.aim.common.AimException;
import org.applesline.aim.common.constants.AimConstants;
import org.applesline.aim.common.constants.MessageType;
import org.applesline.aim.common.constants.SupportedCmd;
import org.applesline.aim.common.handler.AimHandler;
import org.applesline.aim.common.req.AimRequest;

/**
 * @author liuyaping
 * 创建时间：2020年04月30日
 */
public class AimCheckHandler extends AimHandler {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        AimRequest aimRequest = (AimRequest)msg;
        if (!(MessageType.getType(aimRequest.getType()) == MessageType.Command
                && SupportedCmd.LOGIN_CMD.equals(aimRequest.getAttactments().get(AimConstants.COMMAND)))
                && aimRequest.getSessionId() == null) {
            throw new AimException("sessionId is null");
        }

        ctx.fireChannelRead(msg);
    }

}
