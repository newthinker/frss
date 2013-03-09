/**    
 * 文件名：CryptUtil.java    
 *    
 * 版本信息：1.0    
 * 日期：Sep 30, 2010    
 * Copyright (c) ESRI China (Beijing) Ltd. All rights reserved   
 * 版权所有    
 *    
 */
package com.frss.util;

import com.esri.arcgis.util.security.Cryptographer;

/** 
 * <p>   
 * 功能描述: ShareKey加密、解密通用方法类    
 * </p> 
 * <p>   
 * 主要方法:   
 *       <li></li>
 *       <li></li>
 * </p> 
 * @author      Zhao Pu
 * @version     1.0    
 * @date        Sep 30, 2010 11:08:37 AM    
 * @since     
 * @see   
 * <p>
 * 其它：
 * </p>    
 * <p>
 * 修改历史：
 * </p> 
 *  
 */
public class CryptUtil {
	
	/**
	 * 解密
	 */
	public static String getDecryptSharedKey(String sharedkey) {
		String key = null;
		try {
			Cryptographer cryptographer = new Cryptographer();
			key = cryptographer.decrypt(sharedkey);
		} catch (Exception e) {
		}
		return key;
	}
	
	/**
	 * 加密
	 */
	public static String getEncryptSharedKey(String sharedkey) {
		String key = null;
		try {
			Cryptographer cryptographer = new Cryptographer();
			key = cryptographer.encrypt(sharedkey);
		} catch (Exception e) {
		}
		return key;
	}

}
