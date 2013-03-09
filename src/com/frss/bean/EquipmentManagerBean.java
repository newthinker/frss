package com.frss.bean;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import com.frss.dao.main.EquipDAO;
import com.frss.dao.main.UserDAO;
import com.frss.model.main.BackupApplication;
import com.frss.model.main.UserInfo;
import com.frss.util.FrssException;
import com.frss.util.ReturnFlag;
import com.frss.util.SequenceFactory;

public class EquipmentManagerBean {

	// 将前端的备件申请表单数据导入
	public boolean ImportBackReport(List<String> lstBack) throws FrssException {
		boolean bFlag = false;		
		BackupApplication backApp = null;
		EquipDAO equipDAO = null;
		UserInfo userInfo = null;
		
		try {
			// 获取用户信息
			long userId = Long.parseLong(lstBack.get(0));
			UserDAO userDAO = new UserDAO();
			userInfo = userDAO.queryUser(userId);
			if(userId<=0 || userDAO==null || userInfo==null) {
				/// log
				return false;
			}
			
			// 获取输入参数
			backApp = new BackupApplication();
			backApp.Initialize(lstBack);

			// 输入到数据库
			equipDAO = new EquipDAO();
			long backID = equipDAO.insertBackup(backApp, userInfo);
			if(backID<=0) {
				/// 插入失败
			}
			
			bFlag = true;
		} catch(Exception e) {
			throw new FrssException(e);
		}		
		
		return bFlag;
	}

	public ReturnFlag ImportBackReport(Map<String, String> mapBack) throws FrssException {
		ReturnFlag rf = new ReturnFlag();
		String msg = null;	
		BackupApplication backApp = null;
		EquipDAO equipDAO = null;
		UserInfo userInfo = null;
		
		try {
			// 获取用户信息
			long userId = Long.parseLong(mapBack.get("userId"));
			UserDAO userDAO = new UserDAO();
			userInfo = userDAO.queryUser(userId);
			if(userId<=0 || userDAO==null || userInfo==null) {
				rf.setFlag(1);
				msg = "当前登录用户非法!";
				rf.setMessage(msg);
				return rf;
			}
			
			// 获取输入参数
			backApp = new BackupApplication();
			backApp.Initialize(mapBack);
			
			// 生成备件单的序列号 [zuow,2012/04/10]
			SequenceFactory seqFactory = new SequenceFactory(SequenceFactory.backReport, userId);
			long backId = seqFactory.getSequenceLong();
			if(backId<=0) {
				rf.setFlag(2);
				msg = "生成备件单id失败";
				rf.setMessage(msg);
				return rf;
			}
			backApp.setBackID(backId);
			
			// 输入到数据库
			equipDAO = new EquipDAO();
			long backID = equipDAO.insertBackup(backApp, userInfo);
			if(backID<=0) {
				rf.setMessage("插入备件单失败");
				rf.setFlag(3);
				return rf;
			}
		} catch(Exception e) {
			System.out.print("插入备件单出现异常！");
			throw new FrssException(e);
		}		
		
		rf.setFlag(0);
		return rf;
	}
}
