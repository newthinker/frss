package com.frss.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONTokener;

/**
 * Servlet implementation class QueryDataServlet
 */
//@WebServlet("/QueryDataServlet")
public class QueryDataServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String[] _arrStr = {"zbstate", "zbtype", "zbname", "zbid", "zbnum" ,"gzxx", "gzsj", 
		"zbxqcl", "gzpc", "gzbw", "zbsydw", "syr", "jlr", "llfs", "bxsj", "qzzp"};

    /**
     * @see HttpServlet#HttpServlet()
     */
    public QueryDataServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// json对象
		JSONObject jo = new JSONObject();
		jo.put("identifier", _arrStr[2]);
		jo.put("label", _arrStr[1]);
		JSONArray ja = new JSONArray();
		
		// 排序
		String value = request.getParameter("sort");
		if (value != null)
		{
			
		}
		
		// 起始
		value = request.getParameter("start");
		{
			
		}
		
		// 总数
		value = request.getParameter("count");
		{
			
		}
		
		// 进行统计输出
		JSONObject jso = new JSONObject();		//json对象
		// 状态
		value = request.getParameter("state");
		if (value==null || value.equals("")) {		// 非法输入
			// 
		}
		else if (value.equals("undone")) {		// 未处理

		}
		else if (value.equals("done")) {		// 已经处理

		}
		else {

		}
		
		jo.put("items", ja);
		String str = jo.toString();
		System.out.println("QueryDataServlet get:");
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
