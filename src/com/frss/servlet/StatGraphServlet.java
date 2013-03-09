package com.frss.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.frss.bean.StatManagerBean;
import com.frss.dao.main.UserDAO;
import com.frss.model.main.UserInfo;
import com.frss.util.ValidCheck;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class StatGraphServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	// dateFlag：日期类别，1～4分别表示年度、半年度、季度和月
	// year：年份YYYY
	// departName：部门名称
	// equipTag：输入的装备标识，1～3分别表示装备类型、名称和编号
	// equipInfo；对应上面的equipTag，分别表示装备类型名、名称和编号
	private static String[] _arrStr = {"id", "text", "num"};
	
	/*
	 * @see HttpServlet#HttpServlet()
	 */
	public StatGraphServlet() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
		int userType = Integer.parseInt(arrUser.get(3));
		if(userId<0 || userType<UserDAO.Super || userType>UserDAO.Formater || userType==UserDAO.Factory || userType==UserDAO.Expert) {
			/// log 当前登录用户非法
			JSONObject json = new JSONObject();
			json.put("ret", 2);
			json.put("msg", "当前登录用户非法！");
			respond(response, json);
			return;
		}
		
		// 获取用户输入参数
		String value = request.getParameter("dateFlag");
		int dateFlag = Integer.parseInt(value);
		value = request.getParameter("year");
		int year = Integer.parseInt(value);
		value = request.getParameter("departName");
		String departName = null;
		if(value!=null)
			departName = new String(request.getParameter("departName").getBytes("iso8859-1"), "UTF-8");
		if(departName==null || departName.equals("")) {	// 如果默认没有输入或者输入的参数为空，就设置为当前用户所在的部门 [zuow,2012/05/16]
			UserDAO userDAO = new UserDAO();
			UserInfo userInfo = userDAO.queryUser(userId);
			departName = userInfo.getDepartment();
			int level = 0;
			switch(userType) {
			case UserDAO.Formater:
				level = 4;
				break;
			case UserDAO.Military:
				level = 3;
				break;
			case UserDAO.GroupArmy:
				level = 2;
				break;
			case UserDAO.Regiment:
				level = 1;
				break;
			}
			
			departName = Integer.toString(level) + "-" + departName;
		}
		
		value = request.getParameter("equipTag");
		int equipTag = Integer.parseInt(value);
		String equipInfo = null;
		value = request.getParameter("equipInfo");
		if(value!=null && !(value.equals(""))) {
			if(!(ValidCheck.validCheck(value))) {
				JSONObject json = new JSONObject();
				json.put("ret", 3);
				json.put("msg", "装备信息输入非法！");
				respond(response, json);
				return;
			}
			equipInfo = new String(request.getParameter("equipInfo").getBytes("iso8859-1"), "UTF-8");
		}
		
		if(equipInfo==null || equipInfo.equals(""))		// 如果没有输入装备信息，标识置为0 [zuow, 2012/04/18]
			equipTag = 0;
		
		// 获取故障单处理状态 [zuow, 2012/05/13]
		value = request.getParameter("state");
		int state = Integer.parseInt(value);
		if(state!=0 && state!=1 && state!=10 && state!=11) {
			JSONObject json = new JSONObject();
			json.put("ret", 4);
			json.put("msg", "获取故障单出来状态非法！");
			respond(response, json);
			return;
		}
		
		// 进行逻辑判断，多部门统计和多状态统计只能取其一 [zuow, 2012/0517]
		int num = departName.length() - departName.replaceAll(";", "").length();		// 获取部门个数(;个数)
		if(state==11 && num>1) {
			JSONObject json = new JSONObject();
			json.put("ret", 5);
			json.put("msg", "不能同时进行多部门统计或者多状态统计！");
			respond(response, json);
			return;			
		}
		
		// 返回对象
		JSONObject jo = new JSONObject();
		jo.put("identifier", "datePeriod");
		jo.put("idAttribute", "datePeriod");
		jo.put("label", "datePeriod");
		
		JSONArray ja = new JSONArray();
		
		// 根据用户输入信息进行统计
		StatManagerBean statManager = new StatManagerBean();
//		LinkedHashMap<String, Integer> mapStat = statManager.statFault(dateFlag, year, departName, equipTag, equipInfo, userType);
//		if(mapStat!=null&&mapStat.size()>0) {
//			int num = 1;
//			Iterator itKey = mapStat.entrySet().iterator();
//			while(itKey.hasNext()) {
//				Map.Entry<String, Integer> entryMap = (Map.Entry<String, Integer>)itKey.next();
//				String key = entryMap.getKey();
//				Integer amount = entryMap.getValue();
//				
//				JSONObject jso = new JSONObject();
//				jso.put(_arrStr[0], num);
//				jso.put(_arrStr[1], key);
//				jso.put(_arrStr[2], amount.toString());
//				
//				ja.add(jso);
//				num++;
//			}
//		} 
		
		JSONArray jaStat = new JSONArray();
		JSONArray jaDepart = new JSONArray();
		int index = 0;		// 计数值标识 [zuow,2012/05/16]
		LinkedHashMap<String, LinkedHashMap<String, Integer>> mapStat = statManager.statFault(dateFlag, year, departName, equipTag, equipInfo, userType, state);
		if(mapStat!=null && mapStat.size()>0) {
			
			Iterator itKey = mapStat.entrySet().iterator();
			while(itKey.hasNext()) {
				JSONObject joStat1 = new JSONObject();
				
				Map.Entry<String, Map<String, Integer>> entryMap1 = (Map.Entry<String, Map<String,Integer>>)itKey.next();
				String datePeriod = entryMap1.getKey();
				LinkedHashMap<String, Integer> mapAmount = (LinkedHashMap<String, Integer>) entryMap1.getValue();
				
				joStat1.put("datePeriod", datePeriod);
				
				Iterator itStat = mapAmount.entrySet().iterator();
				while(itStat.hasNext()) {					
					Map.Entry<String, Integer> entryMap2 = (Map.Entry<String, Integer>)itStat.next();
					String department = entryMap2.getKey();
					int amount = entryMap2.getValue();
					
					joStat1.put(department, amount);
					
					if(index==0)	// 部门名称只添加一次 [zuow,2012/05/16]
						jaDepart.add(department);
				}
								
				jaStat.add(joStat1);
				
				index = 1;
			}			
		}
		
		jo.put("chars", jaDepart);
		jo.put("items", jaStat);
		jo.put("ret", 0);
		String str = jo.toString();
		System.out.println("StatGraphServlet get:");
		System.out.println(str);
		response.setContentType("text/json; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.write(str);
	}
	
	protected void respond(HttpServletResponse response, JSONObject json) throws ServletException, IOException {
		String str = json.toString();
		System.out.println("StatGraphServlet get:");
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
