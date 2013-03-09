package com.frss.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.frss.bean.StatManagerBean;
import com.frss.util.DateUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class QueryDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String[] _arrStr = {
		"orderid", //单号 --唯一id --等于faultid或者backupid
		"step", //当前进行到哪一步了
		"state", //状态，0中止 1正在进行

		"commiter", //提交者
		"committime", //提交时间
		"contact", //联络方式
		"zbuser", //使用人
		"zbsydw", //Zb使用单位
		
		"faultid", //故障id
		"zbtype", //装备型号
		"zbname", //装备名称
		"zbserial", //装备编号
		"zbnum" , //装备数量
		"zbgzxx", //装备现象
		"zbgzsj", //故障发生时机
		"zbxqcl", //Zb先期处理情况
		"zbgzpc", //故障发生频次
		"zbgzbw", //故障产生部位
		"zbqzzp", //照片

		"backupid", //备件id
		"bjtype", //备件型号
		"bjname", //备件名称
		"bjserial", //备件编号
		"bjnum", //备件数量
		"bjpart", //备件使用zb部位
		"bjfaultid", //维修申请单号

		"l1checker", //1级审核人
		"l1time", //1级审核时间
		"l1opinion", //1级审核意见

		"l2checker", //2级审核人
		"l2time", //2级审核时间
		"l2opinion", //2级审核意见

		"dpid", //派遣单号
		"dpunit", //派遣单位
		"dptime", //派遣时间
		"dpsafeunit", //保障部队
		"dpaddr", //部队地点
		"dpcontact", //部队联系人

		"fbid", //反馈单号
		"fbtime", //反馈时间
		"fbdispatch", //故障派修
		"fbrpcontent", //维修内容
		"fbgzreason", //故障原因
		"fbrpway", //维修手段
		"fbrpresult", //维修结果
		"fbrpquality", //维修质量
		"fbrptime", //维修时间
		"fbrpbjname", //维修使用备件名称
		"fbrpbjtype", //维修使用备件型号
		"fbrpbjfrom", //备件来源情况

		"rsid", //远程支援单号
		"rschannel", //远程支援通道
		"rstype", //远程支援方式
		"rsunit", //远程支援单位
		"rsexpert", //远程支援专家
		"rscontacter", //联络人
		"rscontactway", //联络方式
		
		"re_reporter",		// 回访员
		"re_client",		// 被回访客户
		"re_reporttime",	// 回访时间
		"re_quality",		// 维修质量回访
		"re_reviewway",		// 回访方式
		"re_contact",		// 联系方式
		"re_disc"			// 维修描述
	};

    /**
     * @see HttpServlet#HttpServlet()
     */
    public QueryDetailServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// json对象
		JSONObject jo = new JSONObject();
		
		// 进行统计输出		
		int status = 0;					// 0表示未处理，1表示已经处理
		Date startTime = null;			// 开始时间
		Date endTime = null;			// 结束时间
		int userType = 0;				// 当前登录用户类型

		// 获取当前登录用户类型
		HttpSession session = request.getSession();
		ArrayList<String> arrUser = (ArrayList<String>)session.getAttribute("loginUser");
		if (arrUser == null) {
			JSONObject json = new JSONObject();
			json.put("ret", 1);
			json.put("msg", "请重新登录！");
			respond(response, json);
			return;
		}
		long userId = Long.parseLong(arrUser.get(0));
		userType = Integer.parseInt(arrUser.get(3));
		if(userId<0 || userType<0 || userType>10) {
			/// log 当前登录用户非法
			JSONObject json = new JSONObject();
			json.put("ret", 2);
			json.put("msg", "当前登录用户非法！");
			respond(response, json);
			return;
		}
		
			
		// StatManagerBean进行初始化和统计
		long faultId = Long.parseLong(request.getParameter("orderid"));		/// 从前端返回
		if(faultId<=0) {
			JSONObject json = new JSONObject();
			json.put("ret", 2);
			json.put("msg", "当前订单号无效！");
			respond(response, json);
			return;			
		}
			
		// 进行故障单详情查询
		StatManagerBean statBean = new  StatManagerBean();
		Map<String, String> mapDetail = statBean.queryDetail(faultId);
		
		/// 将统计结果进行处理并输出
		if(mapDetail!=null) {
			jo.put(_arrStr[0], mapDetail.get(_arrStr[0]));
			jo.put(_arrStr[1], mapDetail.get(_arrStr[1]));
			jo.put(_arrStr[2], mapDetail.get(_arrStr[2]));
			for (int i=3; i<_arrStr.length; i++) {
				if(mapDetail.get(_arrStr[i])==null)
					jo.put(_arrStr[i], "");
				else
					jo.put(_arrStr[i], mapDetail.get(_arrStr[i]));
			}
			
			jo.put("ret", 0);
		}
		String str = jo.toString();
		System.out.println("QueryDetailServlet get:");
		System.out.println(str);
		response.setContentType("text/json; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.write(str);
	}

	/**
	 * 
	 */
	protected void respond(HttpServletResponse response, JSONObject json) throws ServletException, IOException {
		String str = json.toString();
		System.out.println("QueryDetailServlet get:");
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
