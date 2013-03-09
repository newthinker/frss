package com.frss.model.mapping;

import java.util.Date;

public class FrssAudioInfo {
	private long id;			// 语音记录编号
	private String filePath;	// 语音文件保存路径
	private int type;			// 语音类别， 0表示故障语音上报，1表示专家远程协助
	private long keyId;			// 保存用户信息表Id号
	private int status;			// 离线语音上报状态，0表示已提交未处理，1表示已经处理
	private String reporter;	// 离线语音上报人
	private Date reportTime;	// 离线语音上报时间

	public FrssAudioInfo(){
		
	}
	public FrssAudioInfo(long audioID, 
			String filePath, int type, long keyId, int status, String reporter, Date reportTime) {
		this.id = audioID;
		this.filePath = filePath;
		this.type = type;
		this.keyId = keyId;
		this.status = status;
		this.reporter = reporter;
		this.reportTime = reportTime;
	}
	
	public long getId() {
		return this.id;
	}
	public void setId(long audioID) {
		this.id = audioID;
	}
	
	public String getFilePath() {
		return this.filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	public int getType() {
		return this.type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	public long getKeyId() {
		return this.keyId;
	}
	public void setKeyId(long keyId) {
		this.keyId = keyId;
	}
	
	public int getStatus() {
		return this.status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	public String getReporter() {
		return this.reporter;
	}
	public void setReporter(String reporter) {
		this.reporter = reporter;
	}
	
	public Date getReportTime() {
		return this.reportTime;
	}
	public void setReportTime(Date reportTime) {
		this.reportTime = reportTime;
	}
}
