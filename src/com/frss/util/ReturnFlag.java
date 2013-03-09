/**
 * @文件名称: ReturnFlag.java
 * @所属包名: com.frss.util
 * @文件描述: TODO
 * @创建时间: 2012-1-5 下午3:19:42
 * @作         者: Michael.Cho, zuow11@gmail.com
 * @版本信息: V1.0
 */
package com.frss.util;

/**
 * @类型名称: ReturnFlag
 * @类型描述: 描述返回值，
 * 			包含两个字段，flag表示状态，1成功或正确，2失败或错误，  
 * 			 message表示失败或错误信息，当flag=1时，可为空
 * @作           者: Michael.Cho, zuow11@gmail.com
 * @创建时间: 2012-1-5 下午3:19:42
 *
 */
public class ReturnFlag {
	
	private int flag;

	private String message;
	
	private Object object;//扩展用，存放对象。

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getObject() {
		return this.object;
	}

	public void setObject(Object object) {
		this.object = object;
	}
}
