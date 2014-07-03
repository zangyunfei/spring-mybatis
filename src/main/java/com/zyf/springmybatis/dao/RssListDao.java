package com.zyf.springmybatis.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.zyf.springmybatis.po.RssList;

public interface RssListDao {

	static final String QUERY_ALL = "SELECT  * FROM  rep_rss_List WHERE rss_flag = 1";

	/**
	 * 
	 * @param id
	 * @return
	 */
	@Select(QUERY_ALL)
	public List<RssList> queryAll();

}
