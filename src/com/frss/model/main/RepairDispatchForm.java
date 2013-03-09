package com.frss.model.main;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.frss.util.DateUtil;
import com.frss.util.FrssException;

public class RepairDispatchForm {
	private long faultId;		// 装备故障维修上报单号
	private long dispatchId;	// 装备维修派遣信息单号
	private String department;	// 派遣单位
	private Date dispatchTime;	// 派遣时间
	private String guarantee;	// 保障部队
	private String guaranteeAddress;	// 部队地址
	private String contact;		// 部队联系人
	private String equipType;	// 装备型号
	private String equipName;	// 装备名称
	private String faultDisp;	// 故障现象描述
	
	public RepairDispatchForm() {
		
	}
	
	public long getFaultId() {
		return this.faultId;
	}
	public void setFaultId(long faultId) {
		this.faultId = faultId;
	}
	
	public long getDispatchId () {
		return this.dispatchId;
	}
	public void setDispatchId(long dispatchId) {
		this.dispatchId = dispatchId;
	}
	
	public String getDepartment() {
		return this.department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	
	public Date getDispatchTime() {
		return this.dispatchTime;
	}
	public void setDispatchTime(Date dispatchTime) {
		this.dispatchTime = dispatchTime;
	}
	
	public String getGuarantee() {
		return this.guarantee;
	}
	public void setGuarantee(String guarantee) {
		this.guarantee = guarantee;
	}
	
	public String getGuaranteeAddress() {
		return this.guaranteeAddress;
	}
	public void setGuaranteeAddress(String guaranteeAddress) {
		this.guaranteeAddress = guaranteeAddress;
	}
	
	public String getContact() {
		return this.contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	
	public String getEquipType() {
		return this.equipType;
	}
	public void setEquipType(String equipType) {
		this.equipType = equipType;
	}
	
	public String getEquipName() {
		return this.equipName;
	}
	public void setEquipName(String equipName) {
		this.equipName = equipName;
	}
	
	public String getFaultDisp() {
		return this.faultDisp;
	}
	public void setFaultDisp(String faultDisp) {
		this.faultDisp = faultDisp;
	}
	
//	public long getFactoryId() {
//		return this.factoryId;
//	}
//	public void setFactoryId(long factoryId) {
//		this.factoryId = factoryId;
//	}
//
//	public int getStatus() {
//		return this.status;
//	}
//	public void setStatus(int status) {
//		this.status = status;
//	}
//	
//	public Date getCheckTime() throws FrssException {
//		try {
//			checkTime = simFormat.parse(simFormat.format(new Date(System.currentTimeMillis())));	//获取当前时间
//		} catch (Exception e) {
//			/// log
//			throw new FrssException(e);
//		}
//		
//		return checkTime;
//	}
//	public void setCheckTime(Date checkTime) {
//		this.checkTime = checkTime;
//	}

	// initialize
	public void initialize(Map<String, String> mapDispatch) throws FrssException {
		try {
			if(mapDispatch.get("orderid")!=null)
				this.faultId = Long.parseLong(mapDispatch.get("orderid"));
//			if(mapDispatch.get("factoryid")!=null)
//				this.factoryId = Long.parseLong(mapDispatch.get("factoryid"));
			if(mapDispatch.get("dpid")!=null)
				this.dispatchId = Long.parseLong(mapDispatch.get("dpid"));
			if(mapDispatch.get("dpunit")!=null)
				this.department = mapDispatch.get("dpunit");
			this.dispatchTime = DateUtil.getCurTime();
			if(mapDispatch.get("dpsafeunit")!=null)
				this.guarantee = mapDispatch.get("dpsafeunit");
			if(mapDispatch.get("zbtype")!=null)
				this.equipType = mapDispatch.get("zbtype");
			if(mapDispatch.get("zbname")!=null)
				this.equipName = mapDispatch.get("zbname");
			if(mapDispatch.get("dpaddr")!=null)
				this.guaranteeAddress = mapDispatch.get("dpaddr");
			if(mapDispatch.get("dpcontact")!=null)
				this.contact = mapDispatch.get("dpcontact");
			if(mapDispatch.get("zbgzxx")!=null)
				this.faultDisp = mapDispatch.get("zbgzxx");			
		} catch (Exception e) {
			/// log
			throw new FrssException(e);
		}				
	}
	
}
