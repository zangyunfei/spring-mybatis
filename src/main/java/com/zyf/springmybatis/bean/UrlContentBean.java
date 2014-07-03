package com.zyf.springmybatis.bean;

public class UrlContentBean {
	private String rssID;
	private String linkURL;
	private String description;
	private String content;
	private String chartSet;
	private String crawlTime;
	private String title;

	public String getRssID() {
		return rssID;
	}

	public void setRssID(String rssID) {
		this.rssID = rssID;
	}

	public String getLinkURL() {
		return linkURL;
	}

	public void setLinkURL(String linkURL) {
		this.linkURL = linkURL;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getChartSet() {
		return chartSet;
	}

	public void setChartSet(String chartSet) {
		this.chartSet = chartSet;
	}

	public String getCrawlTime() {
		return crawlTime;
	}

	public void setCrawlTime(String crawlTime) {
		this.crawlTime = crawlTime;
	}

	@Override
	public String toString() {
		return this.title + "|" + this.content;
	}
}
