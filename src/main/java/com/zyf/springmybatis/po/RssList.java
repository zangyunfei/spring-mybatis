package com.zyf.springmybatis.po;

import java.io.Serializable;
import java.util.Date;

public class RssList implements Serializable {
	private int rssId;
	private int rssClassId;
	private int rssWebsiteId;
	private String rssUrl;
	private int rssFlag;
	private Date rsscreateTime;
	private Date rssModifyTime;
	private String rssOprator;
	/**
	 * 处理到的xml id
	 */
	public String maxXmlId;
	/**
	 * 生成最大的htmlId
	 */
	public String maxHtmlId;

	public String getMaxHtmlId() {
		return maxHtmlId;
	}

	public synchronized void setMaxHtmlId(String maxHtmlId) {
		this.maxHtmlId = maxHtmlId;
	}

	public String getMaxXmlId() {
		return maxXmlId;
	}

	public void setMaxXmlId(String maxXmlId) {
		this.maxXmlId = maxXmlId;
	}

	public int getRssId() {
		return rssId;
	}

	public void setRssId(int rssId) {
		this.rssId = rssId;
	}

	public int getRssClassId() {
		return rssClassId;
	}

	public void setRssClassId(int rssClassId) {
		this.rssClassId = rssClassId;
	}

	public int getRssWebsiteId() {
		return rssWebsiteId;
	}

	public void setRssWebsiteId(int rssWebsiteId) {
		this.rssWebsiteId = rssWebsiteId;
	}

	public String getRssUrl() {
		return rssUrl;
	}

	public void setRssUrl(String rssUrl) {
		this.rssUrl = rssUrl;
	}

	public int getRssFlag() {
		return rssFlag;
	}

	public void setRssFlag(int rssFlag) {
		this.rssFlag = rssFlag;
	}

	public Date getRsscreateTime() {
		return rsscreateTime;
	}

	public void setRsscreateTime(Date rsscreateTime) {
		this.rsscreateTime = rsscreateTime;
	}

	public Date getRssModifyTime() {
		return rssModifyTime;
	}

	public void setRssModifyTime(Date rssModifyTime) {
		this.rssModifyTime = rssModifyTime;
	}

	public String getRssOprator() {
		return rssOprator;
	}

	public void setRssOprator(String rssOprator) {
		this.rssOprator = rssOprator;
	}

	public int getThisMaxHtmlId() {
		if (maxHtmlId == null) {
			return 0;
		}
		return Integer.parseInt(maxHtmlId.split("\\.")[0]);
	}

	public int getThisMaxXmlId() {
		if (maxXmlId == null) {
			return 0;
		}
		return Integer.parseInt(maxXmlId.split("\\.")[0]);
	}
}
