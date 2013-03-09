package com.frss.model.main;

import java.util.Date;

public class AudioInfoForm {
	private long Id;		
	private Date startTime;
	private Date endTime;
	private String path;
	private int type;
	private long keyId;
	
	public AudioInfoForm() {
		
	}
	
	public long getAudioId() {
		return this.Id;
	}
	public void setAudioId(long audioId) {
		this.Id = audioId;
	}
	
	public Date getStartTime() {
		return this.startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	public Date getEndTime() {
		return this.endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	public String getFilePath() {
		return this.path;
	}
	public void setFilePath(String filePath) {
		this.path = filePath;
	}
	
	public int getAudioType() {
		return this.type;
	}
	public void setAudioType(int audioType) {
		this.type = audioType;
	}
	
	public long getKeyId() {
		return this.keyId;
	}
	public void setKeyId(long keyId) {
		this.keyId = keyId;
	}
}
