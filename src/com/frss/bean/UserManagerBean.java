package com.frss.bean;

import java.util.ArrayList;
import java.util.HashSet;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.frss.dao.main.UserDAO;
import com.frss.model.main.UserInfo;

public class UserManagerBean {

	/**
	 * @函数名称: querySubDepartment
	 * @函数描述: 根据用户类型，查询其下所有用户单位(分级别)
	 * @输入参数: @param userType
	 * @返回类型: void
	 * @throws
	 */
	public JSONArray querySubDepartment(long userId) {
		int level = 0;
		
//		// 首先获取当前登录用户的子级级别
//		switch(userType) {
//		case UserDAO.Formater:
//		case UserDAO.Distributor:
//			subType = UserDAO.Military;
//			level = 3;
//			break;
//		case UserDAO.Military:
//			subType = UserDAO.GroupArmy;
//			level = 2;
//			break;
//		case UserDAO.GroupArmy:
//			subType = UserDAO.Regiment;
//			level = 1;
//			break;
//		}
//		
//		// 获取当前用户下的所有直接下级部门
//		UserDAO userDAO = new UserDAO();
//		ArrayList<String> arrDepartment = userDAO.queryDepartment(subType);
		
		// 根据userId查询当前用户所属部门
		UserDAO userDAO = new UserDAO();
		UserInfo userInfo = userDAO.queryUser(userId);
		int userType = userInfo.getUserType();
		String department = userInfo.getDepartment();
		
		switch(userType) {
		case UserDAO.Formater:
		case UserDAO.Distributor:
			level = 4;
			break;
		case UserDAO.Military:
			level = 3;
			break;
		case UserDAO.GroupArmy:
			level = 2;
			break;		
		}
		
		if(level<2 || level>4) {
			// 当前用户非法
			return null;
		}
		
		// by lxd
//		JSONArray ja = getSubDepartment(level, department);
		JSONArray ja = new JSONArray();
		JSONArray ch = new JSONArray();
		getSubDepartment(ja, 0, department, level, ch);
		JSONObject jo = new JSONObject();
		jo.put("id", 0);
		jo.put("level", level);
		jo.put("name", department);
		jo.put("children", ch);
		jo.put("root", true);
		ja.add(jo);


		return ja;
	}
	
	/**
	 * @函数名称: getSubDepartment
	 * @函数描述: 根据当前的级别以及部门列表获取所有的子级列表
	 * @输入参数: @param level
	 * @输入参数: @param arrDepartment
	 * @返回类型: void
	 * @throws
	 */
	public JSONArray getSubDepartment(int level, String department){
		JSONArray ja = null;
		UserDAO userDAO = new UserDAO();
		
		String parentDepartment = department;
		
		// 查出当前部门下的所有子级部门
		HashSet<String> arrSubDepartment =  userDAO.querySubDepartment(parentDepartment);
		
		System.out.print("parent(level): " + parentDepartment + "("+ level + ")\n");
		
		level--;
			
		if(level>0) {
			ja = new JSONArray();
			for(String subDepartment : arrSubDepartment) {
				System.out.print("parent(level)-son(level) :" + parentDepartment + "(" + (level+1) + ")-" + subDepartment + "(" + level + ")\n");		
				
				if(level==1) {	// 如果是最底层级，就列出所有单位
					JSONObject jo = new JSONObject();
					jo.put("level", level);
					jo.put("parent", parentDepartment);
					jo.put("name", subDepartment);
					ja.add(jo);
				} else {	// 如果不是，继续查找其下所有子级部门					
					JSONObject jo = new JSONObject();
					jo.put("level", level);
					jo.put("parent", parentDepartment);
					jo.put("name", subDepartment);
					ja.add(jo);
					
					JSONArray jaTemp = getSubDepartment(level, subDepartment);
					if(jaTemp!=null) {
						ja.add(jaTemp);					
					}
				}				
			}
		}
		
		return ja;
	}
	
