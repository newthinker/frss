/**
 * @文件名称: LoginDAO.java
 * @所属包名: com.frss.dao
 * @文件描述: TODO
 * @创建时间: 2012-1-8 上午1:00:41
 * @作         者: Michael.Cho, zuow11@gmail.com
 * @版本信息: V1.0
 */
package com.frss.dao.util;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.frss.model.main.UserInfo;
import com.frss.model.mapping.FrssUserInfo;

/**
 * @类型名称: LoginDAO
 * @类型描述: 用户登录的数据操作类
 * @作           者: Michael.Cho, zuow11@gmail.com
 * @创建时间: 2012-1-8 上午1:00:41
 *
 */
public class LoginDAO {

	// 根据用户名从数据库中查找用户信息
	public UserInfo userLogin(String userName) {
		UserInfo userInfo = null;
		StringBuffer sql = new StringBuffer();
		sql.append("select fu.id,fu.userName,fu.md5Password,fu.userType,fu.fullName,");
		sql.append("fu.subType,fu.description,fu.department,fu.email,fu.createMan");
		sql.append(" from ");
		sql.append(FrssUserInfo.class.getName()).append(" fu");
		sql.append(" where fu.userName = '").append(userName).append("'");

		Session session = BaseDAO.getSession();
		Query queryObject = session.createQuery(sql.toString());
		List retList = queryObject.list();
		session.close();
		if (retList != null && retList.size() > 0) {
			Object[] tempObject = (Object[]) retList.get(0);
			userInfo = new UserInfo();
			userInfo.setUserID((Long) tempObject[0]);
			userInfo.setUserName((String) tempObject[1]);
			userInfo.setMD5PassWord((String) tempObject[2]);
			userInfo.setUserType((Integer) tempObject[3]);
			userInfo.setFullName((String) tempObject[4]);
			userInfo.setSubType((Integer) tempObject[5]);
			userInfo.setDescription((String) tempObject[6]);
			userInfo.setEmail((String) tempObject[7]);
			userInfo.setCreateMan((String) tempObject[9]);
		}
		
		return userInfo;		
	}
	
}
