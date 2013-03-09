/**    
 * 文件名：GeoShareSecurityDAO.java    
 *    
 * 版本信息：1.0    
 * 日期：2010-7-12    
 * Copyright (c) ESRI China (Beijing) Ltd. All rights reserved   
 * 版权所有    
 *    
 */
package com.frss.dao.main;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import com.frss.util.FrssException;

/**
 * <p>
 * 功能描述: 安全模块DAO基类
 * </p>
 * <p>
 * 主要方法:
 * <li>初始化数据连接conn</li>
 * <li></li>
 * </p>
 * 
 * @author Wang Jinhui
 * @version 1.0
 * @date 2010-7-12 下午07:47:23
 * @since
 * @see <p>
 *      其它：
 *      </p>
 *      <p>
 *      修改历史：
 *      </p>
 * 
 */
public class SecurityDAO {
	private Log log = LogFactory.getLog(SecurityDAO.class);
	private Session session = null;
	public Session getSession() {
		return session;
	}

	public void setSession(Session session) throws FrssException {
		this.session = session;
	}
	
	protected void release()
			throws FrssException {
		try {
			if (session != null && session.isOpen())
				session.close();
		} catch (Exception e) {
			log.error("释放连接失败 ! ERROR = [" + e.getMessage() + "]");
			throw new FrssException(
					FrssException.RELEASEERROR, e);
		}
	}
	
	
	public List findByHqlQuery(String query, Map params, int startRow,
			int maxRow) {
		List list = null;
		try{
			Query queryObject = session.createQuery(query);
			if (params != null) {
				for (Iterator ite = params.keySet().iterator(); ite.hasNext();) {
					String paramName = (String) ite.next();
					queryObject.setParameter(paramName, params.get(paramName));
				}
			}
			if (maxRow != 0) {
				queryObject.setFirstResult(startRow);
				queryObject.setMaxResults(maxRow);
			}
			list = queryObject.list();			
		}catch(Exception e){
			e.printStackTrace();			
		}
		return list;
	}
	
	public Long findByHqlQueryCount(String query){
		return findByHqlQueryCount(query,null);
	}
	public Long findByHqlQueryCount(String query, Map params) {
		Query queryObject = session.createQuery("select count(*) " + query);
		if (params != null) {
			for (Iterator ite = params.keySet().iterator(); ite.hasNext();) {
				String paramName = (String) ite.next();
				queryObject.setParameter(paramName, params.get(paramName));
			}
		}
		Long count = (Long)queryObject.uniqueResult();
		return count;
	}

	public List findBySqlQuery(String query, Map params, int startRow,
			int maxRow) {
		List list = null;
		try{
			Query queryObject = session.createSQLQuery(query).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			if (params != null) {
				for (Iterator ite = params.keySet().iterator(); ite.hasNext();) {
					String paramName = (String) ite.next();
					queryObject.setParameter(paramName, params.get(paramName));
				}
			}
			if (maxRow != 0) {
				queryObject.setFirstResult(startRow);
				queryObject.setMaxResults(maxRow);
			}
			list = queryObject.list();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return list;
	}
	
	public Long findBySqlQueryCount(String query){
		return findBySqlQueryCount(query,null);
	}
	public Long findBySqlQueryCount(String query, Map params) {
		Query queryObject = session.createSQLQuery("select count(*) from (" + query +")");
		if (params != null) {
			for (Iterator ite = params.keySet().iterator(); ite.hasNext();) {
				String paramName = (String) ite.next();
				queryObject.setParameter(paramName, params.get(paramName));
			}
		}
		Long count = Long.valueOf(queryObject.uniqueResult().toString());
		return count;
	}

}
