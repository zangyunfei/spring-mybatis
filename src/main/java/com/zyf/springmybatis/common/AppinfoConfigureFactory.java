package com.zyf.springmybatis.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppinfoConfigureFactory {
    private static AppinfoConfigureFactory instance;
    // 资源文件路径
    private String path = "lookup.properties"; // resource路径
    private Properties config = new Properties();
    private Logger logger = LoggerFactory
        .getLogger(AppinfoConfigureFactory.class);
    static {
        AppinfoConfigureFactory.instance = AppinfoConfigureFactory
            .getInstance();
    }

    private AppinfoConfigureFactory() {
    }

    public static AppinfoConfigureFactory getInstance() {
        if (AppinfoConfigureFactory.instance == null) {
            synchronized (AppinfoConfigureFactory.class) {
                if (AppinfoConfigureFactory.instance == null) {
                    AppinfoConfigureFactory.instance = new AppinfoConfigureFactory();
                    AppinfoConfigureFactory.instance.initConfig();
                }
            }
        }
        return AppinfoConfigureFactory.instance;
    }

    private void initConfig() {
        InputStream in = null;
        in = AppinfoConfigureFactory.class.getClassLoader()
            .getResourceAsStream(this.path);
        if (in == null) {
            this.logger.warn(this.path + "配置文件不存");
            throw new RuntimeException(this.path + "配置文件不存");
        }
        try {
            this.config.load(in);
        } catch (IOException e) {
            e.printStackTrace();
            this.logger.warn("读取配置文件失败");
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String getText(String name) {

        if (name == null || "".equals(name)) {
            throw new RuntimeException("获得资源的属性名称不能为空");
        }
        Object val = this.config.get(name);
        if (val != null) {
            return (String) val;
        } else {
            return null;
        }
    }

    public static String getValue(String name) {

        return AppinfoConfigureFactory.instance.getText(name);
    }

}
