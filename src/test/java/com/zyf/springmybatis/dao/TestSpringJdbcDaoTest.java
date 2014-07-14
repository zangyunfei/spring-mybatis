package com.zyf.springmybatis.dao;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.zyf.springmybatis.base.TestBase;
import com.zyf.springmybatis.po.TestPO;

public class TestSpringJdbcDaoTest extends TestBase {
	@Autowired
	private TestSpringJdbcDao testSpringJdbcDao;

	@Test
	public void testInsert() {
		TestPO test = new TestPO();
		test.setRemark("123");
		testSpringJdbcDao.insert(test);
		Assert.assertTrue(test.getId() > 0);
	}
}
