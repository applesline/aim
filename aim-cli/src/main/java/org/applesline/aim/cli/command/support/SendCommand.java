package org.applesline.aim.cli.command.support;

import com.beust.jcommander.JCommander;
import io.netty.buffer.ByteBuf;
import io.netty.channel.socket.SocketChannel;
import org.applesline.aim.cli.AimCli;
import org.applesline.aim.cli.command.AbstractCliCommand;
import org.applesline.aim.cli.command.AimCommand;
import org.applesline.aim.cli.command.annotation.Cmd;
import org.applesline.aim.cli.command.support.args.SendArgs;
import org.applesline.aim.common.constants.AimConstants;
import org.applesline.aim.common.constants.ClientType;
import org.applesline.aim.common.constants.MessageType;
import org.applesline.aim.common.req.AimRequest;
import org.applesline.aim.common.util.AimUtils;

import java.util.Map;
import java.util.StringJoiner;

/**
 * @author liuyaping
 * 创建时间：2020年05月10日
 */
@Cmd(value = "send",description = "send message to other online users,see [send] command for details")
public class SendCommand extends AbstractCliCommand implements AimCommand {
    @Override
    public void execute(String[] params) {
        if(params.length > 4) {
            StringJoiner messsage = new StringJoiner(" ");
            String[] inputs = new String[4];
            for (int i=0;i<params.length;i++) {
                if (i>=3) {
                    messsage.add(params[i]);
                } else {
                    inputs[i] = params[i];
                }
            }
            inputs[3] = messsage.toString();
            params = inputs;
        }
        SendArgs sendArgs = new SendArgs();
        JCommander commander = JCommander.newBuilder()
                .addObject(sendArgs)
                .build();
        commander.setProgramName("send");
        try {
            commander.parse(params);
        } catch (Exception ex) {
            if (params.length > 1) {
                usage(commander);
                return;
            }
        }
        if (params.length == 0) {
            usage(commander);
        } else {
            SocketChannel channel = (SocketChannel) holder.get().get(AimConstants.CHANNEL);
            if (channel == null) {
                System.out.println("please login before you send message,see [login] command for details");
                return;
            }
            ByteBuf byteBuf = channel.alloc().directBuffer();
            Map<String,String> attachments = AimUtils.attachments(AimConstants.TO,sendArgs.getTo());
            attachments.put(AimConstants.FROM,(String)holder.get().get(AimConstants.LOGIN_NAME));
            attachments.put(AimConstants.CLIENT_TYPE, ClientType.AIM_CLI);
            attachments.put(AimConstants.TO,sendArgs.getTo());
            AimRequest requestParam = new AimRequest
                    .Builder()
                    .type(MessageType.Onchat.code)
                    .sessionId((String)holder.get().get(AimConstants.SESSION_ID))
                    .attactments(attachments)
                    .body(sendArgs.getMsg()== null? params[0]:sendArgs.getMsg())
                    .build();
            byteBuf.writeBytes(AimUtils.toBytes(requestParam));
            channel.writeAndFlush(byteBuf);
        }

    }

    private void usage(JCommander commander) {
        commander.usage();
        System.out.println("for example: send 'hello world !'");
        System.out.print("[aim@"+ AimCli.loginName+"]#");
    }
}
