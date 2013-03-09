package com.frss.bean;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import com.frss.dao.main.ApprovalDAO;
import com.frss.dao.main.RepairDAO;
import com.frss.dao.main.UserDAO;
import com.frss.model.main.UserInfo;
import com.frss.model.mapping.FrssApprovalInfo;
import com.frss.model.mapping.FrssClientReview;
import com.frss.util.DateUtil;
import com.frss.util.ReturnFlag;

public class ApprovalManagerBean {

	/**
	 * @函数名称: importApprovalReport
	 * @函数描述: 导入审核信息表，注意：在通过某级审核后，必须插入同时插入一个下级待审核/确认信息--方便后面统计 [故障/备件单提交-->L1审核-->L2审核-->数据格式审核-->故障表分发-->维修反馈-->电话回访]
	 * @输入参数: @param lstApproval
	 * @输入参数: @return
	 * @返回类型: boolean
	 * @throws
	 */
	public boolean importApprovalReport(List<String> lstApproval) {
		FrssApprovalInfo appInfo = null;
		ApprovalDAO appDAO = null;
		UserInfo userInfo = null;
		long appId= 0;
		long userId = 0;
		SimpleDateFormat simFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		// 导出输入信息
		try {
			appInfo = new FrssApprovalInfo();
			UserDAO userDAO = new UserDAO();
			appDAO = new ApprovalDAO();
			if(userDAO==null || appInfo==null || appDAO==null) {
				/// log
				return false;
			}				
			// 获取用户信息
			userId = Long.parseLong(lstApproval.get(0));			
			userInfo = userDAO.queryUser(userId);
			if(userId<=0 || userInfo==null) {
				/// log
				return false;
			}	
			appInfo.setUserId(userId);
			
			// 首先查询审核表中是否存在记录
			int type = Integer.parseInt(lstApproval.get(1));
			long keyId = Long.parseLong(lstApproval.get(2));
			int status = Integer.parseInt(lstApproval.get(6));
			appInfo = appDAO.queryApproval(type, keyId, status);
			if(appInfo!=null) {		// 如果存在就更新此记录
				boolean bRet = appDAO.updateApprovalInfo(appInfo);
				if(!bRet) {
					/// log 更新失败
					return false;
				}
			} else {	// 如果不存在就插入新纪录
				/// 审核信息表表格格式/顺序需要确定
				// 审核表单类型
				String value = lstApproval.get(1);		
				appInfo.setType(Integer.parseInt(value));
				// 对应表单主键
				value = lstApproval.get(2);
				appInfo.setKeyId(Long.parseLong(value));
				// 审查/提交/确认者
				value = lstApproval.get(3);
				appInfo.setChecker(value);
				// 审核/提交时间
				value = lstApproval.get(4);
				appInfo.setCheckTime(simFormat.parse(value));
				// 审核意见
				value = lstApproval.get(5);
				appInfo.setOpinion(value);
				// 表单状态
				value = lstApproval.get(6);
				appInfo.setStatus(Integer.parseInt(value));		// [0～2]，分别表示表单提交、表单审核通过和表单审核未通过
				
				// 插入审核信息
				appId = appDAO.insertApproval(appInfo);
				if(appId<=0) {
					/// log
					return false;
				}				
			}
					
			// 判别是否需要插入下级确认记录
			type = appInfo.getType();
			status = appInfo.getStatus();
			keyId = appInfo.getKeyId();
			FrssApprovalInfo newApp = null;
			if(type>0&&status==1) {		// 对于通过审核/确认表单需要填写下一级的确认信息记录
				newApp = new FrssApprovalInfo();
				if(newApp==null) {
					/// log
					return false;
				}
				
				switch (type) {
				case ApprovalDAO.faultL1:	// 故障L1级审核
					newApp.setType(ApprovalDAO.faultL2);
					break;
				case ApprovalDAO.faultL2:
					newApp.setType(ApprovalDAO.formatCheck);
					break;
				case ApprovalDAO.backL1:
					newApp.setType(ApprovalDAO.backL2);
					break;
				case ApprovalDAO.backL2:
					newApp.setType(ApprovalDAO.formatCheck);
					break;
				case ApprovalDAO.formatCheck:
					newApp.setType(ApprovalDAO.dispatch);
					break;
				case ApprovalDAO.dispatch:
					newApp.setType(ApprovalDAO.feedback);
					break;
				case ApprovalDAO.feedback:
					newApp.setType(ApprovalDAO.phoneBack);
					break;	
					
				}
				newApp.setStatus(ApprovalDAO.confirm);		// 设置状态为待确认
				newApp.setKeyId(keyId);						// 与当前记录保持一致，有些情况需要在确认时进行修改，目前将故障和备件按照数字段区分
				newApp.setUserId(userId);					// 暂设置为当前登录用户，在确认时进行修改
			}
			// 将下阶段信息插入
			long newAppId = appDAO.insertApproval(appInfo);
			if(newAppId<=0) {
				/// log
				return false;
			}
			
		} catch (Exception e) {
			/// log 插入审核表出现异常
			return false;
		}
	
		return true;
	}

