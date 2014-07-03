package com.zyf.springmybatis.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class AppinfoConfigureFactory {
	private static AppinfoConfigureFactory instance;
	// 资源文件路径
	private String path = "lookup.properties"; // resource路径
	private Properties config = new Properties();
	private Logger logger = Logger.getLogger(AppinfoConfigureFactory.class);
	static {
		instance = getInstance();
	}

	private AppinfoConfigureFactory() {
	}

	public static AppinfoConfigureFactory getInstance() {
		if (instance == null) {
			synchronized (AppinfoConfigureFactory.class) {
				if (instance == null) {
					instance = new AppinfoConfigureFactory();
					instance.initConfig();
				}
			}
		}
		return instance;
	}

	private void initConfig() {
		InputStream in = null;
		in = AppinfoConfigureFactory.class.getClassLoader()
				.getResourceAsStream(path);
		if (in == null) {
			logger.warn(path + "配置文件不存");
			throw new RuntimeException(path + "配置文件不存");
		}
		try {
			config.load(in);
		} catch (IOException e) {
			e.printStackTrace();
			logger.warn("读取配置文件失败");
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
		Object val = config.get(name);
		if (val != null)
			return ((String) val);
		else
			return null;
	}

	public static String getValue(String name) {

		return instance.getText(name);
	}

}
