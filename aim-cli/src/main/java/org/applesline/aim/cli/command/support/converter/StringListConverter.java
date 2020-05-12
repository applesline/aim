package org.applesline.aim.cli.command.support.converter;

import com.beust.jcommander.IStringConverter;

import java.util.Arrays;
import java.util.List;

/**
 * @author liuyaping
 * 创建时间：2020年05月10日
 */
public class StringListConverter implements IStringConverter<List<String>> {
    @Override
    public List<String> convert(String to) {
        String[] users = to.split(",");
        return Arrays.asList(users);
    }

}
