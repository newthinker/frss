package com.frss.bean;

import java.util.List;
import java.util.Map;

import com.frss.dao.main.RepairDAO;
import com.frss.dao.main.UserDAO;
import com.frss.model.main.RemoteSupportForm;
import com.frss.model.main.RepairDispatchForm;
import com.frss.model.main.RepairFeedbackForm;
import com.frss.model.main.UserInfo;
import com.frss.util.ReturnFlag;

public class RepairManagerBean {
	
	// 导入故障维修派遣单
	public ReturnFlag ImprotRepairDispatchForm(Map<String, String> mapDispatch) {
		RepairDispatchForm dispatchForm = null;
		RepairDAO repairDAO = null;
		UserInfo userInfo = null;
		
		ReturnFlag rf = new ReturnFlag();
		
		try {
			// 获取用户信息
			long userId = Long.parseLong(mapDispatch.get("userId"));
			UserDAO userDAO = new UserDAO();
			userInfo = userDAO.queryUser(userId);
			if(userId<=0 || userDAO==null || userInfo==null) {
				/// log
				rf.setFlag(1);
				rf.setMessage("当前登录用户非法，请重新登录！");
				return rf;
			}
			
			dispatchForm = new RepairDispatchForm();
			repairDAO = new RepairDAO();
			// 初始化
			dispatchForm.initialize(mapDispatch);
			// 插入派遣信息并更新相应确认信息
			long dispatchId = repairDAO.insertRepairDispatchForm(dispatchForm, userInfo);
			/// log
			if(dispatchId==0) {
				rf.setFlag(2);
				rf.setMessage("导入分发表单失败！");
				return rf;
			}
			
		} catch (Exception e) {
			rf.setFlag(3);
			rf.setMessage("导入分发表单出现异常！");
			return rf;
		}
		
		rf.setFlag(0);
		return rf;
	}
	
	// 导入故障维修信息反馈表
	public ReturnFlag ImportRepairFeedbakReport(Map<String, String> mapFeedback) {
		ReturnFlag rf = new ReturnFlag();
		String msg = null;
		RepairFeedbackForm feedbackForm = null;
		RepairDAO repairDAO = null;
		UserInfo userInfo = null;
		long feedbackId = 0;
		
		try {	// 插入故障维修反馈表，插入到反馈表中后，再根据维修方式(现场维修/远程支援)来相应更新派遣表和远程支援表
			// 获取用户信息
			long userId = Long.parseLong(mapFeedback.get("userId"));
			UserDAO userDAO = new UserDAO();
			userInfo = userDAO.queryUser(userId);
			if(userId<=0 || userDAO==null || userInfo==null) {
				rf.setFlag(1);
				msg = "申请内存失败!";
				rf.setMessage(msg);
				return rf;
			}			
			
			// 获取输入参数
			feedbackForm = new RepairFeedbackForm();
			repairDAO = new RepairDAO();
			if(feedbackForm==null || repairDAO==null) {
				rf.setFlag(1);
				msg = "申请内存失败!";
				rf.setMessage(msg);
				return rf;
			}
			
			feedbackForm.Initialize(mapFeedback);
			
			feedbackId = repairDAO.insertFeedbackForm(feedbackForm, userInfo);
			/// 异常处理
			if(feedbackId<=0) {
				rf.setFlag(2);
				msg = "导入故障维修反馈表单失败!";
				rf.setMessage(msg);
				return rf;
			}
			
		} catch (Exception e) {
			rf.setFlag(3);
			msg = "导入故障维修反馈表单出现异常!";
			rf.setMessage(msg);
			return rf;
		}
		
		rf.setFlag(0);
		rf.setMessage(Long.toString(feedbackId));
		return rf;
	}

	// 导入远程支援信息表单
	public ReturnFlag ImportRemoteSupportForm(Map<String,String> mapRemote) {
		ReturnFlag rf = new ReturnFlag();
		String msg = null;
		boolean bFlag = false;
		RemoteSupportForm remoteForm = null;
		RepairDAO repairDAO = null;
		UserInfo userInfo = null;
		
		try {
			// 获取用户信息
			long userId = Long.parseLong(mapRemote.get("userId"));
			UserDAO userDAO = new UserDAO();
			userInfo = userDAO.queryUser(userId);
			if(userId<=0 || userDAO==null || userInfo==null) {
				rf.setFlag(1);
				msg = "当前登录用户非法!";
				rf.setMessage(msg);
				return rf;
			}			
			
			remoteForm = new RemoteSupportForm();
			repairDAO = new RepairDAO();
			if(remoteForm==null || repairDAO==null) {
				rf.setFlag(1);
				msg = "申请内存失败";
				rf.setMessage(msg);
				return rf;
			}
			
			remoteForm.initialize(mapRemote);
			
			long remoteId = repairDAO.insertRemoteSupportForm(remoteForm, userInfo);
			/// log
			if(remoteId<=0){
				rf.setFlag(2);
				msg = "导入远程协助表单失败!";
				rf.setMessage(msg);
				return rf;
			}
			
		} catch (Exception e) {
			rf.setFlag(3);
			msg = "导入远程协助表单出现异常!";
			rf.setMessage(msg);
			return rf;
		}
		
		rf.setFlag(0);
		return rf;
	}
	
}
