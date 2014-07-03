package com.zyf.springmybatis.bean;

import java.io.Serializable;
import java.util.List;

public class RssIndexInfoBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -564761329104035039L;
	private String channelName;
	private String channelCode;
	/**
	 * 当前展示列表
	 */
	private List<RssIndexBean> nowRssList;
	/**
	 * 新生成的list列表
	 */
	private List<RssIndexBean> newRssList;

	public List<RssIndexBean> getNowRssList() {
		return nowRssList;
	}

	public void setNowRssList(List<RssIndexBean> nowRssList) {
		this.nowRssList = nowRssList;
	}

	public List<RssIndexBean> getNewRssList() {
		return newRssList;
	}

	public void setNewRssList(List<RssIndexBean> newRssList) {
		this.newRssList = newRssList;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public boolean getShow() {
		if (this.nowRssList != null && nowRssList.size() >= 3) { // 大于三条才显示
			return true;
		}
		return false;
	}

	private boolean show;
}
