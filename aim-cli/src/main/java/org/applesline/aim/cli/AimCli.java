package org.applesline.aim.cli;

import org.applesline.aim.cli.command.AimCommand;
import org.applesline.aim.cli.command.CommandSelector;
import org.applesline.aim.common.constants.AimConstants;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * @author liuyaping
 * 创建时间：2020年05月10日
 */
public class AimCli {

    public static String loginName = AimConstants.NO_LOGIN;

    public static void main(String[] args) {
        CommandSelector commandSelector = CommandSelector.newCommandSelector();

        while (true) {
            System.out.print("[aim@"+loginName+"]#");
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (!"".equals(line)) {
                    String msg = "";
                    if (line.contains("'")) {
                        msg = line.substring(line.indexOf("'")+1,line.length()-1);
                        line = line.substring(0,line.indexOf("'"));
                    }
                    List<String> inputs = Arrays.asList(line.trim().split(" "))
                            .stream()
                            .filter((s)->{return !"".equals(s);})
                            .collect(Collectors.toList());

                    if (!"".equals(msg)) {
                        inputs.add(msg);
                    }
                    String[] params = new String[inputs.size()-1];
                    for (int i=1;i<inputs.size();i++) {
                        params[i-1] = inputs.get(i);
                    }
                    commandSelector.select(inputs.get(0)).execute(params);
                } else {
                    System.out.print("[aim@"+loginName+"]#");
                }
            }
        }
    }

}
