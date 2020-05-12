package org.applesline.aim.common.resp;

import org.applesline.aim.common.AimProtocol;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liuyaping
 * 创建时间：2020年04月29日
 */
public class AimResponse extends AimProtocol {

    private int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    private AimResponse(Builder builder) {
        this.code = builder.code;
        this.type = builder.type;
        this.sessionId = builder.sessionId;
        this.body = builder.body;
        this.attactments = builder.attactments;
    }

    public static class Builder {
        private int code;
        private byte type;
        private String sessionId;
        private Object body;
        private Map<String,String> attactments;

        public Builder code(int code) {
            this.code = code;
            return this;
        }

        public Builder type(byte type) {
            this.type = type;
            return this;
        }

        public Builder sessionId(String sessionId) {
            this.sessionId = sessionId;
            return this;
        }

        public Builder body(Object body) {
            this.body = body;
            return this;
        }

        public Builder attactments(Map<String, String> attactments) {
            this.attactments = attactments;
            return this;
        }

        public AimResponse build() {
            return new AimResponse(this);
        }
    }

}
