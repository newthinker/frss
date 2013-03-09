package com.frss.util;

public class ValidCheck {
	
	private static String[] noChar = {"'", "%", "^", "&", "?", "/", "\\", "\"" };
	
	/**
	 * @函数名称: validCheck
	 * @函数描述: 检查字符串是否包含非法字符
	 * @输入参数: @param inString
	 * @输入参数: @return
	 * @返回类型: boolean
	 * @throws
	 */
	public static boolean validCheck(String inString) {
		for(int i=0;i<noChar.length;i++) {
			if(inString.contains(noChar[i]))
				return false;
		}
		
		return true;
	}
	
	/**
	 * @函数名称: validCheck
	 * @函数描述: 检查2个字符串是否包含非法字符串
	 * @输入参数: @param inString1
	 * @输入参数: @param inString2
	 * @输入参数: @return
	 * @返回类型: boolean
	 * @throws
	 */
	public static boolean validCheck(String inString1, String inString2) {
		for(int i=0;i<noChar.length;i++){
			if(inString1.contains(noChar[i]) || inString2.contains(noChar[i]))
				return false;
		}
		
		return true;
	}
	
}
