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

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.frss.bean.FactoryInfoManagerBean;
import com.frss.bean.UserManagerBean;
import com.frss.dao.main.UserDAO;

public class QueryDepartmentServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static String[] _arrStr = {"name"};
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QueryDepartmentServlet() {
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
//		int subType= 0;
//		if(userType==UserDAO.Formater || userType==UserDAO.Distributor)
//			subType = UserDAO.Military;
//		else if(userType==UserDAO.Military)
//			subType = UserDAO.GroupArmy;
//		else if(userType==UserDAO.GroupArmy || userType==UserDAO.Regiment)
//			subType = UserDAO.Regiment;
//		
//		UserDAO userDAO = new UserDAO();
//		ArrayList<String> arrDepartment = userDAO.queryDepartment(subType);
//		if(arrDepartment==null) {
//			JSONObject json = new JSONObject();
//			json.put("ret", 3);
//			json.put("msg", "查询子类用户单位失败！");
//			respond(response, json);
//			return;			
//		}
		
		UserManagerBean userManager = new UserManagerBean();
		// json对象
		JSONObject jo = new JSONObject();
		JSONArray ja = userManager.querySubDepartment(userId);
		
//		if(arrDepartment!=null && arrDepartment.size()>0) {
//			for( String department : arrDepartment) {
//				if(department==null || department.equals(""))
//					continue;
//				
//				JSONObject jso = new JSONObject();
//				jso.put(_arrStr[0], department);
//				ja.add(jso);
//			}
//		}
		
		jo.put("identifier", "id");
		jo.put("label", "name");
		jo.put("items", ja);
		jo.put("ret", 0);
		String str = jo.toString();
		System.out.println("QueryDepartmentServlet get:");
		System.out.println(str);
		response.setContentType("text/json; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.write(str);
	}

	protected void respond(HttpServletResponse response, JSONObject json) throws ServletException, IOException {
		String str = json.toString();
		System.out.println("QueryDepartmentServlet get:");
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
