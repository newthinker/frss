package com.frss.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.frss.bean.StatManagerBean;
import com.frss.util.DateUtil;
import com.frss.util.ValidCheck;

public class SearchFaultServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static String[] _arrStr = {
		"index",		// 故障单序号
		"orderid", 		//单号 --唯一id
		"step", 		//当前进行到哪一步了，提交-L1-L2-任务分发-维修处理-完成
		"state", 		//状态，0中止 1正在进行
		
		"zbtype", 		//装备型号
		"zbname", 		//装备名称
		"zbnum" , 		//装备数量
		"zbuser", 		//使用者
		"commiter", 	//提交者
		"committime", 	//提交时间
	};
	int fieldNum = _arrStr.length;	
   /*
    * @see HttpServlet#HttpServlet()
    */
   public SearchFaultServlet() {
       super();
       // TODO Auto-generated constructor stub
   }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// json对象
		JSONObject jo = new JSONObject();
		jo.put("identifier", "orderid");
		jo.put("label", "zbname");
		JSONArray ja = new JSONArray();
			
		// 进行统计输出		
		int status = 0;					// 0表示未处理，1表示已经处理
		Date startTime = null;			// 开始时间
		Date endTime = null;			// 结束时间
		int userType = 0;				// 当前登录用户类型

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
		userType = Integer.parseInt(arrUser.get(3));
		if(userId<0 || userType<0 || userType>10) {
			/// log 当前登录用户非法
			JSONObject json = new JSONObject();
			json.put("ret", 2);
			json.put("msg", "当前登录用户非法！");
			respond(response, json);
			return;
		}
				
		// 开始和结束时间
		DateUtil dateUtil = new DateUtil();
		String value = request.getParameter("datebegin");
		if(value==null||value.equals("")){	// 非法
			/// 设置成默认--当天
			startTime = dateUtil.getThisDayStart();
			startTime = dateUtil.getStartTime(startTime);
		} else {	// 将时间序列转换成date
			if(value.indexOf("-")>=0)
				value = value.replaceAll("-", "");
			value += "000000";
			startTime = dateUtil.getDateFromSerial(value);
		}
		value = request.getParameter("dateend");
		if(value==null || value.equals("")) {
			endTime = dateUtil.getThisDayEnd();
			endTime = dateUtil.getEndTime(endTime);
		} else {
			if(value.indexOf("-")>=0)
				value = value.replaceAll("-", "");
			value += "235959";
			endTime = dateUtil.getDateFromSerial(value);
		}
			
		// 获取装备信息
		long  faultId = 0;				// 故障单id查询
		String keyWord = null;			// 关键字查询--故障现象
		String equipType = null;
		String equipName = null;
		String equipNumber = null;
		String reporter = null; 
		String department = null;
		
		value = request.getParameter("faultId");
		if(value!=null&&!value.equals(""))
			faultId = Long.parseLong(value);
		value = request.getParameter("keyword");
		if(value!=null) {
			if(!(ValidCheck.validCheck(value))) {
				JSONObject json = new JSONObject();
				json.put("ret", 3);
				json.put("msg", "关键字输入非法！");
				respond(response, json);
				return;
			}			
			keyWord = new String(request.getParameter("keyword").getBytes("iso8859-1"), "UTF-8");
		}
		
		value = request.getParameter("equip_type");
		if(value!=null) {
			if(!(ValidCheck.validCheck(value))) {
				JSONObject json = new JSONObject();
				json.put("ret", 3);
				json.put("msg", "装备类型输入非法！");
				respond(response, json);
				return;
			}
			equipType = new String(request.getParameter("equip_type").getBytes("iso8859-1"), "UTF-8");
		}
		
		
		value = request.getParameter("equip_name");
		if(value!=null) {
			if(!(ValidCheck.validCheck(value))) {
				JSONObject json = new JSONObject();
				json.put("ret", 3);
				json.put("msg", "装备名称输入非法！");
				respond(response, json);
				return;
			}
			equipName = new String(request.getParameter("equip_name").getBytes("iso8859-1"), "UTF-8");
		}
		
		value = request.getParameter("equip_id");
		if(value!=null) {
			if(!(ValidCheck.validCheck(value))) {
				JSONObject json = new JSONObject();
				json.put("ret", 3);
				json.put("msg", "装备编号输入非法！");
				respond(response, json);
				return;
			}
			equipNumber = new String(request.getParameter("equip_id").getBytes("iso8859-1"), "UTF-8");
		}
		
		value = request.getParameter("reporter");
		if(value!=null) {
			if(!(ValidCheck.validCheck(value))) {
				JSONObject json = new JSONObject();
				json.put("ret", 3);
				json.put("msg", "上报人名称输入非法！");
				respond(response, json);
				return;
			}
			reporter = new String(request.getParameter("reporter").getBytes("iso8859-1"), "UTF-8");
		}
		
		value = request.getParameter("department");
		if(value!=null) {
			if(!(ValidCheck.validCheck(value))) {
				JSONObject json = new JSONObject();
				json.put("ret", 3);
				json.put("msg", "部门名称输入非法！");
				respond(response, json);
				return;
			}
			department = new String(request.getParameter("department").getBytes("iso8859-1"), "UTF-8");
		}
		
		// 格式检查
		if(faultId!=0 && keyWord!=null && startTime==null && endTime==null && equipType==null && equipName==null && equipNumber==null && reporter==null && department==null) {
			JSONObject json = new JSONObject();
			json.put("ret", 3);
			json.put("msg", "输入参数格式有问题！");
			respond(response, json);
			return;
		}
		
		// StatManagerBean进行初始化和搜索
		StatManagerBean statBean = new  StatManagerBean();
		
		if(faultId>0) {		// 加上故障单编号查询 [zuow,2012/05/04]
			statBean.searchFault(faultId);
		} else if(keyWord!=null) {		// 关键字查询 [zuow, 2012/05/04]
			statBean.searchFault(keyWord);
		} else {
			statBean.searchFault(startTime, endTime, equipType, equipName, equipNumber, reporter, department, userType);
		}
		/// 将统计结果进行处理并输出
		// 输出故障现象
		ArrayList<ArrayList<String>> arrResults = statBean.getFormatRestult();
		if(arrResults!=null) {
			int id = 1;		// 查询记录序号
			Iterator itResult = arrResults.iterator();
			while(itResult.hasNext()) {
				ArrayList arrResult = (ArrayList) itResult.next();
				
				JSONObject jso = new JSONObject();
				
				Iterator itKey = arrResult.iterator();				
				int num = 1;
				
				jso.put(_arrStr[0], id);
				
				while(itKey.hasNext() && num<fieldNum) {
					String outValue = (String) itKey.next();
					jso.put(_arrStr[num], outValue);
					num++;
				}
				
				ja.add(jso);
				id++;
			}
		}
		
		jo.put("items", ja);
		jo.put("ret", 0);		// 返回码，0表示正常，!0表示有问题，就必须带上错误消息
		String str = jo.toString();
		System.out.println("SearchFaultServlet get:");
		System.out.println(str);
		response.setContentType("text/json; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.write(str);
	}
	
	/**
	 * 
	 */
	protected void respond(HttpServletResponse response, JSONObject json) throws ServletException, IOException {
		String str = json.toString();
		System.out.println("SearchFaultServlet get:");
		System.out.println(str);
		response.setContentType("text/json; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.write(str);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
}
