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

import com.frss.bean.RepairManagerBean;
import com.frss.dao.main.UserDAO;
import com.frss.util.ReturnFlag;
import com.frss.util.ValidCheck;

public class UploadDispatchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// 表格各字段标识
	private static String[] _arrStr = {"orderid", "factoryid", "dpid", "dpunit", "dptime", "dpsafeunit", 
		"zbtype", "zbname", "dpaddr", "dpcontact", "zbgzxx"};
	int fieldNum = _arrStr.length;		// 表格字段个数
	
    /**
     * @see HttpServlet#HttpServlet()
     */
	public UploadDispatchServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("UploadDispatchServlet get:");
		
		RepairManagerBean repairManager = new RepairManagerBean();
		Map<String, String> mapRepair = new HashMap<String, String>();
		JSONObject json = new JSONObject();		// 返回值
		
		// 获取当前登录用户Id
		HttpSession session = request.getSession();
		ArrayList<String> arrUser = (ArrayList)session.getAttribute("loginUser");		
		
		try {
			long userId = Long.parseLong(arrUser.get(0));
			int userType = Integer.parseInt(arrUser.get(3));
			String userName = arrUser.get(2);
			if(userType!=UserDAO.Distributor) {		// 维修中心分发人员进行故障分发
				json.put("ret", 1);
				json.put("msg", "当前登录用户非法，请重新登录！");
				respond(response, json);
				return;
			}		
			mapRepair.put("userId", arrUser.get(0));
			
			for (int i=0; i<fieldNum; i++)
			{
				System.out.println(_arrStr[i] + ": " + request.getParameter(_arrStr[i]));
				String value = request.getParameter(_arrStr[i]);
				if(value!=null) {
					value = new String(request.getParameter(_arrStr[i]).getBytes("iso8859-1"), "UTF-8");
					if(!(ValidCheck.validCheck(value))) {
						JSONObject jo = new JSONObject();
						jo.put("ret", 1);
						jo.put("msg", "参数输入非法:" + value);
						respond(response, jo);
						return;
					}
					
					mapRepair.put(_arrStr[i], value);
				}
			}
			
			ReturnFlag rf = repairManager.ImprotRepairDispatchForm(mapRepair);
			/// 根据返回的标识在前端展现不同的提示语
			if(rf.getFlag()!=0) {
				json.put("ret", 2);
				json.put("msg", "导入分发表单失败:"+rf.getMessage());
				respond(response, json);
				return;				
			}
			
		} catch (Exception e) {
			json.put("ret", 3);
			json.put("msg", "导入分发表单出现异常！");
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
		System.out.println("UploadDispatchServlet get:");
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
