package com.zyf.springmybatis.web.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    public static Logger log = LoggerFactory.getLogger(TestController.class);

    @RequestMapping("/echo")
    @ResponseBody
    public String echo(@RequestParam(value = "message") String message,
            ModelMap map) {
        TestPO t = new TestPO();
        t.setRemark(message);
        this.testSpringJdbcDao.insert(t);
        TestController.log.info("first insert id not have:" + t.getId());
        this.testIbatisDao.insert(t);
        TestController.log.info("second insert have  id:" + t.getId());
        return message;
    }

    @RequestMapping("/log4j")
    @ResponseBody
    public void log4j(@RequestParam(value = "message") String message) {
        TestController.log.info("info  info:" + message);
        TestController.log.warn("warn  warn:" + message);
        TestController.log.error("error  error:" + message);
    }
}
