/**
 * @文件名称: LoginManager.java
 * @所属包名: com.frss.business
 * @文件描述: 管理登录操作
 * @创建时间: 2012-1-5 下午3:10:41
 * @作         者: Michael.Cho, zuow11@gmail.com
 * @版本信息: V1.0
 */
package com.frss.bean;

import java.util.ArrayList;
import java.util.Map;

import net.sf.json.JSONObject;

import com.frss.dao.main.UserDAO;
import com.frss.dao.util.LoginDAO;
import com.frss.model.main.UserInfo;
import com.frss.model.mapping.FrssUserInfo;
import com.frss.util.CryptUtil;
import com.frss.util.FrssException;
import com.frss.util.ReturnFlag;

/**
 * @类型名称: LoginManager
 * @类型描述: 登录的业务类
 * @作           者: Michael.Cho, zuow11@gmail.com
 * @创建时间: 2012-1-5 下午3:10:41
 *
 */
public class LoginManagerBean {

	// 用户信息
	private UserInfo loginUser = null;
	private boolean bLogin = false;
	public ArrayList<String> arrUser = null;
	
	/// 验证用户登录信息
	public ReturnFlag userLogin(String username, String password) {
		bLogin = false;
		String inputMD5 = "";
		String msg = null;
		ReturnFlag rf = new ReturnFlag();
		
//		String md5Pwd = CryptUtil.getEncryptSharedKey("admin");
		
		LoginDAO logindao = new LoginDAO();
		loginUser = logindao.userLogin(username);
		
		if (loginUser == null) {
			msg = "用户不存在，请确认！";
			System.out.print(msg);
			rf.setFlag(1);
			rf.setMessage(msg);
			return rf;
			
		}		
		
		inputMD5 = loginUser.getMD5Str(password);
		// 剔除超级用户 !userinfo.getUserType().equals("0") 
		if ( loginUser.getMD5PassWord() != null
				&& loginUser.getMD5PassWord().equals(inputMD5)) {
			/// 记录登录日志信息		
			bLogin = true;		
			
		}  else {
			msg = "密码不正确,请重新输入！";
			System.out.print(msg);
			rf.setFlag(2);
			rf.setMessage(msg);
			
			bLogin = false;
			loginUser = null;
			
			return rf;
		}
		
		System.out.print("username="+username+"\n");
		System.out.print("userlevel="+loginUser.getUserType()+"\n");
		
		rf.setFlag(0);
		
		return rf;
	}
	
	/**
	 * @函数名称: modifyUserPassword
	 * @函数描述: 修改用户密码
	 * @输入参数: @param oldMd5Pwd
	 * @输入参数: @param newMd5Pwd
	 * @输入参数: @return
	 * @输入参数: @throws FrssException
	 * @返回类型: ReturnFlag
	 * @throws
	 */
	public ReturnFlag modifyUserPassword(String oldMd5Pwd, String newMd5Pwd) throws FrssException {
		ReturnFlag rf = new ReturnFlag();
		String msg = null;
		
		if(loginUser==null) {
			/// log，先登录
			msg = "无法获取当前登录用户，请重新登录！";
			rf.setMessage(msg);
			rf.setFlag(1);
			
			return rf;
		}
		
		oldMd5Pwd = loginUser.getMD5Str(oldMd5Pwd);
		if(!loginUser.getMD5PassWord().equals(oldMd5Pwd)) {
			msg = "输入的旧密码不正确，请确认！";
			rf.setMessage(msg);
			rf.setFlag(2);
			
			return rf;
		}
		
		try {
			// 将明文密码进行转换 [zuow, 2012/04/09]
			newMd5Pwd = loginUser.getMD5Str(newMd5Pwd);
			loginUser.setMD5PassWord(newMd5Pwd);
			UserDAO userDAO = new UserDAO();
			if(userDAO==null) {
				msg = "修改密码出现异常！";
				rf.setMessage(msg);
				rf.setFlag(1);
				return rf;
			}
			
			int iFlag = userDAO.updateUser(loginUser);
			if(iFlag>0) {
				rf.setFlag(0);
			} else {
				msg = "修改密码记录失败！";
				rf.setMessage(msg);
				rf.setFlag(1);
			}
		} catch (Exception e) {
			/// log
			msg = "修改密码出现异常！";
			rf.setMessage(msg);
			rf.setFlag(1);
			return rf;
		}
			
		return rf;
	}
		
