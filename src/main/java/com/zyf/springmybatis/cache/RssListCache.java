package com.zyf.springmybatis.cache;

import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zyf.springmybatis.bean.ChannelInfo;
import com.zyf.springmybatis.common.CacheDataBase;
import com.zyf.springmybatis.dao.RssListDao;
import com.zyf.springmybatis.po.RssList;

/**
 * rss 具体xml文件配置表，缓存
 * 
 * @author zyf
 * 
 */
@Service
public class RssListCache {
	private static final Logger log = Logger.getLogger(RssListCache.class);
	@Autowired
	private RssListDao rssListDao;
	private static CacheDataBase cache;

	/**
	 * 初始化rs_id
	 */
	private synchronized void processorInit() {
		cache = new CacheDataBase("rss_list", Integer.class, RssList.class);
		cache.openCache();
		if (cache != null && cache.db != null) { // 不为空，清空数据
			cache.db.close();
			long size = CacheDataBase.env.truncateDatabase(null,
					cache.cacheName, true);
			log.info("Truncate [" + cache.cacheName + "]:" + size + " Rows");
			cache.openCache();
		}
		// 业务代码
		List<RssList> list = rssListDao.queryAll();
		for (RssList rs : list) {
			Integer key = rs.getRssId();
			cache.map.put(key, rs);
		}
		cache.db.sync();
	}

	public RssList getObject(Integer key) {
		if (cache == null) {
			processorInit();
		}
		return (RssList) cache.map.get(key);
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
				List<RssList> list = rssListDao.queryAll();
				for (RssList rs : list) {
					Integer key = rs.getRssId();
					cache.map.put(key, rs);
				}
				cache.db.sync();
			} catch (Exception e) {
				log.info("refresh channel error : ", e);
			}
		}
	}
}
