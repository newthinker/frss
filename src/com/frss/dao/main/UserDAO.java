package com.frss.dao.main;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;

import com.frss.dao.util.BaseDAO;
import com.frss.model.main.UserInfo;
import com.frss.model.mapping.FrssEquipmentInfo;
import com.frss.model.mapping.FrssFaultInfo;
import com.frss.model.mapping.FrssUserInfo;
import com.frss.util.FrssException;

/**
 * <p>
 * 功能描述:
 * </p>
 * <p>
 * 主要方法:
 * <li></li>
 * <li></li>
 * </p>
 * 
 * @author Wang Jinhui
 * @version 1.0
 * @date 2010-7-13 上午11:59:20
 * @since
 * @see <p>
 *      其它：
 *      </p>
 *      <p>
 *      修改历史：
 *      </p>
 * 
 */
public class UserDAO extends SecurityDAO {
	private Log log = LogFactory.getLog(SecurityDAO.class);
	
	// 用户分类
	public final static int Super = 0;			// 超级管理员
	public final static int Military = 1;		// 军区
	public final static int GroupArmy = 2;		// 集团军
	public final static int Regiment = 3;		// 团级
	public final static int Distributor = 4;	// 维修中心分发人员
	public final static int Expert = 5;			// 专家
	public final static int Factory = 6;		// 工业部门
	public final static int Formater = 7;		// 维修中心故障审核者/接线员
	
	/**
	 * @函数名称: insertUser
	 * @函数描述: 将新用户插入到数据库中
	 * @输入参数: @param user
	 * @输入参数: @return
	 * @返回类型: long
	 * @throws
	 */
	public long insertUser(FrssUserInfo user) {
		Session session = null;
		Transaction trans = null;
		long userId = 0;
		
		try {
			session = BaseDAO.getSession();
			trans = session.beginTransaction();
			session.save(user);
			trans.commit();
			session.close();
			
			userId = user.getId();
			
		} catch (Exception e) {
			/// log
			trans.rollback();
			return 0;
		}
		
		return userId;
	}
	
	/**
	 * @函数名称: insertUser
	 * @函数描述: 添加用户
	 * @输入参数: @param user
	 * @输入参数: @throws FrssException
	 * @返回类型: void
	 * @throws
	 */
	public void insertUser(UserInfo user) throws FrssException {
		Session session = null;
		Transaction trans = null;
		
		try {
//			log.debug("增加用户 user = " + user.toString());
			FrssUserInfo insertObject = new FrssUserInfo();
			insertObject.setUserName(user.getUserName());
			insertObject.setFullName(user.getFullName());
			insertObject.setUserType(user.getUserType());
			insertObject.setSubType(user.getSubType());
			insertObject.setDescription(user.getDescription());
			insertObject.setMd5PassWord(user.getMD5PassWord());
			insertObject.setEmail(user.getEmail());
			insertObject.setCreateMan(user.getCreateMan());
			insertObject.setDepartment(user.getDepartment());
			
			session = BaseDAO.getSession();
			trans = session.beginTransaction();
			session.save(insertObject);
			trans.commit();
			session.close();
			
		} catch (Exception e) {
			trans.rollback();	
//			log.error(e.getMessage());
			throw new FrssException(e);
		}
	}
	
	/**
	 * @函数名称: updateUser
	 * @函数描述: 更新用户信息，包括修改密码
	 * @输入参数: @param user
	 * @输入参数: @throws FrssException
	 * @返回类型: void
	 * @throws
	 */
	public int updateUser(UserInfo user) throws FrssException {
		int ret = 0;
		String hql = "update " + FrssUserInfo.class.getName() +" user "
				+ "set user.userName = :username, user.fullName = :fullname, user.userType = :usertype, user.subType = :subtype, "
				+ "user.description = :description, user.department = :department, user.email = :email, user.createMan = :createman ";
		if (user.getMD5PassWord() != null) {
			hql = hql.concat(", user.md5Password = :md5password");
		}
		hql += " where user.id = :userid";
		
		Transaction trans = null;
		try {

			Session session = BaseDAO.getSession();
//			trans = session.beginTransaction();
			Query query = session.createQuery(hql);	
			query.setString("username", user.getUserName());
			query.setString("fullname", user.getFullName());
			query.setInteger("usertype", user.getUserType());
			query.setInteger("subtype", user.getSubType());
			query.setString("description", user.getDescription());
			query.setString("department", user.getDepartment());
			query.setString("email", user.getEmail());
			query.setString("createman", user.getCreateMan());
			if (user.getMD5PassWord() != null) {
				query.setString("md5password", user.getMD5PassWord());
			} 
			query.setLong("userid", user.getUserID());
			
			ret = query.executeUpdate();			
//			trans.commit();			
			
		} catch (Exception e) {
//			trans.rollback();
			throw new FrssException(e);
		}
		
		return ret;
	}

