package com.zyf.springmybatis.web.test;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zyf.springmybatis.dao.TestIbatisDao;
import com.zyf.springmybatis.dao.TestSpringJdbcDao;
import com.zyf.springmybatis.po.TestPO;

@Controller
@RequestMapping("/test")
public class TestController {
	@Autowired
	private TestSpringJdbcDao testSpringJdbcDao;
	@Autowired
	TestIbatisDao testIbatisDao;
	private static final Logger log = Logger.getLogger(TestController.class);

	@RequestMapping("/echo")
	@ResponseBody
	public String echo(@RequestParam(value = "message") String message,
			ModelMap map) {
		TestPO t = new TestPO();
		t.setRemark(message);
		testSpringJdbcDao.insert(t);
		log.info("first insert id not have:" + t.getId());
		testIbatisDao.insert(t);
		log.info("second insert have  id:" + t.getId());
		return message;
	}
}
