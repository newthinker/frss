package com.frss.model.main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.frss.util.DateUtil;
import com.frss.util.FrssException;

public class RepairFeedbackForm {
	private long faultId;			// 装备故障维修信息单号
	private long feedbackId;		// 装备故障维修信息反馈单号
	private String equipType;		// 装备型号
	private String equipName;		// 装备名称
	private Date faultTime;			// 故障发生时间
	private String faultDispatch;	// 故障派修(暂不清楚字段信息)
	private String repairDisp;		// 维修内容
	private String cause;			// 故障原因
	private int repairWay;			// 维修手段
	private String results;			// 维修结果
	private int quality;			// 维修质量
	private Date repairTime;		// 维修时间
	private String backName;		// 维修使用备件名称
	private String backType;		// 维修使用备件型号
	private String backSource;		// 备件来源情况(暂不清楚字段信息)
	private int phoneBack;			// 是否电话回访，0表示没有--待确认，1表示已经电话回访
	
	private int status;			// 表单状态, [0~2] 
	private Date checkTime;		// 表单确认状态

	SimpleDateFormat simFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");	
	
	public RepairFeedbackForm() {
		
	}
	
	public long getFaultId() {
		return this.faultId;
	}
	public void setFaultId(long faultId) {
		this.faultId = faultId;
	}

	public long getFeedbackId() {
		return this.feedbackId;
	}
	public void setFeedbackId(long feedbackId) {
		this.feedbackId = feedbackId;
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
	
	public Date getFaultTime() {
		return this.faultTime;
	}
	public void setFaultTime(Date faultTime) {
		this.faultTime = faultTime;
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
	
	public String getFaultCause() {
		return this.cause;
	}
	public void setFaultCause(String cause) {
		this.cause = cause;
	}
	
	public int getRepairWay() {
		return this.repairWay;
	}
	public void setRepairWay(int repairWay) {
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
	public void setBackSource(String backSource) {
		this.backSource = backSource;
	}
	
	public int getStatus() {
		return this.status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	public Date getCheckTime() throws FrssException {
		Date curDate = null;
		try {
			curDate = simFormat.parse(simFormat.format(new Date(System.currentTimeMillis())));	//获取当前时间
		} catch (Exception e) {
			/// log
			throw new FrssException(e);
		}
		
		return curDate;
	}
	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}	
	
	public int getPhoneBack() {
		return this.phoneBack;
	}
	public void setPhoneBack(int phoneBack) {
		this.phoneBack = phoneBack;
	}

	public void Initialize(Map<String, String> mapFeedback) throws FrssException {
		try {
			DateUtil dateUtil = new DateUtil();
			
			if(mapFeedback.get("orderid")!=null)
				this.faultId = Long.parseLong(mapFeedback.get("orderid").toString());
			if(mapFeedback.get("zbtype")!=null)
				this.equipType = mapFeedback.get("zbtype").toString();
			if(mapFeedback.get("zbname")!=null)
				this.equipName = mapFeedback.get("zbname").toString();
			if(mapFeedback.get("zbgzsj")!=null) 				
				this.faultTime = dateUtil.getTheTime(mapFeedback.get("zbgzsj").toString());
			if(mapFeedback.get("fbdispatch")!=null)
				this.faultDispatch = mapFeedback.get("fbdispatch").toString();
			if(mapFeedback.get("fbrpcontent")!=null)
				this.repairDisp = mapFeedback.get("fbrpcontent").toString();
			if(mapFeedback.get("fbgzreason")!=null)
				this.cause = mapFeedback.get("fbgzreason").toString();
			if(mapFeedback.get("fbrpway")!=null)
				this.repairWay = Integer.parseInt(mapFeedback.get("fbrpway").toString());
			if(mapFeedback.get("fbrpresult")!=null)
				this.results = mapFeedback.get("fbrpresult").toString();
			if(mapFeedback.get("fbrpquality")!=null)
				this.quality = Integer.parseInt(mapFeedback.get("fbrpquality").toString());
			if(mapFeedback.get("fbrptime")!=null) {
				// 首先获取当前时间
				String strDate = mapFeedback.get("fbrptime").toString();
				if(strDate.indexOf("-")>=0)
					strDate = strDate.replaceAll("-", "");
				if(strDate.indexOf(":")>=0)
					strDate = strDate.replaceAll(":", "");
				if(strDate.length()<14) {
					strDate = strDate.substring(0, 8);
					strDate += "000000";
				}
				
				this.repairTime = dateUtil.getDateFromSerial(strDate);
			}
			if(mapFeedback.get("fbrpbjname")!=null)
				this.backName = mapFeedback.get("fbrpbjname").toString();
			if(mapFeedback.get("fbrpbjtype")!=null)
				this.backType = mapFeedback.get("fbrpbjtype").toString();
			if(mapFeedback.get("fbrpbjfrom")!=null)
				this.backSource = mapFeedback.get("fbrpbjfrom").toString();
		} catch (Exception e) {
			/// log
			throw new FrssException(e);
		}
	}
}
