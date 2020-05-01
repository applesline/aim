package org.applesline.aim.server.command;

import org.applesline.aim.common.AimException;
import org.applesline.aim.server.command.support.LoginCommand;
import org.applesline.aim.server.command.support.LogoutCommand;
import org.applesline.aim.server.command.support.UserListCommand;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author liuyaping
 * 创建时间：2020年04月30日
 */
public class CommandChooser {

    public static final Map<String,ICommand> supportCmds = new ConcurrentHashMap<>();

    static {
        supportCmds.put("loginCmd",new LoginCommand());
        supportCmds.put("logoutCmd",new LogoutCommand());
        supportCmds.put("userlistCmd",new UserListCommand());
    }

    public ICommand choose(String cmd) {
        check(cmd);
        return supportCmds.get(cmd);
    }


    public static void addCmd(String name,ICommand command) {
        supportCmds.put(name,command);
    }

    public void check(String cmd) {
        if (!supportCmds.containsKey(cmd)) {
            throw new AimException("unsupport command ["+cmd+"]");
        }
    }

}
