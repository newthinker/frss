/**
 * @文件名称: users.java
 * @所属包名: com.frss.model.mapping
 * @文件描述: TODO
 * @创建时间: 2012-1-9 上午11:17:16
 * @作         者: Michael.Cho, zuow11@gmail.com
 * @版本信息: V1.0
 */
package com.frss.model.mapping;

/**
 * @类型名称: users
 * @类型描述: TODO
 * @作           者: Michael.Cho, zuow11@gmail.com
 * @创建时间: 2012-1-9 上午11:17:16
 *
 */
public class FrssUserInfo {

	private long id;
	private String userName;
	private String md5Password;
	private int userType;
	private String fullName;
	private int subType;
	private String description;
	private String email;
	private String createMan;
	private String department;
	
	// Constructors

	/** default constructor */
	public FrssUserInfo() {
	}

	/** minimal constructor */
	public FrssUserInfo(long userid, String username, String md5password, int usertype) {
		this.id = userid;
		this.userName = username;
		this.md5Password = md5password;
		this.userType = usertype;
	}

	/** full constructor */
	public FrssUserInfo(long userid, String username, String md5password,
			int usertype, String fullname, int subtype, String description, String department,
			String email, String org, String createman) {
		this.id = userid;
		this.userName = username;
		this.md5Password = md5password;
		this.userType = usertype;
		this.fullName = fullname;
		this.subType = subtype;
		this.description = description;
		this.department = department;
		this.email = email;
		this.createMan = createman;
	}
	
	public long getId() {
		return this.id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public String getUserName() {
		return this.userName;
	}
	public void setUserName(String username) {
		this.userName = username;
	}
	
	public String getMd5PassWord(){
		return this.md5Password;
	}
	public void setMd5PassWord(String md5password) {
		this.md5Password = md5password;
	}

	public int getUserType() {
		return this.userType;
	}
	public void setUserType(int usertype) {
		this.userType = usertype;
	}
	
	public String getFullName() {
		return this.fullName;
	}
	public void setFullName(String fullname){
		this.fullName = fullname;
	}
	
	public int getSubType() {
		return this.subType;
	}
	public void setSubType(int subtype) {
		this.subType = subtype;
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
	
	public String getEmail() {
		return this.email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getCreateMan() {
		return this.createMan;
	}
	public void setCreateMan(String createman) {
		this.createMan = createman;
	}
	
}