	/**
	 * @函数名称: queryDepartment
	 * @函数描述: 根据用户类型查询当前用户类型下所有的单位名称
	 * @输入参数: @param userType
	 * @输入参数: @return
	 * @返回类型: ArrayList<String>
	 * @throws
	 */
	public ArrayList<String> queryDepartment(int userType) {
		ArrayList<String> arrDepartment = null;
		
		String hql = "select user.department ";
		hql += "from " + FrssUserInfo.class.getName() + " user ";
		hql += "where user.userType=" + userType;
		
		try {
			Session session = BaseDAO.getSession();
			Query objQuery = session.createQuery(hql.toString());
			List<Object> lstResult = objQuery.list();
			session.close();
			
			if(lstResult!=null && lstResult.size()>0) {
				arrDepartment = new ArrayList<String>();
				for(Object obj : lstResult) {
					String department = (String) obj;
					if(department!=null && !(department.equals("")))
						arrDepartment.add(department);
				}
			}
		} catch (Exception e) {
			/// log
			return null;
		}
		
		return arrDepartment;
	}
	
	/**
	 * @函数名称: queryDepartment
	 * @函数描述: 根据创建者查询所有被创建者的单位 [zuow, 2012/04/27]
	 * @输入参数: @param creatMan
	 * @输入参数: @return
	 * @返回类型: ArrayList<String>
	 * @throws
	 */
	public ArrayList<String> queryDepartment(String createMan){
		ArrayList<String> arrDepartment = null;
		
		String hql = "select user.department from " + FrssUserInfo.class.getName() + " user ";
		hql += "where user.createMan='" + createMan + "'";
		
		try {
			Session session = BaseDAO.getSession();
			Query objQuery = session.createQuery(hql.toString());
			List<Object> lstResult = objQuery.list();
			session.close();
			
			if(lstResult!=null && lstResult.size()>0) {
				arrDepartment = new ArrayList<String>();
				for(Object obj : lstResult) {
					String department = (String) obj;
					if(department!=null && !(department.equals("")))
						arrDepartment.add(department);
				}
			}
		} catch (Exception e) {
			/// log
			return null;
		}
		
		return arrDepartment;
	}
	
	/**
	 * @函数名称: querySubDepartment
	 * @函数描述: 查询当前单位的所有下属单位：首先查询当前单位的所有用户名，然后根据用户名查询所有创建的下级用户，再获取所有下级用户的单位 [zuow, 2012/04/27]
	 * @输入参数: @param department
	 * @输入参数: @return
	 * @返回类型: ArrayList<String>
	 * @throws
	 */
	public HashSet<String> querySubDepartment(String department) {
		HashSet<String> subDepartment = null;
		
		// 首先查询当前部门的所有用户
		String hql = "select user.userName from " + FrssUserInfo.class.getName() + " user ";
		hql += "where user.department='" + department + "'";
		
		try {
			Session session = BaseDAO.getSession();
			Query objQuery = session.createQuery(hql.toString());
			List<Object> lstResult = objQuery.list();
			session.close();
			
			if(lstResult!=null && lstResult.size()>0) {
				subDepartment = new HashSet<String>();
				for(Object obj : lstResult) {
					String userName = (String) obj;
					
					// 根据用户名查询所有
					if(userName!=null && !(userName.equals(""))) {
						ArrayList<String> arrDepartment = queryDepartment(userName);
						if(arrDepartment!=null&&arrDepartment.size()>0) {
							subDepartment.addAll(arrDepartment);
						}
					}
				}
			}
		} catch (Exception e) {
			/// log
			return null;
		}
		
		return subDepartment;
	}
	
	/**
	 * @函数名称: queryUser
	 * @函数描述: 根据部门名称查找其下所有用户，增加用户级别条件，排除同名但不同级别的部门名 [zuow, 2012/05/16]
	 * @输入参数: @param department
	 * @输入参数: @return
	 * @返回类型: HashSet<String>
	 * @throws
	 */
	public HashSet<String> queryUser(int userType, String department) {
		HashSet<String> subUser = null;
		
		// 首先查询当前部门的所有用户
		String hql = "select user.userName from " + FrssUserInfo.class.getName() + " user ";
		hql += "where user.department='" + department + "' and user.userType=" + userType;
		
		try {
			Session session = BaseDAO.getSession();
			Query objQuery = session.createQuery(hql.toString());
			List<Object> lstResult = objQuery.list();
			session.close();
			
			if(lstResult!=null && lstResult.size()>0) {
				subUser = new HashSet<String>();
				for(Object obj : lstResult) {
					String userName = (String) obj;
					
					// 根据用户名查询所有
					if(userName!=null && !(userName.equals(""))) {
						subUser.add(userName);
					}
				}
			}
		} catch (Exception e) {
			/// log
			return null;
		}
		
		return subUser;
	}
	
	/**
	 * @函数名称: getUserForID
	 * @函数描述: 根据用户编号获取用户信息
	 * @输入参数: @param userid
	 * @输入参数: @return
	 * @返回类型: UserInfo
	 * @throws
	 */
	public UserInfo queryUser(long userid)  {
		UserInfo info = null;
		StringBuffer hql = new StringBuffer();
		hql.append("from ").append(FrssUserInfo.class.getName()).append(" u ");
		hql.append("where u.id = ").append(userid);
		
		try {
			Session session = BaseDAO.getSession();
			Query query = session.createQuery(hql.toString());
			List resultList = query.list();
			if (resultList!=null && resultList.size()>0) {
				info = new UserInfo();
				FrssUserInfo userInfo = (FrssUserInfo)resultList.get(0);
				
				info.initialize(userInfo);
			}
			return info;
		} catch (Exception e) {
			/// log
			return null;
		}
	}
 
