package org.applesline.aim.common.req;

import org.applesline.aim.common.AimProtocol;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liuyaping
 * 创建时间：2020年04月27日
 */
public class AimRequest extends AimProtocol {

    private AimRequest(Builder builder) {
        this.sessionId = builder.sessionId;
        this.type = builder.type;
        this.body = builder.body;
        this.attactments = builder.attactments;
    }

    public static class Builder {
        private String sessionId;
        private byte type;
        private Object body;
        private Map<String,String> attactments = new HashMap<String,String>();

        public Builder sessionId(String sessionId) {
            this.sessionId = sessionId;
            return this;
        }

        public Builder type(byte type) {
            this.type = type;
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

        public AimRequest build() {
            return new AimRequest(this);
        }
    }

}
