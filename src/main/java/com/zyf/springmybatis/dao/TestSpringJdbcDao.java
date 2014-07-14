package com.zyf.springmybatis.dao;

import org.springframework.stereotype.Repository;

import com.zyf.springmybatis.po.TestPO;

@Repository
public class TestSpringJdbcDao extends SpringJdbcBase {

	public void insert(TestPO testPO) {
		String remark = testPO.getRemark();
		String sql = "INSERT INTO test(remark) VALUES(?)";
		String key = super.insert(sql, new Object[] { remark });
		testPO.setId(Integer.parseInt(key));
	}
}
