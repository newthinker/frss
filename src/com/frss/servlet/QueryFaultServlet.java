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

import com.frss.bean.StatManagerBean;
import com.frss.dao.main.UserDAO;
import com.frss.model.main.UserInfo;
import com.frss.util.DateUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class QueryFaultServlet extends HttpServlet {
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
	 

    /**
     * @see HttpServlet#HttpServlet()
     */
    public QueryFaultServlet() {
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
		
		// 获取输入参数
		String value = null;

		// 进行统计输出		
		int status = 0;					// 0表示未处理，1表示已经处理
		Date startTime = null;			// 开始时间
		Date endTime = null;			// 结束时间
		int userType = 0;				// 当前登录用户类型
		String userName = null;			// 当前登录用户名

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
		userName = arrUser.get(1);
		userType = Integer.parseInt(arrUser.get(3));
		if(userId<0 || userType<0 || userType>10 || userName==null || userName.equals("")) {
			/// log 当前登录用户非法
			JSONObject json = new JSONObject();
			json.put("ret", 2);
			json.put("msg", "当前登录用户非法！");
			respond(response, json);
			return;
		}
		
		// 状态
		value = request.getParameter("state");
		if (value==null || value.equals("")) {		// 非法输入
			/// 设置成默认
			status = 0;
		}
		else if (value.equals("undone")) {		// 未处理
			status = 0;
		}
		else if (value.equals("done")) {		// 已经处理
			status = 1;
		}
		
		// 开始和结束时间
		DateUtil dateUtil = new DateUtil();
		value = request.getParameter("starttime");
		if(value==null||value.equals("")){	// 非法
			/// 设置成默认--当天
			startTime = dateUtil.getThisDayStart();
			startTime = dateUtil.getStartTime(startTime);
		} else {	// 将时间序列转换成date
			startTime = dateUtil.getDateFromSerial(value);
		}
		value = request.getParameter("endtime");
		if(value==null || value.equals("")) {
			endTime = dateUtil.getThisDayEnd();
			endTime = dateUtil.getEndTime(endTime);
		} else {
			endTime = dateUtil.getDateFromSerial(value);
		}
				
		// StatManagerBean进行初始化和统计
		StatManagerBean statBean = new  StatManagerBean();
		statBean.queryFault(startTime, endTime, userType, userName, status);

		/// 将统计结果进行处理并输出
		// 输出故障现象
		JSONArray ja = new JSONArray();
		ArrayList<ArrayList<String>> arrResults = statBean.getFormatRestult();
		if(arrResults!=null) {
			
			int id = 1;		// 查询的记录序号
			Iterator itResult = arrResults.iterator();
			while(itResult.hasNext()) {
				ArrayList arrResult = (ArrayList) itResult.next();
				
				JSONObject jso = new JSONObject();
				
				Iterator itKey = arrResult.iterator();
				
				// 首先插入序号
				jso.put(_arrStr[0], id);
				
				int num = 1;
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
		System.out.println("QueryFaultServlet get:");
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
		System.out.println("QueryFaultServlet get:");
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
