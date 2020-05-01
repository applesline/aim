package org.applesline.aim.common;

/**
 * @author liuyaping
 * 创建时间：2020年04月30日
 */
public class AimException extends RuntimeException {

    public AimException() {
        super();
    }

    public AimException(String message) {
        super(message);
    }

    public AimException(String message, Throwable cause) {
        super(message, cause);
    }

    public AimException(Throwable cause) {
        super(cause);
    }

    protected AimException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, false, false);
    }
}
