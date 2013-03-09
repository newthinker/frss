package com.frss.model.main;

public class MachinistInfoForm {
	private long id;				// 维修人员编号
	private String name;			// 维修人员姓名
	private String contactWay;		// 维修人员联系方式
	private String email;			// 维修人员电子邮箱
	private int ability;			// 维修人员水平等级
	private long factoryId;			// 维修人员所属工厂编号
	
	public MachinistInfoForm() {
		
	}
	
	public long getMachinistId() {
		return this.id;
	}
	public void setMachinistId(long machinistId) {
		this.id = machinistId;
	}
	
	public String getMachinistName() {
		return this.name;
	}
	public void setMachinistName(String name) {
		this.name = name;
	}
	
	public String getContactWay() {
		return this.contactWay;
	}
	public void setContactWay(String contactWay) {
		this.contactWay = contactWay;
	}
	
	public String getEmail() {
		return this.email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public int getAbility() {
		return this.ability;
	}
	public void setAbility(int ability) {
		this.ability = ability;
	}
	
	public long getFactoryId() {
		return this.factoryId;
	}
	public void setFactoryId(long factoryId) {
		this.factoryId = factoryId;
	}
		
}
