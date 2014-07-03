package com.zyf.springmybatis.web.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zyf.springmybatis.dao.TestDao;
import com.zyf.springmybatis.po.TestPO;

@Controller
@RequestMapping("/test")
public class TestController {
	@Autowired
	private TestDao testDao;

	@RequestMapping("/echo")
	@ResponseBody
	public String echo(@RequestParam(value = "message") String message,
			ModelMap map) {
		TestPO t = new TestPO();
		t.setRemark(message);
		testDao.insert(t);
		return message;
	}
}
