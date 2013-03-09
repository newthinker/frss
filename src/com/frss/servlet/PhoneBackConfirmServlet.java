package com.frss.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
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

import com.frss.bean.ApprovalManagerBean;
import com.frss.bean.FactoryInfoManagerBean;
import com.frss.dao.main.UserDAO;
import com.frss.util.ReturnFlag;
import com.frss.util.ValidCheck;

public class PhoneBackConfirmServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String[] _arrStr = {"orderid", "client", "quality", "reviewway", "contact", "discription" };

    /**
     * @see HttpServlet#HttpServlet()
     */
    public PhoneBackConfirmServlet() {
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
		Map<String, String> mapReview = new HashMap<String, String>();
		
		try {
			if (arrUser == null) {
				JSONObject json = new JSONObject();
				json.put("ret", 1);
				json.put("msg", "请重新登录！");
				respond(response, json);
				return;
			}
			long userId = Long.parseLong(arrUser.get(0));
			int userType = Integer.parseInt(arrUser.get(3));
			if(userId<0 || userType!=UserDAO.Formater) {
				/// log 当前登录用户非法
				JSONObject json = new JSONObject();
				json.put("ret", 2);
				json.put("msg", "当前登录用户非法！");
				respond(response, json);
				return;
			}
			mapReview.put("userId", arrUser.get(0));
			
			// 获取其它收入信息
			for (int i=0; i<_arrStr.length; i++)
			{
				System.out.println(_arrStr[i] + ": " + request.getParameter(_arrStr[i]));
				String value = request.getParameter(_arrStr[i]);
				if(value!=null) {
					value = new String(request.getParameter(_arrStr[i]).getBytes("iso8859-1"), "UTF-8");
					if(!(ValidCheck.validCheck(value))) {
						JSONObject json = new JSONObject();
						json.put("ret", 1);
						json.put("msg", "参数输入非法:" + value);
						respond(response, json);
						return;
					}
					
					mapReview.put(_arrStr[i], value);
				}				
			}
			
//			// 状态
//			long faultId = 0;
//			String value = request.getParameter("orderid");
//			if(value==null || value=="") {
//				/// log 当前登录用户非法
//				JSONObject json = new JSONObject();
//				json.put("ret", 3);
//				json.put("msg", "故障单id非法！");
//				respond(response, json);
//				return;
//			}
//			
//			boolean bFlag = ValidCheck.validCheck(value);
//			if(!bFlag) {
//				JSONObject json = new JSONObject();
//				json.put("ret", 3);
//				json.put("msg", "故障单id非法！");
//				respond(response, json);
//				return;
//			}
//			
//			faultId = Long.parseLong(value);
//			if(faultId<=0) {
//				JSONObject json = new JSONObject();
//				json.put("ret", 4);
//				json.put("msg", "故障单id非法！");
//				respond(response, json);
//				return;
//			}
			
			// json对象
			JSONObject jo = new JSONObject();			
			ApprovalManagerBean appManager = new ApprovalManagerBean();
			ReturnFlag rf = appManager.importPhoneBack(mapReview);
			if(rf.getFlag()!=0) {
				jo.put("msg", "更新电话回访确认信息失败:"+rf.getMessage());
				jo.put("ret", 1);
			} else {
				jo.put("ret", 0);
			}
					
			String str = jo.toString();
			System.out.println("PhoneBackConfirmServlet get:");
			System.out.println(str);
			response.setContentType("text/json; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.write(str);
			
		} catch (Exception e) {
			JSONObject json = new JSONObject();
			json.put("ret", 5);
			json.put("msg", "导入审核信息表单出现异常！");
			respond(response, json);
			return;	
		}		
	}

	/**
	 * 
	 */
	protected void respond(HttpServletResponse response, JSONObject json) throws ServletException, IOException {
		String str = json.toString();
		System.out.println("PhoneBackConfirmServlet get:");
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
