package org.applesline.aim.server.handler.http.annotation;

import java.lang.annotation.*;

/**
 * @author liuyaping
 * 创建时间：2020年05月08日
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD})
public @interface Router {

    String value();
}
