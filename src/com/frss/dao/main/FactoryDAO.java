package com.frss.dao.main;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.frss.dao.util.BaseDAO;
import com.frss.model.main.FactoryInfoForm;
import com.frss.model.main.MachinistInfoForm;
import com.frss.model.mapping.FrssEquipCreating;
import com.frss.model.mapping.FrssEquipmentInfo;
import com.frss.model.mapping.FrssFactoryInfo;
import com.frss.model.mapping.FrssFaultInfo;
import com.frss.model.mapping.FrssMachinistInfo;
import com.frss.util.FrssException;

public class FactoryDAO {
	
	/**
	 * @函数名称: insertFactory
	 * @函数描述: 将工业部门信息插入表中
	 * @输入参数: @param insertObj
	 * @输入参数: @return
	 * @返回类型: long
	 * @throws
	 */
	public long insertFactory(FrssFactoryInfo insertObj) {
		Session session = null;
		Transaction transaction = null;
		long factoryId = 0;
		
		// 入库
		try {
			session = BaseDAO.getSession();
			transaction = session.beginTransaction();
			session.save(insertObj);
			transaction.commit();
			session.close();	
			
			factoryId = insertObj.getId();
		} catch (Exception e) {
			transaction.rollback();	
			/// log
			return -1;
		}		
		
		return factoryId;
	}
	
	/**
	 * @函数名称: insertEquipCreating
	 * @函数描述: 将装备制造信息插入表中
	 * @输入参数: @param insertObj
	 * @输入参数: @return
	 * @返回类型: long
	 * @throws
	 */
	public long insertEquipCreating(FrssEquipCreating insertObj) {
		Session session = null;
		Transaction transaction = null;
		long insertId = 0;
		
		// 入库
		try {
			session = BaseDAO.getSession();
			transaction = session.beginTransaction();
			session.save(insertObj);
			transaction.commit();
			session.close();	
			
			insertId = insertObj.getId();
		} catch (Exception e) {
			transaction.rollback();	
			/// log
			return -1;
		}		
		
		return insertId;		
	}
	
	// 将工业部门信息表单插入数据库中，首先插入工业信息表，然后将工业部门Id更新装备信息表和派遣表
	public long insertFactoryInfoForm(FactoryInfoForm factoryForm) {
		int ret = 0;
		long factoryId = 0;
		
		FrssFactoryInfo insertObj = new FrssFactoryInfo();
		if(insertObj==null) {
			///log
			return -1;
		}
		
		/// 需要查询是否重复录入工业部门信息？？？
		insertObj.setName(factoryForm.getFactoryName());
		insertObj.setAddress(factoryForm.getFactoryAddress());
		insertObj.setCode(factoryForm.getFactoryCode());
		insertObj.setRange(factoryForm.getFactoryCode());
		insertObj.setGuarantee(factoryForm.getGuarantee());
//		insertObj.setGuardAddress(factoryForm.getGuaranteeAddress());
		insertObj.setMachinist(factoryForm.getMachinist());
		insertObj.setAbility(factoryForm.getAbility());
		insertObj.setContact(factoryForm.getContact());
		insertObj.setContactWay(factoryForm.getContactWay());
		insertObj.setMachinist(factoryForm.getMachinist());
		
		// 入库
		factoryId = insertFactory(insertObj);
		if(factoryId<=0) {
			/// log
			return 0;
		}
		
		Session session = null;
		Transaction transaction = null;
//		// 目前假设装备类型和名字可以唯一确定装备，或者是装备型号和名字查处的这些装备只由一个工业部门提供
//		for(int num=0;num<factoryForm.getProducedEquipName().size();num++) {
//			String equipType = factoryForm.getProducedEquipType().get(num);
//			String equipName = factoryForm.getProducedEquipName().get(num);
//			
//			EquipDAO equipDAO = new EquipDAO();
//			ArrayList<FrssEquipmentInfo> lstEquip = equipDAO.getEquipList(equipType, equipName);
//			/// 设置工厂信息
//			for (FrssEquipmentInfo equipInfo : lstEquip) {
//				equipInfo.setFactoryId(factoryId);
//				
//				// 入库
//				try {
//					session = BaseDAO.getSession();
//					transaction = session.beginTransaction();
//					session.save(equipInfo);
//					transaction.commit();
//					session.close();					
//				} catch (Exception e) {
//					/// log
//					continue;
//				}
//			}
//		}
		
		// 将工业部门和装备之间的联系入库 [zuow, 2012/04/18]
		int num = factoryForm.getProducedEquipType().size()<factoryForm.getProducedEquipName().size()?factoryForm.getProducedEquipType().size():factoryForm.getProducedEquipName().size();
		for(int i=0;i<num;i++) {
			String equipType = factoryForm.getProducedEquipType().get(i);
			String equipName = factoryForm.getProducedEquipName().get(i);
			// 首先查询数据库中是否已经存在此条记录，如果有就跳过
			FrssEquipCreating equipCreating = queryEquipCreating(equipType, equipName, factoryId);
			if(equipCreating!=null) {
				/// log
				continue;
			} else { // 将新纪录插入数据库中
				equipCreating = new FrssEquipCreating(equipType, equipName, factoryId);
				long linkId = insertEquipCreating(equipCreating);
				if(linkId<=0) {
					/// log
				}
			}			
		}
		
		return factoryId;
	}
	
