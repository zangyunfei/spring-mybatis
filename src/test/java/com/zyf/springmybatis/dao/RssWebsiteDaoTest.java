package com.zyf.springmybatis.dao;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.zyf.springmybatis.base.TestBase;
import com.zyf.springmybatis.po.RssWebSite;

public class RssWebsiteDaoTest extends TestBase {
	@Autowired
	RssWebsiteDao rssWebsiteDao;

	@Test
	public void findByIdTest() {
		RssWebSite rs = rssWebsiteDao.findById(1);
		Assert.assertTrue(rs != null);
	}
}
