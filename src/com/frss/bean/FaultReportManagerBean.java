package com.frss.bean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.frss.dao.main.ApprovalDAO;
import com.frss.dao.main.AudioDAO;
import com.frss.dao.main.FaultDAO;
import com.frss.dao.main.UserDAO;
import com.frss.model.main.FaultRepairReport;
import com.frss.model.main.UserInfo;
import com.frss.model.mapping.FrssApprovalInfo;
import com.frss.model.mapping.FrssAudioInfo;
import com.frss.util.DateUtil;
import com.frss.util.FrssException;
import com.frss.util.ReturnFlag;
import com.frss.util.SequenceFactory;

public class FaultReportManagerBean {

	// 将前端输入的故障维修上报表信息导入
	public boolean ImportFaultReport(List<String> lstFault) throws FrssException {
		FaultRepairReport faultReport = null;
		FaultDAO faultDAO = null;
		UserInfo userInfo = null;
		long userId = 0;
		
		// 导出维修上报单中的信息
		try {
			// 获取用户信息
			userId = Long.parseLong(lstFault.get(0));
			UserDAO userDAO = new UserDAO();
			userInfo = userDAO.queryUser(userId);
			if(userId<=0 || userDAO==null || userInfo==null) {
				/// log
				return false;
			}			
			
			faultReport = new FaultRepairReport();			

			faultDAO = new FaultDAO();
			faultReport.initialize(lstFault);

		} catch (Exception e) {
			throw new FrssException(e);
		}
		
		// 插入数据库
		if(faultReport!=null) {
			faultDAO = new FaultDAO();
			long faultID = faultDAO.insertFault(faultReport, userId);
			if(faultID<=0) {
				/// 插入失败
				
				return false;
			}
		}	
		
		return true;
	}
	
	public ReturnFlag ImportFaultReport(Map<String, String> mapFault) throws FrssException {
		ReturnFlag rf = new ReturnFlag();
		String msg = null;
		FaultRepairReport faultReport = null;
		FaultDAO faultDAO = null;
		UserInfo userInfo = null;
		long faultId = 0;
		long userId = 0;
		int userType = 0;
		
		// 导出维修上报单中的信息
		try {
			// 获取用户信息
			userId = Long.parseLong(mapFault.get("userId"));
			UserDAO userDAO = new UserDAO();
			userInfo = userDAO.queryUser(userId);
			if(userId<=0 || userDAO==null || userInfo==null) {
				rf.setFlag(1);
				msg = "申请内存失败";
				rf.setMessage(msg);
				return rf;
			}			
			
			faultReport = new FaultRepairReport();			

			faultDAO = new FaultDAO();
			faultReport.initialize(mapFault);
			
			userType = userInfo.getUserType();
			if(userType==UserDAO.Regiment) {
				// 生成并设置故障单序列号 [zuow, 2012/04/10]
				SequenceFactory seqFactory = new SequenceFactory(SequenceFactory.faultReport, userId);
				faultId = seqFactory.getSequenceLong();
				if(faultId<=0) {
					rf.setFlag(2);
					msg = "生成故障单序列号失败	";
					rf.setMessage(msg);
					return rf;
				}
			} else if(userType==UserDAO.Formater) {
				faultId = Long.parseLong(mapFault.get("keyid"));
				
				// 查询语音记录表获取上报人 [zuow,2012/05/05]
				AudioDAO audioDAO = new AudioDAO();
				FrssAudioInfo audioInfo = audioDAO.queryAudio(faultId);
				if(audioInfo==null) {
					rf.setFlag(3);
					msg = "查询离线语音记录失败	";
					rf.setMessage(msg);
					return rf;
				}
				faultReport.setReporter(audioInfo.getReporter());
				
				// 根据上报人用户名查询其id [zuow, 2012/05/20]
				userInfo = userDAO.queryUser(audioInfo.getReporter(), UserDAO.Regiment);
				userId = userInfo.getUserID();
			}
			
			if(faultId<=0) {
				rf.setFlag(4);
				msg = "生成/获取故障单序列号失败！";
				rf.setMessage(msg);
				return rf;
			}
			
			faultReport.setFaultID(faultId);
			String timeString = (Long.toString(faultId)).substring(4);
			faultReport.setReportTime(timeString);
			
		} catch (Exception e) {
			throw new FrssException(e);
		}
		
		// 插入数据库
		if(faultReport!=null) {
			faultDAO = new FaultDAO();
//			boolean flag = true;
//			if(userType==UserDAO.Regiment) {
//				faultId = faultDAO.insertFault(faultReport, userId);
//			} else if(userType==UserDAO.Formater) {
//				// 首先插入一条待审核记录 [zuow,2012/05/05]
//				ApprovalDAO appDAO = new ApprovalDAO();
//				FrssApprovalInfo appInfo = new FrssApprovalInfo();
//				if(appDAO==null || appInfo==null) {
//					/// log 警告
//					return false;
//				}
//				appInfo.setKeyId(faultId);
//				appInfo.setType(ApprovalDAO.faultL1);
//				appInfo.setUserId(userId);
//				appInfo.setStatus(ApprovalDAO.confirm);
//				appInfo.setChecker(faultReport.getReporter());		// 故障提交者
//				appInfo.setCheckTime(DateUtil.getCurTime());
//				
//				long appId = appDAO.insertApproval(appInfo);
//				if(appId<=0) {
//					/// log 插入提交审核信息失败
//					return false;
//				}				
//				
//				flag = faultDAO.updateFault(faultReport, userId);
//			}
			
			faultId = faultDAO.insertFault(faultReport, userId);
			
			if(faultId<=0) {
				rf.setFlag(5);
				msg = "插入故障表单失败!";
				rf.setMessage(msg);
				return rf;
			}
			
			// 如果是离线语音上报，还需要修改语音表中的状态 [zuow,2012/05/05]
			if(userType==UserDAO.Formater) {
				AudioDAO audioDAO = new AudioDAO();
				FrssAudioInfo audioInfo = audioDAO.queryAudio(faultId);
				if(audioInfo!=null) {
					audioInfo.setStatus(1);
					
					audioDAO.updateAudio(audioInfo);
				}
			}
		}	
		
		rf.setFlag(0);
		return rf;
	}	
	
}
