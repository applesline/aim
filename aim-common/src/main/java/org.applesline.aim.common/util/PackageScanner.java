package org.applesline.aim.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author liuyaping
 * 创建时间：2020年05月08日
 */
public class PackageScanner {

    private static final Logger log = LoggerFactory.getLogger(PackageScanner.class);

    public static final String PROJECT_AIM_CLI = "aim-cli";
    public static final String PROJECT_AIM_SERVER = "aim-server";

    public static List<Class<?>> scan(String projectName,String basePackage) {
        List<Class<?>> classes = new ArrayList<>();
        String classRootPath = classRootPath(projectName);
        String basePath = basePackage.replace(".", File.separator);
        boolean isJar = isJar(classRootPath);

        if (isJar) {
            doScan(classRootPath,basePackage,classes);
        } else {
            doScan(new File(classRootPath+File.separator+basePath),classes);
        }
        return classes;
    }

    public static void doScan(File file,List<Class<?>> classes) {
        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                doScan(f,classes);
            }
        } else {
            try {
                String classPath = file.getPath();
                classPath = classPath.split("classes")[1];
                classPath = classPath.replace(".class","")
                        .replace("\\",".")
                        .replace("/",".")
                        .substring(1);
                log.debug(classPath);
                Class<?> clazz = Class.forName(classPath);
                classes.add(clazz);
            } catch (ClassNotFoundException ex) {
               log.error(ex.getMessage(),ex);
            }
        }
    }

    public static boolean isJar(String path) {
        return path.contains(".jar");
    }

    public static String classRootPath(String projectName) {
        for (String path : System.getProperty("java.class.path").split(";")) {
            if (path.contains(projectName)) {
                return path;
            }
        }
        return null;
    }

    public static void doScan(String path,String basePackage,List<Class<?>> classes) {
        try {
            JarFile jarFile = new JarFile(path);
            Enumeration<JarEntry> jarEntry = jarFile.entries();

            String name = "";
            while (jarEntry.hasMoreElements()) {
                name = jarEntry.nextElement().getName()
                        .replace("/",".")
                        .replace(File.separator,".");
                if (name.contains(basePackage) && !name.endsWith(".")) {
                    name = name.replace(".class","");
                    Class clz = Class.forName(name);
                    classes.add(clz);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }
    }

}
