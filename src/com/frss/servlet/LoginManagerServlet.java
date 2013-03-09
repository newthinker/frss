/**
 * @文件名称: LoginServlet.java
 * @所属包名: com.frss.servlet
 * @文件描述: TODO
 * @创建时间: 2012-1-13 上午11:48:23
 * @作         者: Michael.Cho, zuow11@gmail.com
 * @版本信息: V1.0
 */
package com.frss.servlet;

import java.io.IOException; 
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.frss.bean.LoginManagerBean;
import com.frss.util.ReturnFlag;
import com.frss.util.ValidCheck;

/**
 * @类型名称: LoginServlet
 * @类型描述: TODO
 * @作           者: Michael.Cho, zuow11@gmail.com
 * @创建时间: 2012-1-13 上午11:48:23
 *
 */
public class LoginManagerServlet extends HttpServlet {
	
	
	/**
	 * @ 字段 serialVersionUID: TODO
	 * @ 字段类型: long
	 */
	private static final long serialVersionUID = 1L;
	
	private LoginManagerBean iManager;

	/**
	 * Constructor of the object.
	 */
	public LoginManagerServlet() {
		super();
		iManager = new LoginManagerBean();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String name = request.getParameter("name");		// 支持中文 [zuow, 2012/04/22]
		String password = request.getParameter("pwd");
		
		// 首先进行安全性检查 [zuow, 2012/05/07]
		if(name==null || password==null || name.equals("") || password.equals("")) {			
			System.out.print("没有输入参数！");
			return;
		}
					
		name = new String(request.getParameter("name").getBytes("iso8859-1"), "UTF-8");	
		password = new String(request.getParameter("pwd").getBytes("iso8859-1"), "UTF-8");
		
		// 非法字符检查 [zuow,2012/05/07]
		boolean bFlag = ValidCheck.validCheck(name, password);
		if(!bFlag) {
			System.out.print("输入参数中包含非法字符，请重新输入！");
			return;
		}
		
		ReturnFlag rf = iManager.userLogin(name, password);
		if (rf.getFlag() != 0) {
			session.setAttribute("error", rf.getFlag());
			response.sendRedirect("./index.jsp");
			return;
		}
		
		ArrayList<String> arrUser = iManager.getArrayUserInfo();
		if(arrUser==null || arrUser.size()<=0) {
			// log
			session.setAttribute("error", 1);
			response.sendRedirect("./index.jsp");
			return;
		}
		
		session.setAttribute("loginUser", arrUser);
		response.sendRedirect("./main.jsp");
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request,response);
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occure
	 */
	public void init() throws ServletException {
		// Put your code here
	}
	
}
