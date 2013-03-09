package com.frss.bean;

import java.util.ArrayList;
import java.util.Map;

import com.frss.dao.main.AudioDAO;
import com.frss.dao.main.UserDAO;
import com.frss.model.main.UserInfo;
import com.frss.model.mapping.FrssAudioInfo;
import com.frss.util.DateUtil;
import com.frss.util.ReturnFlag;
import com.frss.util.SequenceFactory;

public class AudioManagerBean {

	/**
	 * @函数名称: importAudioReport
	 * @函数描述: 导入离线语音上报单
	 * @输入参数: @param mapApproval
	 * @输入参数: @return
	 * @返回类型: ReturnFlag
	 * @throws
	 */
	public ReturnFlag importAudioReport(Map<String, String> mapAudio) {
		ReturnFlag rf = new ReturnFlag();
		String msg = null;
		AudioDAO audioDAO = null;
		
		try {
			UserDAO userDAO = new UserDAO();
			audioDAO = new AudioDAO();
			if(userDAO==null || audioDAO==null) {
				/// log
				rf.setFlag(1);
				msg = "当前登录用户非法!";
				System.out.println(msg);
				rf.setMessage(msg);
				return rf;
			}				
			// 获取用户信息
			long userId = Long.parseLong(mapAudio.get("userId"));			
			UserInfo userInfo = userDAO.queryUser(userId);
			if(userId<=0 || userInfo==null) {
				/// log
				rf.setFlag(1);
				msg = "当前登录用户非法!";
				System.out.println(msg);
				rf.setMessage(msg);
				return rf;
			}
			
			FrssAudioInfo audioInfo = new FrssAudioInfo();
			if(mapAudio.get("filePath")!=null)
				audioInfo.setFilePath(mapAudio.get("filePath"));
			
			System.out.println("Input filePath");
			
			// 生成并设置故障单序列号 
			SequenceFactory seqFactory = new SequenceFactory(SequenceFactory.faultReport, userId);
			long faultId = seqFactory.getSequenceLong();
			if(faultId<=0) {
				rf.setFlag(2);
				msg = "生成故障单编号失败!";
				System.out.println(msg);
				rf.setMessage(msg);
				return rf;
			}
			System.out.println("Generator faultId");
			
			audioInfo.setStatus(0);
			audioInfo.setKeyId(faultId);
			audioInfo.setType(0);
			audioInfo.setReporter(userInfo.getUserName());
			audioInfo.setReportTime(DateUtil.getCurTime());

			System.out.println("Input other info");
			
			// 插入语言记录
			long audioId = audioDAO.insertAudio(audioInfo);
			if(audioId<=0) {
				rf.setFlag(3);
				msg = "插入理想语音上报表单失败!";
				System.out.println(msg);
				rf.setMessage(msg);
				return rf;				
			}	
			
			System.out.println("Insert successfully");
		} catch (Exception e) {
			rf.setFlag(1);
			msg = "插入离线语音上报表单出现异常!";
			System.out.println(msg);
			rf.setMessage(msg);
			return rf;
		}
		
		return rf;
	}
	
}
