package com.frss.model.main;

import java.sql.Blob;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.frss.model.mapping.FrssApprovalInfo;
import com.frss.util.DateUtil;

public class FaultRepairReport {
	private long faultId;			// 故障编号，也即维修申请单号
	
	private String equipType;		// 装备型号
	private String equipNumber;		// 装备编号
	private String equipName;		// 装备名称
	private String department;		// 装备使用单位
	private String operater;		// 装备使用人
	
	private int faultAmount;		// 故障装备数量
	private String faultDesp;		// 故障描述
	private Date faultTime;			// 故障发生时间
	private String preProcess;		// 故障前期处理
	private int faultFrequency;		// 故障发生频次
	private String faultPlace;		// 故障发生部位
	private String reporter;		// 故障记录者
	private String reporterContact;	// 记录者联系方式
	private Date reportTime;		// 报修时间
	private String photoName;		// 照片名字
	private Blob faultPhotos;		// 取证照片
	
	private String l1Checker;		// L1级审核者
	private Date l1CheckTime;		// L1级审核时间
	private String l1CheckOpinion;	// L1级审核意见
	private String l2Checker;		// L2级审核者
	private Date l2CheckTime;		// L2级审核时间
	private String l2CheckOpinion;	// L2级审核意见	
	
	private String faultCause;		// 故障原因
	
	private int reportType;			// 报告单类型
	private int status;				// 表单审核状态，[0-2]，0表示待审核、1表示审核/确认通过、2表示审核没通过
	
	private int step;				// 故障单审核进度，[1-8]	

	public FaultRepairReport() {
		
	}
	
	public FaultRepairReport(long faultId, String equipType, String equipName, String equipNumber, int faultAmount, String faultDesp, 
			Date faultTime, String preProcess, int frequency, String faultPlace, String department, String operater, String reporter, 
			String contact, Date reportTime, String photoName, Blob faultPhotos, String l1Checker, Date l1CheckTime, String l1Opinion, String l2Checker,
			Date l2CheckTime, String l2Opinion, int status) {
		this.faultId = faultId;
		this.equipType = equipType;
		this.equipNumber = equipNumber;
		this.equipName = equipName;
		this.department = department;
		this.operater = operater;
		this.faultAmount = faultAmount;
		this.faultDesp = faultDesp;
		this.preProcess = preProcess;
		this.faultFrequency = frequency;
		this.faultPlace = faultPlace;
		this.reporter = reporter;
		this.reporterContact = contact;
		this.reportTime = reportTime;
		this.photoName = photoName;
		this.faultPhotos = faultPhotos;
		this.l1Checker = l1Checker;
		this.l1CheckOpinion = l1Opinion;
		this.l1CheckTime = l1CheckTime;
		this.l2Checker = l2Checker;
		this.l2CheckOpinion = l2Opinion;
		this.l2CheckTime = l2CheckTime;
		this.status = status;
	}
	
	// 装备故障维修申请单号
	public long getFaultID() {
		return this.faultId;
	}
	public void setFaultID(long faultId) {
		/// 需要考虑流程操作，最终确认由什么方式生成
		this.faultId = faultId;
	}
	
	public String getEquipType() {
		return this.equipType;
	}
	public void setEquipType(String equipType) {
		this.equipType = equipType;
	}
	
	public String getEquipNumber() {
		return this.equipNumber;
	}
	public void setEquipNumber(String equipNumber) {
		this.equipNumber = equipNumber;
	}
	
	public String  getEquipName() {
		return this.equipName;
	}
	public void setEquipName(String equipName) {
		this.equipName = equipName;
	}
	
	public int getFaultAmount() {
		return this.faultAmount;
	}
	public void setfaultAmount(int faultAmount) {
		this.faultAmount = faultAmount;
	}
	
	public String getEquipDepartment() {
		return this.department;
	}
	public void setEquipDepartment(String equipDepartment) {
		this.department = equipDepartment;
	}
	
	public String getEquipOperater() {
		return this.operater;
	}
	public void setEquipOperater(String equipOperater) {
		this.operater = equipOperater;
	}
	
	public String getFaultDesp() {
		return this.faultDesp;
	}
	public void setFaultDesp(String faultDesp) {
		this.faultDesp = faultDesp;
	}
	
	public Date getFaultTime() {
		return this.faultTime;
	}
	public void setFaultTime(Date faultTime) {
		this.faultTime = faultTime;
	}
	
	public String getPreprocess() {
		return this.preProcess;
	}
	public void setPreprocess(String preProcess) {
		this.preProcess = preProcess;
	}
	
	public int getFaultFrequency() {
		return this.faultFrequency;
	}
	public void setFaultFrequency(int faultFrequency) {
		this.faultFrequency = faultFrequency;
	}
	
