package com.frss.model.main;

import java.util.Date;

public class EquipInfo {
	
	private Long idEquip;
	private String equipType;
	private String equipNumber;
	private String equipName;
	private String description;
	private String department;
	private String operater;
	private Date createTime;
	private Long idFactory;
	private String nameFactory;
	
	public EquipInfo() {
		
	}

	public Long getEquipId() {
		return this.idEquip;
	}
	public void setId(Long equipID) {
		this.idEquip = equipID;
	}
	
	public String getEquipType() {
		return this.equipType;
	}
	public void setEquipType(String equipType) {
		this.equipType = equipType;
	}
	
	public String getEquipNumber() {
		return this.equipNumber;
	}
	public void setEquipNumber(String equipNumber) {
		this.equipNumber = equipNumber;
	}
	
	public String getEquipName() {
		return this.equipName;
	}
	public void setEquipName(String equipName) {
		this.equipName = equipName;
	}
	
	public Date getCreateTime() {
		return this.createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public String getDescription() {
		return this.description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getDepartment() {
		return this.department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	
	public String getOperater() {
		return this.operater;
	}
	public void setOperater(String operater) {
		this.operater = operater;
	}
	
	public Long getKeyFactoryInfo() {
		return this.idFactory;
	}
	public void setKeyFactoryInfo(Long keyFactoryInfo) {
		this.idFactory = keyFactoryInfo;
	}

	public String getFactoryName() {
		return this.nameFactory;
	}
	public void setFactoryName(String nameFactory) {
		this.nameFactory = nameFactory;
	}
	
}
