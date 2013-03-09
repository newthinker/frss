package com.frss.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import com.frss.bean.ApprovalManagerBean;
import com.frss.bean.FaultReportManagerBean;
import com.frss.dao.main.UserDAO;
import com.frss.util.ReturnFlag;
import com.frss.util.ValidCheck;

public class UploadFormatServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String[] _arrStr = {"orderid", "ispass"};	// 表单主键id， 是否通过格式审查(1--通过，0--不通过)
	int fieldNum = _arrStr.length;		// 表格字段个数

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadFormatServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("UploadFormatServlet get:");
		JSONObject json = new JSONObject();		// 返回值
		
		ApprovalManagerBean appManager = new ApprovalManagerBean();
		Map<String, String> mapFormat = new HashMap<String, String>();
		
		// 获取当前登录用户Id
		HttpSession session = request.getSession();
		ArrayList<String> arrUser = (ArrayList)session.getAttribute("loginUser");			
		
		try {
			// 获取用户信息
			long userId = Long.parseLong(arrUser.get(0));
			int userType = Integer.parseInt(arrUser.get(3));
			String userName = arrUser.get(1);
			if(userType!=UserDAO.Formater) {
				/// log 当前登录用户非法				
				json.put("ret", 1);
				json.put("msg", "当前登录用户非法，请重新登录！");
				respond(response, json);
				return;
			}		
			mapFormat.put("userId", arrUser.get(0));
			
			for (int i=0; i<fieldNum; i++)
			{
				System.out.println(_arrStr[i] + ": " + request.getParameter(_arrStr[i]));
				String value = request.getParameter(_arrStr[i]);
				if(value!=null) {
					value = new String(request.getParameter(_arrStr[i]).getBytes("iso8859-1"), "UTF-8");
					if(!(ValidCheck.validCheck(value))) {
						json.put("ret", 1);
						json.put("msg", "参数输入非法:" + value);
						respond(response, json);
						return;
					}
					mapFormat.put(_arrStr[i], value);
				}
			}
			
			ReturnFlag rf = appManager.importFormatReport(mapFormat);
			if(rf.getFlag()!=0) {
				/// log
				json.put("ret", 2);
				json.put("msg", "导入格式审核数据失败:"+rf.getMessage());
				respond(response, json);
				return;
			}			
		} catch (Exception e) {
			json.put("ret", 3);
			json.put("msg", "导入格式审核数据出现异常！");
			respond(response, json);			
			return;			
		}
		
		json.put("ret", 0);
		respond(response, json);
		return;
	}

	/**
	 * 
	 */
	protected void respond(HttpServletResponse response, JSONObject json) throws ServletException, IOException {
		String str = json.toString();
		System.out.println("UploadFormatServlet get:");
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
