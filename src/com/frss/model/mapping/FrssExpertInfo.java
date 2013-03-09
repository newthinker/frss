package com.frss.model.mapping;

import java.util.Set;

public class FrssExpertInfo {
	private long id;				// 专家编号
	private String name;			// 专家姓名
	private String department;		// 专家所属单位
	private String email;			// 专家的email邮箱地址
	private String contact;			// 专家的联系人
	private String contactWay;		// 专家的联系人的联系方式
	private Set professions;// 专家擅长的专业方向
	private long userId;			// 对应用户信息表中的Id
	
	public FrssExpertInfo() {
		
	}
	public FrssExpertInfo (Long expertID, String expertName, String expertEmail,
			String expertContact, String contactWay, String[] professional, long userId) {
		this.id = expertID;
		this.name = expertName;
		this.email = expertEmail;
		this.contact = expertContact;
		this.contactWay = contactWay;
//		this.professional = professional;
		this.userId = userId;
	}
	
	public Long getId() {
		return this.id;
	}
	public void setId (Long expertID) {
		this.id = expertID;
	}
	
	public String getName () {
		return this.name;
	}
	public void setName (String expertName) {
		this.name = expertName;
	}
	
	public String getDepartment() {
		return this.department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}

	public String getEmail() {
		return this.email;
	}
	public void setEmail (String expertEmail) {
		this.email = expertEmail;
	}
	
	public String getContact() {
		return this.contact;
	}
	public void setContact (String expertContact) {
		this.contact = expertContact;
	}
	
	public String getContactWay () {
		return this.contactWay;
	}
	public void setContactWay (String contactWay) {
		this.contactWay = contactWay;
	}
	
	public Set getProfessions() {
		return this.professions;
	}
	public void setProfessions(Set professions) {
		this.professions = professions;
	}
	
	public long getUserId() {
		return this.userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
}
