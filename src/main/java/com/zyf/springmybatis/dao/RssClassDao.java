package com.zyf.springmybatis.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.zyf.springmybatis.po.RssClass;

public interface RssClassDao {

	static final String QUERYALL = "SELECT  * FROM  rep_rss_class ";

	/**
	 * 
	 * @param id
	 * @return
	 */
	@Select(QUERYALL)
	public List<RssClass> queryAll();

}
