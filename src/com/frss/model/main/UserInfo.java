/**
 * @文件名称: UserInfo.java
 * @所属包名: com.frss.model
 * @文件描述: 数据库中Users表的映射类
 * @创建时间: 2012-1-5 下午10:55:58
 * @作         者: Michael.Cho, zuow11@gmail.com
 * @版本信息: V1.0
 */
package com.frss.model.main;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.frss.model.mapping.FrssUserInfo;
import com.frss.util.CryptUtil;

/**
 * @类型名称: UserInfo
 * @类型描述: 描述登录用户的信息
 * @作           者: Michael.Cho, zuow11@gmail.com
 * @创建时间: 2012-1-5 下午10:55:58
 *
 */
public class UserInfo {
	// 登录用户自增量编码，作为主键
	private Long userID;
	// 登录用户名
	private String userName;
	// 登录用户的md5密码
	private String md5Password;
	// 用户的类型
	private Integer userType;
	// 用户的名字
	private String fullName;
	// 用户的下属类型
	private Integer subType;
	// 用户描述信息
	private String description;
	// 用户所属部门
	private String department;
	// 用户的email地址
	private String email;
	// 用户的创建者
	private String createMan;
	
	// 用户明文密码
	private String passWord;
	
	public Long getUserID() {
		return this.userID;
	}
	public void setUserID(Long id) {
		this.userID = id;
	}
	
	public String getUserName() {
		return this.userName;
	}
	public void setUserName(String username) {
		this.userName = username;
	}
	
	public Integer getUserType() {
		return this.userType;
	}
	
	public String getMD5PassWord(){
		return this.md5Password;
	}
	public void setMD5PassWord(String md5password) {
		this.md5Password = md5password;
	}
	
	public void setUserType(Integer usertype) {
		this.userType = usertype;
	}
	
	public String getFullName() {
		return this.fullName;
	}
	public void setFullName(String fullname){
		this.fullName = fullname;
	}
	
	public Integer getSubType() {
		return this.subType;
	}
	public void setSubType(Integer subtype) {
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
	
	public String getPassWord() {
		return this.passWord;
	}
	public void setPassWord(String password) {
		/// 还需要加上MD5加密算法
		this.passWord = password;
		if ((password != null) && (!password.equals(""))) {
			this.md5Password = getMD5Str(password);
		}
	}
	
	// 重载操作
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;

		UserInfo other = (UserInfo) obj;

		return true;
	}
	
	/** 
     * MD5 加密 
     */  
    public String getMD5Str(String str) {  
    	String md5String = null;
  
    	if(str==null) {
    		/// log
    		return null;
    	}
    	
    	md5String = CryptUtil.getEncryptSharedKey(str);
    	
        return md5String;  
    } 
    // MD5解密
    public String getPassword(String str) {
    	String passWord = null;
    	
    	if(str==null) {
    		// log
    		return null;
    	}
    	
    	passWord = CryptUtil.getDecryptSharedKey(str);
    	
    	return passWord;
    }
  
    // 使用FrssUserInfo 初始化 UserInfo
    public void initialize(FrssUserInfo userInfo) {
    	this.userID = userInfo.getId();
    	this.userName = userInfo.getUserName();
    	this.md5Password = userInfo.getMd5PassWord();
    	this.passWord = getPassword(this.md5Password);
    	this.userType = userInfo.getUserType();
    	this.fullName = userInfo.getFullName();
    	this.subType = userInfo.getSubType();
    	this.description = userInfo.getDescription();
    	this.department = userInfo.getDepartment();
    	this.email = userInfo.getEmail();
    	this.createMan = userInfo.getCreateMan();  	
    }
}
