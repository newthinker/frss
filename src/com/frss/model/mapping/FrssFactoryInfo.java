package com.frss.model.mapping;

public class FrssFactoryInfo {

	private long id;			// 记录编号
	private String name;		// 工厂名称
	private String address;		// 工厂地址
	private String code;		// 工厂代号
	private String range;		// 生产范围
	private String guarantee;	// 编配部队编号
	private String guardAddress;// 编配部队地址
	private int ability;		// 维修人员维修能力
	private String contact;		// 工业部门联系人姓名
	private String contactWay;	// 工业部门联系人联系方式
	private String machinist;	// 维修人员姓名
	
	public FrssFactoryInfo () {
		
	}
	
	public FrssFactoryInfo (long id, String name, String address, int ability) {
		this.id = id;
		this.name = name;
		this.address = address;
		this.ability = ability;
	}
	
	public FrssFactoryInfo(long id, String name, String address, String code, 
			String range, String guarantee, String guardAddress, 
			int ability, String contact, String contactWay, String machinist) {
		this.id = id;
		this.name = name;
		this.address = address;
		this.code = code;
		this.range = range;
		this.guarantee = guarantee;
		this.guardAddress = guardAddress;
		this.ability = ability;
		this.contact = contact;
		this.contactWay = contactWay;
		this.machinist = machinist;
	}
	
	public long getId() {
		return this.id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getAddress() {
		return this.address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getCode() {
		return this.code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getRange() {
		return this.range;
	}
	public void setRange(String range) {
		this.range = range;
	}
	
	public String getGuarantee() {
		return this.guarantee;
	}
	public void setGuarantee(String guarantee) {
		this.guarantee = guarantee;
	}
	
	public String getGuardAddress() {
		return this.guardAddress;
	}
	public void setGuardAddress(String guardAddress) {
		this.guardAddress = guardAddress;
	}
	
	public int getAbility() {
		return this.ability;
	}
	public void setAbility(int ability) {
		this.ability = ability;
	}
	
	public String getContact() {
		return this.contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	
	public String getContactWay(){
		return this.contactWay;
	}
	public void setContactWay(String contactWay) {
		this.contactWay = contactWay;
	}

	public String getMachinist() {
		return this.machinist;
	}
	public void setMachinist(String machinist) {
		this.machinist = machinist;
	}
	
}
