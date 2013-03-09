package com.frss.model.mapping;

import java.util.Date;

public class FrssRepairFeedback {
	private long id;					// 故障维修反馈信息单号
	private int repairWay;			// 维修方式，1表示现场维修，2表示专家远程协助，3表示前面两种方式都采用了
	private String repairDisp;			// 维修内容
	private String results;				// 维修结果
	private Date repairTime;			// 维修时间
	private String faultDispatch;		// 故障派修
	private String backName;			// 使用备件名称 [zuow, 2012/04/22]
	private String backType;			// 使用备件型号 [zuow, 2012/04/22]
	private String backSource;			// 备件来源
	private int quality;			// 维修质量
	private long faultId;		// 对应的故障编号
	
	public FrssRepairFeedback() {
		
	}
	public FrssRepairFeedback (long feedbackID, int repairWay, String repairDisp,
			String repairResults, Date repairTime, int repairQuality, long faultId) {
		this.id = feedbackID;
		this.repairWay = repairWay;
		this.repairDisp = repairDisp;
		this.results = repairResults;
		this.repairTime = repairTime;
		this.quality = repairQuality;
		this.faultId = faultId;
	}
	
	public long getId() {
		return this.id;
	}
	public void setId (long feedbackID) {
		this.id = feedbackID;
	}
	
	public int getRepairWay() {
		return this.repairWay;
	}
	public void setRepairWay(int repairWay) {
		this.repairWay = repairWay;
	}
	
	public String getRepairDisp () {
		return this.repairDisp;
	}
	public void setRepairDisp(String repairDisp) {
		this.repairDisp = repairDisp;
	}
	
	public String getResults() {
		return this.results;
	}
	public void setResults(String repairResults) {
		this.results = repairResults;
	}
	
	public Date getRepairTime() {
		return this.repairTime;
	}
	public void setRepairTime(Date repairTime) {
		this.repairTime = repairTime;
	}
	
	public String getFaultDispatch() {
		return this.faultDispatch;
	}
	public void setFaultDispatch(String faultDispatch) {
		this.faultDispatch = faultDispatch;
	}
	
	public String getBackName() {
		return this.backName;
	}
	public void setBackName(String backName) {
		this.backName = backName;
	}
	
	public String getBackType() {
		return this.backType;
	}
	public void setBackType(String backType) {
		this.backType = backType;
	}
	
	public String getBackSource() {
		return this.backSource;
	}
	public void setBackSource(String backSource){
		this.backSource = backSource;
	}
	
	public int getQuality() {
		return this.quality;
	}
	public void setQuality(int repairQuality) {
		this.quality = repairQuality;
	}
	
	public long getFaultId() {
		return this.faultId;
	}
	public void setFaultId(long faultId)  {
		this.faultId = faultId;
	}
}
