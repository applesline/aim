package org.applesline.aim.common.constants;

/**
 * @author liuyaping
 * 创建时间：2020年04月27日
 */
public enum MessageType {

    Command((byte)1),
    Onchat((byte)2),
    Heartbeat((byte)3);

    public byte code;

    public static MessageType getType(byte type) {
        for (MessageType t : MessageType.values()) {
            if (t.code == type) {
                return t;
            }
        }
        return null;
    }

    MessageType(byte code) {
        this.code = code;
    }
}
