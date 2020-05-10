package org.applesline.aim.common.req;

import org.applesline.aim.common.AimProtocol;
import org.applesline.aim.common.util.AimUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

/**
 * @author liuyaping
 * 创建时间：2020年04月27日
 */
public class AimRequest extends AimProtocol {

    private Map<String,String> attactments = new HashMap<String,String>();

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public Map<String, String> getAttactments() {
        return attactments;
    }

    public void setAttactments(Map<String, String> attactments) {
        this.attactments = attactments;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    private AimRequest(Builder builder) {
        this.sessionId = builder.sessionId;
        this.type = builder.type;
        this.attactments = builder.attactments;
        this.body = builder.body;
    }

    public static class Builder {
        private String sessionId;
        private byte type;
        private Map<String,String> attactments = new HashMap<String,String>();
        private Object body;

        public Builder sessionId(String sessionId) {
            this.sessionId = sessionId;
            return this;
        }

        public Builder type(byte type) {
            this.type = type;
            return this;
        }

        public Builder attactments(Map<String, String> attactments) {
            this.attactments = attactments;
            return this;
        }

        public Builder body(Object body) {
            this.body = body;
            return this;
        }

        public AimRequest build() {
            return new AimRequest(this);
        }
    }

}
