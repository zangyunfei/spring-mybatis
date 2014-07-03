package com.zyf.springmybatis.job;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zyf.springmybatis.bean.ChannelInfo;
import com.zyf.springmybatis.bean.RssIndexBean;
import com.zyf.springmybatis.bean.RssIndexInfoBean;
import com.zyf.springmybatis.bean.UrlContentBean;
import com.zyf.springmybatis.cache.ChannelInfoCache;
import com.zyf.springmybatis.cache.RssIndexCache;
import com.zyf.springmybatis.common.CreateHTMLHelper;
import com.zyf.springmybatis.common.LookUpConfig;
import com.zyf.springmybatis.common.VelocityUtil;
import com.zyf.springmybatis.po.RssList;

/**
 * rss 订阅运行主程序
 * 
 * @author zyf
 * 
 */
@Service
public class RssDetailJob {
	private static final Logger log = Logger.getLogger(RssDetailJob.class);
	@Autowired
	private RssIndexHtmlJob rssIndexHtmlJob;
	@Autowired
	private ChannelInfoCache channelInfoCache;
	@Autowired
	private RssIndexCache rssIndexCache;
	/**
	 * 生成html页面路径
	 */
	private String detailHtmlPath = LookUpConfig.htmlPath;
	/**
	 * xml源文件路径
	 */
	private String detailXMLPath = LookUpConfig.xmlPath;
	// 每个频道 最大抓取条数为20个
	private int IndexMaxNum = 10;
	static {
		LookUpConfig.init();
	}

	/**
	 * 取得频道列表，循环取最新的xml文件
	 */
	public void processor() {
		try {
			channelInfoCache.refreshChannel(); // 没次都更新一次缓存
			Set<String> set = channelInfoCache.getAllKey();
			log.info("update channel info ,the  Channel size is : "
					+ set.size());
			for (String channelCode : set) {
				// 取得频道更新信息
				ChannelInfo info = channelInfoCache.getObject(channelCode);
				/**
				 * 组装首页待更新信息数据结构
				 * */
				RssIndexInfoBean rssInfoBean = rssIndexCache
						.getObject(channelCode);
				if (rssInfoBean == null) {
					rssInfoBean = new RssIndexInfoBean();
				}
				rssInfoBean.setChannelCode(channelCode);
				rssInfoBean.setChannelName(info.getChannelName());
				List<RssIndexBean> newIndexList = new ArrayList<RssIndexBean>();
				rssInfoBean.setNewRssList(newIndexList);
				int indexNum = 0; // 首页更新条数
				for (RssList rc : info.getList()) {
					if (indexNum >= IndexMaxNum) {
						break;
					}
					List<UrlContentBean> list = getListByRssId(rc); // 根据id
					// ，获取相关内容
					if (list == null || list.size() == 0) {
						log.info(String
								.format("this Rss ID not update XML :%s", rc
										.getRssId()));
						continue;
					}
					log.info(String.format(
							"this Rss  id  %s update  size :%d ",
							rc.getRssId(), list.size()));
					// 读取当前目录下html最大编号
					int htmlMaxId = rc.getThisMaxHtmlId();

					// 生成html文件
					for (UrlContentBean ucb : list) {
						// 最多同步 maxNum条数据
						if (++indexNum > IndexMaxNum) {
							break;
						}
						// 需要一个cache缓存当前channel的最大html.index 整数
						String fileName = ++htmlMaxId + ".html";
						boolean b = CreateHTMLHelper.createDetailFile(
								detailHtmlPath + "/" + rc.getRssClassId(),
								fileName, VelocityUtil.getString(
										getDetailMap(ucb), "vm/detail.vm"));
						if (b) {// 写入主页new发布
							RssIndexBean rib = new RssIndexBean();
							rib.setHtmlTitle(ucb.getTitle());
							rib.setRssId(rc.getRssClassId() + "");
							rib.setHtmlUrl(rc.getRssClassId() + "/"
									+ getDatePath() + "/" + fileName);
							rib.setCrawTitle(ucb.getCrawlTime());
							newIndexList.add(rib);
						}

					}
					rc.setMaxHtmlId(htmlMaxId + ".html");
				}
				rssIndexCache.putObject(channelCode, rssInfoBean);
				channelInfoCache.putObject(channelCode, info);
			}
			/** 同步缓存 */
			channelInfoCache.updateCache();
			rssIndexCache.updateCache();
			startIndexJob();
		} catch (Exception e) {
			log.error("have Error:", e);
		}
	}

	private void startIndexJob() {
		rssIndexHtmlJob.processor();
	}

	/**
	 * 根据html页面规则，写map
	 * 
	 * @param ucb
	 * @return
	 */
	private Map<String, Object> getDetailMap(UrlContentBean ucb) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("title", ucb.getTitle());
		map.put("content", ucb.getContent());
		map.put("time", ucb.getCrawlTime());
		map.put("chartSet", ucb.getChartSet());
		return map;
	}

	private List<UrlContentBean> getListByRssId(RssList rc) {
		List<UrlContentBean> list = new ArrayList<UrlContentBean>();
		String path = detailXMLPath + "/" + rc.getRssClassId() + "/"
				+ getDatePath();
		File dir = new File(path);
		File[] files = dir.listFiles();
		if (files == null) {
			log.warn("this path have no xml file " + path);
			return null;
		}

		for (int i = 0; i < files.length; i++) {
			String filePath = files[i].getAbsolutePath().toLowerCase();
			String fileName = files[i].getName().toLowerCase();
			log.info("exe the file :" + fileName);
			String[] strs = fileName.split("\\.");
			if (strs[0] == null || "".equals(strs[0]))
				continue;
			int fileNum = Integer.parseInt(strs[0]);// 取得数字
			if (fileNum <= rc.getThisMaxXmlId()) {
				continue;
			}
			File file = new File(filePath);
			InputStream io;
			try {
				io = new FileInputStream(file);
				list.addAll(CreateHTMLHelper.doXML(io));// 新解析的list增加到列表中
				rc.setMaxXmlId(fileName);// 修改最大文件名称值
				if (list.size() >= IndexMaxNum) // 只处理指定条目
					break;
			} catch (Exception e) {
				log.error("error ! ", e);
				break;
			}
		}
		return list;
	}

	private String getDatePath() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return sdf.format(new Date());
	}

}