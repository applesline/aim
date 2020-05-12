package org.applesline.aim.cli.handler;

import io.netty.channel.ChannelHandlerContext;
import org.applesline.aim.cli.AimCli;
import org.applesline.aim.common.constants.AimConstants;
import org.applesline.aim.common.constants.ClientType;
import org.applesline.aim.common.constants.MessageType;
import org.applesline.aim.common.constants.SupportedCmd;
import org.applesline.aim.common.handler.AimHandler;
import org.applesline.aim.common.req.AimRequest;
import org.applesline.aim.common.resp.AimResponse;
import org.applesline.aim.common.util.AimUtils;

import java.util.List;
import java.util.Map;


/**
 * @author liuyaping
 * 创建时间：2020年05月10日
 */
public class AimCliHandler extends AimHandler {

    private String nickname;
    private Map<String,Object> holder;

    public AimCliHandler(Map<String,Object> holder,String nickname) {
        this.nickname = nickname;
        this.holder = holder;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Map<String,String> attachments = AimUtils.attachments(AimConstants.LOGIN_NAME, nickname);
        attachments.put(AimConstants.COMMAND,SupportedCmd.LOGIN_CMD);
        attachments.put(AimConstants.CLIENT_TYPE, ClientType.AIM_CLI);
        AimRequest aimRequest = new AimRequest.Builder()
                .type(MessageType.Command.code)
                .attactments(attachments)
                .build();
        writeRequest(ctx,aimRequest);
        ctx.fireChannelActive();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        AimResponse aimResponse = (AimResponse)msg;
        switch (MessageType.getType(aimResponse.getType())) {
            case Command:
                handleCommand(ctx,aimResponse);
                break;
            case Onchat:
                String from = aimResponse.getAttactments().get(AimConstants.FROM);
                String name = AimCli.loginName.equals(from)? "me":from;
                if (!AimCli.loginName.equals(from)) {
                    System.out.println();
                }
                System.out.println("["+name+"]:"+aimResponse.getBody().toString());
                System.out.print("[aim@"+AimCli.loginName+"]#");
                break;
            case Heartbeat:
                break;
        }
        ctx.fireChannelRead(msg);

    }


    private void handleCommand(ChannelHandlerContext ctx,AimResponse aimResponse) {
        Map<String,String> attachments = aimResponse.getAttactments();
        switch (attachments.get(AimConstants.COMMAND)) {
            case SupportedCmd.LOGIN_CMD:
                holder.put(AimConstants.CHANNEL,ctx.channel());
                holder.put(AimConstants.LOGIN_NAME,nickname);
                holder.put(AimConstants.SESSION_ID,aimResponse.getSessionId());
                if (AimConstants.NO_LOGIN.equals(AimCli.loginName)) {
                    AimCli.loginName = aimResponse.getAttactments().get(AimConstants.LOGIN_NAME);
                }

                System.out.println(aimResponse.getBody().toString());
                System.out.print("[aim@"+AimCli.loginName+"]#");
                break;
            case SupportedCmd.USERLIST_CMD:
                List<String> users = (List)aimResponse.getBody();
                System.out.println("当前在线用户数:["+users.size()+"],在线用户列表：");
                System.out.println("--------------------");
                for (String user : users) {
                    String charSpace = "";
                    for(int i=0;i<(18-charLength(user));i++) {
                        charSpace+= " ";
                    }
                    System.out.println("| "+user+charSpace+"|");
                    System.out.println("--------------------");
                }
                System.out.print("[aim@"+AimCli.loginName+"]#");
                break;
            case SupportedCmd.LOGOUT_CMD:
                System.out.println(aimResponse.getBody().toString());
                break;
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println();
        System.out.println(cause.getMessage());
        ctx.channel().close();
        System.exit(0);
    }

    private int charLength(String user) {
        int strLeng = user.length();
        int charLeng = user.getBytes().length;
        if (strLeng == charLeng) {
            return strLeng;
        }
        int y = (charLeng - strLeng) / 2;
        int x = strLeng - y;
        return 3 * y + x;
    }

}
