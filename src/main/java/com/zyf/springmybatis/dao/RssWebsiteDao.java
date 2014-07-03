package com.zyf.springmybatis.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.zyf.springmybatis.po.RssWebSite;

public interface RssWebsiteDao {

	String INSERT = "INSERT INTO userAccount(uaid,balance,payingbalance,updatetime,createtime)"
			+ "VALUES (#{uaid},#{balance},#{payingbalance},#{updatetime,jdbcType=TIMESTAMP},#{createtime,jdbcType=TIMESTAMP}) ";

	String FINDBYID = "SELECT  RSS_WEBSITE_NAME ,RSS_WEBSITE_URL FROM "
			+ "rep_rss_website WHERE RSS_WEBSITE_ID = #{id}";

	String UPDATE = "UPDATE useraccount SET "
			+ "balance = balance + #{balance}, payingbalance = payingbalance + #{payingbalance} , updatetime = #{updatetime,jdbcType=TIMESTAMP} WHERE uaid = #{uaid}";

	/**
	 * 根据id查询
	 * 
	 * @param id
	 * @return
	 */
	@Select(FINDBYID)
	public RssWebSite findById(long id);

	/**
	 * 新增一条记录
	 * 
	 * @param rssWebSite
	 */
	@Insert(INSERT)
	public void add(RssWebSite rssWebSite);

	/**
	 * 修改信息
	 * 
	 * @param rssWebSite
	 */
	@Update(UPDATE)
	public int update(RssWebSite rssWebSite);
}
