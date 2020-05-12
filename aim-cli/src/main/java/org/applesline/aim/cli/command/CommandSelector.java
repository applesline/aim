package org.applesline.aim.cli.command;

import org.applesline.aim.cli.command.annotation.Cmd;
import org.applesline.aim.common.util.PackageScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author liuyaping
 * 创建时间：2020年05月10日
 */
public class CommandSelector {

    private final static Logger log = LoggerFactory.getLogger(CommandSelector.class);

    private final static String cmdBasePackage = "org.applesline.aim.cli.command.support";

    private final static Map<String,AimCommand> SUPPORTED_COMMANDS = new ConcurrentHashMap<>(16);

    private final static Map<String,String> COMMAND_DESCRIPTION = new ConcurrentHashMap<>(16);

    private final static CommandSelector cmdSelector = new CommandSelector();

    public static CommandSelector newCommandSelector() {
        return cmdSelector;
    }

    private CommandSelector() {
        List<Class<?>> classes = PackageScanner.scan(PackageScanner.PROJECT_AIM_CLI,cmdBasePackage);
        classes.forEach(clz->{
            if (clz.isAnnotationPresent(Cmd.class)) {
                Cmd cmd = (Cmd)clz.getAnnotation(Cmd.class);
                try {
                    SUPPORTED_COMMANDS.put(cmd.value(),(AimCommand) clz.newInstance());
                    COMMAND_DESCRIPTION.put(cmd.value(),cmd.description());
                } catch (InstantiationException e) {
                    log.error(e.getMessage(),e);
                } catch (IllegalAccessException e) {
                    log.error(e.getMessage(),e);
                }
            }
        });
    }

    public AimCommand select(String cmd) {
        if (!SUPPORTED_COMMANDS.containsKey(cmd)) {
            return SUPPORTED_COMMANDS.get("unknow");
        }
        return SUPPORTED_COMMANDS.get(cmd);
    }

    public Map<String,String> allCmd() {
        return new TreeMap<>(COMMAND_DESCRIPTION);
    }

}
