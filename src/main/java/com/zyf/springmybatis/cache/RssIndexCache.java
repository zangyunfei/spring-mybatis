package com.zyf.springmybatis.cache;

import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.zyf.springmybatis.bean.RssIndexInfoBean;
import com.zyf.springmybatis.common.CacheDataBase;

@Service
public class RssIndexCache {
	private static final Logger log = Logger.getLogger(RssIndexCache.class);
	private static CacheDataBase cache;

	/**
	 * 初始化rs_id
	 */
	private synchronized void processorInit() {
		cache = new CacheDataBase("rss_index", String.class,
				RssIndexInfoBean.class);
		cache.openCache();
		if (cache != null && cache.db != null) { // 不为空，清空数据
			cache.db.close();
			long size = CacheDataBase.env.truncateDatabase(null,
					cache.cacheName, true);
			log.info("Truncate [" + cache.cacheName + "]:" + size + " Rows");
			cache.openCache();
		}
		// 业务代码
	}

	public RssIndexInfoBean getObject(String key) {
		if (cache == null) {
			processorInit();
		}
		if (cache.map == null) {
			return null;
		}
		return (RssIndexInfoBean) cache.map.get(key);
	}

	public void putObject(String key, RssIndexInfoBean ci) {
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
	 * map
	 */
	public Map getMap() {
		return cache.map;
	}

}
