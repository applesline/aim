package org.applesline.aim.server.handler.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liuyaping
 * 创建时间：2020年05月08日
 */
public class PackageScanner {

    private static final Logger log = LoggerFactory.getLogger(PackageScanner.class);

    public static List<Class<?>> scan(String basePackage) {
        List<Class<?>> classes = new ArrayList<>();
        String rootDir = PackageScanner.class.getResource("/").getPath();
        basePackage = basePackage.replace(".", File.separator);
        File file = new File(rootDir+basePackage);
        doScan(file,classes);
        return classes;
    }

    public static void doScan(File file,List<Class<?>> classes) {
        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                doScan(f,classes);
            }
        } else {
            try {
                String classPath = file.getPath().split("classes")[1]
                        .replace(".class","")
                        .replace(File.separator,".")
                        .replace("/",".");
                classPath = classPath.substring(1,classPath.length());
                log.debug(classPath);
                Class<?> clazz = Class.forName(classPath);
                classes.add(clazz);
            } catch (ClassNotFoundException ex) {
               log.error(ex.getMessage(),ex);
            }
        }
    }

}
