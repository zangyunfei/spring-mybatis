package com.zyf.springmybatis.bean;

import java.io.Serializable;
import java.util.List;

import com.zyf.springmybatis.po.RssList;

/**
 * 频道相关更新数据信息
 * 
 * @author Administrator
 * 
 */
public class ChannelInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8603437268137260287L;

	private String channelName;
	private String channelCode;

	private List<RssList> list;

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

	public List<RssList> getList() {
		return list;
	}

	public void setList(List<RssList> list) {
		this.list = list;
	}
}
