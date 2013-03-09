package com.frss.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import com.frss.bean.LoginManagerBean;
import com.frss.dao.main.UserDAO;
import com.frss.model.main.UserInfo;
import com.frss.util.ReturnFlag;
import com.frss.util.ValidCheck;

public class AddNewUserServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static String[] _arrStr = {"name", "level", "fullname", "depart", "descript"};
	int fieldNum = _arrStr.length;		// 表格字段个数	
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddNewUserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("AddNewUserServlet get:");
		JSONObject json = new JSONObject();
		
		// 获取当前登录用户Id
		HttpSession session = request.getSession();
		ArrayList<String> arrUser = (ArrayList)session.getAttribute("loginUser");	
		
		Map<String, String> mapNewUser = new HashMap<String, String>();
		
		try {
			// 获取用户信息
			long userId = Long.parseLong(arrUser.get(0));
			int userType = Integer.parseInt(arrUser.get(3));
			String userName = arrUser.get(1);
			if(userType<UserDAO.Super || userType>UserDAO.Formater) {
				/// log 当前登录用户非法
				json.put("ret", 1);
				json.put("msg", "当前登录用户非法！");
				respond(response, json);							
				return;
			}	
			mapNewUser.put("userId", arrUser.get(0));
			
			// 获取用户输入的新帐号信息
			for(int i=0;i<_arrStr.length;i++) {
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
					mapNewUser.put(_arrStr[i], value);
				}
			}
			
			// 验证新用户的级别等信息
			int newType = Integer.parseInt(mapNewUser.get("level"));
			if(newType<UserDAO.Super || newType>UserDAO.Formater) {
				json.put("ret", 1);
				json.put("msg", "当前登录用户非法！");
				respond(response, json);								
				return;				
			}
			
			// 首先验证用户输入的旧密码是否正确
			LoginManagerBean login = new LoginManagerBean();
			if(login==null) {
				json.put("ret", 2);
				json.put("msg", "新建用户类型非法！");
				respond(response, json);				
				return;
			} else if(userType==UserDAO.Super) {
				if(newType!=UserDAO.Formater && newType!=UserDAO.Distributor) {
					json.put("ret", 2);
					json.put("msg", "新建用户类型非法！");
					respond(response, json);				
					return;
				}
			} else	if(userType==UserDAO.Formater || userType==UserDAO.Distributor) {
				if(newType!=UserDAO.Expert&&newType!=UserDAO.Factory&&newType!=UserDAO.Military) {
					json.put("ret", 2);
					json.put("msg", "新建用户类型非法！");
					respond(response, json);				
					return;
				}
			} else	if(userType==UserDAO.Military) {
				if(newType!=UserDAO.GroupArmy) {
					json.put("ret", 2);
					json.put("msg", "新建用户类型非法！");
					respond(response, json);				
					return;				
				}
			} else if(userType==UserDAO.GroupArmy) {
				if(newType!=UserDAO.Regiment) {
					json.put("ret", 2);
					json.put("msg", "新建用户类型非法！");
					respond(response, json);				
					return;					
				}
			} else {
				json.put("ret", 2);
				json.put("msg", "当前登录用户不能创建帐号！");
				respond(response, json);				
				return;		
			}
			
			/// 将新用户输入到数据库中
			LoginManagerBean userManager = new LoginManagerBean();
			ReturnFlag rf = userManager.insertNewUser(mapNewUser);
			if(rf.getFlag()!=0) {
				json.put("ret", 3);
				json.put("msg", "创建帐号失败:"+rf.getMessage());
				respond(response, json);				
				return;		
			}			
		} catch (Exception e) {
			json.put("ret", 2);
			json.put("msg", "创建帐号出现异常！");
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
		System.out.println("AddNewUserServlet get:");
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
