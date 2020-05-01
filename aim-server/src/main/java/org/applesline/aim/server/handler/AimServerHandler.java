package org.applesline.aim.server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import org.applesline.aim.common.constants.MessageType;
import org.applesline.aim.common.req.AimRequest;
import org.applesline.aim.common.resp.AimResponse;
import org.applesline.aim.server.command.CommandInvoker;
import org.applesline.aim.server.storage.DataCenter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * @author liuyaping
 * 创建时间：2020年04月25日
 */
public class AimServerHandler extends AimBaseHandler {

    protected CommandInvoker commandInvoker;

    public AimServerHandler() {
        commandInvoker = new CommandInvoker();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        commandInvoker.setChannel(ctx.channel());
        commandInvoker.setAimRequest((AimRequest)msg);
        AimRequest aimRequest = (AimRequest)msg;
        AimResponse aimResponse;
        System.out.println(GSON.toJson(aimRequest));
        switch (MessageType.getType(aimRequest.getType())) {
            case Login:
                AimResponse loginResponse = commandInvoker.invoker("loginCmd");
                AimResponse usersResponse = commandInvoker.invoker("userlistCmd");
                for (String user : DataCenter.USER_CHANNEL_MAP.keySet()) {
                    Channel ch = DataCenter.USER_CHANNEL_MAP.get(user);
                    writeResponse(ctx,ch,loginResponse);
                    writeResponse(ctx,ch,usersResponse);
                }
                break;
            case Command:
                String command = aimRequest.getAttactments().get("command");
                aimResponse = commandInvoker.invoker(command);
                writeResponse(ctx,aimResponse);
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
                aimResponse = new AimResponse.Builer().code(200)
                        .body(aimRequest.getBody())
                        .type(MessageType.Onchat.code)
                        .from(aimRequest.getAttactments().get("from"))
                        .sessionId(aimRequest.getSessionId())
                        .build();
                for (String user : users) {
                    writeResponse(ctx,DataCenter.USER_CHANNEL_MAP.get(user),aimResponse);
                }
                break;
            case Heartbeat:

                break;
            case Logout:
                AimResponse logoutResponse = commandInvoker.invoker("logoutCmd");
                usersResponse = commandInvoker.invoker("userlistCmd");
                for (String user : DataCenter.USER_CHANNEL_MAP.keySet()) {
                    Channel ch = DataCenter.USER_CHANNEL_MAP.get(user);
                    writeResponse(ctx,ch,logoutResponse);
                    writeResponse(ctx,ch,usersResponse);
                }
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
//        cause.printStackTrace();??
//        System.out.println("IM error "+cause.getMessage());
    }
}
