package com.frss.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.frss.dao.main.ApprovalDAO;
import com.frss.dao.main.UserDAO;

public class QueryYearsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String[] _arrStr = {"department"};
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QueryYearsServlet() {
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
		
		// 获取当前登录用户下级类型
		int subType= 0;
		if(userType==UserDAO.Formater || userType==UserDAO.Distributor)
			subType = UserDAO.Military;
		else if(userType==UserDAO.Military)
			subType = UserDAO.GroupArmy;
		else if(userType==UserDAO.GroupArmy || userType==UserDAO.Regiment)
			subType = UserDAO.Regiment;
		
		ApprovalDAO appDAO = new ApprovalDAO();
		ArrayList<Integer> arrYear = appDAO.queryYear(subType);
		if(arrYear==null) {
			JSONObject json = new JSONObject();
			json.put("ret", 3);
			json.put("msg", "查询故障单上报年份信息或者审核年份信息失败！");
			respond(response, json);
			return;			
		}
		
		// json对象
		JSONObject jo = new JSONObject();
		JSONArray ja = new JSONArray();
		
		if(arrYear!=null && arrYear.size()>0) {
			for( Integer year : arrYear) {
				if(year==null || year<1000)
					continue;
				
				JSONObject jso = new JSONObject();
				jso.put(_arrStr[0], year.toString());
				ja.add(jso);
			}
		}
		
		jo.put("items", ja);
		jo.put("ret", 0);
		String str = jo.toString();
		System.out.println("QueryYearsServlet get:");
		System.out.println(str);
		response.setContentType("text/json; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.write(str);
	}

	protected void respond(HttpServletResponse response, JSONObject json) throws ServletException, IOException {
		String str = json.toString();
		System.out.println("QueryYearsServlet get:");
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