	/**
	 * @函数名称: importApprovalReport
	 * @函数描述: 导入审核表单
	 * @输入参数: @param mapApproval
	 * @输入参数: @return
	 * @返回类型: boolean
	 * @throws
	 */
	public ReturnFlag importApprovalReport(Map<String, String> mapApproval) {
		ReturnFlag rf = new ReturnFlag();
		FrssApprovalInfo appInfo = null;
		ApprovalDAO appDAO = null;
		UserInfo userInfo = null;
		long appId= 0;
		long userId = 0;
		String msg = null;
		
		// 导出输入信息
		try {
			UserDAO userDAO = new UserDAO();
			appDAO = new ApprovalDAO();
			if(userDAO==null || appDAO==null) {
				/// log
				rf.setFlag(1);
				msg = "当前登录用户非法!";
				rf.setMessage(msg);
				return rf;
			}				
			// 获取用户信息
			userId = Long.parseLong(mapApproval.get("userId"));	
			userInfo = userDAO.queryUser(userId);
			if(userId<=0 || userInfo==null) {
				/// log
				rf.setFlag(1);
				msg = "当前登录用户非法!";
				rf.setMessage(msg);
				return rf;
			}	
			
			int type = Integer.parseInt(mapApproval.get("reporttype"));
			long keyId = Long.parseLong(mapApproval.get("keyid"));
			int status = Integer.parseInt(mapApproval.get("state"));
			if(status==0)	// 根据前端返回值修改
				status=ApprovalDAO.nopass;
			else if(status==1)
				status=ApprovalDAO.pass;
			
			// 首先查询审核表中是否存在记录，防止前端故障、用户重复提交审核数据 [zuow, 2012/04/22]
			appInfo = appDAO.queryApproval(type, keyId, status);
			if(appInfo!=null) {
				rf.setFlag(3);
				msg = "数据库中已存在审核记录!";
				rf.setMessage(msg);
				return rf;	
			}
			
			appInfo = new FrssApprovalInfo();
			appInfo.setType(type);
			// 对应表单主键
			appInfo.setKeyId(keyId);
			// 审查/提交/确认者
			if(mapApproval.get("checker")!=null)
				appInfo.setChecker(mapApproval.get("checker"));
			// 审核/提交时间
			appInfo.setCheckTime(DateUtil.getCurTime());
			// 审核意见
			if(mapApproval.get("opinion")!=null)
				appInfo.setOpinion(mapApproval.get("opinion"));
			// 表单状态
			appInfo.setStatus(status);		// [0～2]，分别表示表单提交、表单审核通过和表单审核未通过
			appInfo.setUserId(userId);			
			
			FrssApprovalInfo tempApp  = appDAO.queryApproval(type, keyId, ApprovalDAO.confirm);			// 这儿应该是查询待确认信息，如果有就更新，没有就插入 
			if(tempApp!=null) {		// 如果存在就更新此记录				
				appInfo.setId(tempApp.getId());
				boolean bRet = appDAO.updateApprovalInfo(appInfo);
				if(!bRet) {
					/// log 更新失败
					rf.setFlag(1);
					msg = "更新审核信息记录失败!";
					rf.setMessage(msg);
					return rf;
				}
			} else {	// 如果不存在就插入新纪录	
				// 插入审核信息
				appId = appDAO.insertApproval(appInfo);
				if(appId<=0) {
					/// log
					rf.setFlag(1);
					msg = "插入审核记录失败!";
					rf.setMessage(msg);
					return rf;
				}				
			}
					
			// 判别是否需要插入下级确认记录
			FrssApprovalInfo newApp = null;
			if(type>=ApprovalDAO.faultL1 && status==ApprovalDAO.pass) {		// 对于通过审核/确认表单需要填写下一级的确认信息记录
				newApp = new FrssApprovalInfo();
				if(newApp==null) {
					/// log
					rf.setFlag(1);
					msg = "插入下一步确认信息失败!";
					rf.setMessage(msg);
					return rf;
				}
				
				switch (type) {
				case ApprovalDAO.faultL1:	// 故障L1级审核
					newApp.setType(ApprovalDAO.faultL2);
					break;
				case ApprovalDAO.faultL2:
					newApp.setType(ApprovalDAO.formatCheck);
					break;
				case ApprovalDAO.backL1:
					newApp.setType(ApprovalDAO.backL2);
					break;
				case ApprovalDAO.backL2:
					newApp.setType(ApprovalDAO.formatCheck);
					break;
				case ApprovalDAO.formatCheck:
					newApp.setType(ApprovalDAO.dispatch);
					break;
				case ApprovalDAO.dispatch:
					newApp.setType(ApprovalDAO.feedback);
					break;
				case ApprovalDAO.feedback:
					newApp.setType(ApprovalDAO.phoneBack);
					break;	
					
				}
				newApp.setStatus(ApprovalDAO.confirm);		// 设置状态为待确认
				newApp.setKeyId(keyId);						// 与当前记录保持一致，有些情况需要在确认时进行修改，目前将故障和备件按照数字段区分
				newApp.setUserId(userId);					// 暂设置为当前登录用户，在确认时进行修改
				newApp.setCheckTime(DateUtil.getCurTime());
				newApp.setChecker(userInfo.getUserName());
			
				// 将下阶段信息插入
				long newAppId = appDAO.insertApproval(newApp);
				if(newAppId<=0) {
					/// log
					rf.setFlag(1);
					msg = "插入下一步确认信息失败!";
					rf.setMessage(msg);
					return rf;
				}			
			}
		} catch (Exception e) {
			/// log 插入审核表出现异常
			rf.setFlag(1);
			msg = "插入审核信息表单出现异常!";
			rf.setMessage(msg);
			return rf;
		}
	
		rf.setFlag(0);
		return rf;
	}
	
