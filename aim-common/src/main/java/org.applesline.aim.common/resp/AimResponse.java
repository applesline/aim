package org.applesline.aim.common.resp;

import org.applesline.aim.common.AimProtocol;

/**
 * @author liuyaping
 * 创建时间：2020年04月29日
 */
public class AimResponse extends AimProtocol {

    private int code;
    private String from;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    private AimResponse(Builer builer) {
        this.code = builer.code;
        this.type = builer.type;
        this.from = builer.from;
        this.sessionId = builer.sessionId;
        this.body = builer.body;
    }

    public static class Builer {
        private int code;
        private byte type;
        private String sessionId;
        private String from;
        private Object body;

        public Builer code(int code) {
            this.code = code;
            return this;
        }

        public Builer type(byte type) {
            this.type = type;
            return this;
        }

        public Builer sessionId(String sessionId) {
            this.sessionId = sessionId;
            return this;
        }

        public Builer from(String from) {
            this.from = from;
            return this;
        }

        public Builer body(Object body) {
            this.body = body;
            return this;
        }

        public AimResponse build() {
            return new AimResponse(this);
        }
    }

}
