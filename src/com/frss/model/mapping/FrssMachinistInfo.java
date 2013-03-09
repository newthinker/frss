package com.frss.model.mapping;

public class FrssMachinistInfo {
	private long id;
	private String name;
	private String contactWay;
	private String email;
	private int ability;
	private long factoryId;
	
	public FrssMachinistInfo () {
		
	}
	public FrssMachinistInfo(long machinistID, String machinistName, String contactWay,
			String machinistEmail, int ability, long factoryId) {
		this.id = machinistID;
		this.name = machinistName;
		this.contactWay = contactWay;
		this.email = machinistEmail;
		this.ability = ability;
		this.factoryId = factoryId;
	}
	
	public long getId () {
		return this.id;
	}
	public void setId(long machinistID) {
		this.id = machinistID;
	}
	
	public String getName () {
		return this.name;
	}
	public void setName(String machinistName) {
		this.name = machinistName;
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
	public void setEmail (String machinistEmail) {
		this.email = machinistEmail;
	}
	
	public int getAbility() {
		return this.ability;
	}
	public void setAbility(int ability) {
		this.ability = ability;
	}

	public long getFactoryId () {
		return this.factoryId;
	}
	public void setFactoryId(long factoryId) {
		this.factoryId = factoryId;
	}
}
