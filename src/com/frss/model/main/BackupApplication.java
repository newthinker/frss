package com.frss.model.main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.frss.util.DateUtil;
import com.frss.util.FrssException;

public class BackupApplication {
	private long faultID;				// 维修申请单号
	private long backID;				// 备件申请单编号
	
	private String backType;			//备件型号
	private String backName;			//备件名称
	private String backNumber;			//备件编号
	private Integer backAmount;			//备件数量
	private String useEquipName;		//备件使用装备名称
	private String useEquipType;		//备件使用装备型号
	private String userEquipPlace;		//备件使用装备部位
	private String equipDepartment;		//装备使用单位
	private String operater;			//装备使用人
	private String reporter;			//申请单记录人
	private String reporterContact;		//记录人联系方式
	private Date reportTime;			//申请时间
	private String l1Checker;			//L1级审核人
	private Date l1CheckTime;			//L1级审核时间
	private String l1CheckOpinion;		//L1级审核意见
	private String l2Checker;			//L2级审核人
	private Date l2CheckTime;			//L2级审核时间
	private String l2CheckOpinion;		//L2级审核意见
	
	private int status;					// 备件申请单的状态，[0~2]:0表示申请确认状态、1表示L1级审核状态、2表示L2级审核状态 
	
	public long getFaultID() {
		return this.faultID;
	}
	public void setFaultID(long faultID) {
		this.faultID = faultID;
	}
	
	public long getBackID() {
		return this.backID;
	}
	public void setBackID(long backID) {
		this.backID = backID;
	}
	
	public String getBackType() {
		return this.backType;
	}
	public void setBackType(String backType) {
		this.backType = backType;
	}
	
	public String getBackName() {
		return this.backName;
	}
	public void setBackName(String backName) {
		this.backName = backName;
	}
	
	public String getBackNumber() {
		return this.backNumber;
	}
	public void setBackNumber(String backNumber) {
		this.backNumber = backNumber;
	}
	
	public Integer getBackAmount() {
		return this.backAmount;
	}
	public void setBackAmount(Integer backAmount) {
		this.backAmount = backAmount;
	}
	
	public String getUseEquipName() {
		return this.useEquipName;
	}
	public void setUseEquipName(String useEquipName) {
		this.useEquipName = useEquipName;
	}
	
	public String getUseEquipType() {
		return this.useEquipType;
	}
	public void setUseEquipType(String useEquipType) {
		this.useEquipType = useEquipType;
	}
	
	public String getUseEquipPlace() {
		return this.userEquipPlace;
	}
	public void setUseEquipPlace(String useEquipPlace) {
		this.userEquipPlace = useEquipPlace;
	}
	
	public String getEquipDepartment() {
		return this.equipDepartment;
	}
	public void setEquipDepartment(String equipDepartment) {
		this.equipDepartment = equipDepartment;
	}

	public String getOperater() {
		return this.operater;
	}
	public void setOperater(String operater) {
		this.operater = operater;
	}

	public String getReporter() {
		return this.reporter;
	}
	public void setReporter(String reporter) {
		this.reporter = reporter;
	}
	
	public String getReporterContact() {
		return this.reporterContact;
	}
	public void setReporterContact(String reporterContact) {
		this.reporterContact = reporterContact;
	}

	public Date getReportTime() {
		return this.reportTime;
	}
	public void setReportTime(Date reportTime) {
		this.reportTime = reportTime;
	}
	
	public String getL1Checker() {
		return this.l1Checker;
	}
	public void setL1Checker(String l1Checker) {
		this.l1Checker = l1Checker;
	}
	
	public Date getL1CheckTime() {
		return this.l1CheckTime;
	}
	public void setL1CheckTime(Date l1CheckTime) {
		this.l1CheckTime = l1CheckTime;
	}
	
	public String getL1CheckOpinion() {
		return this.l1CheckOpinion;
	}
	public void setL1CheckOpinion(String l1CheckOpinion) {
		this.l1CheckOpinion = l1CheckOpinion;
	}

	public String getL2Checker() {
		return this.l2Checker;
	}
	public void setL2Checker(String l2Checker) {
		this.l2Checker = l2Checker;
	}
	
	public Date getL2CheckTime() {
		return this.l2CheckTime;
	}
	public void setL2CheckTime(Date l2CheckTime) {
		this.l2CheckTime = l2CheckTime;
	}
	
	public String getL2CheckOpinion() {
		return this.l2CheckOpinion;
	}
	public void setL2CheckOpinion(String l2CheckOpinion) {
		this.l2CheckOpinion = l2CheckOpinion;
	}

	public int getStatus(){
		return this.status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	// 使用备件输入表单初始化备件表单对象
	public void Initialize(List<String> lstBack) throws FrssException {
		SimpleDateFormat simFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			this.backType = lstBack.get(0);
			this.backName = lstBack.get(1);
			this.backNumber = lstBack.get(2);
			this.backAmount = Integer.parseInt(lstBack.get(3));
			this.useEquipName = lstBack.get(4);
			this.useEquipType = lstBack.get(5);
			this.userEquipPlace = lstBack.get(6);
			this.faultID = Long.parseLong(lstBack.get(7));
			this.equipDepartment = lstBack.get(8);
			this.operater = lstBack.get(9);
			this.reporter = lstBack.get(10);
			this.reporterContact = lstBack.get(11);			
			this.reportTime = simFormat.parse(lstBack.get(12)); 
			
		} catch (Exception e) {
			throw new FrssException(e);
		}
		
	}

	public void Initialize(Map<String, String> mapBack) throws FrssException {
		try {
			if(mapBack.get("backtype")!=null)
				this.backType = mapBack.get("backtype");
			if(mapBack.get("backname")!=null)
				this.backName = mapBack.get("backname");
			if(mapBack.get("backid")!=null)
				this.backNumber = mapBack.get("backid");
			if(mapBack.get("backnum")!=null)
				this.backAmount = Integer.parseInt(mapBack.get("backnum"));
			if(mapBack.get("equipname")!=null)
				this.useEquipName = mapBack.get("equipname");
			if(mapBack.get("equiptype")!=null)
				this.useEquipType = mapBack.get("equiptype");
			if(mapBack.get("equiplace")!=null)
				this.userEquipPlace = mapBack.get("equiplace");
			if(mapBack.get("faultid")!=null)
				this.faultID = Long.parseLong(mapBack.get("faultid"));
			if(mapBack.get("department")!=null)
				this.equipDepartment = mapBack.get("department");
			if(mapBack.get("operator")!=null)
				this.operater = mapBack.get("operator");
			if(mapBack.get("reporter")!=null)
				this.reporter = mapBack.get("reporter");
			if(mapBack.get("contactway")!=null)
				this.reporterContact = mapBack.get("contactway");	
//			if(mapBack.get("reportime")!=null) {
//				String timeString = mapBack.get("reportime");
//				if(timeString.indexOf("-")>=0)
//					timeString = timeString.replaceAll("-", "");
//				if(timeString.indexOf(":")>=0)
//					timeString = timeString.replaceAll(":", "");
//				if(timeString.length()==8)
//					timeString += "000000";
//				DateUtil dateUtil = new DateUtil();
//				this.reportTime = dateUtil.getTheTime(timeString); 
//			}
			this.reportTime = DateUtil.getCurTime();
		} catch (Exception e) {
			throw new FrssException(e);
		}
		
	}	
}
