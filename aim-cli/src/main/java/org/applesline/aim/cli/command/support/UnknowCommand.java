package org.applesline.aim.cli.command.support;

import org.applesline.aim.cli.AimCli;
import org.applesline.aim.cli.command.AimCommand;
import org.applesline.aim.cli.command.annotation.Cmd;

/**
 * @author liuyaping
 * 创建时间：2020年05月10日
 */
@Cmd(value = "unknow",description = "unknow command,that means not supprted by aim in current")
public class UnknowCommand implements AimCommand {
    @Override
    public void execute(String[] params) {
        System.out.println("unknow command");
        System.out.print("[aim@"+ AimCli.loginName+"]#");
    }
}
