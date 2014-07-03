package com.zyf.springmybatis.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.zyf.springmybatis.base.TestBase;
import com.zyf.springmybatis.po.TestPO;

public class TestDAOTest extends TestBase {
	@Autowired
	private TestDao testDao;

	@Test
	public void testInsert() {
		TestPO test = new TestPO();
		test.setRemark("123");
		testDao.insert(test);
		Assert.assertTrue(true);
	}

	@Test
	public void testSelect() {
		List<TestPO> list = testDao.queryAll();
		Assert.assertTrue(list != null);
	}
}
