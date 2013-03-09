package com.frss.dao.main;

import java.util.HashMap;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.frss.dao.util.BaseDAO;
import com.frss.model.main.AudioInfoForm;
import com.frss.model.main.ExpertInfoForm;
import com.frss.model.main.UserInfo;
import com.frss.model.mapping.FrssAudioInfo;
import com.frss.model.mapping.FrssExpertInfo;
import com.frss.model.mapping.FrssUserInfo;

public class ExpertDAO {
	
	// 插入专家信息
	public int insertExpertInfoForm(ExpertInfoForm expertInfo) {
		
		long expertId = 0;			// 返回的专家Id号
		FrssExpertInfo insertObj = new FrssExpertInfo();
		if(insertObj==null) {
			/// log
			return -1;
		}
		
		insertObj.setName(expertInfo.getExpertName());
		insertObj.setEmail(expertInfo.getExpertEmail());
		insertObj.setDepartment(expertInfo.getDepartment());
		insertObj.setContact(expertInfo.getContact());
		insertObj.setContactWay(expertInfo.getContactWay());
		// 设置专业信息
		for(String profession : expertInfo.getProfession()) {
			insertObj.getProfessions().add(profession);
		}
		
		// 插入专家信息表
		try {
			Session session = BaseDAO.getSession();
			Transaction transaction = session.beginTransaction();
			session.save(insertObj);
			transaction.commit();
			session.close();
			
			expertId = insertObj.getId();				
		} catch (Exception e) {
			/// log			
			return 1;
		}
		
		return 0;
	}
	
	// 插入语言信息表单，语音的KEYID可以通过当前用户登录的身份进行获取其用户信息，然后查询审核表或者专家信息表信息表获取ID
	public int insertAudioInfoForm(AudioInfoForm audioInfo, long userId) {
		long audioId = 0;
		
		FrssAudioInfo insertObj = new FrssAudioInfo();
		if(insertObj==null) {
			/// log
			return -1;
		}
		
//		insertObj.setStartTime(audioInfo.getStartTime());
//		insertObj.setEndTime(audioInfo.getEndTime());
//		insertObj.setFilePath(audioInfo.getFilePath());
//		insertObj.setHelpType(audioInfo.getAudioType());
//		insertObj.setKeyID(userId);
		
		// 插入专家信息表
		try {
			Session session = BaseDAO.getSession();
			Transaction transaction = session.beginTransaction();
			session.save(insertObj);
			transaction.commit();
			session.close();
			
			audioId = insertObj.getId();				
		} catch (Exception e) {
			/// log			
			return 1;
		}
		
		return 0;
	}
	
	/**
	 * @函数名称: queryExpert
	 * @函数描述: 通过专家姓名获取其信息，假设姓名是唯一的
	 * @输入参数: @param expertName
	 * @输入参数: @return
	 * @返回类型: FrssExpertInfo
	 * @throws
	 */
	public FrssExpertInfo queryExpert(String expertName) {
		FrssExpertInfo expertInfo = null;
		
		String hql = "from " + FrssExpertInfo.class.getName() + " ex " +
				"where ex.name='" + expertName + "'";
		
		try {
			Session session = BaseDAO.getSession();
			Query objQuery = session.createQuery(hql);
			List lstResult =  objQuery.list();
			session.close();
			
			if(lstResult!=null&&lstResult.size()>0) {
				expertInfo = (FrssExpertInfo)lstResult.get(0);
			}
			
		} catch (Exception e) {
			/// log
			return null;
		}
		
		return expertInfo;
	}

	/**
	 * @函数名称: queryExpert
	 * @函数描述: 查询当前专家表中所有专家姓名和对应id
	 * @输入参数: @return
	 * @返回类型: HashMap<Long,String>
	 * @throws
	 */
	public HashMap<Long, String> queryExpert() {
		HashMap<Long, String> mapExpert = null;
		
		String hql = "select ex.id, ex.name ";
		hql += "from " + FrssExpertInfo.class.getName() + " ex ";
		
		try{
			Session session = BaseDAO.getSession();
			Query objQuery = session.createQuery(hql.toString());
			List<Object[]> lstResult = objQuery.list();
			session.close();
			
			if(lstResult!=null&&lstResult.size()>0) {
				
				mapExpert = new HashMap<Long, String>();
				for(Object[] object : lstResult) {
					if(object[0]!=null)
						mapExpert.put(Long.parseLong(object[0].toString()), object[1].toString());
				}
			}
			
		} catch(Exception e) {
			/// log 查询专家信息失败
			return null;
		}
		
		return mapExpert;
	}
}
