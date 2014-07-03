package com.zyf.springmybatis.common;

import java.io.StringWriter;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.runtime.RuntimeConstants;

public class VelocityUtil {

	static {
		initVM();
	}

	/**
	 * 外部提供方法类
	 * 
	 * @param map
	 * @param vmpath
	 * @return
	 */
	public static String getString(Map<String, Object> map, String vmpath) {
		try {
			VelocityContext context = new VelocityContext();
			Set<Entry<String, Object>> set = map.entrySet();
			for (Entry<String, Object> e : set) {
				context.put(e.getKey(), e.getValue());
			}
			return VelocityUtil.writeToString(vmpath, context);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 初始化velocity 方法
	 */
	private static void initVM() {
		try {
			Velocity
					.setProperty("class.resource.loader.class",
							"org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
			Velocity.setProperty(RuntimeConstants.RESOURCE_LOADER, "class");
			Velocity
					.setProperty(RuntimeConstants.COUNTER_NAME, "velocityCount");
			Velocity.setProperty(RuntimeConstants.COUNTER_INITIAL_VALUE, "0");
			Velocity.setProperty(RuntimeConstants.ENCODING_DEFAULT, "UTF-8");
			Velocity.setProperty(RuntimeConstants.INPUT_ENCODING, "UTF-8");
			Velocity.setProperty(RuntimeConstants.OUTPUT_ENCODING, "UTF-8");
			Velocity.setProperty(RuntimeConstants.OUTPUT_ENCODING, "UTF-8");
			Velocity.init();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String writeToString(String templateName,
			VelocityContext context) throws Exception {
		StringWriter sw = null;
		String res = null;
		try {
			Template t = Velocity.getTemplate(templateName, "UTF-8");
			sw = new StringWriter();
			t.merge(context, sw);
			res = sw.getBuffer().toString();
			return res;
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			if (sw != null)
				sw.close();
		}
	}
}
