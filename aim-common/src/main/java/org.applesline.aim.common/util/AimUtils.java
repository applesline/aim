package org.applesline.aim.common.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.applesline.aim.common.AimProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author liuyaping
 * 创建时间：2020年04月27日
 */
public class AimUtils {

    private static final Logger log = LoggerFactory.getLogger(AimUtils.class);

    public static final String MSG_DELIMITER = "\n";
    public static final Gson GSON = new GsonBuilder().create();

    public static String wrapJson(AimProtocol aimProtocol) {
        return GSON.toJson(aimProtocol)+MSG_DELIMITER;
    }

    public static byte[] toBytes(AimProtocol aimProtocol) {
        try {
            return wrapJson(aimProtocol).getBytes("utf-8");
        } catch (UnsupportedEncodingException ex) {
            log.error("unsupported encoding",ex);
        }
        return null;
    }

    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-","");
    }

    public static Map<String,String> attachments(String key, String value) {
        Map<String,String> attachments = new HashMap<>();
        attachments.put(key,value);
        return attachments;
    }

    public static boolean isEmptyParam(String... params) {
        for (String param : params) {
            if (param == null || "".equals(param)) {
                return true;
            }
        }
        return false;
    }

}
