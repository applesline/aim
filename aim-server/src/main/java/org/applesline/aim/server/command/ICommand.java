package org.applesline.aim.server.command;

import io.netty.channel.Channel;
import org.applesline.aim.common.req.AimRequest;
import org.applesline.aim.common.resp.AimResponse;

/**
 * @author liuyaping
 * 创建时间：2020年04月30日
 */
public interface ICommand {

    AimResponse execute(Channel channel, AimRequest aimRequest);

}
