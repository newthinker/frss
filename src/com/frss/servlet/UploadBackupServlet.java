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

import com.frss.bean.EquipmentManagerBean;
import com.frss.bean.FaultReportManagerBean;
import com.frss.dao.main.UserDAO;
import com.frss.util.ReturnFlag;
import com.frss.util.ValidCheck;

/**
 * @类型名称: UploadBackServlet
 * @类型描述: 上传备件申请单
 * @作           者: Michael.Cho, zuow11@gmail.com
 * @创建时间: 2012-3-19 下午8:08:14
 *
 */
public class UploadBackupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// 表格各字段标识
	private static String[] _arrStr = {"backtype", "backname", "backid", "backnum" ,"equipname", "equiptype", 
		"equiplace", "faultid", "department", "operator", "reporter", "contactway", "reportime"};
	int fieldNum = _arrStr.length;		// 表格字段个数
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadBackupServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("UploadBackServlet get:");
		
		EquipmentManagerBean backManager = new EquipmentManagerBean();
		Map<String, String> mapBack = new HashMap<String, String>();
		
		// 获取当前登录用户Id
		HttpSession session = request.getSession();
		ArrayList<String> arrUser = (ArrayList)session.getAttribute("loginUser");
		
		try {
			long userId = Long.parseLong(arrUser.get(0));
			int userType = Integer.parseInt(arrUser.get(3));
			String userName = arrUser.get(1);
			if(userType!=UserDAO.Regiment) {	// 只有团级用户才能进行备件申请
				/// log 当前登录用户非法
				JSONObject json = new JSONObject();
				json.put("ret", 1);
				json.put("msg", "当前登录用户非法，请重新登录！");
				respond(response, json);
				return;
			}
			mapBack.put("userId", arrUser.get(0));
			
			for (int i=0; i<fieldNum; i++)
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
					
					mapBack.put(_arrStr[i], value);
				}
			}
			mapBack.put("reporter", userName);
			
			ReturnFlag rf = backManager.ImportBackReport(mapBack);
			/// 根据返回的标识在前端展现不同的提示语
			if(rf.getFlag()!=0) {
				JSONObject json = new JSONObject();
				json.put("ret", 2);
				json.put("msg", "导入备件上报表单失败:"+rf.getMessage());
				respond(response, json);
				return;		
			}
			
		} catch (Exception e) {
			JSONObject json = new JSONObject();
			json.put("ret", 3);
			json.put("msg", "导入备件上报表单出现异常！");
			respond(response, json);
			return;				
		}
		
		JSONObject json = new JSONObject();
		json.put("ret", 0);
		respond(response, json);
		return;
	}

	protected void respond(HttpServletResponse response, JSONObject json) throws ServletException, IOException {
		String str = json.toString();
		System.out.println("UploadBackServlet get:");
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
