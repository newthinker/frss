package com.frss.model.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.frss.util.FrssException;

public class FactoryInfoForm {
	private long factoryId;				// 工厂Id
	private String factoryName;			// 工厂名称
	private String factoryAddress;		// 工厂地址
	private String factoryCode;			// 工厂代号
	private String range;				// 生产范围
	private ArrayList<String> producedEquipType;	// 已出厂设备型号
	private ArrayList<String> producedEquipName;	// 已出厂设备名称
	private long guaranteeId;			// 编配部队编号
	private String guarantee;			// 编配部队
	private String guaranteeAddress;	// 编配部队地址
	private String machinist;			// 维修人员姓名
	private int ability;				// 工业部门修理能力
	private String repairInfo;			// 维修设备情况
	private String contact;				// 工厂联系人
	private String contactWay;			// 工厂联系人的联系方式
	
	
	public FactoryInfoForm() {
		
	}
	
	public long getFactoryId () {
		return this.factoryId;
	}
	public void setFactoryId(long factoryId) {
		this.factoryId = factoryId;
	}
	
	public String getFactoryName() {
		return this.factoryName;
	}
	public void setFactoryName(String factoryName) {
		this.factoryName = factoryName;
	}
	
	public String getFactoryAddress() {
		return this.factoryAddress;
	}
	public void setFactoryAddress(String factoryAddress) {
		this.factoryAddress = factoryAddress;
	}
	
	public String getFactoryCode() {
		return this.factoryCode;
	}
	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	}
	
	public String getRange() {
		return this.range;
	}
	public void setRange(String range) {
		this.range = range;
	}

	public ArrayList<String> getProducedEquipType() {
		return producedEquipType;
	}
	public void setProducedEquipType(String equipTypes) {
		producedEquipType = new ArrayList<String>();
		if(producedEquipType==null) {
			return;
		}
		
		producedEquipType = getArray(equipTypes);
	}
	
	public ArrayList<String> getProducedEquipName() {
		return producedEquipName;
	}
	public void SetProducedEquipType(String equipNames) {
		producedEquipName = new ArrayList<String>();
		if(producedEquipName==null) {
			return;
		}
		
		producedEquipName = getArray(equipNames);
	}
	
	public long getGuaranteeId() {
		return this.guaranteeId;
	}
	public void setGuaranteeId(long guaranteeId) {
		this.guaranteeId = guaranteeId;
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
	
	public int getAbility() {
		return this.ability;
	}
	public void setAbility(int ability) {
		this.ability = ability;
	}
	
	public String getRepairInfo() {
		return this.repairInfo;
	}
	public void setRepairInfo(String repairInfo) {
		this.repairInfo = repairInfo;
	}
	
	public String getContact() {
		return this.contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	
	public String getContactWay() {
		return this.contactWay;
	}
	public void setContactWay(String contactWay) {
		this.contactWay = contactWay;
	}

	public String getMachinist() {
		return this.machinist;
	}
	public void setMachinist(String machinist) {
		this.machinist = machinist;
	}

	private ArrayList getArray(String strSequence) {
		ArrayList<String> arrList = new ArrayList<String>();
		if(arrList==null) {
			return null;
		}
		
		String sequence = strSequence;
		
		if(sequence!=null) {
			while(sequence.indexOf(" ")>=0) {
				int loc = sequence.indexOf(" ");
				
				String value = sequence.substring(0, loc);
				sequence = sequence.substring(loc+1);
				arrList.add(value);
				
//				if(sequence.indexOf(";")<0 && sequence.indexOf(",")<0 && !(sequence.equals(""))) {
//					arrList.add(sequence.trim());
//				}
			}
			
			// 如果只有一个 [zuow, 2012/04/22]
			if(!(sequence.equals("")))
				arrList.add(sequence.trim());
		}
		
		return arrList;
	}

	// initialize
	public void initialize(Map<String, String> mapFactory) throws FrssException {
		try {
			if(mapFactory.get("name")!=null)
				this.factoryName = mapFactory.get("name");
			if(mapFactory.get("address")!=null)
				this.factoryAddress = mapFactory.get("address");
			if(mapFactory.get("code")!=null)
				this.factoryCode = mapFactory.get("code");
			if(mapFactory.get("range")!=null)
				this.range = mapFactory.get("range");
			if(mapFactory.get("pdtype")!=null)
				this.producedEquipType = getArray(mapFactory.get("pdtype"));
			if(mapFactory.get("pdname")!=null)
				this.producedEquipName = getArray(mapFactory.get("pdname"));
			if(mapFactory.get("guarantee")!=null)
				this.guarantee = mapFactory.get("guarantee");
			if(mapFactory.get("rpinfo")!=null)
				this.machinist = mapFactory.get("rpinfo");
			if(mapFactory.get("ability")!=null)
				this.ability = Integer.parseInt(mapFactory.get("ability"));
			if(mapFactory.get("rpdevice")!=null)
				this.repairInfo = mapFactory.get("rpdevice");
			if(mapFactory.get("contact")!=null)
				this.contact = mapFactory.get("contact");
			if(mapFactory.get("contactWay")!=null)
				this.contactWay = mapFactory.get("contactWay");
		} catch (Exception e) {
			throw new FrssException(e);
		}
	}
	
}
