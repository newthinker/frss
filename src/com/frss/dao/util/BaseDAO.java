/**
 * @文件名称: BaseDAO.java
 * @所属包名: com.frss.dao
 * @文件描述: TODO
 * @创建时间: 2012-1-8 上午12:51:40
 * @作         者: Michael.Cho, zuow11@gmail.com
 * @版本信息: V1.0
 */
package com.frss.dao.util;

import org.hibernate.Session;
import org.hibernate.Transaction;


/**
 * @类型名称: BaseDAO
 * @类型描述: 基本数据库操作类
 * @作           者: Michael.Cho, zuow11@gmail.com
 * @创建时间: 2012-1-8 上午12:51:40
 *
 */
public class BaseDAO {
	 /** transaction的线程本地变量 */
    private static final ThreadLocal threadLocalTrans = new ThreadLocal();
    
	public static Session getSession() {
		return HibernateSessionFactory.getSession();
	}
	
	public static  void closeSession(){
		try{
			HibernateSessionFactory.closeSession();
		}catch(Exception e){
			
		}
	}	
	/**
     * 在本地session实例上开启事务
    */
    public static void beginTransaction(){
      Transaction Trans = (Transaction)threadLocalTrans.get();
      if(Trans == null){
           Trans =getSession().beginTransaction();
           threadLocalTrans.set(Trans);
      }
    }
        
    /**
     * 在本地session实例上提交事务
     */
    public static void commitTransaction(){
      Transaction Trans = (Transaction)threadLocalTrans.get();
      if(Trans != null && !Trans.wasCommitted() && !Trans.wasRolledBack()){
   	   Trans.commit();
          threadLocalTrans.set(null);
      }
    }         
    /**
     * 在本地session实例上回滚事务
     */
    public static void rollbackTransaction(){
      Transaction Trans = (Transaction)threadLocalTrans.get();
      threadLocalTrans.set(null);
      if(Trans != null && !Trans.wasCommitted() && !Trans.wasRolledBack()){
   	   Trans.rollback();
      }
    }
}
