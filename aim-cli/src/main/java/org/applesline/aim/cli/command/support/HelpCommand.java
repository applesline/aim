package org.applesline.aim.cli.command.support;

import org.applesline.aim.cli.AimCli;
import org.applesline.aim.cli.command.AimCommand;
import org.applesline.aim.cli.command.CommandSelector;
import org.applesline.aim.cli.command.annotation.Cmd;

import java.util.Map;

/**
 * @author liuyaping
 * 创建时间：2020年05月10日
 */
@Cmd(value = "help",description = "show all command that aim supported for users on current")
public class HelpCommand implements AimCommand {

    @Override
    public void execute(String[] params) {
        for (Map.Entry<String,String> cmd : CommandSelector.newCommandSelector().allCmd().entrySet()) {
            System.out.println("------------------------------------------------------------------------------------------");
            String keySpacke = "";
            for (int i=0;i<10-cmd.getKey().length();i++) {
                keySpacke+=" ";
            }
            String valueSpace = "";
            for (int i=0;i<73-cmd.getValue().length();i++) {
                valueSpace+=" ";
            }
            System.out.println("| "+cmd.getKey()+keySpacke+" | "+cmd.getValue()+valueSpace+" |");
        }
        System.out.println("------------------------------------------------------------------------------------------");
        System.out.print("[aim@"+ AimCli.loginName+"]#");
    }
}
