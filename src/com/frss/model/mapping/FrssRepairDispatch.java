package com.frss.model.mapping;

import java.util.Date;

public class FrssRepairDispatch {
	private long id;					// 装备故障维修派遣单号
//	private Date dispatchTime;			// 派遣时间
//	private String guarantee;			// 保障部队
//	private String guaranteeAddress;	// 保障部队地址
//	private String contact;				// 部队联系人
	private long faultId;				// 对应故障编号
//	private long factoryId;				// 对应工业部门编号
//	private long feedbackId;			// 对应反馈信息单编号
	
	public FrssRepairDispatch() {
		
	}
//	public FrssRepairDispatch(long dispatchID, Date dispatchTime,
//			String guarantee, String guaranteeAddress, String contact,
//			long faultId, long factoryId, long feedbackId) {
//		this.id = dispatchID;
//		this.dispatchTime = dispatchTime;
//		this.guarantee = guarantee;
//		this.guaranteeAddress = guaranteeAddress;
//		this.contact = contact;
//		this.faultId = faultId;
//		this.factoryId = factoryId;
//		this.feedbackId = feedbackId;
//	}
	
	public long getId() {
		return this.id;
	}
	public void setId (long dispatchID) {
		this.id = dispatchID;
	}
	
//	public Date getDispatchTime() {
//		return this.dispatchTime;
//	}
//	public void setDispatchTime(Date dispatchTime) {
//		this.dispatchTime = dispatchTime;
//	}
	
//	public int getConfirmed () {
//		return this.confirmed;
//	}
//	public void setConfirmed(int confirmed) {
//		this.confirmed = confirmed;
//	}
	
//	public String getGuarantee() {
//		return this.guarantee;
//	}
//	public void setGuarantee(String guarantee) {
//		this.guarantee = guarantee;
//	}
//	
//	public String getGuaranteeAddress() {
//		return this.guaranteeAddress;
//	}
//	public void setGuaranteeAddress(String guaranteeAddress) {
//		this.guaranteeAddress = guaranteeAddress;
//	}
//	
//	public String getContact() {
//		return this.contact;
//	}
//	public void setContact(String contact) {
//		this.contact = contact;
//	}

	public long getFaultId() {
		return this.faultId;
	}
	public void setFaultId(long faultId) {
		this.faultId = faultId;
	}
	
//	public long getFactoryId() {
//		return this.factoryId;
//	}
//	public void setFactoryId(long factoryId) {
//		this.factoryId = factoryId;
//	}
//	
//	public long getFeedbackId() {
//		return this.feedbackId;
//	}
//	public void setFeedbackId(long feedbackId) {
//		this.feedbackId = feedbackId;
//	}
}
