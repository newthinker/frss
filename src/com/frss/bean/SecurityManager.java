package com.frss.bean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.frss.dao.main.SecurityDAO;
import com.frss.dao.util.BaseDAO;
import com.frss.util.FrssException;

public class SecurityManager {
	// LOG
	private Log log = LogFactory.getLog(SecurityManager.class);
		
	protected Session session = null;
	protected Transaction Trans = null;
	protected FlushMode flushMode = null;
	
	// DB 连接管理
	protected void connect(SecurityDAO dao) throws FrssException {
		if (session == null) {
			try {
				session = BaseDAO.getSession();
				flushMode = session.getFlushMode();
			} catch (Exception e) {
				log.error("获得数据库连接失败 ! ERROR = [" + e.getMessage() + "]");
				throw new FrssException(
						FrssException.CONNERROR, e);
			}
		}
		if (!session.isOpen()){
			session = BaseDAO.getSession();
			flushMode = session.getFlushMode();
		}
		dao.setSession(session);		
	}

	// DB 开始事务处理
	protected void startTrans() throws FrssException {
		if (session == null) {
			log.error("数据库连接为空，不能开始事务 ! ");
			throw new FrssException(
					FrssException.CONNERROR);
		}
		try {
			session.setFlushMode(FlushMode.COMMIT);
			Trans = session.beginTransaction();
		} catch (Exception e) {
			log.error("开始事务失败 ! ERROR = [" + e.getMessage() + "]");
			throw new FrssException(
					FrssException.CONNERROR, e);
		}
	}	

	// DB 提交事务处理
	protected void commitTrans() throws FrssException {
		if (session == null || Trans == null) {
			throw new FrssException("数据库连接为空，不能提交事务!");
		}
		try {
			Trans.commit();
			session.setFlushMode(this.flushMode);
		} catch (Exception e) {
			log.error("开始事务失败 ! ERROR = [" + e.getMessage() + "]");
			throw new FrssException(
					FrssException.CONNERROR, e);
		}
	}

	// DB 事务回滚
	protected void rollbackTrans() throws FrssException {
		if (session == null || Trans == null) {
			throw new FrssException("数据库连接为空，不能回滚事务!");
		}
		try {
			Trans.rollback();
			session.setFlushMode(this.flushMode);
		} catch (Exception e) {
			log.error("事务失败 ! ERROR = [" + e.getMessage() + "]");
			throw new FrssException(
					FrssException.CONNERROR, e);
		}
	}
	
	// DB 释放所有连接
	protected void closeAll()  {
		try {
			if (session != null && session.isOpen()){
				session.close();
				session = null;
			}
			
		} catch (Exception e) {
			log.error("释放连接失败 ! ERROR = [" + e.getMessage() + "]");
			e.printStackTrace();
		}
	}		
	
}

