package org.applesline.aim.cli.command.support.args;

import com.beust.jcommander.Parameter;
import org.applesline.aim.common.util.AimUtils;

/**
 * @author liuyaping
 * 创建时间：2020年05月10日
 */
public class SendArgs {

//    @Parameter(names = {"--to","-t"},listConverter = StringListConverter.class,description = "input remote server address ,for example:localhost")
    @Parameter(names = {"--to","-t"},description = "message send to the user who was specified,default to ALL if not specified")
    private String to;
    @Parameter(names = {"--msg","-m"},description = "the message you want to send,user single quotes('') to wrap the message")
    private String msg;

    public String getTo() {
        if (AimUtils.isEmptyParam(to)) {
            to = "ALL";
        }
        return to;
    }

    public String getMsg() {
        return msg;
    }
}
