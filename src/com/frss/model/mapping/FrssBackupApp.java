package com.frss.model.mapping;

import java.util.Date;

public class FrssBackupApp {
	private long id;				// 备件申请单编号
	private String equiPlace;		// 备件使用装备部位
	private int backCount;			// 备件数量
	private String reporter;		// 备件申请记录人
	private String contactWay;		// 备件申请记录人的联系方式
	private Date reportTime;		// 备件申请时间
	private long equipId;			// 对应的装备表记录编号
	private long faultId;			// 对应的故障表记录编号
	
	public FrssBackupApp () {
		
	}
	public FrssBackupApp (long backupID, String equiPlace, int backCount,
			String reporter, String contactWay, long equipId, long faultId) {
		this.id = backupID;
		this.equiPlace = equiPlace;
		this.backCount = backCount;
		this.reporter = reporter;
		this.contactWay = contactWay;
		this.equipId = equipId;
		this.faultId = faultId;
	}
	
	public long getId () {
		return this.id;
	}
	public void setId (long backupID) {
		this.id = backupID;
	}
	
	public String getEquiPlace() {
		return this.equiPlace;
	}
	public void setEquiPlace (String equiPlace) {
		this.equiPlace = equiPlace;
	}
	
	public int getBackCount () {
		return this.backCount;
	}
	public void setBackCount (int backCount) {
		this.backCount = backCount;
	}
	
	public String getReporter() {
		return this.reporter;
	}
	public void setReporter (String reporter) {
		this.reporter = reporter;
	}
	
	public String getContactWay () {
		return this.contactWay;
	}
	public void setContactWay (String contactWay) {
		this.contactWay = contactWay;
	}
	
	public Date getReportTime() {
		return this.reportTime;
	}
	public void setReportTime(Date reportTime) {
		this.reportTime = reportTime;
	}
	
	public long getEquipId () {
		return this.equipId;
	}
	public void setEquipId (long equipId) {
		this.equipId = equipId;
	}
	
	public long getFaultId () {
		return this.faultId;
	}
	public void setFaultId (long faultId) {
		this.faultId = faultId;
	}
	
}
