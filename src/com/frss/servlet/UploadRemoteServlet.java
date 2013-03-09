package com.frss.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.frss.bean.EquipmentManagerBean;
import com.frss.bean.RepairManagerBean;
import com.frss.dao.main.UserDAO;
import com.frss.util.ReturnFlag;

/**
 * @类型名称: UploadRemoteServlet
 * @类型描述: 上传远程支援表单
 * @作           者: Michael.Cho, zuow11@gmail.com
 * @创建时间: 2012-3-21 下午8:35:05
 *
 */
public class UploadRemoteServlet extends HttpServlet {	
	private static final long serialVersionUID = 1L;
	
	// 表格各字段标识
	private static String[] _arrStr = {
		"orderid",
		"fbid", //
//		"rsid", //远程支援单号
		"rschannel", //远程支援通道
		"rstype", //远程支援方式
		"rsunit", //远程支援单位
		"rsexpert", //远程支援专家
		"rscontacter", //联络人
		"rscontactway", //联络方式
	};
	int fieldNum = _arrStr.length;		// 表格字段个数
	
    /**
     * @see HttpServlet#HttpServlet()
     */
	public UploadRemoteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("UploadRemoteServlet get:");
		
		RepairManagerBean repairManager = new RepairManagerBean();
		Map<String, String> mapRemote = new HashMap<String, String>();
		
		// 获取当前登录用户Id
		HttpSession session = request.getSession();
		ArrayList<String> arrUser = (ArrayList)session.getAttribute("loginUser");		
		
		try {
			long userId = Long.parseLong(arrUser.get(0));
			if(userId!=UserDAO.Factory) {		// 工业部门进行远程支援表单提交
				/// log 当前登录用户非法
				return;
			}		
			mapRemote.put("userId", arrUser.get(0));
			
			for (int i=0; i<fieldNum; i++)
			{
				System.out.println(_arrStr[i] + ": " + request.getParameter(_arrStr[i]));
				String value = request.getParameter(_arrStr[i]);
				if(value!=null) {
					value = new String(request.getParameter(_arrStr[i]).getBytes("iso8859-1"), "UTF-8");
					mapRemote.put(_arrStr[i], value);
				}
			}
			
			ReturnFlag rf = repairManager.ImportRemoteSupportForm(mapRemote);
			/// 根据返回的标识在前端展现不同的提示语
			
		} catch (Exception e) {
			
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
