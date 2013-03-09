package com.frss.servlet;

import java.io.ByteArrayInputStream;
import java.io.CharArrayReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.frss.bean.StatManagerBean;
import com.frss.util.DateUtil;

public class OutputResultsServlet extends HttpServlet {

	/**
	 * @ 字段 serialVersionUID: TODO
	 * @ 字段类型: long
	 */
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
	};
	
	private String contentType = "application/x-msdownload";
	private String encode = "utf-8";
	private String fileRoot = "";
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OutputResultsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    // 初始化
    public void init(ServletConfig config) throws ServletException {
    	String value = config.getInitParameter("contentType");
    	if(value!=null && !value.equals(""))
    		contentType = value;
    	
    	value = config.getInitParameter("encode");
    	if(value!=null && !value.equals(""))
    		encode = value;
    	
    	value = config.getInitParameter("fileRoot");
    	if(value!=null && !value.equals(""))
    		fileRoot = value;
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 进行统计输出		
		int status = 0;					// 0表示未处理，1表示已经处理
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
		
		// 获取文件下载路径 [zuow, 2012/05/05]
		String filePath = request.getParameter("filePath");
		String fileFullPath = fileRoot + filePath;
		
		// 获取查询条件 [zuow,2012/05/05]
		Date startTime = null;			// 开始时间
		Date endTime = null;			// 结束时间
		long  faultId = 0;				// 故障单id查询
		String keyWord = null;			// 关键字查询--故障现象
		String equipType = null;
		String equipName = null;
		String equipNumber = null;
		String reporter = null; 
		String department = null;
		
		DateUtil dateUtil = new DateUtil();
		String value = request.getParameter("datebegin");
		if(value==null||value.equals("")){	// 非法
			/// 设置成默认--当天
			startTime = dateUtil.getThisDayStart();
			startTime = dateUtil.getStartTime(startTime);
		} else {	// 将时间序列转换成date
			if(value.indexOf("-")>=0)
				value = value.replaceAll("-", "");
			value += "000000";
			startTime = dateUtil.getDateFromSerial(value);
		}
		value = request.getParameter("dateend");
		if(value==null || value.equals("")) {
			endTime = dateUtil.getThisDayEnd();
			endTime = dateUtil.getEndTime(endTime);
		} else {
			if(value.indexOf("-")>=0)
				value = value.replaceAll("-", "");
			value += "235959";
			endTime = dateUtil.getDateFromSerial(value);
		}
		
		value = request.getParameter("faultId");
		if(value!=null&&!value.equals(""))
			faultId = Long.parseLong(value);
		value = request.getParameter("keyword");
		if(value!=null)
			keyWord = new String(request.getParameter("keyword").getBytes("iso8859-1"), "UTF-8");
		value = request.getParameter("equip_type");
		if(value!=null)
			equipType = new String(request.getParameter("equip_type").getBytes("iso8859-1"), "UTF-8");
		value = request.getParameter("equip_name");
		if(value!=null)
			equipName = new String(request.getParameter("equip_name").getBytes("iso8859-1"), "UTF-8");
		value = request.getParameter("equip_id");
		if(value!=null)
			equipNumber = new String(request.getParameter("equip_id").getBytes("iso8859-1"), "UTF-8");
		value = request.getParameter("reporter");
		if(value!=null)
			reporter = new String(request.getParameter("reporter").getBytes("iso8859-1"), "UTF-8");
		value = request.getParameter("department");
		if(value!=null)
			department = new String(request.getParameter("department").getBytes("iso8859-1"), "UTF-8");
		
		// 格式检查
		if(faultId!=0 && keyWord!=null && startTime==null && endTime==null && equipType==null && equipName==null && equipNumber==null && reporter==null && department==null) {
			JSONObject json = new JSONObject();
			json.put("ret", 3);
			json.put("msg", "当前数据格式有问题！");
			respond(response, json);
			return;
		}
		
		StatManagerBean statBean = new  StatManagerBean();
		// 进行查询
		if(faultId>0) {		// 加上故障单编号查询 [zuow,2012/05/04]
			statBean.searchFault(faultId);
		} else if(keyWord!=null) {		// 关键字查询 [zuow, 2012/05/04]
			statBean.searchFault(keyWord);
		} else {
			statBean.searchFault(startTime, endTime, equipType, equipName, equipNumber, reporter, department, userType);
		}
		// 获取查询结果
		ArrayList<ArrayList<String>> arrResults = statBean.getFormatRestult();
		
		/// 根据传入的条件进行查询
		ArrayList<Long> arrFaultId = new ArrayList<Long>();
		// json对象
		JSONObject jo = new JSONObject();
		
		if(arrResults!=null) {
			Iterator itResult = arrResults.iterator();
			while(itResult.hasNext()) {
				ArrayList arrResult = (ArrayList) itResult.next();
				
				long orderId = Long.parseLong(arrResult.get(0).toString());
				
				arrFaultId.add(orderId);
			}
		}
		
		/// 结构化将查询结果详情
		JSONArray ja = new JSONArray();		
		for(Long faultid : arrFaultId) {
			Map<String, String> mapDetail = statBean.queryDetail(faultid);				
			if(mapDetail!=null) {
				JSONObject jso = new JSONObject();
				jso.put(_arrStr[0], mapDetail.get(_arrStr[0]));
				jso.put(_arrStr[1], mapDetail.get(_arrStr[1]));
				jso.put(_arrStr[2], mapDetail.get(_arrStr[2]));
				for (int i=3; i<_arrStr.length; i++) {
					if(mapDetail.get(_arrStr[i])==null)
						jso.put(_arrStr[i], "");
					else
						jso.put(_arrStr[i], mapDetail.get(_arrStr[i]));
				}
				
				ja.add(jso);
			}
		}
		jo.put("items", ja);
		jo.put("ret", 0);
		
		// 将结构化数据输出到文件流中
		String fileName = "Output-" + dateUtil.getDateSerial(1) + ".json";
		
		String filename = URLEncoder.encode(fileName, encode);
		response.reset();
		response.setContentType(contentType);
		response.addHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
		byte[] buffer = jo.toString().getBytes();
		response.setContentLength(buffer.length);
		
		// 如果文件长度大于0
		if(buffer.length > 0) {
			// 创建输入流
			byte[] buf = new byte[2048];
			InputStream inStream = new ByteArrayInputStream(buffer);
			// 创建输出流
			ServletOutputStream servletOS = response.getOutputStream();
			int readLength;
			while (((readLength=inStream.read(buf)) != -1)) {
                servletOS.write(buf, 0, readLength);
            }
			inStream.close();
			servletOS.flush();
			servletOS.close();
			return;
		} else {
			jo.put("ret", 4);
			jo.put("msg", "当前文件内容为空！");
			String str = jo.toString();
			System.out.println("OutputResultsServlet get:");
			System.out.println(str);
			response.setContentType("text/json; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.write(str);
		}

	}

	protected void respond(HttpServletResponse response, JSONObject json) throws ServletException, IOException {
		String str = json.toString();
		System.out.println("OutputResultsServlet get:");
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
