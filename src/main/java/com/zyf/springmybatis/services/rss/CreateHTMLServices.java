package com.zyf.springmybatis.services.rss;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.zyf.springmybatis.bean.UrlContentBean;
import com.zyf.springmybatis.common.CreateHTMLHelper;
import com.zyf.springmybatis.common.VelocityUtil;

@Service
public class CreateHTMLServices {
	private static final Logger log = Logger
			.getLogger(CreateHTMLServices.class);

	/**
	 * 拿到一个xml文件，生成hmtl文本<br/>
	 * 1.向爬虫服务获得xml文件 <br/>
	 * 2.分析后入库 <br/>
	 * 3.取得最大id，拼接url地址，存储 <br/>
	 */
	public void processorDetail() {
		String filePath = "c:/logs/";
		InputStream io;
		try {
			File f = new File(
					"C:/work/xingmai/workspace/lenovoPortalAdmin/src/main/resources/test.xml");
			io = new FileInputStream(f);
			List<UrlContentBean> list = CreateHTMLHelper.doXML(io);
			int i = 0;
			String fileName = "";
			for (UrlContentBean ucb : list) {
				Map m = new HashMap();
				m.put("title", ucb.getTitle());
				m.put("charset", ucb.getChartSet());
				m.put("content", ucb.getContent());
				String str = VelocityUtil.getString(m, "vm/detail.vm");
				i++;
				fileName = "" + i; // html文件路径加1
				if (!CreateHTMLHelper.createIndexFile(filePath + ucb.getRssID(),
						fileName, str))
					log.error("write HTML error " + filePath + i);
			}
		} catch (Exception e) {
			log.error("", e);
		}
	}

	/**
	 * 生成首页
	 */
	public void processorIndex(Map m) {
		String filePath = "c:/logs/";

		String str = VelocityUtil.getString(m, "vm/index.vm");
		if (!CreateHTMLHelper.createIndex(filePath, "index.html", str)) {
			log.error("createIndex error ! ");
		}
	}
}
