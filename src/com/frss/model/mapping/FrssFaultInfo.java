package com.frss.model.mapping;

import java.sql.Blob;
import java.util.Date;

public class FrssFaultInfo {
	private long id;
	private int amount;
	private String faultDesp;
	private Date faultTime;
	private String preProcess;
	private int frequency;
	private String faultPlace;
	private String reporter;
	private String contactWay;
	private Date reportTime;
	private String photoName;
	private Blob photos;
	private String cause;
	private long equipId;
	
	public FrssFaultInfo() {
		
	}
	public FrssFaultInfo (long faultID, int amount, Date faultTime, 
			String faultPlace, String reporter, long equipId) {
		this.id = faultID;
		this.amount = amount;
		this.faultTime = faultTime;
		this.faultPlace = faultPlace;
		this.reporter = reporter;
		this.equipId = equipId;
	}
	public FrssFaultInfo(long faultID, int amount, String faultDesp, Date faultTime, 
			String preProcess, int frequency, String faultPlace, String reporter, 
			String contactWay, Date reportTime, String photoName, Blob photos, String cause, long equipId) {
		this.id = faultID;
		this.amount = amount;
		this.faultDesp = faultDesp;
		this.faultTime = faultTime;
		this.preProcess = preProcess;
		this.frequency = frequency;
		this.faultPlace = faultPlace;
		this.reporter = reporter;
		this.contactWay = contactWay;
		this.reportTime = reportTime;
		this.photoName = photoName;
		this.photos = photos;
		this.cause = cause;
		this.equipId = equipId;
	}
	
	public long getId(){
		return this.id;
	}
	public void setId(long faultID) {
		this.id = faultID;
	}
	
	public int getAmount() {
		return this.amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	public String getFaultDesp() {
		return this.faultDesp;
	}
	public void setFaultDesp(String faultDesp) {
		this.faultDesp = faultDesp;
	}
	
	public Date getFaultTime(){
		return this.faultTime;
	}
	public void setFaultTime(Date faultTime) {
		this.faultTime = faultTime;
	}

	public String getPreProcess() {
		return this.preProcess;
	}
	public void setPreProcess(String preProcess) {
		this.preProcess = preProcess;
	}
	
	public int getFrequency(){
		return this.frequency;
	}
	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}
	
	public String getFaultPlace() {
		return this.faultPlace;
	}
	public void setFaultPlace(String faultPlace) {
		this.faultPlace = faultPlace;
	}
	
	public String getReporter(){
		return this.reporter;
	}
	public void setReporter(String reporter) {
		this.reporter = reporter;
	}
	
	public String getContactWay() {
		return this.contactWay;
	}
	public void setContactWay(String contactWay) {
		this.contactWay = contactWay;
	}
	
	public Date getReportTime() {
		return this.reportTime;
	}
	public void setReportTime(Date reportTime) {
		this.reportTime = reportTime;
	}
	
	public String getPhotoName() {
		return this.photoName;
	}
	public void setPhotoName(String photoName) {
		this.photoName = photoName;
	}
	
	public Blob getPhotos() {
		return this.photos;
	}
	public void setPhotos(Blob photos) {
		this.photos = photos;
	}
	
	public String getCause() {
		return this.cause;
	}
	
	public void setCause(String cause) {
		this.cause = cause;
	}
	
	public long getEquipId() {
		return this.equipId;
	}
	public void setEquipId(long equipId) {
		this.equipId = equipId;
	}
}
