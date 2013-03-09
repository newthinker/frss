package com.frss.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class FrssException extends Exception {
	/**
	 * @ 字段 serialVersionUID: TODO
	 * @ 字段类型: long
	 */
	private static final long serialVersionUID = 3971812021344216255L;
	// log
	private Log log = LogFactory.getLog(FrssException.class);
	// Error message type
	public static enum errType {DBConnect, DBRelease, SQL};
	public static final String CONNERROR = "DBConnError";
	public static final String RELEASEERROR = "DBReleaseError";
	public static final String SQLERROR = "SQLError";	
	
	public FrssException(String message, Exception cause) {
		super(message, cause);
		log.error(message + "<" +cause.getMessage() +">");
	}

	public FrssException(String message) {
		super(message);
		log.error(message);
	}
	public FrssException(Exception cause) {
		super(cause);
	}
}
