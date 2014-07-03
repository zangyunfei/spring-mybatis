package com.zyf.springmybatis.job;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zyf.springmybatis.bean.RssIndexBean;
import com.zyf.springmybatis.bean.RssIndexInfoBean;
import com.zyf.springmybatis.cache.ChannelInfoCache;
import com.zyf.springmybatis.cache.RssIndexCache;
import com.zyf.springmybatis.common.CreateHTMLHelper;
import com.zyf.springmybatis.common.LookUpConfig;
import com.zyf.springmybatis.common.VelocityUtil;

@Service
public class RssIndexHtmlJob {
	private static final Logger log = Logger.getLogger(RssDetailJob.class);
	/**
	 * xml源文件路径
	 */
	private String IndexHtmlPath = LookUpConfig.htmlPath;
	// 每个频道 最更新数量
	private int IndexMaxNum = 10;
	@Autowired
	private RssIndexCache rssIndexCache;
	@Autowired
	private ChannelInfoCache rssIdCache;

	static {
		// LookUpConfig.init();
	}

	/**
	 * 取得频道列表，循环取最新的xml文件
	 */
	public void processor() {
		try {
			// 页面是否需要刷新
			boolean isUpdate = false;
			Set<String> set = rssIdCache.getAllKey();
			log.info("get Channel list :" + set.size());
			for (String channelCode : set) {
				List<RssIndexBean> endList = new ArrayList<RssIndexBean>();
				RssIndexInfoBean rssInfoBean = rssIndexCache
						.getObject(channelCode);
				List<RssIndexBean> newIndexList = rssInfoBean.getNewRssList();
				if (newIndexList == null || newIndexList.size() == 0) {
					continue;
				}
				/** 不够10条更新 */
				if (newIndexList.size() < IndexMaxNum) {
					List<RssIndexBean> nowIndexList = rssInfoBean
							.getNowRssList();
					if (nowIndexList == null) {
						endList = newIndexList;
					} else {
						int i = IndexMaxNum - newIndexList.size(); // 需要保留多少条历史记录
						if (i < nowIndexList.size()) // 历史记录够用，剪切掉多余的
							nowIndexList = nowIndexList.subList(0, i);
						endList.addAll(newIndexList);
						endList.addAll(nowIndexList);
					}
				} else if (newIndexList.size() == IndexMaxNum) {// 10条全更新
					endList = newIndexList;
				} else { // 大于10条
					endList.addAll(newIndexList.subList(0, IndexMaxNum));
				}
				rssInfoBean.setNowRssList(endList);
				rssInfoBean.setNewRssList(null);
				rssIndexCache.putObject(channelCode, rssInfoBean);
				log.info(String.format("channel %s ,update rssNumber : %d",
						channelCode, endList.size()));
				isUpdate = true;
			}
			if (!isUpdate) {
				log.info("The index html  no update info ");
				return;
			}

			/** 更新 index页面 */
			rssIndexCache.updateCache();
			log.info("update index html success " + IndexHtmlPath
					+ "/index.html");
			boolean b = CreateHTMLHelper.createIndexFile(IndexHtmlPath,
					"index.html", VelocityUtil.getString(
							createMap(rssIndexCache.getMap()), "vm/index.vm"));
			log.info("update index html success ");
		} catch (Exception e) {
			log.error("have Error:", e);
		}
	}

	private Map<String, Object> createMap(Map map) {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("indexMap", map);
		m.put("data", new Date());
		return m;
	}

	private Map<String, Object> getDetailMap(Map map) {
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("title", "lenovo Portal");
		Set<String> set = rssIdCache.getAllKey();
		log.info("get Channel list" + set.size());
		for (String channelCode : set) {
			RssIndexInfoBean rssInfoBean = rssIndexCache.getObject(channelCode);
			maps.put(channelCode, rssInfoBean);
		}
		return maps;
	}

}
