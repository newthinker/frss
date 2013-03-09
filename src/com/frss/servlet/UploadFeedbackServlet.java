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

public class UploadFeedbackServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// 表格各字段标识
	private static String[] _arrFeedbak = {
		"orderid", //
//		"fbid", //反馈单号
		"zbtype", //装备型号
		"zbname", //装备名称
		"zbgzsj", //故障发生时机
		"fbdispatch", //故障派修
		"fbrpcontent", //维修内容
		"fbgzreason", //故障原因
//		"fbrpway", //维修手段
		"fbrpresult", //维修结果
		"fbrpquality", //维修质量
		"fbrptime", //维修时间
		"fbrpbjname", //维修使用备件名称
		"fbrpbjtype", //维修使用备件型号
		"fbrpbjfrom", //备件来源情况
	};
	
	private static String[] _arrRemote = {
		"orderid",
//		"fbid", //
//		"rsid", //远程支援单号
		"rschannel", //远程支援通道
		"rstype", //远程支援方式
		"rsunit", //远程支援单位
		"rsexpert", //远程支援专家
		"rscontacter", //联络人
		"rscontactway", //联络方式
	};

	
    /**
     * @see HttpServlet#HttpServlet()
     */
	public UploadFeedbackServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("UploadFeedbackServlet get:");
		
		RepairManagerBean repairManager = new RepairManagerBean();
		Map<String, String> mapFeedback = new HashMap<String, String>();
		Map<String, String> mapRemote = new HashMap<String, String>();
		
		// 获取当前登录用户Id
		HttpSession session = request.getSession();
		ArrayList<String> arrUser = (ArrayList)session.getAttribute("loginUser");		
		
		JSONObject json = new JSONObject();		// 返回值
		
		try {
			long userId = Long.parseLong(arrUser.get(0));
			int userType = Integer.parseInt(arrUser.get(3));
			if(userType!=UserDAO.Factory) {		// 工业部门用户进行提交反馈表单
				/// log 当前登录用户非法
				json.put("ret", 1);
				json.put("msg", "当前登录用户非法，请重新登录！");
				respond(response, json);
				return;
			}		
			mapFeedback.put("userId", arrUser.get(0));
			mapRemote.put("userId", arrUser.get(0));
			
			for (int i=0; i<_arrFeedbak.length; i++)
			{
				System.out.println(_arrFeedbak[i] + ": " + request.getParameter(_arrFeedbak[i]));
				String value = request.getParameter(_arrFeedbak[i]);
				if(value!=null) {
					value = new String(request.getParameter(_arrFeedbak[i]).getBytes("iso8859-1"), "UTF-8");
					if(!(ValidCheck.validCheck(value))) {
						JSONObject jo = new JSONObject();
						jo.put("ret", 1);
						jo.put("msg", "参数输入非法:" + value);
						respond(response, jo);
						return;
					}
					mapFeedback.put(_arrFeedbak[i], value);
				}
			}

			// 解析维修方式
			boolean bflag1 = false;
			boolean bflag2 = false;
			String repairWay1 = request.getParameter("fbrpway1");
			String repairWay2 = request.getParameter("fbrpway2");
			if(repairWay1!=null && repairWay1.equals("on"))
				bflag1 = true;
			if(repairWay2!=null && repairWay2.equals("on"))
				bflag2 = true;
			
			int repairWay = 0;		//默认两种方式都不采用
			if(bflag1 && !bflag2)		// 只有现场维修
				repairWay = 1;
			else if(!bflag1 && bflag2)	// 只有远程支援
				repairWay = 2;
			else if(bflag1 && bflag2)		// 两种方式都采用
				repairWay = 3;
			mapFeedback.put("fbrpway", Integer.toString(repairWay));
			
			ReturnFlag rf = repairManager.ImportRepairFeedbakReport(mapFeedback);
			// 根据返回的标识在前端展现不同的提示语
			if(rf.getFlag()!=0) {
				json.put("ret", 2);
				json.put("msg", "导入维修反馈表单失败:"+rf.getMessage());
				respond(response, json);
				return;				
			}
			long feedbackId = Long.parseLong(rf.getMessage());		// 从msg进行传出 [zuow, 2012/05/12]
			
			// 如果有远程协助，还需要打开远程信息表单进行录入 [zuow, 2012/04/05]			
			if(bflag2) {
				for(int i=0;i<_arrRemote.length;i++) {
					System.out.println(_arrRemote[i] + ": " + request.getParameter(_arrRemote[i]));
					String value = new String(request.getParameter(_arrRemote[i]).getBytes("iso8859-1"), "UTF-8");
					if(!(ValidCheck.validCheck(value))) {
						JSONObject jo = new JSONObject();
						jo.put("ret", 1);
						jo.put("msg", "参数输入非法:" + value);
						respond(response, jo);
						return;
					}
					mapRemote.put(_arrRemote[i], value);
				}
				mapRemote.put("fbid", Long.toString(feedbackId));
				
				rf = repairManager.ImportRemoteSupportForm(mapRemote);
				if(rf.getFlag()!=0) {
					json.put("ret", 2);
					json.put("msg", "导入远程支援信息表单失败:"+rf.getMessage());
					respond(response, json);
					return;		
				}
			}
		} catch (Exception e) {
			/// log
			json.put("ret", 3);
			json.put("msg", "导入维修反馈表单出现异常！");
			respond(response, json);
			return;				
			
		}
		
		json.put("ret", 0);
		respond(response, json);
		return;
	}

	protected void respond(HttpServletResponse response, JSONObject json) throws ServletException, IOException {
		String str = json.toString();
		System.out.println("UploadFeedbackServlet get:");
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