	/// 获取当前登录用户
	public UserInfo getLoginUser() {
		
		return this.loginUser;
	}

	public ArrayList<String> getArrayUserInfo() {
		// 检查是否登录
		if(!bLogin) {
			// 没有登录
			return null;
		}
		
		// 保存用户信息
		arrUser = new ArrayList<String>();
		if(arrUser==null || loginUser==null) {
			// log 获取用户信息失败
			return null;
		}		
		
		arrUser.add((loginUser.getUserID()).toString());
		arrUser.add(loginUser.getUserName());
		arrUser.add(loginUser.getDepartment());
		arrUser.add((loginUser.getUserType()).toString());
		
		return arrUser;
	}

	/**
	 * @函数名称: insertNewUser
	 * @函数描述: 插入新用户到数据库
	 * @输入参数: @param mapNewUser
	 * @输入参数: @return
	 * @返回类型: ReturnFlag
	 * @throws
	 */
	public ReturnFlag insertNewUser(Map<String, String> mapNewUser) {
		ReturnFlag rf = new ReturnFlag();
		String msg = null;
		
		// 首先获取当前登录帐号信息
		try {	// 插入故障维修反馈表，插入到反馈表中后，再根据维修方式(现场维修/远程支援)来相应更新派遣表和远程支援表
			// 获取用户信息
			long userId = Long.parseLong(mapNewUser.get("userId"));
			UserDAO userDAO = new UserDAO();
			loginUser = userDAO.queryUser(userId);
			if(userId<=0 || userDAO==null || loginUser==null) {
				/// log
				rf.setFlag(1);
				msg = "获取当前登录用户失败！";
				rf.setMessage(msg);
				return rf;
			}	
			
			FrssUserInfo newUser = new FrssUserInfo();
			rf.setFlag(2);
			msg = "输入新用户信息非法！";
			rf.setMessage(msg);
			if(mapNewUser.get("name")==null) {
				return rf;
			} else {
				newUser.setUserName(mapNewUser.get("name"));
			}
			
			// 检查是否有重复用户名 [zuow, 2012/05/07]
			boolean flag = userDAO.isUserNameExist(mapNewUser.get("name"));
			if(flag) {
				msg = "存在同名用户，请重新输入！";
				System.out.print(msg);
				rf.setFlag(4);
				rf.setMessage(msg);
				return rf;
			}
			
			int userType = 0;
			if(mapNewUser.get("level")==null) {
				return rf;
			} else {
				userType = Integer.parseInt(mapNewUser.get("level"));
				newUser.setUserType(userType);
			}
			if(mapNewUser.get("fullname")!=null)
				newUser.setFullName(mapNewUser.get("fullname"));
			if(mapNewUser.get("depart")!=null)
				newUser.setDepartment(mapNewUser.get("depart"));
			if(mapNewUser.get("descript")!=null)
				newUser.setDescription(mapNewUser.get("description"));
			newUser.setCreateMan(loginUser.getUserName());
			if(mapNewUser.get("depart")!=null)
				newUser.setDepartment(mapNewUser.get("depart"));
			// 设置密码，默认密码为111111
			String md5Pwd = "111111";
			md5Pwd = loginUser.getMD5Str(md5Pwd);
			newUser.setMd5PassWord(md5Pwd);
			
			// 插入新用户到数据库中
			userId = userDAO.insertUser(newUser);
			if(userId<=0) {
				rf.setFlag(3);
				msg = "导入新用户失败！";
				rf.setMessage(msg);
				return rf;
			}
			
		} catch(Exception e) {
			rf.setFlag(4);
			msg = "导入新用户出现异常！";
			rf.setMessage(msg);
			return rf;
		}
		
		rf.setFlag(0);
		return rf;
	}

}
