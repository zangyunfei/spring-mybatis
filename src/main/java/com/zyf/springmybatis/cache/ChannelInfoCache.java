package com.zyf.springmybatis.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zyf.springmybatis.bean.ChannelInfo;
import com.zyf.springmybatis.common.CacheDataBase;
import com.zyf.springmybatis.po.RssClass;
import com.zyf.springmybatis.po.RssList;

/**
 * 保存channel_code和 rssId列表的关系，用于页面展示
 * 
 * @author zyf
 * 
 */
@Service
public class ChannelInfoCache {
	private static final Logger log = Logger.getLogger(ChannelInfoCache.class);
	@Autowired
	private RssClassCache rssClassCache;
	@Autowired
	private RssListCache rssListCache;
	private static CacheDataBase cache;

	/**
	 * 初始化rs_id
	 */
	private synchronized void processorInit() {
		cache = new CacheDataBase("channel_info", String.class,
				ChannelInfo.class);
		cache.openCache();
		if (cache != null && cache.db != null) { // 不为空，清空数据
			cache.db.close();
			long size = CacheDataBase.env.truncateDatabase(null,
					cache.cacheName, true);
			log.info("Truncate [" + cache.cacheName + "]:" + size + " Rows");
			cache.openCache();
		}
		syncDb();
	}

	/**
	 * 同步数据库数据到dbd
	 */
	private void syncDb() {
		// 业务代码
		Set<Integer> set = rssListCache.getAllKey(); // 取得所有rssId
		/** 从组 channel - rssList的结构 */
		for (int rssId : set) {
			RssList rssList = rssListCache.getObject(rssId);
			Integer rssClassId = rssList.getRssClassId();
			RssClass rssClass = rssClassCache.getObject(rssClassId);
			String channelCode = rssClass.getRssChannelCode() + "";
			ChannelInfo channelInfo = (ChannelInfo) cache.map.get(channelCode);
			if (channelInfo == null) {
				channelInfo = new ChannelInfo();
				channelInfo.setChannelCode(channelCode);
				channelInfo.setChannelName(rssClass.getRssChannelName());
				List<RssList> list = new ArrayList<RssList>();
				list.add(rssList);
				channelInfo.setList(list);
			} else {
				List<RssList> list = channelInfo.getList();
				list.add(rssList);
			}
			cache.map.put(channelCode, channelInfo);
		}
		cache.db.sync();

	}

	public ChannelInfo getObject(String key) {
		if (cache == null) {
			processorInit();
		}
		return (ChannelInfo) cache.map.get(key);
	}

	public void putObject(String key, ChannelInfo ci) {
		if (cache == null) {
			processorInit();
		}
		cache.map.put(key, ci);
	}

	/**
	 * 取得所有频道名称
	 * 
	 * @param key
	 * @return
	 */
	public Set<String> getAllKey() {
		if (cache == null) {
			processorInit();
		}
		return cache.map.keySet();
	}

	/**
	 * 同步缓存
	 */
	public void updateCache() {
		cache.db.sync();
	}

	/**
	 * 刷新db channel 数据
	 */
	public void refreshChannel() {
		if (cache == null) {
			processorInit();
		} else {
			syncDb();
		}
	}
}
