package com.frss.dao.main;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.frss.dao.util.BaseDAO;
import com.frss.model.mapping.FrssApprovalInfo;
import com.frss.model.mapping.FrssAudioInfo;
import com.frss.util.DateUtil;
import com.frss.util.FrssException;

public class AudioDAO {

	/**
	 * @函数名称: insertAudio
	 * @函数描述: 插入离线语音记录
	 * @输入参数: @param audioInfo
	 * @输入参数: @return
	 * @输入参数: @throws FrssException
	 * @返回类型: long
	 * @throws
	 */
	public long insertAudio(FrssAudioInfo audioInfo) throws FrssException {
		long audioId = 0;
		Session session = null;
		Transaction transaction = null;
		
		try {
			session = BaseDAO.getSession();
			transaction = session.beginTransaction();
			session.save(audioInfo);
			transaction.commit();
			session.close();
			
			audioId = audioInfo.getId();
		} catch (Exception e) {
			transaction.rollback();
			///log	
			throw new FrssException(e);
		}		
		
		return audioId;
	}
	
	/**
	 * @函数名称: queryAudio
	 * @函数描述: 根据状态查询所有离线语音上报记录
	 * @输入参数: @param status
	 * @输入参数: @return
	 * @返回类型: ArrayList<FrssAudioInfo>
	 * @throws
	 */
	public ArrayList<FrssAudioInfo> queryAudio(int status) {
		ArrayList<FrssAudioInfo> arrAudio = null;
		
		String hql = "from " + FrssAudioInfo.class.getName() + " au ";
		hql += "where au.status=" + status;
		hql += " order by au.id";

		try{
			// 执行查询
			Session session = BaseDAO.getSession();
			Query objQuery = session.createQuery(hql);
			List lstResult =  objQuery.list();
			session.close();
			
			if (lstResult != null && lstResult.size() > 0) {
				arrAudio = new ArrayList<FrssAudioInfo>();
				
				for(int num=0; num<lstResult.size();num++) {
					FrssAudioInfo audioInfo = (FrssAudioInfo)lstResult.get(num);
					if(audioInfo!=null) {
						arrAudio.add(audioInfo);
					}
				}
			}
		} catch (Exception e) {
			/// log
			System.out.print("查询离线语音记录出现异常！");
			return null;
		}
		
		return arrAudio;
	}
	
	/**
	 * @函数名称: queryAudio
	 * @函数描述: 根据故障单编号查找离线语音记录
	 * @输入参数: @param faultId
	 * @输入参数: @return
	 * @返回类型: FrssAudioInfo
	 * @throws
	 */
	public FrssAudioInfo queryAudio(long faultId) {
		FrssAudioInfo audioInfo = null;
		
		String hql = "from " + FrssAudioInfo.class.getName() + " au ";
		hql += "where au.keyId=" + faultId;

		try{
			// 执行查询
			Session session = BaseDAO.getSession();
			Query objQuery = session.createQuery(hql);
			List lstResult =  objQuery.list();
			session.close();
			
			if (lstResult != null && lstResult.size() > 0) {				
				audioInfo = (FrssAudioInfo)lstResult.get(0);
			}
		} catch (Exception e) {
			/// log
			return null;
		}		
		
		return audioInfo;
	}
	
	/**
	 * @函数名称: updateAudio
	 * @函数描述: 更新离线语音上报记录
	 * @输入参数: @param audioInfo
	 * @输入参数: @return
	 * @返回类型: boolean
	 * @throws
	 */
	public boolean updateAudio(FrssAudioInfo audioInfo) {
		if(audioInfo.getId()<=0) {
			/// log 非法记录
			return false;
		}
		
		Transaction trans = null;
		Session session = null;
		DateUtil dateUtil = new DateUtil();
		
		String hql = null;
		hql = "update " + FrssAudioInfo.class.getName() + " au ";
		hql += "set au.filePath='" + audioInfo.getFilePath() + "',";
		hql += "au.type=" + audioInfo.getType() + ",";
		hql += "au.keyId=" + audioInfo.getKeyId() + ",";
		hql += "au.status=" + audioInfo.getStatus() + ", ";
		hql += "au.reporter='" + audioInfo.getReporter() + "',";
		hql += "au.reportTime=:date ";
		hql += "where au.id=" + audioInfo.getId();
		
		try {	// 执行更新操作
			session = BaseDAO.getSession();
			trans = session.beginTransaction();
			Query update = session.createQuery(hql);
			update.setParameter("date", audioInfo.getReportTime());
			int ret = update.executeUpdate();
			trans.commit();
		} catch(Exception e) {
			trans.rollback();
			return false;
		}
		
		return true;
	}

}
