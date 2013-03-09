package com.frss.bean;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.frss.dao.main.FactoryDAO;
import com.frss.dao.main.FaultDAO;
import com.frss.model.main.FactoryInfoForm;
import com.frss.model.main.FaultRepairReport;
import com.frss.model.mapping.FrssEquipCreating;
import com.frss.model.mapping.FrssFactoryInfo;
import com.frss.util.ReturnFlag;

public class FactoryInfoManagerBean {
	private LinkedHashMap<Long, String> mapFactory = null;			// 根据故障Id查询到的工业部门信息，包括编号和工业部门名称
	
	// 导入工业部门信息表
	public ReturnFlag ImportFactoryInfo(Map<String, String> mapFactory) {
		ReturnFlag rf = new ReturnFlag();
		String msg = null;	
		FactoryInfoForm factoryInfo = null;
		FactoryDAO factoryDAO = null;
		
		try {
			factoryInfo = new FactoryInfoForm();
			factoryDAO = new FactoryDAO();
			if(factoryInfo==null || factoryDAO==null) {
				rf.setFlag(1);
				msg = "申请内存失败!";
				rf.setMessage(msg);
				return rf;
			}
			
			factoryInfo.initialize(mapFactory);
			long factoryId = factoryDAO.insertFactoryInfoForm(factoryInfo);
			if(factoryId<=0) {
				rf.setFlag(2);
				msg = "导入工业部门信息表单失败!";
				rf.setMessage(msg);
				return rf;
			}			
		} catch(Exception e) {
			e.printStackTrace();
			rf.setFlag(3);
			msg = "导入工业部门信息表单出现异常!";
			rf.setMessage(msg);
			return rf;		
		}
		
		rf.setFlag(0);
		return rf;
	}
	
	/**
	 * @函数名称: queryFactoryInfo
	 * @函数描述: 通过故障单编号查找对应的分发工业部门，查找步骤：先根据故障单查找装备类型和名称，然后根据装备型号和名称查找工业部门id [zuow, 2012/04/21]
	 * @输入参数: @param faultId
	 * @返回类型: void
	 * @throws
	 */
	public void queryFactoryInfo(long faultId) {
		FactoryDAO factoryDAO = new FactoryDAO();
		FaultDAO faultDAO = new FaultDAO();
		
		try {
			// 首先根据故障单序列号查找装备型号和名称
			FaultRepairReport faultReport = faultDAO.queryFault(faultId);
			if(faultReport==null) {
				/// log
				return;
			}
			
			String equipType = faultReport.getEquipType();
			String equipName = faultReport.getEquipName();
			
			ArrayList<FrssEquipCreating> arrEquipCreating = factoryDAO.queryEquipCreating(equipType, equipName);
			if(arrEquipCreating==null) {
				/// log
				return;
			}
			
			for (FrssEquipCreating obj : arrEquipCreating) {
				
				if(mapFactory==null) {
					mapFactory = new LinkedHashMap<Long, String>();
				}
				
				long factoryId = obj.getFactoryId();
				FrssFactoryInfo facInfo = factoryDAO.queryFactory(factoryId);
				if(facInfo==null)
					continue;
				else {
					String facName = facInfo.getName();
					mapFactory.put(factoryId, facName);
				}					
			}
			
		} catch (Exception e) {
			/// log
		}		
	}
	
	public LinkedHashMap<Long, String> getFactorys() {
		return this.mapFactory;
	}
}
