package org.applesline.aim.cli.command.support;

import com.beust.jcommander.JCommander;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.applesline.aim.cli.AimCli;
import org.applesline.aim.cli.command.AbstractCliCommand;
import org.applesline.aim.cli.command.AimCommand;
import org.applesline.aim.cli.command.annotation.Cmd;
import org.applesline.aim.cli.command.support.args.LoginArgs;
import org.applesline.aim.cli.initializer.AimChannelInitializer;

/**
 * @author liuyaping
 * 创建时间：2020年05月10日
 */
@Cmd(value = "login",description = "login aim to chat with others,see [login] command for details")
public class LoginCommand extends AbstractCliCommand implements AimCommand {
    @Override
    public void execute(String[] params) {
        LoginArgs loginArgs = new LoginArgs();
        JCommander commander = JCommander.newBuilder()
                .addObject(loginArgs)
                .build();
        try {
            commander.parse(params);
        } catch (Exception ex) {
            if (params.length > 1) {
                commander.usage();
                System.out.print("[aim@"+ AimCli.loginName+"]#");
                return;
            }
        }
        if (params.length == 0) {
            commander.usage();
            System.out.print("[aim@"+ AimCli.loginName+"]#");
        } else {
            try {
                EventLoopGroup workerGroup = new NioEventLoopGroup();
                Bootstrap b = new Bootstrap();
                b.group(workerGroup);
                b.channel(NioSocketChannel.class);
                b.option(ChannelOption.SO_KEEPALIVE, true);
                b.handler(new AimChannelInitializer(holder.get(),loginArgs.getName()==null?params[0]:loginArgs.getName()));
                ChannelFuture f = b.connect(loginArgs.getHost(), loginArgs.getPort()).sync();
            } catch (Exception ex){
                System.out.println(ex.getMessage());
            }
        }

    }
}