	public String getFaultPlace() {
		return this.faultPlace;
	}
	public void setFaultPlace(String faultPlace) {
		this.faultPlace = faultPlace;
	}

	public String getReporter() {
		return this.reporter;
	}
	public void setReporter(String reporter) {
		this.reporter = reporter;
	}
	
	public String getReporterContact(){
		return this.reporterContact;
	}
	public void setReporterContact(String reporterContact) {
		this.reporterContact = reporterContact;
	}
	
	public Date getReportTime() {
		return this.reportTime;
	}
	public void setReportTime(Date reportTime){
		this.reportTime = reportTime;
	}
	public void setReportTime(String timeString) {
		DateUtil dateUtil = new DateUtil();
		this.reportTime = dateUtil.getDateFromSerial(timeString);
	}
	
	public String getPhotoName() {
		return this.photoName;
	}
	public void setPhotoName(String photoName) {
		this.photoName = photoName;
	}
	
	public Blob getFaultPhotos() {
		return this.faultPhotos;
	}
	public void setFaultPhotos(Blob faultPhotos) {
		this.faultPhotos = faultPhotos;
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

	public int getReportType() {
		return this.reportType;
	}
	public void setReportType(int type) {
		this.reportType = type;
	}

	public int getStatus() {
		return this.status;
	}
	public void setStatus(int status) {
		this.status = status;
	}

	public int getStep(){
		/// 需要重新进行划分步骤，将后台的阶段映射到前端的阶段
		return this.step;
	}
	public void setStep(int step) {
		this.step = step;
	}
	
	public String getFaultCause() {
		return this.faultCause;
	}
	public void setFaultCause(String faultCause) {
		this.faultCause = faultCause;
	}
	
	// initialize
	public void initialize(List<String> lstFault) throws ParseException {
		DateUtil dateUtil = new DateUtil();
		
		int loc = 1;
		this.equipType = lstFault.get(loc);
		this.equipName = lstFault.get(loc+1);
		this.equipNumber = lstFault.get(loc+2);
		this.faultAmount = Integer.parseInt(lstFault.get(loc+3));
		this.faultDesp = lstFault.get(loc+4);
		this.faultTime = dateUtil.getTheTime(lstFault.get(loc+5));
		this.preProcess = lstFault.get(loc+6);
		this.faultFrequency = Integer.parseInt(lstFault.get(loc+7));
		this.faultPlace = lstFault.get(loc+8);
		this.department = lstFault.get(loc+9);
		this.operater = lstFault.get(loc+10);
		this.reporter = lstFault.get(loc+11);
		this.reporterContact = lstFault.get(loc+12);
		this.reportTime = dateUtil.getCurTime();
		/// 照片如何处理?
	}
	
	public void initialize(Map<String, String> mapFault) throws ParseException {
		DateUtil dateUtil = new DateUtil();
		if(mapFault.get("zbtype")!=null)
			this.equipType = mapFault.get("zbtype");
		if(mapFault.get("zbname")!=null)
			this.equipName = mapFault.get("zbname");
		if(mapFault.get("zbserial")!=null)
			this.equipNumber = mapFault.get("zbserial");
		if(mapFault.get("zbnum")!=null)
			this.faultAmount = Integer.parseInt(mapFault.get("zbnum"));
		if(mapFault.get("zbgzxx")!=null)
			this.faultDesp = mapFault.get("zbgzxx");
		if(mapFault.get("zbgzsj")!=null) {
			String dateString = mapFault.get("zbgzsj");
			if(dateString.indexOf("-")>=0)
				dateString = dateString.replaceAll("-", "");
			if(dateString.indexOf(":")>=0)
				dateString = dateString.replaceAll(":", "");
			if(dateString.length()<14) {
				dateString = dateString.substring(0, 8);
				dateString += "000000";
			}
			this.faultTime = dateUtil.getDateFromSerial(dateString);
		}
		if(mapFault.get("zbxqcl")!=null)
			this.preProcess = mapFault.get("zbxqcl");
		if(mapFault.get("zbgzpc")!=null)
			this.faultFrequency = Integer.parseInt(mapFault.get("zbgzpc"));
		if(mapFault.get("zbgzbw")!=null)
			this.faultPlace = mapFault.get("zbgzbw");
		if(mapFault.get("zbsydw")!=null)
			this.department = mapFault.get("zbsydw");
		if(mapFault.get("zbuser")!=null)
			this.operater = mapFault.get("zbuser");
		if(mapFault.get("commiter")!=null)
			this.reporter = mapFault.get("commiter");
		if(mapFault.get("contact")!=null)
			this.reporterContact = mapFault.get("contact");
//		this.reportTime = DateUtil.getCurTime();
		if(mapFault.get("photoName")!=null) 
			this.photoName = mapFault.get("photoName");
		
	}
}
