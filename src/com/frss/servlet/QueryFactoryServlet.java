package com.frss.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.frss.bean.FactoryInfoManagerBean;
import com.frss.dao.main.UserDAO;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONTokener;

/**
 * Servlet implementation class QueryDataServlet
 */
//@WebServlet("/QueryFactoryServlet")
public class QueryFactoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String[] _arrStr = {"id", "name"};

    /**
     * @see HttpServlet#HttpServlet()
     */
    public QueryFactoryServlet() {
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
		if(userId<0 || userType!=UserDAO.Distributor) {
			/// log 当前登录用户非法
			JSONObject json = new JSONObject();
			json.put("ret", 2);
			json.put("msg", "当前登录用户非法！");
			respond(response, json);
			return;
		}
		
		// 状态
		long faultId = 0;
		String value = request.getParameter("faultid");
		if(value==null || value=="") {
			faultId = 0;
		}
		faultId = Long.parseLong(value);
		if(faultId<=0)
			faultId = 0;
		
		// json对象
		JSONObject jo = new JSONObject();
		JSONArray ja = new JSONArray();
		
		// 查找工业部门信息，然后进行故障自动分发
		FactoryInfoManagerBean facManager = new FactoryInfoManagerBean();
		facManager.queryFactoryInfo(faultId);
		LinkedHashMap<Long, String> mapFactory = facManager.getFactorys();
		if(mapFactory!=null && mapFactory.size()>0) {
			Iterator itFac = mapFactory.entrySet().iterator();
			while (itFac.hasNext()) {
				Map.Entry<Long, String> mapEntry = (Map.Entry<Long, String>)itFac.next();
				long id = mapEntry.getKey();
				String name = mapEntry.getValue();
				JSONObject jso = new JSONObject();
				jso.put(_arrStr[0], id);
				jso.put(_arrStr[1], name);
				ja.add(jso);
			}
		}

		jo.put("items", ja);
		jo.put("ret", 0);
		String str = jo.toString();
		System.out.println("QueryFactoryServlet get:");
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
		System.out.println("QueryFactoryServlet get:");
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
