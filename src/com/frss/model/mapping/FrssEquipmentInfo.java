package com.frss.model.mapping;

import java.util.Date;

public class FrssEquipmentInfo {
	private long id;				// 装备编号
	private String equipType;		// 装备型号
	private String equipNumber;		// 装备编号
	private String equipName;		// 装备名称
	private Date createTime;		// 装备生产时间
	private String description;		// 装备描述
	private String department;		// 装备使用单位
	private String operater;		// 装备操作者
//	private long factoryId;			// 制造工厂编号ID
	
	public FrssEquipmentInfo() {
		
	}
	public FrssEquipmentInfo (long equipID, String equipType, String equipNumber,
			String equipName, Date createTime, long factoryId) {
		this.id = equipID;
		this.equipType = equipType;
		this.equipNumber = equipNumber;
		this.equipName = equipName;
		this.createTime = createTime;
//		this.factoryId = factoryId;
	}
	public FrssEquipmentInfo(long equipID, String equipType, String equipNumber,
			String equipName, Date createTime, String description, String department,
			String operater, long factoryId) {
		this.id = equipID;
		this.equipType = equipType;
		this.equipNumber = equipNumber;
		this.equipName = equipName;
		this.createTime = createTime;
		this.description = description;
		this.department = department;
		this.operater = operater;
//		this.factoryId = factoryId;
	}
	
	public long getId() {
		return this.id;
	}
	public void setId(long equipID) {
		this.id = equipID;
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
	
}
