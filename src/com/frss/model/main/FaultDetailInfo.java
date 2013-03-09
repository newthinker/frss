package com.frss.model.main;

import java.sql.Blob;
import java.util.Date;

public class FaultDetailInfo {
	private long orderId;		// 单号 --唯一id --等于faultid或者backupid 
	private int step;			// 进程步骤
	private int state;			// 进程状态，0终止，1正在进行--输出
	private int status;			// 最后一步的状态，0待审核/确认，1审核/确认通过，2审核/确认没有通过--对内
	
	/// 故障信息表
	private String reporter;	// 故障提交者
	private Date reportTime;	// 故障提交时间
	private String reporterContactWay;		// 故障提交者联络方式
	private String operater;	// 使用者
	private String department;	// 装备使用单位
	
	private long faultId;		// 故障单编号
	private String equipType;	// 装备型号
	private String equipName;	// 装备名称
	private String equipNumber;	// 装备编号
	private int faultAmount;	// 装备数量
	private String faultDesp;	// 故障描述
	private Date faultTime;		// 故障发生时间
	private String preProcess;	// 故障前期处理
	private int frequence;		// 故障发生频次
	private String faultPlace;	// 故障发生部位
	private Blob photos;		// 故障取证照片
	
	/// 备件信息表
	private long backupId;		// 备件编号
	private String backType;	// 备件型号
	private String backName;	// 备件名称
	private String backNumber;	// 备件编号
	private int bakAmount;		// 备件数量
	private String bakPlace;	// 备件使用装备部位
	// 维修申请单好同faultId
	
	/// 审核信息表
	private String l1Checker;	// L1级审核人
	private Date l1CheckTime;	// L1级审核时间
	private String l1Opinion;	// L1级审核意见
	private String l2Checker;	// L2级审核人
	private Date l2CheckTime;	// L2级审核时间
	private String l2Opinion;	// L2级审核意见
	
	/// 派遣信息表
	private long dispatchId;	// 派遣单号
	// 派遣单位同装备使用单位
	private Date dispatchTime;	// 派遣时间
	
	/// 工业部门信息表 
	private String guarantee;	// 保障部队
	private String guaranteeAddress;	// 保障部队地址
	private String guaranteeContact;	// 部队联系人
	
	/// 反馈信息单
	private long feedbackId;	// 反馈信息单号
	private String faultDispatch;	// 故障派修
	private String repairDisp;	// 维修内容
	private String cause;		// 故障原因
	private int repairWay;		// 维修手段
	private String results;		// 维修结果
	private int quality;		// 维修质量
	private Date repairTime;	// 维修时间
	// 维修使用备件名称同备件名称
	// 维修使用备件型号同备件型号
	private String backSource;	// 备件来源情况

	/// 远程支援信息
	private long remoteId;		// 远程支援信息单号
	private String channel;		// 远程支援通道
	private int supportType;	// 远程支援方式
	// 远程支援单位同装备使用单位
	private String expert;		// 远程支援专家
	private String expertContact;		// 专家联络人
	private String expertContactWay;	// 联系方式
	
	public long getOrderId() {
		return this.orderId;
	}
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	
	public int getStep() {
		return this.step;
	}
	public void setStep(int type) {
		/// 根据单号类型计算step
		this.step = step;
	}
	
	public int getState() {
		return this.state;
	}
	public void setState(int state) {
		this.state = state;
	}
	
	public int getStatus() {
		return this.status;
	}
	public void setStatus(int status) {
		this.status = status;
		/// 根据status计算state
		
	}
	
	public String getReporter() {
		return this.reporter;
	}
	public void setReporter(String reporter) {
		this.reporter = reporter;
	}
	
	public Date getReportTime() {
		return this.reportTime;
	}
	public void setReportTime(Date reportTime) {
		this.reportTime = reportTime;
	}
	
	public String getReportContactWay() {
		return this.reporterContactWay;
	}
	public void setReportContactWay(String reportContactWay) {
		this.reporterContactWay = reportContactWay;
	}
	
	public String getOperater() {
		return this.operater;
	}
	public void setOperater(String operater) {
		this.operater = operater;
	}
	