	/**
	 * @函数名称: importFormatReport
	 * @函数描述: 导入格式审查
	 * @输入参数: @param mapFormat
	 * @输入参数: @return
	 * @返回类型: ReturnFlag
	 * @throws
	 */
	public ReturnFlag importFormatReport(Map<String, String> mapFormat) {
		ReturnFlag rf = new ReturnFlag();

		FrssApprovalInfo appInfo = null;
		ApprovalDAO appDAO = null;
		UserInfo userInfo = null;
		long appId= 0;
		long userId = 0;
		String msg = null;

		// 导出输入信息
		try {
			UserDAO userDAO = new UserDAO();
			appDAO = new ApprovalDAO();
			if(userDAO==null || appDAO==null) {
				/// log
				rf.setFlag(1);
				msg = "申请内存失败!";
				rf.setMessage(msg);
				return rf;
			}				
			// 获取用户信息
			userId = Long.parseLong(mapFormat.get("userId"));			
			userInfo = userDAO.queryUser(userId);
			if(userId<=0 || userInfo==null) {
				/// log
				rf.setFlag(1);
				msg = "当前登录用户非法!";
				rf.setMessage(msg);				
				return rf;
			}	
			
			// 获取故障单id和审核状态
			long faultId = Long.parseLong(mapFormat.get("orderid"));
			int state = Integer.parseInt(mapFormat.get("ispass"));
			
			// 首先查找格式审查待确认记录
			appDAO = new ApprovalDAO();
			int type = ApprovalDAO.formatCheck;
			int status = ApprovalDAO.confirm;
			appInfo = appDAO.queryApproval(type, faultId, status);
			// 将表单审核信息进行转换(前后台转换)
			if(state==0)	// 审核不通过
				status = ApprovalDAO.nopass;
			else if(state==1)
				status = ApprovalDAO.pass;
			
			if(appInfo!=null) {		// 根据审核状态进行更新记录
				appInfo.setChecker(userInfo.getUserName());
				appInfo.setCheckTime(DateUtil.getCurTime());
				appInfo.setStatus(status);
				appInfo.setUserId(userId);
				
				boolean bFlag = appDAO.updateApprovalInfo(appInfo);
				if(!bFlag) {
					/// log
					rf.setFlag(2);
					msg = "更新格式确认记录失败!";
					rf.setMessage(msg);					
					return rf;
				}
				appId = appInfo.getId();
				
			} else {	// 如果不存在，就插入一条审核记录
				appInfo = new FrssApprovalInfo();
				appInfo.setChecker(userInfo.getUserName());
				appInfo.setCheckTime(DateUtil.getCurTime());
				appInfo.setKeyId(faultId);
				appInfo.setStatus(status);
				appInfo.setType(ApprovalDAO.formatCheck);
				appInfo.setUserId(userId);
				
				appId = appDAO.insertApproval(appInfo);
				if(appId<=0) {	// 插入失败
					/// log
					rf.setFlag(2);
					msg = "插入格式确认记录失败!";
					rf.setMessage(msg);
					return rf;
				}
			}
			
			// 根据审核状态决定是否插入下一级的待审核记录
			if(state==1) {	// 审核通过，就需要插入一条分发待确认记录
				FrssApprovalInfo newAppInfo = new FrssApprovalInfo();
				newAppInfo.setChecker(userInfo.getUserName());
				newAppInfo.setCheckTime(DateUtil.getCurTime());
				newAppInfo.setKeyId(faultId);
				newAppInfo.setStatus(ApprovalDAO.confirm);
				newAppInfo.setType(ApprovalDAO.dispatch);
				newAppInfo.setUserId(userId);
				
				long newId = appDAO.insertApproval(newAppInfo);
				if(newId<=0) {
					/// log
					rf.setFlag(3);
					msg = "插入分发待确认记录失败!";
					rf.setMessage(msg);					
					return rf;
				}
			}			
		} catch (Exception e) {
			rf.setFlag(4);
			msg = "导入格式检查数据出现异常!";
			rf.setMessage(msg);
			return rf;
		}
		
		rf.setFlag(0);		
		return rf;
	}

