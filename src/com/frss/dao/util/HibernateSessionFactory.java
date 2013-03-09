/**    
 * 文件名：HibernateSessionFactory.java    
 *    
 * 版本信息：1.0    
 * 日期：2011-3-20    
 * Copyright (c) 2010-2011 ESRI China (Beijing) Ltd. All rights reserved   
 * 版权所有    
 *    
 */
package com.frss.dao.util;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

/** 
 * <p>   
 * 功能描述:  TODO增加描述   
 * </p> 
 * <p>   
 * 主要方法:   
 *       <li></li>
 *       <li></li>
 * </p> 
 * @author      Zhao Pu
 * @version     1.0    
 * @date        2011-3-20 下午08:57:32    
 * @since     
 * @see   
 * <p>
 * 其它：
 * </p>    
 * <p>
 * 修改历史：
 * </p> 
 * @preserve public
 */
public class HibernateSessionFactory {

	/**
	 * Location of hibernate.cfg.xml file. Location should be on the classpath
	 * as Hibernate uses #resourceAsStream style lookup for its configuration
	 * file. The default classpath location of the hibernate config file is in
	 * the default package. Use #setConfigFile() to update the location of the
	 * configuration file for the current session.
	 */
	private static String CONFIG_FILE_LOCATION = "conf/hibernate.cfg.xml";
	private static final ThreadLocal<Session> threadLocal = new ThreadLocal<Session>();
	private static Configuration configuration = new Configuration();
	private static org.hibernate.SessionFactory sessionFactory;
	private static String configFile = CONFIG_FILE_LOCATION;

	static {
		try {
			configuration.configure(configFile);
			sessionFactory = configuration.buildSessionFactory();
		} catch (Exception e) {
			System.err.println("%%%% Error Creating SessionFactory %%%%");
			e.printStackTrace();
		}
	}

	private HibernateSessionFactory() {
	}

	/**
	 * Returns the ThreadLocal Session instance. Lazy initialize the
	 * <code>SessionFactory</code> if needed.
	 * 
	 * @return Session
	 * @throws HibernateException
	 */
	public static Session getSession() throws HibernateException {
		Session session = (Session) threadLocal.get();

		if (session == null || !session.isOpen()) {
			if (sessionFactory == null) {
				rebuildSessionFactory();
			}
			session = (sessionFactory != null) ? sessionFactory.openSession()
					: null;
			threadLocal.set(session);
		}

		return session;
	}

	/**
	 * Rebuild hibernate session factory
	 * 
	 */
	public static void rebuildSessionFactory() {
		try {
			configuration.configure(configFile);
			sessionFactory = configuration.buildSessionFactory();
		} catch (Exception e) {
			System.err.println("%%%% Error Creating SessionFactory %%%%");
			e.printStackTrace();
		}
	}

	/**
	 * Close the single hibernate session instance.
	 * 
	 * @throws HibernateException
	 */
	public static void closeSession() throws HibernateException {
		Session session = (Session) threadLocal.get();
		threadLocal.set(null);

		if (session != null) {
			session.close();
		}
	}

	/**
	 * return session factory
	 * 
	 */
	public static org.hibernate.SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/**
	 * return session factory
	 * 
	 * session factory will be rebuilded in the next call
	 */
	public static void setConfigFile(String configFile) {
		HibernateSessionFactory.configFile = configFile;
		sessionFactory = null;
	}

	/**
	 * return hibernate configuration
	 * 
	 */
	public static Configuration getConfiguration() {
		return configuration;
	}
	
}
