package org.applesline.aim.server.handler;

import io.netty.channel.ChannelHandlerContext;
import org.applesline.aim.common.constants.AimConstants;
import org.applesline.aim.common.constants.ClientType;
import org.applesline.aim.common.constants.MessageType;
import org.applesline.aim.common.handler.AimHandler;
import org.applesline.aim.common.req.AimRequest;
import org.applesline.aim.common.resp.AimResponse;
import org.applesline.aim.common.util.AimUtils;
import org.applesline.aim.server.command.CommandInvoker;
import org.applesline.aim.server.storage.DataCenter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * @author liuyaping
 * 创建时间：2020年04月25日
 */
public class AimServerHandler extends AimHandler {

    protected CommandInvoker commandInvoker;

    public AimServerHandler() {
        commandInvoker = new CommandInvoker();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        AimRequest aimRequest = (AimRequest)msg;

        log.info(GSON.toJson(aimRequest));
        commandInvoker.setChannel(ctx.channel());
        commandInvoker.setAimRequest(aimRequest);

        switch (MessageType.getType(aimRequest.getType())) {
            case Command:
                String command = aimRequest.getAttactments().get(AimConstants.COMMAND);
                AimResponse aimResponse = commandInvoker.invoker(command);
                if (ClientType.AIM_CLI.equals(aimRequest.getAttactments().get(AimConstants.CLIENT_TYPE))) {
                    writeResponse(ctx,DataCenter.SESSION_CHANNEL_MAP.get(AimConstants.SESSION_ID),aimResponse);
                } else {
                    for (String user : DataCenter.USER_CHANNEL_MAP.keySet()) {
                        if (!DataCenter.USER_CLI.contains(user)) {
                            writeResponse(ctx,DataCenter.USER_CHANNEL_MAP.get(user),aimResponse);
                        }
                    }
                }
                break;
            case Onchat:
                String to = aimRequest.getAttactments().get("to");
                List<String> users = new ArrayList<>();
                if ("ALL".equalsIgnoreCase(to)) {
                    users.addAll(DataCenter.USER_CHANNEL_MAP.keySet());
                } else {
                    users.addAll(Arrays.asList(to.split(USER_DELIMITER)));
                    users.add(aimRequest.getAttactments().get("from"));
                }
                aimResponse = new AimResponse.Builder()
                        .code(200)
                        .body(aimRequest.getBody())
                        .type(MessageType.Onchat.code)
                        .sessionId(aimRequest.getSessionId())
                        .attactments(AimUtils.attachments(AimConstants.FROM,aimRequest.getAttactments().get(AimConstants.FROM)))
                        .build();
                for (String user : users) {
                    writeResponse(ctx,DataCenter.USER_CHANNEL_MAP.get(user),aimResponse);
                }
                break;
            case Heartbeat:
                break;
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.channel().close();
        log.error(cause.getMessage(),cause);
    }
}
