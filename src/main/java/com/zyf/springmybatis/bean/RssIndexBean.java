package com.zyf.springmybatis.bean;

import java.io.Serializable;

public class RssIndexBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -849528006262140541L;
	/**
	 * 属于哪个rssId
	 */
	private String rssId = "";
	/**
	 * html url地址
	 */
	private String htmlUrl = "";
	/**
	 * 主页显示的文字
	 */
	private String htmlTitle = "";
	private String crawTitle;

	public String getRssId() {
		return rssId;
	}

	public void setRssId(String rssId) {
		this.rssId = rssId;
	}

	public String getHtmlUrl() {
		return htmlUrl;
	}

	public void setHtmlUrl(String htmlUrl) {
		this.htmlUrl = htmlUrl;
	}

	public String getHtmlTitle() {
		return htmlTitle;
	}

	public void setHtmlTitle(String htmlTitle) {
		this.htmlTitle = htmlTitle;
	}

	public String getCrawTitle() {
		return crawTitle;
	}

	public void setCrawTitle(String crawTitle) {
		this.crawTitle = crawTitle;
	}
}