	public String getDepartment() {
		return this.department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	
	public long getFaultId() {
		return this.faultId;
	}
	public void setFaultId(long faultId) {
		this.faultId = faultId;
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
	
	public String getEquipNumber() {
		return this.equipNumber;
	}
	public void setEquipNumber(String equipNumber) {
		this.equipNumber = equipNumber;
	}
	
	public int getFaultAmount() {
		return this.faultAmount;
	}
	public void setFaultAmount(int faultAmount) {
		this.faultAmount = faultAmount;
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
	
	public int getFrequence() {
		return this.frequence;
	}
	public void setFrequence(int frequence) {
		this.frequence = frequence;
	}
	
	public String getFaultPlace() {
		return this.faultPlace;
	}
	public void setFaultPlace(String faultPlace) {
		this.faultPlace = faultPlace;
	}
	
	public Blob getPhotos() {
		return this.photos;
	}
	public void setPhotos(Blob photos) {
		this.photos = photos;
	}
	
	public long getBackupId() {
		return this.backupId;
	}
	public void setBackupId(long backupId) {
		this.backupId = backupId;
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
	
	public int getBackAmount() {
		return this.bakAmount;
	}
	public void setBackAmount(int bakAmount){
		this.bakAmount = bakAmount;
	}
	
	public String getBakPlace() {
		return this.bakPlace;
	}
	public void setBakPlace(String bakPlace) {
		this.bakPlace = bakPlace;
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
		return this.l1Opinion;
	}
	public void setL1CheckOpinion(String l1Opinion) {
		this.l1Opinion = l1Opinion;
	}
	
	public String getL2Checker(){
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
	
	public String getL2CheckOpinion(){
		return this.l2Opinion;
	}
	public void setL2CheckOpinion(String l2Opinion) {
		this.l2Opinion = l2Opinion;
	}
	
	public long getDispatchId(){
		return this.dispatchId;
	}
	public void setDispatchId(long dispatchId) {
		this.dispatchId = dispatchId;
	}
	
	public Date getDispatchTime() {
		return this.dispatchTime;
	}
	public void setDispatchTime(Date dispatchTime){
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
	
	public String getGuaranteeContact() {
		return this.guaranteeContact;
	}
	public void setGuaranteeContact(String guaranteeContact) {
		this.guaranteeContact = guaranteeContact;
	}
	
	public long getFeedbackId(){
		return this.feedbackId;
	}
	public void setFeedbackId(long feedbackId) {
		this.feedbackId = feedbackId;
	}
	
	public String getFaultDispatch() {
		return this.faultDispatch;
	}
	public void setFaultDispatch(String faultDispatch) {
		this.faultDispatch = faultDispatch;
	}
	
	public String getRepairDisp() {
		return this.repairDisp;
	}
	public void setRepairDisp(String repairDisp) {
		this.repairDisp = repairDisp;
	}
	
	public String getCause() {
		return this.cause;
	}
	public void setCause(String cause) {
		this.cause = cause;
	}
	
	public int getRepairWay() {
		return this.repairWay;
	}
	public void setRepariWay(int repairWay) {
		this.repairWay = repairWay;
	}
	
	public String getRepairResults() {
		return this.results;
	}
	public void setRepairResults(String results) {
		this.results = results;
	}
	
	public int getRepairQuality() {
		return this.quality;
	}
	public void setRepairQuality(int quality) {
		this.quality = quality;
	}
	
	public Date getRepairTime() {
		return this.repairTime;
	}
	public void setRepairTime(Date repairTime) {
		this.repairTime = repairTime;
	}
	
	public String getBackSource() {
		return this.backSource;
	}
	public void setBackSource(String backSource) {
		this.backSource = backSource;
	}
	
	public long getRemoteId() {
		return this.remoteId;
	}
	public void setRemoteId(long remoteId) {
		this.remoteId = remoteId;
	}
	
	public String getRemoteChannel() {
		return this.channel;
	}
	public void setRemoteChannel(String channel) {
		this.channel = channel;
	}
	
	public int getSupportType() {
		return this.supportType;
	}
	public void setSupportType(int supportType) {
		this.supportType = supportType;
	}
	
	public String getExpert() {
		return this.expert;
	}
	public void setExpert(String expert) {
		this.expert = expert;
	}
	
	public String getExpertContact() {
		return this.expertContact;
	}
	public void setExpertContact(String expertContact) {
		this.expertContact = expertContact;
	}
	
	public String getExpertContactWay(){
		return this.expertContactWay;
	}
	public void setExpertContactWay(String expertContactWay) {
		this.expertContactWay = expertContactWay;
	}
	
}
