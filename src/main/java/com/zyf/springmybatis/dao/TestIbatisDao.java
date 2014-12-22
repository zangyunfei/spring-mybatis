package com.zyf.springmybatis.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import com.zyf.springmybatis.po.TestPO;

public interface TestIbatisDao {

	@Insert("INSERT INTO TEST (remark) VALUES (#{remark}) ")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	public void insert(TestPO testPO);

	@Select("SELECT  * FROM  test ")
	public List<TestPO> queryAll();

}
