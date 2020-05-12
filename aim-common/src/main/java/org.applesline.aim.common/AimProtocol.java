package org.applesline.aim.common;

import java.util.Map;

/**
 * @author liuyaping
 * 创建时间：2020年04月29日
 */
public abstract class AimProtocol {

    protected final int ver = 0x20200511;
    protected byte type;
    protected String sessionId;
    protected Object body;
    protected Map<String,String> attactments;

    public int getVer() {
        return ver;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Map<String, String> getAttactments() {
        return attactments;
    }

    public void setAttactments(Map<String, String> attactments) {
        this.attactments = attactments;
    }
}
