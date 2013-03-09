package com.frss.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.frss.dao.main.UserDAO;
import com.frss.util.DateUtil;

//import servlet.Gnmk;
//import servlet.GnmkDAO;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONTokener;

public class TreeServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static String[] _arrItems = {"id", "name", "url", "urltype", "root", "children", "parent"};
	private ArrayList<String> arrLabel = null;
	private DateUtil dateUtil = new DateUtil();
	private int _userType = -1;
	
	private void initialize() {
		arrLabel = new ArrayList<String>();
		dateUtil = new DateUtil();
		
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// 获取当前登录用户类型
		HttpSession session = request.getSession();
		ArrayList<String> arrUser = (ArrayList<String>)session.getAttribute("loginUser");
		if (arrUser == null) {
			JSONObject json = new JSONObject();
			json.put("ret", 1);
			json.put("msg", "请重新登录！");
			respond(response, json);
			return;
		}
		long userId = Long.parseLong(arrUser.get(0));
		_userType = Integer.parseInt(arrUser.get(3));
		
		System.out.print("TreeServlet.userId="+userId+"\n");
		System.out.print("TreeServlet.userType="+_userType+"\n");
		
		if(userId<0 || _userType<0 || _userType>10) {
			/// log 当前登录用户非法
			JSONObject json = new JSONObject();
			json.put("ret", 2);
			json.put("msg", "当前登录用户非法！");
			respond(response, json);
			return;
		}
		
		String type = request.getParameter("t");

		response.setContentType("text/json; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.write(getSample(type));
	}

	/**
	 * 
	 */
	protected void respond(HttpServletResponse response, JSONObject json) throws ServletException, IOException {
		String str = json.toString();
		System.out.println("TreeServlet get:");
		System.out.println(str);
		response.setContentType("text/json; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.write(str);
	}
	
	private String getSample(String type) {
		System.out.print("TreeServlet.type="+type+"\n");
		if (type == null) {
			return "";
		}
		else if (type.equals("formTree_noProcessed"))
		{
//			return "{identifier:'id',label:'name',items:[" +
//					"{id:101,name:'今天',url:'./QueryDataServlet?state=undone&date=d0',urltype:'griddata',root:true}" +
//					",{id:102,name:'上一周',url:'./QueryDataServlet?state=undone&date=d-7',urltype:'griddata',root:true}" +
//					",{id:103,name:'上个月',url:'./QueryDataServlet?state=undone&date=m-1',urltype:'griddata',root:true}" +
//					",{id:104,name:'2011年',url:'',urltype:'none',root:true,children:[{_reference:105},{_reference:106},{_reference:107},{_reference:108}]}" +
//					",{id:105,name:'第一季度',url:'./QueryDataServlet?state=undone&date=y2011q1',urltype:'griddata',parent:104}" +
//					",{id:106,name:'第二季度',url:'./QueryDataServlet?state=undone&date=y2011q2',urltype:'griddata',parent:104}" +
//					",{id:107,name:'第三季度',url:'./QueryDataServlet?state=undone&date=y2011q3',urltype:'griddata',parent:104}" +
//					",{id:108,name:'第四季度',url:'./QueryDataServlet?state=undone&date=y2011q4',urltype:'griddata',parent:104}" +
//					",{id:109,name:'更早',url:'',urltype:'none',root:true,children:[{_reference:110}]}" +
//					",{id:110,name:'2010年',url:'./QueryDataServlet?state=undone&date=2010y',urltype:'griddata',parent:109}" +
//					"]}";
			return getJsonString(type);
		}
		else if (type.equals("formTree_processed"))
		{
			return getJsonString(type);
		}
		else if (type.equals("faultCommit_manage"))
		{
			return "{ret:0,identifier:'id',label:'name',items:[" +
					"{id:1,name:'表单上报',url:'./PushFault',urltype:'dialog',root:true}" +
					",{id:2,name:'语音上报',url: './PushAudio',urltype:'dialog',root: true}" +
					"]}";
		}
//		else if (type.equals("faultSearch"))
//		{
//			return "{ret:0,identifier:'id',label:'name',items:[" +
//					"{id:1,name:'常用搜索',url:'./Search',urltype:'dialog',root:true}" +
//					",{id:2,name:'高级搜索',url: './Search',urltype:'dialog',root: true}" +
//					"]}";
//		}
		else if (type.equals("user_manage"))
		{
			String str = "{ret:0,identifier:'id',label:'name',items:[" +
					//"{id:1,name:'查看日志',url: './LogServlet',urltype:'dialog',root: true}" +
					"{id:2,name:'修改密码',url:'./ChangePass',urltype:'dialog',root:true}";
			switch (_userType)
			{
			case 0:
			case 1:
			case 2:
			case 4:
			case 7:
				str += ",{id:3,name:'添加用户',url:'./AddUser',urltype:'dialog',root:true}";
				break;
			}
			str += "]}";
			return str;
		}
		else
		{
			return "";
		}
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * @函数名称: getURL
	 * @函数描述: 返回URL字符串
	 * @输入参数: @param servletName，处理的servlet类名，区分是故障或者是备件
	 * @输入参数: @param status，处理状态，0表示未处理，1表示已经处理
	 * @输入参数: @param year，与当前年的间隔，为正
	 * @输入参数: @param month，与当前月份的间隔，为正
	 * @输入参数: @param day，与当前天的时间间隔，为正
	 * @输入参数: @return
	 * @返回类型: String
	 * @throws
	 */
	private String getURL(String servletName, int status, int year, int month, int day) {
		String servlet = null;
		String state = null;
		String timeStart = null;
		String timeEnd = null;
		
		int curYear = dateUtil.getThisYear();			// 年
		int curMonth = dateUtil.getThisMonth();			// 月
		
		// 表单状态
		if(status==0) {		
			state = "undone&";
		} else if(status==1) {
			state = "done&";
		}
		
		if(year==0) {		// 今年
			if(month==0) {	// 本月
				if(day==0) {	// 今天
					dateUtil.getThisDayStart();
					timeStart = dateUtil.getDateSerial(0);
					dateUtil.getThisDayEnd();
					timeEnd = dateUtil.getDateSerial(2);					
				} else if(day==1) {	// 昨天
					dateUtil.getLastDayStart();
					timeStart = dateUtil.getDateSerial(0);
					dateUtil.getLastDayEnd();
					timeEnd = dateUtil.getDateSerial(2);						
				} else if(day==6) {	// 本周
					dateUtil.getThisWeekStart();
					timeStart = dateUtil.getDateSerial(0);
					dateUtil.getThisWeekEnd();
					timeEnd = dateUtil.getDateSerial(2);						
				} else if(day==7) {	// 上周
					dateUtil.getLastWeekStart();
					timeStart = dateUtil.getDateSerial(0);
					dateUtil.getLastWeekEnd();
					timeEnd = dateUtil.getDateSerial(2);						
				} else if(day==30){	// 本月
					dateUtil.getThisMonthStart();
					timeStart = dateUtil.getDateSerial(0);
					dateUtil.getThisMonthEnd();
					timeEnd = dateUtil.getDateSerial(2);						
				}
			} else if(month>0) {	// 前面某月
				dateUtil.getTheMonthStart(curYear, curMonth-month);
				timeStart = dateUtil.getDateSerial(0);
				dateUtil.getTheMonthEnd(curYear, curMonth-month);
				timeEnd = dateUtil.getDateSerial(2);
			}
		} else if(year==1) {	// 去年
			if(month==1) {		// 第一季
				dateUtil.getTheQuarterStart(curYear-1, 1);
				timeStart = dateUtil.getDateSerial(0);
				dateUtil.getTheQuarterEnd(curYear-1, 1);
				timeEnd = dateUtil.getDateSerial(2);
			} else if(month==2) {	// 第二季
				dateUtil.getTheQuarterStart(curYear-1, 2);
				timeStart = dateUtil.getDateSerial(0);
				dateUtil.getTheQuarterEnd(curYear-1, 2);
				timeEnd = dateUtil.getDateSerial(2);
			} else if(month==3) {	// 第三季
				dateUtil.getTheQuarterStart(curYear-1, 3);
				timeStart = dateUtil.getDateSerial(0);
				dateUtil.getTheQuarterEnd(curYear-1, 3);
				timeEnd = dateUtil.getDateSerial(2);				
			} else if(month==4) {	// 第四季
				dateUtil.getTheQuarterStart(curYear-1, 4);
				timeStart = dateUtil.getDateSerial(0);
				dateUtil.getTheQuarterEnd(curYear-1, 4);
				timeEnd = dateUtil.getDateSerial(2);					
			}			
		} else if(year>1) {		// 前几年
			dateUtil.getTheYearStart(curYear-year);
			timeStart = dateUtil.getDateSerial(0);
			dateUtil.getTheYearEnd(curYear-year);
			timeEnd = dateUtil.getDateSerial(2);
		}
		
		
		String url =  "./" + servletName + "?state=" + state + "starttime=" + timeStart + 
				"&"	+ "endtime=" + timeEnd;
		
		return url;
	}

	private JSONObject getParams(int status, int year, int month, int day) {
		String state = null;
		String timeStart = null;
		String timeEnd = null;
		
		int curYear = dateUtil.getThisYear();			// 年
		int curMonth = dateUtil.getThisMonth();			// 月
		
		// 表单状态
		if(status==0) {		
			state = "undone";
		} else if(status==1) {
			state = "done";
		}
		
		if(year==0) {		// 今年
			if(month==0) {	// 本月
				if(day==0) {	// 今天
					dateUtil.getThisDayStart();
					timeStart = dateUtil.getDateSerial(0);
					dateUtil.getThisDayEnd();
					timeEnd = dateUtil.getDateSerial(2);					
				} else if(day==1) {	// 昨天
					dateUtil.getLastDayStart();
					timeStart = dateUtil.getDateSerial(0);
					dateUtil.getLastDayEnd();
					timeEnd = dateUtil.getDateSerial(2);						
				} else if(day==6) {	// 本周
					dateUtil.getThisWeekStart();
					timeStart = dateUtil.getDateSerial(0);
					dateUtil.getThisWeekEnd();
					timeEnd = dateUtil.getDateSerial(2);						
				} else if(day==7) {	// 上周
					dateUtil.getLastWeekStart();
					timeStart = dateUtil.getDateSerial(0);
					dateUtil.getLastWeekEnd();
					timeEnd = dateUtil.getDateSerial(2);						
				} else if(day==30){	// 本月
					dateUtil.getThisMonthStart();
					timeStart = dateUtil.getDateSerial(0);
					dateUtil.getThisMonthEnd();
					timeEnd = dateUtil.getDateSerial(2);						
				}
			} else if(month>0) {	// 前面某月
				dateUtil.getTheMonthStart(curYear, month);
				timeStart = dateUtil.getDateSerial(0);
				dateUtil.getTheMonthEnd(curYear, month);
				timeEnd = dateUtil.getDateSerial(2);
			}
		} else if(year==1) {	// 去年
			if(month==1) {		// 第一季
				dateUtil.getTheQuarterStart(curYear-1, 1);
				timeStart = dateUtil.getDateSerial(0);
				dateUtil.getTheQuarterEnd(curYear-1, 1);
				timeEnd = dateUtil.getDateSerial(2);
			} else if(month==2) {	// 第二季
				dateUtil.getTheQuarterStart(curYear-1, 2);
				timeStart = dateUtil.getDateSerial(0);
				dateUtil.getTheQuarterEnd(curYear-1, 2);
				timeEnd = dateUtil.getDateSerial(2);
			} else if(month==3) {	// 第三季
				dateUtil.getTheQuarterStart(curYear-1, 3);
				timeStart = dateUtil.getDateSerial(0);
				dateUtil.getTheQuarterEnd(curYear-1, 3);
				timeEnd = dateUtil.getDateSerial(2);				
			} else if(month==4) {	// 第四季
				dateUtil.getTheQuarterStart(curYear-1, 4);
				timeStart = dateUtil.getDateSerial(0);
				dateUtil.getTheQuarterEnd(curYear-1, 4);
				timeEnd = dateUtil.getDateSerial(2);					
			}			
		} else if(year>1) {		// 前几年
			dateUtil.getTheYearStart(year);
			timeStart = dateUtil.getDateSerial(0);
			dateUtil.getTheYearEnd(year);
			timeEnd = dateUtil.getDateSerial(2);
		}		
		
//		JSONArray ja = new JSONArray();
		JSONObject jo = new JSONObject();
		jo.put("state", state);
		jo.put("starttime", timeStart);
		jo.put("endtime", timeEnd);
		
		return jo;
	}
	
	/**
	 * @函数名称: getJsonString
	 * @函数描述: 根据标识字符串返回JSON
	 * @输入参数: @param itemName
	 * @输入参数: @return
	 * @返回类型: String
	 * @throws
	 */
	private String getJsonString(String itemName) {
		JSONObject jo = new JSONObject();
		jo.put("identifier", "id");
		jo.put("label", "name");
		
		if(itemName==null)
			return null;
		
		int id = 0;
		String label = null;
		String servlet = null;
		int status = 0;
		int thisYear = dateUtil.getThisYear();				// 当前年
		int thisMonth = dateUtil.getThisMonth();			// 当前月
		int thisDay = dateUtil.getThisDay();				// 当前天
		int dayOfWeek = dateUtil.getThisDayofWeek();		// 当前星期
		int year = 0;	int month = 0;	int day = 0;
		
		JSONArray ja = new JSONArray();
		if(itemName.equals("formTree_noProcessed")) {		// 未处理的故障单
			servlet = "QueryFaultServlet";
			status = 0;			
			id = 100;
		} else if(itemName.equals("formTree_processed")) {	// 已经处理的故障单
			servlet = "QueryFaultServlet";
			status = 1;			
			id = 200;
		}
			
		JSONObject oneJO = new JSONObject();
		JSONArray joChildren = new JSONArray();
		
		// 当天[x00-x09]
		oneJO.put("id", id);
		oneJO.put("name", "今天");
		year = month = day = 0;
		oneJO.put("url", "./"+servlet);
		oneJO.put("params", getParams(status, year, month, day));
		oneJO.put("urltype", "griddata");
		oneJO.put("root", true);
		ja.add(oneJO);
		
		// 昨天
		oneJO.clear();
		oneJO.put("id", id+1);
		oneJO.put("name", "昨天");
		year = month = 0;
		day = 1;
		oneJO.put("url", "./"+servlet);
		oneJO.put("params", getParams(status, year, month, day));
		oneJO.put("urltype", "griddata");
		oneJO.put("root", true);
		ja.add(oneJO);
		
		// 本周
		if(dayOfWeek>3) {
			oneJO.clear();
			oneJO.put("id", id+2);
			oneJO.put("name", "本周");
			year = month = 0;
			day = 6;
			oneJO.put("url", "./"+servlet);
			oneJO.put("params", getParams(status, year, month, day));
			oneJO.put("urltype", "griddata");
			oneJO.put("root", true);
			ja.add(oneJO);		
		}
		
		// 上周
		oneJO.clear();
		oneJO.put("id", id+3);
		oneJO.put("name", "上周");
		year = month = 0;
		day = 7;
		oneJO.put("url", "./"+servlet);
		oneJO.put("params", getParams(status, year, month, day));
		oneJO.put("urltype", "griddata");
		oneJO.put("root", true);
		ja.add(oneJO);	
		
		// 本月
		oneJO.clear();
		oneJO.put("id", id+4);
		oneJO.put("name", "本月");
		year = month = 0;
		day = 30;
		oneJO.put("url", "./"+servlet);
		oneJO.put("params", getParams(status, year, month, day));
		oneJO.put("urltype", "griddata");
		oneJO.put("root", true);
		ja.add(oneJO);		
		
		// 前几个月[x10-x29]
		if(thisMonth>1)	{
			id = id + 10;
			oneJO.clear();
			oneJO.put("id", id);
			oneJO.put("name", "前几个月");
			oneJO.put("url", "");			
			oneJO.put("urltype", "none");
			oneJO.put("root", true);
			
			joChildren.clear();
			for(int i=thisMonth-1;i>0;i--){
				JSONObject joChild = new JSONObject();
				joChild.put("_reference", id+i);
				joChildren.add(joChild);
			}
			oneJO.put("children", joChildren);
			ja.add(oneJO);		
			
			year = day = 0;
			for(int i=thisMonth-1;i>0;i--) {
				oneJO.clear();
				oneJO.put("id", id+i);
				oneJO.put("name", i+"月");					
				oneJO.put("url", "./"+servlet);
				oneJO.put("params", getParams(status, year, i, day));		
				oneJO.put("urltype", "griddata");
				oneJO.put("parent", id);
				ja.add(oneJO);
			}
		}
		
		// 去年[x30-x39]
		id = id + 20;
		oneJO.clear();
		oneJO.put("id", id);
		oneJO.put("name", (thisYear-1)+"年");
		oneJO.put("url", "");			
		oneJO.put("urltype", "none");
		oneJO.put("root", true);
		joChildren.clear();
		for(int i=1;i<5;i++) {
			JSONObject joChild = new JSONObject();
			joChild.put("_reference", id+i);
			joChildren.add(joChild);			
		}
		oneJO.put("children", joChildren);
		ja.add(oneJO);	
		year = 1;
		day = 0;
		for(int i=1; i<5; i++) {
			oneJO.clear();
			oneJO.put("id", id+i);
			oneJO.put("name", "第"+i+"季度");					
			oneJO.put("url", "./"+servlet);
			oneJO.put("params", getParams(status, year, i, day));
			oneJO.put("urltype", "griddata");
			oneJO.put("parent", id);
			ja.add(oneJO);				
		}
		
		// 更早[x40-x50]
		id = id + 10;
		oneJO.clear();
		oneJO.put("id", id);
		oneJO.put("name", "更早");
		oneJO.put("url", "");			
		oneJO.put("urltype", "none");
		oneJO.put("root", true);
		joChildren.clear();
		for(int i=1;i<3;i++) {
			JSONObject joChild = new JSONObject();
			joChild.put("_reference", id+i);
			joChildren.add(joChild);			
		}
		oneJO.put("children", joChildren);
		ja.add(oneJO);	
		for(int i=1;i<3;i++) {
			year = thisYear-1-i;
			month = day = 0;
			oneJO.clear();
			oneJO.put("id", id+i);
			oneJO.put("name", year+"年");
			oneJO.put("url", "./"+servlet);
			oneJO.put("params", getParams(status, year, month, day));
			oneJO.put("urltype", "griddata");
			oneJO.put("parent", id);
			ja.add(oneJO);
		}			

		// 未处理离线语音上报 [zuow, 2012/05/03]
		if(_userType==UserDAO.Formater) {
			id = id + 10;
			servlet = "QueryAudioServlet";
			oneJO.clear();
			oneJO.put("id", id);
			oneJO.put("name", "离线语音");
			oneJO.put("url", "./"+servlet);
			JSONObject jso = new JSONObject();
			jso.put("status", status);
			oneJO.put("params", jso);
			oneJO.put("urltype", "gridaudio");
			oneJO.put("root", true);
			ja.add(oneJO);
		}

		jo.put("items", ja);
		jo.put("ret", 0);
		
//		System.out.print(jo.toString());
		
		return jo.toString();
	}
}