	/**
	 * @函数名称: importPhoneBack
	 * @函数描述: 导入电话确认信息
	 * @输入参数: @param orderId
	 * @输入参数: @param userId
	 * @输入参数: @return
	 * @返回类型: ReturnFlag
	 * @throws
	 */
	public ReturnFlag importPhoneBack(long orderId, long userId) {
		ReturnFlag rf = new ReturnFlag();
		FrssApprovalInfo appInfo = null;
		ApprovalDAO appDAO = null;
		String msg = null;
		UserInfo userInfo = null;
		long appId = 0;
		 
		long faultId = orderId;
		try {
			// 首先获取当前登录用户信息
			UserDAO userDAO = new UserDAO();
			appDAO = new ApprovalDAO();
			if(userDAO==null || appDAO==null) {
				/// log
				rf.setFlag(1);
				msg = "当前登录用户非法!";
				rf.setMessage(msg);
				return rf;
			}				
			// 获取用户信息
			userInfo = userDAO.queryUser(userId);
			if(userId<=0 || userInfo==null) {
				/// log
				rf.setFlag(1);
				msg = "当前登录用户非法!";
				rf.setMessage(msg);
				
				return rf;
			}	
			
			// 首先查找审核表中是否有电话回访待确认记录
			appDAO = new ApprovalDAO();
			int type = ApprovalDAO.phoneBack;
			int status = ApprovalDAO.confirm;
			appInfo = appDAO.queryApproval(type, faultId, status);
			if(appInfo!=null) {		// 更新记录
				appInfo.setChecker(userInfo.getUserName());
				appInfo.setCheckTime(DateUtil.getCurTime());
				appInfo.setStatus(ApprovalDAO.pass);
				appInfo.setUserId(userId);
				
				boolean bFlag = appDAO.updateApprovalInfo(appInfo);
				if(!bFlag) {
					/// log
					rf.setFlag(2);
					msg = "更新格式确认记录失败!";
					rf.setMessage(msg);
					
					return rf;
				}
				appId = appInfo.getId();
			} else {	// 插入一条确认记录
				appInfo = new FrssApprovalInfo();
				appInfo.setChecker(userInfo.getUserName());
				appInfo.setCheckTime(DateUtil.getCurTime());
				appInfo.setKeyId(faultId);
				appInfo.setStatus(ApprovalDAO.pass);
				appInfo.setType(ApprovalDAO.phoneBack);
				appInfo.setUserId(userId);
				
				appId = appDAO.insertApproval(appInfo);
				if(appId<=0) {	// 插入失败
					/// log
					rf.setFlag(2);
					msg = "插入格式确认记录失败!";
					rf.setMessage(msg);
					return rf;
				}
			}			
		} catch(Exception e) {
			rf.setFlag(4);
			msg = "导入格式检查数据出现异常!";
			rf.setMessage(msg);
			return rf;
		}
		
		rf.setFlag(0);
		return rf;
	}
	
