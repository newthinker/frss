package com.frss.model.main;

import java.util.ArrayList;

public class ExpertInfoForm {
	private long id;			// 专家编号
	private String name;		// 专家姓名
	private String email;		// 专家邮箱
	private String contact;		// 专家联系人
	private String contactWay;	// 专家联系方式
	private String department;	// 专家所属单位
	private ArrayList<String> profession;		// 专家擅长的方向
	
	public ExpertInfoForm() {
		
	}
	
	public long getExpertId() {
		return this.id;
	}
	public void setExpertId(long expertId) {
		this.id = expertId;
	}
	
	public String getExpertName() {
		return this.name;
	}
	public void setExpertName(String expertName) {
		this.name = expertName;
	}
	
	public String getExpertEmail() {
		return this.email;
	}
	public void setExpertEmail(String expertEmail) {
		this.email = expertEmail;
	}
	
	public String getContact() {
		return this.contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	
	public String getContactWay() {
		return this.contactWay;
	}
	public void setContactWay(String contactWay) {
		this.contactWay = contactWay;
	}
	
	public String getDepartment() {
		return this.department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	
	public ArrayList<String> getProfession() {
		return this.profession;
	}
	public void setProfession(ArrayList<String> profession) {
		this.profession = profession;
	}
	
}
