package com.zyf.springmybatis.common;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 属性文件读取工具 **/
public class ConfigUtil {
    public static Logger log = LoggerFactory.getLogger("ConfigUtil");
    public static String separator = File.separator;

    /***
     * 默认在SRC下读取，生产系统在conf下读取
     *
     * @param propertiesFileName
     * @return
     * @throws IOException
     **/
    public static Properties getProperties(String propertiesFileName)
            throws IOException {
        InputStream in = null;
        Properties p = null;
        File configFile = null;

        /*** 当前路径的上一级找配置文件路径 ****/
        Properties props = System.getProperties();
        String userDir = props.getProperty("user.dir");
        configFile = new File(propertiesFileName);
        ConfigUtil.log.info("Config path=" + configFile.getAbsolutePath());
        // System.out.println("Config path="+configFile.getAbsolutePath());
        if (configFile.exists() == false) {
            String path = new File(userDir).getParentFile().getAbsolutePath()
                + ConfigUtil.separator + "conf" + ConfigUtil.separator
                + propertiesFileName;
            ConfigUtil.log.info("properties path:" + path);
            configFile = new File(path);
            if (configFile.exists() == false) {
                ConfigUtil.log.error("Config file does not exist["
                    + configFile.getAbsolutePath() + "]");
                if (configFile.createNewFile()) {
                    ConfigUtil.log.info("Create Config File["
                        + configFile.getAbsolutePath() + "] Success");
                } else {
                    ConfigUtil.log.error("Create Config File["
                        + configFile.getAbsolutePath() + "] Failed");
                    return null;
                }
            }
        }
        p = new Properties();
        in = new BufferedInputStream(new FileInputStream(
            configFile.getAbsolutePath()));
        p.load(in);
        in.close();
        return p;
    }

    public static File getConfigFile(String fileName) {
        Properties props = System.getProperties();
        String userDir = props.getProperty("user.dir");
        File configFile = new File(userDir + ConfigUtil.separator + "src"
            + ConfigUtil.separator + fileName);
        if (configFile.exists() == false) {
            configFile = new File(new File(userDir).getParentFile()
                .getAbsolutePath()
                + ConfigUtil.separator
                + "conf"
                + ConfigUtil.separator + fileName);
        }
        if (configFile.exists()) {
            return configFile;
        } else {
            return null;
        }
    }

}
