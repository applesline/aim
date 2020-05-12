package org.applesline.aim.cli.command.annotation;

import java.lang.annotation.*;

/**
 * @author liuyaping
 * 创建时间：2020年05月10日
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Cmd {

    String value();

    String description();

}
