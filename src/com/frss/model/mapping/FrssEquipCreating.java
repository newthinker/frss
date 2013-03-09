package com.frss.model.mapping;

public class FrssEquipCreating {
	private long id;				// 装备编号
	private String equipType;		// 装备型号
	private String equipName;		// 装备名称
	private long factoryId;			// 制造工厂编号ID
	
	public FrssEquipCreating() {
		
	}
	
	public FrssEquipCreating(String equipType, String equipName, long factoryId) {
		this.equipType = equipType;
		this.equipName = equipName;
		this.factoryId = factoryId;
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
	
	public String getEquipName() {
		return this.equipName;
	}
	public void setEquipName(String equipName) {
		this.equipName = equipName;
	}
	
	public long getFactoryId() {
		return this.factoryId;
	}
	public void setFactoryId(long factoryId) {
		this.factoryId = factoryId;
	}
	
}
