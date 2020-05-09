package org.applesline.aim.server.handler.http;

import org.applesline.aim.server.handler.http.annotation.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author liuyaping
 * 创建时间：2020年05月08日
 */
public class HttpRouter {

    private static final Logger log = LoggerFactory.getLogger(HttpRouter.class);
    private static final Map<String,RouteHandler> ROUTES_MAP = new ConcurrentHashMap<>();

    public static final HttpRouter router = new HttpRouter();

    private HttpRouter() {}

    public static HttpRouter init(Properties config) {
        String[] basePackages = config.getProperty("aim.server.http.basePackage").split(",");
        List<Class<?>> classes = new ArrayList<>();
        for (String basePackage : basePackages) {
            classes.addAll(PackageScanner.scan(basePackage));
        }

        log.info("init supported http interface");
        for (Class clz : classes) {
            if (clz.isAnnotationPresent(Router.class)) {
                Router router = (Router) clz.getDeclaredAnnotation(Router.class);
                try {
                    ROUTES_MAP.put(router.value(),(RouteHandler) clz.newInstance());
                } catch (Exception ex) {
                    log.error("Initialization httpRoute error ",ex);
                }
                log.info("http api [{}] handle by [{}]",router.value(),clz.getSimpleName());
            }
        }
        return router;
    }

    public RouteHandler getRouteHandler(String path) {
        return ROUTES_MAP.get(path);
    }

}