	// 将维修人员信息表单入库
	public int insertMachinistInfoForm(MachinistInfoForm machinistInfo) {
		long machinistId = 0;
		FrssMachinistInfo insertObj = new FrssMachinistInfo();
		if(insertObj==null) {
			return 1;
		}
		
		insertObj.setName(machinistInfo.getMachinistName());
		insertObj.setContactWay(machinistInfo.getContactWay());
		insertObj.setEmail(machinistInfo.getEmail());
		insertObj.setAbility(machinistInfo.getAbility());
		insertObj.setFactoryId(machinistInfo.getFactoryId());
		
		try {
			// 入库
			Session session = BaseDAO.getSession();
			Transaction transaction = session.beginTransaction();
			session.save(insertObj);
			transaction.commit();
			session.close();
			
			machinistId = insertObj.getId();			
		} catch (Exception e) {
			/// log
			return 2;
		}
				
		return 0;
	}
	
	/// 根据故障表单号从故障信息表中查询装备编号继而在装备表中查询工业部门编号
	public FrssFactoryInfo queryFactoryId(long faultId) {
		FrssFactoryInfo factoryInfo = null;
		long factoryId = 0;
		
		// 组织查询语句，查找此装备的工业部门
		String hql = null;
		hql = "select eq.factoryId from ";
		hql += FrssFaultInfo.class.getName() + " fa," + FrssEquipmentInfo.class.getName() + " eq ";
		hql += "where fa.equipId=eq.id and fa.id=" + faultId;
				
		try {
			Session session = BaseDAO.getSession();
			Query objQuery = session.createQuery(hql);
			List lstResult = objQuery.list();
			session.close();
			if (lstResult!=null && lstResult.size()>0) {
				factoryId = (Long)lstResult.get(0);
			}
			
			// 查找factory
			if(factoryId<=0)
				return null;
			hql = "from " + FrssFactoryInfo.class.getName() + " fa " + 
				"where fa.id=" + factoryId;
			session = BaseDAO.getSession();
			objQuery = session.createQuery(hql);
			lstResult = objQuery.list();
			session.close();
			if(lstResult!=null && lstResult.size()>0)
				factoryInfo = (FrssFactoryInfo) lstResult.get(0);			
			
		} catch (Exception e) {
			return null;
		}
		
		return factoryInfo;
	}

	/**
	 * @函数名称: queryFactory
	 * @函数描述: 根据工业部门id查找工业部门信息记录
	 * @输入参数: @param factoryId
	 * @输入参数: @return
	 * @返回类型: FrssFactoryInfo
	 * @throws
	 */
	public FrssFactoryInfo queryFactory(long factoryId) {
		FrssFactoryInfo factoryInfo = null;
		
		String hql = "from " + FrssFactoryInfo.class.getName() + " fac ";
		hql += "where fac.id=" + factoryId;
		
		try {
			Session session = BaseDAO.getSession();
			Query objQuery = session.createQuery(hql);
			List lstResult = objQuery.list();
			session.close();
			
			if(lstResult!=null && lstResult.size()>0) {
				factoryInfo = (FrssFactoryInfo)lstResult.get(0);
			}
		} catch (Exception e) {
			/// log
			return null;
		}
		
		return factoryInfo;
	}
	
	/**
	 * @函数名称: queryEquipCreating
	 * @函数描述: 根据装备类型、装备名称和工业部门id查找表中是否有重复记录
	 * @输入参数: @param equipType
	 * @输入参数: @param equipName
	 * @输入参数: @param factoryId
	 * @输入参数: @return
	 * @返回类型: FrssEquipCreating
	 * @throws
	 */
	public FrssEquipCreating queryEquipCreating(String equipType, String equipName, long factoryId) {
		FrssEquipCreating equipCreating = null;
		
		String hql = null;
		hql = "from " + FrssEquipCreating.class.getName() + " ec ";
		hql += "where ec.equipType='" + equipType + "' ";
		hql += "and ec.equipName='" + equipName + "' ";
		hql += "and ec.factoryId=" + factoryId;
		
		try{
			Session session = BaseDAO.getSession();
			Query objQuery = session.createQuery(hql);
			List lstResult = objQuery.list();
			session.close();
			
			if(lstResult!=null && lstResult.size()>0) {
				equipCreating = (FrssEquipCreating) lstResult.get(0);
			}
		} catch (Exception e) {
			/// log
			return null;
		}
		
		return equipCreating;
	}

	/**
	 * @函数名称: queryEquipCreating
	 * @函数描述: 根据装备类型和名称查找所有制造单位
	 * @输入参数: @param equipType
	 * @输入参数: @param equipName
	 * @输入参数: @return
	 * @返回类型: ArrayList<FrssEquipCreating>
	 * @throws
	 */
	public ArrayList<FrssEquipCreating> queryEquipCreating(String equipType, String equipName) {
		ArrayList<FrssEquipCreating> arrEquipCreating = null;
		
		String hql = null;
		hql = "from " + FrssEquipCreating.class.getName() + " ec ";
		hql += "where ec.equipType='" + equipType + "' ";
		hql += "and ec.equipName='" + equipName + "' ";
		hql += "order by ec.id";

		try{
			Session session = BaseDAO.getSession();
			Query objQuery = session.createQuery(hql);
			List lstResult = objQuery.list();
			session.close();
			
			if(lstResult!=null && lstResult.size()>0) {
				arrEquipCreating = new ArrayList<FrssEquipCreating>();
				
				for (int num=0;num<lstResult.size();num++) {
					FrssEquipCreating equipCreating = (FrssEquipCreating) lstResult.get(num);
					
					if(equipCreating!=null)
						arrEquipCreating.add(equipCreating);
				}
			}
		} catch (Exception e) {
			/// log
			return null;
		}
		
		return arrEquipCreating;
	}
}
