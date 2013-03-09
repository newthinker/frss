/**
 * @文件名称: LogoutServlet.java
 * @所属包名: com.frss.servlet
 * @文件描述: TODO
 * @创建时间: 2012-1-13 上午11:48:23
 * @作         者: 
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

/**
 * @类型名称: LogoutServlet
 * @类型描述: TODO
 * @作           者: 
 * @创建时间: 2012-1-13 上午11:48:23
 *
 */
public class LogoutManagerServlet extends HttpServlet {
	
	
	/**
	 * @ 字段 serialVersionUID: TODO
	 * @ 字段类型: long
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor of the object.
	 */
	public LogoutManagerServlet() {
		super();
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
		session.setAttribute("loginUser", null);
		session.removeAttribute("error");
		response.sendRedirect("./index.jsp");
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
