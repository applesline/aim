package org.applesline.aim.server.command;

import io.netty.channel.Channel;
import org.applesline.aim.common.req.AimRequest;
import org.applesline.aim.common.resp.AimResponse;

/**
 * @author liuyaping
 * 创建时间：2020年04月30日
 */
public class CommandInvoker {

    private Channel channel;
    private AimRequest aimRequest;
    private static CommandChooser commandChooser = new CommandChooser();;

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public void setAimRequest(AimRequest aimRequest) {
        this.aimRequest =aimRequest;
    }

    public AimResponse invoker(String cmd) {
        return commandChooser.choose(cmd).execute(channel,aimRequest);
    }

}
