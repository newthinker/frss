package com.frss.dao.util;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

public class DAOUtil {
	/**
	 * @description 根据hql语句查询，hql语句为map方式
	 * @maintenance
	 * @date
	 * @author
	 * @version
	 * @since
	 * @see
	 * @param ht
	 *            Hibernate模版
	 * @param query
	 *            hql语句
	 * @param params
	 *            map参数
	 * @return
	 */
	public static List findByHqlQuery(String query) {
		return findByHqlQuery(query, null, 0, 0);
	}

	public static List findByHqlQuery(String query, Map params) {
		return findByHqlQuery(query, params, 0, 0);
	}

	public static List findByHqlQuery(String query, int startRow, int maxRow) {
		return findByHqlQuery(query, null, startRow, maxRow);
	}

	public static List findByHqlQuery(String query, Map params, int startRow,
			int maxRow) {
		List list = null;
		try{
			Session session = BaseDAO.getSession();
			BaseDAO.beginTransaction();
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
			BaseDAO.commitTransaction();			
		}catch(Exception e){
			e.printStackTrace();
			BaseDAO.rollbackTransaction();
		}finally{
			BaseDAO.closeSession();
		}
		return list;
	}
	
	public static Long findByHqlQueryCount(String query){
		return findByHqlQueryCount(query,null);
	}
	public static Long findByHqlQueryCount(String query, Map params) {
		Long count = null;
		try{
			Session session = BaseDAO.getSession();
				BaseDAO.beginTransaction();
				Query queryObject = session.createQuery("select count(*) " + query);
				if (params != null) {
					for (Iterator ite = params.keySet().iterator(); ite.hasNext();) {
						String paramName = (String) ite.next();
						queryObject.setParameter(paramName, params.get(paramName));
					}
				}
			count = (Long)queryObject.uniqueResult();
			BaseDAO.commitTransaction();
		}catch(Exception e){
			e.printStackTrace();
			BaseDAO.rollbackTransaction();
		}finally{
			BaseDAO.closeSession();
		}
		return count;
	}

	/**
	 * @description hibernate直接调用sql语句的方法
	 * 
	 * @maintenance
	 * @date
	 * @author
	 * @version
	 * @since
	 * @see
	 * @param ht
	 *            Hibernate模版
	 * @param query
	 *            sql语句
	 * @param params
	 *            map参数
	 * @return
	 */
	public static List findBySqlQuery(String query) {
		return findBySqlQuery(query, null, 0, 0);
	}

	public static List findBySqlQuery(String query, Map params) {
		return findBySqlQuery(query, params, 0, 0);
	}

	public static List findBySqlQuery(String query, int startRow, int maxRow) {
		return findBySqlQuery(query, null, startRow, maxRow);
	}
	public static List findBySqlQuery(String query, Map params, int startRow,
			int maxRow) {
		List list = null;
		try{
			Session session = BaseDAO.getSession();
			BaseDAO.beginTransaction();
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
			BaseDAO.commitTransaction();			
		}catch(Exception e){
			e.printStackTrace();
			BaseDAO.rollbackTransaction();
		}finally{
			BaseDAO.closeSession();
		}
		
		return list;
	}
	
	public static Long findBySqlQueryCount(String query){
		return findBySqlQueryCount(query,null);
	}
	public static Long findBySqlQueryCount(String query, Map params) {
		Long count = null;
		try{
			Session session = BaseDAO.getSession();
				BaseDAO.beginTransaction();
				Query queryObject = session.createSQLQuery("select count(*) from (" + query +")");
				if (params != null) {
					for (Iterator ite = params.keySet().iterator(); ite.hasNext();) {
						String paramName = (String) ite.next();
						queryObject.setParameter(paramName, params.get(paramName));
					}
				}
				count = Long.valueOf(queryObject.uniqueResult().toString());
				BaseDAO.commitTransaction();
				
		}catch(Exception e){
			e.printStackTrace();
			BaseDAO.rollbackTransaction();
		}finally{
			BaseDAO.closeSession();
		}		
		return count;
	}


}
