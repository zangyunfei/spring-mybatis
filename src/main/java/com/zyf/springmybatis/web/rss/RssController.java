package com.zyf.springmybatis.web.rss;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/rss")
public class RssController {

	@RequestMapping("news")
	public String findClassList(ModelMap map) {
		map.put("from_list", new ArrayList()); // 渠道列表
		return "rss/queryList";
	}
}
