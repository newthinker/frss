package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.Validate;

import bean.LoginBean;

public class LoginServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public LoginServlet() {
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
		Validate vd = new Validate();
		String name = vd.getUnicode(request.getParameter("name"));
		String pwd = vd.getUnicode(request.getParameter("pwd"));
		String str = "";
		LoginBean lb = new LoginBean();
		int flag = lb.login(name, pwd);
		ArrayList al = lb.getArrayLst();
		HttpSession session = request.getSession();
		session.setAttribute("login", al);
//		if(flag < 0){
//			str = "/index.jsp";
//			request.setAttribute("error", "1");
//		}
//		else if(flag == 0){
//			str = "/ruku.jsp";
//		}
//		else if(flag == 1){
//			str = "/ruku.jsp";
//		}
//		else if(flag == 2){
//			str = "/ruku.jsp";
//		}
//		else if(flag == 3){
//			str = "/ruku.jsp";
//		}
//		else if(flag == 4){
//			str = "/ruku.jsp";
//		}
//		else if(flag == 0){
//			str = "/ruku.jsp";
//		}
		switch (flag)
		{
		case 0:
			str = "/super.jsp";
			break;
		case 1:
			str = "/main.jsp";
			request.setAttribute("qx", "1");
			break;
		case 2:
			str = "/main.jsp";
			request.setAttribute("qx", "2");
			break;
		case 3:
			str = "/main.jsp";
			request.setAttribute("qx", "3");
			break;
		case 4:
			str = "/main.jsp";
			request.setAttribute("qx", "4");
			break;
		case 5:
			str = "/main.jsp";
			request.setAttribute("qx", "5");
			break;
		case 6:
			str = "/main.jsp";
			request.setAttribute("qx", "6");
			break;
		default:
			str = "/index.jsp";
			request.setAttribute("error", "1");
			break;
		}
		RequestDispatcher rd=request.getRequestDispatcher(str);
		rd.forward(request,response);
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
