package org.applesline.aim.cli.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author liuyaping
 * 创建时间：2020年05月10日
 *
 */
public class AbstractCliCommand {

    protected Logger log = LoggerFactory.getLogger(this.getClass());
    protected final static ThreadLocal<Map<String,Object>> holder = new ThreadLocal<>();

    public AbstractCliCommand() {
        Map<String,Object> map = new ConcurrentHashMap<>();
        holder.set(map);
    }

    public Map<String, Object> get() {
        return holder.get();
    }

}
