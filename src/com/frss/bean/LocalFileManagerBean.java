package com.frss.bean;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.jdom.Element;

import net.sf.json.JSONObject;

import com.frss.model.main.FaultRepairReport;
import com.frss.util.XmlFileUtil;

public class LocalFileManagerBean {
	private static final String[] keyName = {"equipType", "equipName", "equipNumber", "amount", "faultDesp", 
		"faultTime", "prePorcess", "frequence", "faultPlace", "department", "operater", "reporter", "contact",
		"reportTime", "photos", "l1Checker"};
		
	// 解析指定的xml文件
	public LinkedHashMap<Integer, LinkedHashMap<String, String>> importLocalFile(String fileName) {
		XmlFileUtil faultFile = null;
		LinkedHashMap<Integer, LinkedHashMap<String, String>> mapFaults = null;
		
		// 首先判断文件是否存在
		File file = new File(fileName);
		if(!file.exists()) {
			/// log
			return null;
		}
		
		// 开始解析
		faultFile = new XmlFileUtil(fileName);
		if(faultFile==null) {
			/// log
			return null;
		}
		String xpath = "/root";
		try {
			// 首先获取其下所有的fault
			Element lstElement = faultFile.getElement(xpath);		// 获取当前服务器标识下的所有文件名
			List lstFault = lstElement.getChildren();
			if(lstFault==null || lstFault.size()<=0) {
				/// log
				return null;
			}
		
			// 处理每个fault故障单
			int i = 0;
			mapFaults = new LinkedHashMap<Integer, LinkedHashMap<String, String>>();
		
			while (i<lstFault.size()) {
				Element eleFault = (Element)lstFault.get(i);
				// 处理当前节点
				List lstField = eleFault.getChildren();
				if(lstField==null || lstField.size()<=0) {
					/// log
					continue;
				}
				
				int j = 0;
				LinkedHashMap<String, String> mapFault = new LinkedHashMap<String, String>();
				while (j<lstField.size()) {
					Element eleField = (Element)lstField.get(j);
					String key = eleField.getName();
					String value = eleField.getText();
					
					mapFault.put(key, value);
					j++;
				}

				mapFaults.put(i, mapFault);
										
				i++;
			}
		} catch(Exception e) {
			// log
			return null;
		}
		
		return mapFaults;
	}
		
}
