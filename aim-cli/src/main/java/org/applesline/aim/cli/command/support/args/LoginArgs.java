package org.applesline.aim.cli.command.support.args;

import com.beust.jcommander.Parameter;
import org.applesline.aim.common.util.AimUtils;

/**
 * @author liuyaping
 * 创建时间：2020年05月10日
 */
public class LoginArgs {

    @Parameter(names = {"--host","-h"},description = "remote server address,default localhost")
    private String host;
    @Parameter(names = {"--port","-p"},description = "remote server port,default 9658")
    private Integer port;
    @Parameter(names = {"--name","-n"},description = "your login nickname")
    private String name;

    public String getHost() {
        if (AimUtils.isEmptyParam(host)) {
            host = "localhost";
        }
        return host;
    }

    public int getPort() {
        if (port == null) {
            port = 9658;
        }
        return port;
    }

    public String getName() {
        return name;
    }
}
