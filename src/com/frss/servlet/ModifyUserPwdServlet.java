package com.frss.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import com.frss.bean.LoginManagerBean;
import com.frss.dao.main.UserDAO;
import com.frss.util.ReturnFlag;
import com.frss.util.ValidCheck;

public class ModifyUserPwdServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static String[] _arrStr = {"newpwd", "oldpwd"};
	int fieldNum = _arrStr.length;		// 表格字段个数	
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModifyUserPwdServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("ModifyUserPwdServlet get:");
		
		// 获取当前登录用户Id
		HttpSession session = request.getSession();
		ArrayList<String> arrUser = (ArrayList)session.getAttribute("loginUser");			
		
		try {
			// 获取用户信息
			long userId = Long.parseLong(arrUser.get(0));
			int userType = Integer.parseInt(arrUser.get(3));
			String userName = arrUser.get(1);
			if(userType<UserDAO.Super || userType>UserDAO.Formater) {
				/// log 当前登录用户非法
				JSONObject json = new JSONObject();
				json.put("ret", 1);
				json.put("msg", "当前登录用户非法！");
				respond(response, json);
								
				return;
			}		
			
			/// 获取用户输入的新/旧密码
			String oldPwd = request.getParameter(_arrStr[1]);
			String newPwd = request.getParameter(_arrStr[0]);
			
			// 首先验证用户输入的旧密码是否正确
			LoginManagerBean login = new LoginManagerBean();
			if(login==null) {
				/// log
				JSONObject json = new JSONObject();
				json.put("ret", 2);
				json.put("msg", "操作出现异常！");
				respond(response, json);				
				return;
			}
			
			// 检查是否包含非法字符 [zuow,2012/05/07]
			boolean bFlag = ValidCheck.validCheck(oldPwd, newPwd);
			if(!bFlag) {
				JSONObject json = new JSONObject();
				json.put("ret", 2);
				json.put("msg", "输入参数中包含非法字符，请重新输入！");
				respond(response, json);				
				return;
			}
			
			ReturnFlag rf = login.userLogin(userName, oldPwd);
			if(rf.getFlag()!=0) {
				/// log ，输入的旧密码不正确
				JSONObject json = new JSONObject();
				json.put("ret", 2);
				json.put("msg", "用户登录失败！" + rf.getMessage());
				respond(response, json);			
				return;
			}
			
			// 进行修改用户密码
			rf = login.modifyUserPassword(oldPwd, newPwd);
			if(rf.getFlag()!=0) {
				/// log
				JSONObject json = new JSONObject();
				json.put("ret", 3);
				json.put("msg", "修改密码失败:" + rf.getMessage());
				respond(response, json);				
				return ;
			} else {
				/// log 修改密码成功
				JSONObject json = new JSONObject();
				json.put("ret", 0);
				json.put("msg", "修改密码成功！");
				respond(response, json);				
			}
			
		} catch (Exception e) {
			JSONObject json = new JSONObject();
			json.put("ret", 2);
			json.put("msg", "修改密码出现异常！");
			respond(response, json);				
			return ;			
		}
	}

	/**
	 * 
	 */
	protected void respond(HttpServletResponse response, JSONObject json) throws ServletException, IOException {
		String str = json.toString();
		System.out.println("ModifyUserPwdServlet get:");
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
