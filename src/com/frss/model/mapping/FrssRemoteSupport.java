package com.frss.model.mapping;

public class FrssRemoteSupport {
	private long id;				// 装备维修远程支援信息单号
	private String supportType;		// 支援方式
	private String channel;			// 远程支援通道
	private String department;		// 接受远程支援的单位
	private long faultId;			// 故障维修单号
	private long expertId;			// 对应的专家编号
	private long feedbackId;		// 对应的反馈单号
	
	public FrssRemoteSupport() {
	}
	public FrssRemoteSupport(long supportID, String supportType, String supportChannel,
			String department, long faultId, long expertId, long feedbackId) {
		this.id = supportID;
		this.supportType = supportType;
		this.channel = supportChannel;
		this.department = department;
		this.faultId = faultId;
		this.expertId = expertId;
		this.feedbackId = feedbackId;
	}
	
	public long getId() {
		return this.id;
	}
	public void setId(long supportID) {
		this.id = supportID;
	}
	
	public String getSupportType() {
		return this.supportType;
	}
	public void setSupportType(String supportType) {
		this.supportType = supportType;
	}
	
	public String getChannel() {
		return this.channel;
	}
	public void setChannel(String supportChannel) {
		this.channel = supportChannel;
	}
	
	public String getDepartment() {
		return this.department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	
	public long getFaultId() {
		return this.faultId;
	}
	public void setFaultId (long faultId) {
		this.faultId = faultId;
	}
	
	public long getExpertId() {
		return this.expertId;
	}
	public void setExpertId(long expertId) {
		this.expertId = expertId;
	}
	
	public long getFeedbackId() {
		return this.feedbackId;
	}
	public void setFeedbackId(long feedbackId) {
		this.feedbackId = feedbackId;
	}
}
