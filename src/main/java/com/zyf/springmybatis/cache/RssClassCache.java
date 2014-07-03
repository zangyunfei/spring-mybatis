package com.zyf.springmybatis.cache;

import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zyf.springmybatis.common.CacheDataBase;
import com.zyf.springmybatis.dao.RssClassDao;
import com.zyf.springmybatis.po.RssClass;

/**
 * rss 频道类缓存
 * 
 * @author zyf
 * 
 */
@Service
public class RssClassCache {
	private static final Logger log = Logger.getLogger(RssClassCache.class);
	@Autowired
	private RssClassDao rssClassDao;
	private static CacheDataBase cache;

	/**
	 * 初始化rs_id
	 */
	private synchronized void processorInit() {
		cache = new CacheDataBase("rss_class", Integer.class, RssClass.class);
		cache.openCache();
		if (cache != null && cache.db != null) { // 不为空，清空数据
			cache.db.close();
			long size = CacheDataBase.env.truncateDatabase(null,
					cache.cacheName, true);
			log.info("Truncate [" + cache.cacheName + "]:" + size + " Rows");
			cache.openCache();
		}
		// 业务代码
		List<RssClass> list = rssClassDao.queryAll();
		for (RssClass rs : list) {
			Integer key = rs.getRssClassId();
			cache.map.put(key, rs);
		}
		cache.db.sync();
	}

	public RssClass getObject(Integer key) {
		if (cache == null) {
			processorInit();
		}
		return (RssClass) cache.map.get(key);
	}

	public void putObject(String key, RssClass ci) {
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
	public Set<Integer> getAllKey() {
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
			try {
				// 业务代码
				List<RssClass> list = rssClassDao.queryAll();
				for (RssClass rs : list) {
					Integer key = rs.getRssClassId();
					cache.map.put(key, rs);
				}
				cache.db.sync();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