	/**
	 * @函数名称: queryUser
	 * @函数描述: 根据用户名和用户级别查询用户信息
	 * @输入参数: @param userName
	 * @输入参数: @param userType
	 * @输入参数: @return
	 * @返回类型: UserInfo
	 * @throws
	 */
	public UserInfo queryUser(String userName, int userType) {
		UserInfo info = null;
		String hql = "from " + FrssUserInfo.class.getName() + " us ";
		hql += "where us.userName='" + userName + "' ";
		hql += "and us.userType=" + userType;
		
		try {
			Session session = BaseDAO.getSession();
			Query query = session.createQuery(hql.toString());
			List resultList = query.list();
			if (resultList!=null && resultList.size()>0) {
				info = new UserInfo();
				FrssUserInfo userInfo = (FrssUserInfo)resultList.get(0);
				
				info.initialize(userInfo);
			}
		} catch (Exception e) {
			System.out.println("查询用户信息出现异常!");
			return null;
		}
		
		return info;
	}
	
	/**
	 * @函数名称: isUserNameExist
	 * @函数描述: 查找用户表中是否存在同名用户
	 * @输入参数: @param userName
	 * @输入参数: @return
	 * @返回类型: boolean
	 * @throws
	 */
	public boolean isUserNameExist(String userName) {
		
		String hql = "select count(*) from " + FrssUserInfo.class.getName() + " us ";
		hql += "where us.userName='" + userName + "' ";
//		hql += "group by us.id";
		
		try {
			Session session = BaseDAO.getSession();
			Query query = session.createQuery(hql);
			List resultList = query.list();
			
			long count = (Long)resultList.get(0);
			if(count==0)
				return false;
			
		} catch (Exception e) {
			System.out.print("查找同名用户过程中出现异常!");
			return true;
		}
		
		return true;
	}
	
	// 根据userid来删除用户，如果实现同审核表的同步--即如何处理外键关系？
	// RE:将审核表的ID主键当作用户表的外键，这样在用户表中进行删除操作时就没有影响，
	// 或者将审核表的姓名与用户表的fullname关联也行
	public void delUser(long userid) throws FrssException {
		try {
			String sql = "delete "+ FrssUserInfo.class.getName() 
			+" where userid = :userid";
			Query query = this.getSession().createQuery(sql);
			query.setLong("userid", userid);
			query.executeUpdate();
		} catch (Exception e) {
			throw new FrssException(e);
		}
	}
	
	
	public void deleteUserByName(String username) throws FrssException {
		try {
			String sql = "from " + FrssUserInfo.class.getName() + " where username = :username";
			Query query = this.getSession().createQuery(sql);
			query.setString("username", username);
			List<FrssUserInfo> users = query.list();
			for (int i=0;i<users.size();i++){
				this.delUser(users.get(i).getId());
			}
		} catch (Exception e) {
			throw new FrssException(e);
		} 
	}

//	public UserInfo getUserByName(String username)
//			throws FrssException {
//		UserInfo info = null;
//		StringBuffer sql = new StringBuffer();
//		sql.append("select u.userid as userid,u.username as username,");
//		sql.append("u.fullname as fullname,u.usertype as usertype,");
//		sql.append("u.subtype as subtype,u.description as description,");
//		sql.append("u.md5password as md5password,u.email as email,");
//		sql.append("u.createman as createman, u.department as department");
//		sql.append(" from FRSS_USER_INFO u");
//		sql.append(" where u.username = '").append(username).append("'");
//		
//		try {
//			Query query = this.getSession().createSQLQuery(sql.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
//			List resultList = query.list();
//			if (resultList!=null && resultList.size()>0) {
//				Map tempMap = (Map)resultList.get(0);
//				info = new UserInfo();
//				info = this.setUserInfo(tempMap);
//			}
//			return info;
//		} catch (Exception e) {
//			throw new FrssException(e);
//		}
//	}



//	private UserInfo setUserInfo(Map objMap) throws SQLException {
//		UserInfo user = new UserInfo();
//		user.setUserID(Long.valueOf(objMap.get("USERID").toString()));
//		user.setUserName((String)objMap.get("USERNAME"));
//		user.setFullName((String)objMap.get("FULLNAME"));
//		user.setUserType((Integer)objMap.get("USERTYPE"));
//		user.setSubType((Integer)objMap.get("SUBTYPE"));
//		user.setDescription((String)objMap.get("DESCRIPTION"));
//		user.setEmail((String)objMap.get("EMAIL"));
//		user.setMD5PassWord((String)objMap.get("MD5PASSWORD"));
//		user.setDepartment((String)objMap.get("DEPARTMENT"));
//		user.setCreateMan((String)objMap.get("CREATEMAN"));
//		return user;
//	}

}
