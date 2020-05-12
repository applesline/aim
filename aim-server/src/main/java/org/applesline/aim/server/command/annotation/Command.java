package org.applesline.aim.server.command.annotation;

import java.lang.annotation.*;

/**
 * @author liuyaping
 * 创建时间：2020年05月12日
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Command {

    String value();

}