	/**
	 * @函数名称: getSubDepartment
	 * @函数描述: 查找当前部门下的所有子级部门 [luoxd, 2012/05/16]
	 * @输入参数: @param ja
	 * @输入参数: @param id
	 * @输入参数: @param department
	 * @输入参数: @param level
	 * @输入参数: @param children
	 * @输入参数: @return
	 * @返回类型: int
	 * @throws
	 */
	public int getSubDepartment(JSONArray ja, int id, String department, int level, JSONArray children) {
		UserDAO userDAO = new UserDAO();
		String parentDepartment = department;
		
		HashSet<String> arrSubDepartment =  userDAO.querySubDepartment(parentDepartment);
		System.out.println("parent(level): " + parentDepartment + "("+ level + ")");

		level--;
		int thisid = id;
		if(level>0) {
			for(String subDepartment : arrSubDepartment) {
				System.out.print("parent(level)-son(level) :" + parentDepartment + "(" + (level+1) + ")-" + subDepartment + "(" + level + ")\n");

				id++;
				int newid = id;
				if(level==1) {	// 如果是最底层级，就列出所有单位
					JSONObject jo = new JSONObject();
					jo.put("id", id);
					jo.put("level", level);
//					jo.put("parentdep", parentDepartment);
					jo.put("name", subDepartment);
					jo.put("parent", thisid);
					ja.add(jo);
				} else {	// 如果不是，继续查找其下所有子级部门
					JSONArray childrenTmp = new JSONArray();
					newid = getSubDepartment(ja, id, subDepartment, level, childrenTmp);
					
					JSONObject jo = new JSONObject();
					jo.put("id", id);
					jo.put("level", level);
//					jo.put("parentdep", parentDepartment);
					jo.put("name", subDepartment);
					jo.put("parent", thisid);
					jo.put("children", childrenTmp);
					ja.add(jo);
				}
				JSONObject jo = new JSONObject();
				jo = new JSONObject();
				jo.put("_reference", id);
				children.add(jo);

				id = newid;
			}
		}
		return id;
	}
	
//	/**
//	 * @函数名称: getRegimentUser
//	 * @函数描述: 查找某级部门下所有的团级用户
//	 * @输入参数: @param level
//	 * @输入参数: @param department
//	 * @输入参数: @return
//	 * @返回类型: HashSet<String>
//	 * @throws
//	 */
//	public HashSet<String> getRegimentUser(int level, String department) {
//		HashSet<String> arrUser = null;
//		
//		// 根据userId查询当前用户所属部门
//		UserDAO userDAO = new UserDAO();
//
//		if(level<1 || level>4) {
//			// 当前用户非法
//			return null;
//		}
//		
//		ArrayList<String> arrDepartment = null;
//		if(level==1) {
//			arrDepartment = new ArrayList<String>();
//			arrDepartment.add(department);
//		}else {
//			arrDepartment = getRegimentDepartment(level, department);
//		}
//		if(arrDepartment!=null&&arrDepartment.size()>0) {
//			arrUser = new HashSet<String>();
//			
//			for(String departName : arrDepartment) {
////				HashSet<String> subUser = userDAO.queryUser(departName);
//				
////				arrUser.addAll(subUser);
//			}
//		}
//		
//		return arrUser;
//	}
	
	/**
	 * @函数名称: getSubUser
	 * @函数描述: 根据部门级别以及部门名查找其下所有用户 [zuow, 2012/05/16]
	 * @输入参数: @param level
	 * @输入参数: @param department
	 * @输入参数: @return
	 * @返回类型: HashSet<String>
	 * @throws
	 */
	public HashSet<String> getSubUser(int level, String department) {
		HashSet<String> arrUser = null;
		
		// 根据userId查询当前用户所属部门
		UserDAO userDAO = new UserDAO();

		if(level<1 || level>4) {
			// 当前用户非法
			return null;
		}
		
		int userType = 0;
		switch(level) {
		case 1:
			userType = UserDAO.Regiment;
			break;
		case 2:
			userType = UserDAO.GroupArmy;
			break;
		case 3:
			userType = UserDAO.Military;
			break;
		case 4:
			userType = UserDAO.Formater;
			break;
		}
		
		arrUser = userDAO.queryUser(userType, department);
		
		return arrUser;
	}
	
	/**
	 * @函数名称: getRegimentUser
	 * @函数描述: 获取当前部门下的所有团级部门
	 * @输入参数: @param level
	 * @输入参数: @param department
	 * @输入参数: @return
	 * @返回类型: ArrayList<String>
	 * @throws
	 */
	public ArrayList<String> getRegimentDepartment(int level, String department) {
		ArrayList<String> arrRegiment = new ArrayList<String>();
		UserDAO userDAO = new UserDAO();
		
		String parentDepartment = department;
		
		// 查出当前部门下的所有子级部门
		HashSet<String> arrSubDepartment =  userDAO.querySubDepartment(parentDepartment);
		
		System.out.print("parent(level): " + parentDepartment + "("+ level + ")\n");
		
		level--;
			
		if(level>0) {
			for(String subDepartment : arrSubDepartment) {
				System.out.print("parent(level)-son(level) :" + parentDepartment + "(" + (level+1) + ")-" + subDepartment + "(" + level + ")\n");		
				
				if(level==1) {	// 如果是最底层级，就列出所有单位
					System.out.print(subDepartment);
					
					arrRegiment.add(subDepartment);
				} else {	// 如果不是，继续查找其下所有子级部门					
					ArrayList<String> arrTemp = getRegimentDepartment(level, subDepartment);
					if(arrTemp!=null)
						arrRegiment.addAll(arrTemp);
				}				
			}
		}
		
		return arrRegiment;
	}
}
