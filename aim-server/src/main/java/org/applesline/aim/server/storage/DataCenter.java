package org.applesline.aim.server.storage;

import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author liuyaping
 * 创建时间：2020年04月30日
 */
public class DataCenter {

    /**
     * Map<sessionId,channel>
     */
    public static final Map<String,Channel> SESSION_CHANNEL_MAP = new ConcurrentHashMap<>(64);
    /**
     * Map<sessionId,user>
     */
    public static final Map<String,String> SESSION_USER_MAP = new ConcurrentHashMap<String,String>(128);

    public static final Map<String,Channel> USER_CHANNEL_MAP = new ConcurrentHashMap<>(128);

    public static void add(String sessionId,String user,Channel channel) {
        synchronized (DataCenter.class) {
            SESSION_CHANNEL_MAP.put(sessionId,channel);
            USER_CHANNEL_MAP.put(user,channel);
            SESSION_USER_MAP.put(sessionId,user);
        }
    }

    public static void remove(String sessionId) {
        synchronized (DataCenter.class) {
            SESSION_CHANNEL_MAP.get(sessionId).close();
            SESSION_CHANNEL_MAP.remove(sessionId);
            String user = SESSION_USER_MAP.remove(sessionId);
            USER_CHANNEL_MAP.remove(user);
        }
    }
}
