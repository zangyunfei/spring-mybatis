package com.zyf.springmybatis.po;

import java.io.Serializable;

public class RssClass implements Serializable {
	public int rssClassId;
	public String rssClassName;
	public String rssChannelCode;
	public String rssChannelName;
	public String rssCatgeoryCode;
	public String rssCatgeoryName;

	public int getRssClassId() {
		return rssClassId;
	}

	public void setRssClassId(int rssClassId) {
		this.rssClassId = rssClassId;
	}

	public String getRssClassName() {
		return rssClassName;
	}

	public void setRssClassName(String rssClassName) {
		this.rssClassName = rssClassName;
	}

	public String getRssChannelCode() {
		return rssChannelCode;
	}

	public void setRssChannelCode(String rssChannelCode) {
		this.rssChannelCode = rssChannelCode;
	}

	public String getRssChannelName() {
		return rssChannelName;
	}

	public void setRssChannelName(String rssChannelName) {
		this.rssChannelName = rssChannelName;
	}

	public String getRssCatgeoryCode() {
		return rssCatgeoryCode;
	}

	public void setRssCatgeoryCode(String rssCatgeoryCode) {
		this.rssCatgeoryCode = rssCatgeoryCode;
	}

	public String getRssCatgeoryName() {
		return rssCatgeoryName;
	}

	public void setRssCatgeoryName(String rssCatgeoryName) {
		this.rssCatgeoryName = rssCatgeoryName;
	}

}