	/**
	 * @函数名称: importPhoneBack
	 * @函数描述: 导入客户回访单 [zuow,2012/05/08]
	 * @输入参数: @param mapBack
	 * @输入参数: @return
	 * @返回类型: ReturnFlag
	 * @throws
	 */
	public ReturnFlag importPhoneBack(Map<String, String> mapBack) {
		ReturnFlag rf = new ReturnFlag();
		FrssApprovalInfo appInfo = null;
		ApprovalDAO appDAO = null;
		String msg = null;
		UserInfo userInfo = null;
		long backId = 0;
		
		try {
			// 首先获取当前登录用户信息
			String value = null;
			if(mapBack.get("userId")==null) {
				rf.setFlag(1);
				msg = "输入参数无效!";
				rf.setMessage(msg);
				return rf;
			}
			long userId = Long.parseLong(mapBack.get("userId"));
			
			UserDAO userDAO = new UserDAO();
			appDAO = new ApprovalDAO();
			if(userDAO==null || appDAO==null) {
				rf.setFlag(1);
				msg = "申请内存失败!";
				rf.setMessage(msg);
				return rf;
			}				
			// 获取用户信息
			userInfo = userDAO.queryUser(userId);
			if(userId<=0 || userInfo==null) {
				rf.setFlag(1);
				msg = "当前登录用户非法!";
				rf.setMessage(msg);
				
				return rf;
			}	
			
			long faultId = Long.parseLong(mapBack.get("orderid"));
			FrssClientReview review = new FrssClientReview();
			
			// 首先将回访信息入库
			review.setFaultId(faultId);
			if(mapBack.get("client")!=null)
				review.setClient(mapBack.get("client"));
			if(mapBack.get("quality")!=null) {
				String temp = mapBack.get("quality");
				review.setQuality(Integer.parseInt(temp));
			}
			if(mapBack.get("reviewway")!=null) {
				String temp = mapBack.get("reviewway");
				review.setReviewWay(Integer.parseInt(temp));
			}
			if(mapBack.get("contact")!=null)
				review.setContact(mapBack.get("contact"));
			if(mapBack.get("discription")!=null)	// 维修描述 [zuow, 2012/05/15]
				review.setDiscription(mapBack.get("discription"));				
			
			RepairDAO repairDAO = new RepairDAO();
			backId = repairDAO.insertClientReview(review);
			if(backId<=0) {
				rf.setFlag(1);
				msg = "插入客户回访单出现异常!";
				rf.setMessage(msg);
				
				return rf;
			}
			
			// 查找是否存在电话回访待确认记录
			appDAO = new ApprovalDAO();
			int type = ApprovalDAO.phoneBack;
			int status = ApprovalDAO.confirm;
			appInfo = appDAO.queryApproval(type, faultId, status);
			if(appInfo!=null) {		// 更新记录
				appInfo.setChecker(userInfo.getUserName());
				appInfo.setCheckTime(DateUtil.getCurTime());
				appInfo.setStatus(ApprovalDAO.pass);
				appInfo.setUserId(userId);
				
				boolean bFlag = appDAO.updateApprovalInfo(appInfo);
				if(!bFlag) {
					/// log
					rf.setFlag(2);
					msg = "更新格式确认记录失败!";
					rf.setMessage(msg);
					
					return rf;
				}
			} else {	// 插入一条确认记录
				appInfo = new FrssApprovalInfo();
				appInfo.setChecker(userInfo.getUserName());
				appInfo.setCheckTime(DateUtil.getCurTime());
				appInfo.setKeyId(faultId);
				appInfo.setStatus(ApprovalDAO.pass);
				appInfo.setType(ApprovalDAO.phoneBack);
				appInfo.setUserId(userId);
				
				long appId = appDAO.insertApproval(appInfo);
				if(appId<=0) {	// 插入失败
					/// log
					rf.setFlag(2);
					msg = "插入格式确认记录失败!";
					rf.setMessage(msg);
					return rf;
				}
			}						
			
		} catch (Exception e) {
			rf.setFlag(3);
			msg = "导入客户回访单出现异常！";
			rf.setMessage(msg);
			return rf;
		}
		
		return rf;
	}
}
