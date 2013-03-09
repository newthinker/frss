package com.frss.model.mapping;

import java.util.Date;

public class FrssApprovalInfo {
	private long id;			// 审核者编号
	private String checker;		// 审核者姓名
	private String opinion;		// 审核意见
	private Date checkTime;		// 审核时间
	private int type;			// 审核表单类型，[1～8]，分别表示故障维修上报申请(2级)、备件申请(2级)、远程支援、维修派遣、维修反馈和电话回访
	private int status;			// 审核阶段，[0～2]，分别表示表单提交、表单审核通过和表单审核未通过
	private long keyId;			// 被提交表单主键号
	private long userId;		// 对应的用户编号
	
	public FrssApprovalInfo () {
		
	}
	public FrssApprovalInfo (long aprovalID, String checker, String approvalOpinion, 
			Date checkTime, int type, int approvalStatus, long keyId, long userId) {
		this.id = aprovalID; 
		this.checker = checker;
		this.opinion = approvalOpinion;
		this.checkTime = checkTime;
		this.type = type;
		this.status = approvalStatus;
		this.keyId = keyId;
		this.userId = userId;
	}
	
	public long getId () {
		return this.id;
	}
	public void setId (long approvalID) {
		this.id = approvalID;
	}
	
	public String getChecker () {
		return this.checker;
	}
	public void setChecker (String checker) {
		this.checker = checker;
	}

	public String getOpinion () {
		return this.opinion;
	}
	public void setOpinion (String approvalOpinion) {
		this.opinion = approvalOpinion;
	}
	
	public Date getCheckTime() {
		return this.checkTime;
	}
	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}
	
	public int getType() {
		return this.type;
	}
	public void setType(int type) {
		this.type = type;
	}

	public int getStatus() {
		return this.status;
	}
	public void setStatus(int approvalStatus) {
		this.status = approvalStatus;
	}
	
	public long getKeyId () {
		return this.keyId;
	}
	public void setKeyId (long keyId) {
		this.keyId = keyId;
	}
	
	public long getUserId () {
		return this.userId;
	}
	public void setUserId (long userId) {
		this.userId = userId;
	}
}
