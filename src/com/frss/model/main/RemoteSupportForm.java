package com.frss.model.main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.frss.util.FrssException;

public class RemoteSupportForm {
	private long remoteId;		// 远程支援单号
	private long faultId;		// 装备故障维修信息单号
	private long feedbackId;	// 装备故障维修信息反馈单号
	private long supprotId;		// 装备维修远程支援信息单号
	private String channel;		// 远程支援通道
	private String supportType;	// 远程支援方式
	private String department;	// 远程支援单位
	private String expert;		// 远程支援专家
	private String contact;		// 专家联络人
	private String contactWay;	// 联络人的联络方式
	
	private int status;			// 表单状态 [0~2] 
	private Date checkTime;		// 表单提交或确认时间
	
	SimpleDateFormat simFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
	
	public RemoteSupportForm() {
		
	}
	
	public long getRemoteId() {
		return this.remoteId;
	}
	public void setRemoteId(long remoteId) {
		this.remoteId = remoteId;
	}
	
	public long getFaultId() {
		return this.faultId;
	}
	public void setFaultId(long faultId) {
		this.faultId = faultId;
	}
	
	public long getFeedbackId() {
		return this.feedbackId;
	}
	public void setFeedbackId(long feedbackId) {
		this.feedbackId = feedbackId;
	}
	
	public long getSupportId() {
		return this.supprotId;
	}
	public void setSupprotId(long supportId) {
		this.supprotId = supportId;
	}
	
	public String getSupportChannel() {
		return this.channel;
	}
	public void setSupportChannel(String channel) {
		this.channel = channel;
	}
	
	public String getSupportType() {
		return this.supportType;
	}
	public void setSupportType(String supportType) {
		this.supportType = supportType;
	}
	
	public String getDepartment() {
		return this.department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	
	public String getExpert() {
		return this.expert;
	}
	public void setExpert(String expert) {
		this.expert = expert;
	}
	
	public String getContact() {
		return this.contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	
	public String getContactWay() {
		return this.contactWay;
	}
	public void setContactWay(String contactWay) {
		this.contactWay = contactWay;
	}
	
	public int getStatus() {
		return this.status;
	}
	public void setStatus(int status) {
		this.status = status;
	}

	public Date getCheckTime() throws FrssException {
		Date curDate = null;
		try {
			curDate = simFormat.parse(simFormat.format(new Date(System.currentTimeMillis())));	//获取当前时间
		} catch (Exception e) {
			/// log
			throw new FrssException(e);
		}
		
		return curDate;
	}
	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}
		
	// initialize
	public void initialize(List<String> lstRemote) throws FrssException {
		int loc = 1;
		try {
			this.faultId = Long.parseLong(lstRemote.get(loc));
			this.feedbackId = Long.parseLong(lstRemote.get(loc+1));
			this.supprotId = Long.parseLong(lstRemote.get(loc+2));
			this.channel = lstRemote.get(loc+3);
			this.supportType = lstRemote.get(loc+4);
			this.department = lstRemote.get(loc+5);
			this.expert = lstRemote.get(loc+6);
			this.contact = lstRemote.get(loc+7);
			this.contactWay = lstRemote.get(loc+8);
		} catch (Exception e) {
			/// log
			throw new FrssException(e);
		}		
	}
	
	public void initialize(Map<String, String> mapRemote) throws FrssException {
		try {
			if(mapRemote.get("orderid")!=null)
				this.faultId = Long.parseLong(mapRemote.get("orderid"));
			if(mapRemote.get("fbid")!=null)
				this.feedbackId = Long.parseLong(mapRemote.get("fbid"));
//			if(mapRemote.get("rsid")!=null)
//				this.supprotId = Long.parseLong(mapRemote.get("rsid"));
			if(mapRemote.get("rschannel")!=null)
				this.channel = mapRemote.get("rschannel").toString();
			if(mapRemote.get("rstype")!=null)
				this.supportType = mapRemote.get("rstype").toString();
			if(mapRemote.get("rsunit")!=null)
				this.department = mapRemote.get("rsunit").toString();
			if(mapRemote.get("rsexpert")!=null)
				this.expert = mapRemote.get("rsexpert").toString();
			if(mapRemote.get("rscontacter")!=null)
				this.contact = mapRemote.get("rscontacter").toString();
			if(mapRemote.get("rscontactway")!=null)
				this.contactWay = mapRemote.get("rscontactway").toString();
		} catch (Exception e) {
			/// log
			throw new FrssException(e);
		}		
	}	
	
}
