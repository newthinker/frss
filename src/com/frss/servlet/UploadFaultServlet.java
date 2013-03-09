package com.frss.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Iterator;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
//import org.hsqldb.lib.Iterator;

import net.sf.json.JSONObject;

import com.frss.bean.FaultReportManagerBean;
import com.frss.dao.main.UserDAO;
import com.frss.util.ReturnFlag;
import com.frss.util.ValidCheck;

/**
 * Servlet implementation class UploadFaultServlet
 */
//@WebServlet("/UploadFaultServlet")
public class UploadFaultServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String[] _arrStr = {"zbtype", "zbname", "zbserial", "zbnum" ,"zbgzxx", "zbgzsj", 
		"zbxqcl", "zbgzpc", "zbgzbw", "zbsydw", "zbuser", "commiter", "contact", "committime", "zbqzzp", "keyid"};		// 增加faultid，兼容离线语音上报
	int fieldNum = _arrStr.length;		// 表格字段个数
	
	private static String localFilePath = null;		//"." + File.separator + "FaultPhotos";
	private static String localFullPath = null;		//"." + File.separator + "FaultPhotos";

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadFaultServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	public void init(ServletConfig config) throws ServletException {
        super.init(config); 
        
        if(config.getInitParameter("photopath")!=null){ 
        	localFilePath=config.getInitParameter("photopath");
        	localFullPath = this.getServletContext().getRealPath(localFilePath);
        }else{
        	/// log
	        return;	        
        }
        
        //创建目录
        File mkr = new File(localFullPath);
	    if(mkr.exists()==false){
		    mkr.mkdir();			
	    }	    
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("UploadFaultServlet get:");
		request.setCharacterEncoding("UTF-8");
		
		FaultReportManagerBean faultManager = new FaultReportManagerBean();
		Map<String, String> mapFault = new HashMap<String, String>();
		JSONObject json = new JSONObject();		// 返回值
		
		// 获取当前登录用户Id
		HttpSession session = request.getSession();
		ArrayList<String> arrUser = (ArrayList)session.getAttribute("loginUser");
		if(arrUser == null) {
			/// log 当前登录用户非法
			json.put("ret", 1);
			json.put("msg", "当前登录用户非法，请重新登录！");
			respond(response, json);
			return;
		}
		
		try {
			// 获取用户信息
			long userId = Long.parseLong(arrUser.get(0));
			String userName = arrUser.get(1);
			int userType = Integer.parseInt(arrUser.get(3));
			if(userType!=UserDAO.Regiment && userType!=UserDAO.Formater) {	// 团级用户进行故障上报
				/// log 当前登录用户非法
				json.put("ret", 1);
				json.put("msg", "当前登录用户非法，请重新登录！");
				respond(response, json);
				return;
			}		
			mapFault.put("userId", arrUser.get(0));
			
			// 获取故障取证图片保存路径 [zuow, 2012/04/15]
	        if(getServletConfig().getInitParameter("photopath")!=null && getServletConfig().getInitParameter("photopath").length()>0){ 
	        	localFilePath = getServletConfig().getInitParameter("photopath");
	        }else{
				json.put("ret", 2);
				json.put("msg", "获取故障取证图片保存路径失败！");
				respond(response, json);
				return;       
	        }
			
			// 获取其它收入信息
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload up = new ServletFileUpload(factory);
			up.setSizeMax(8*1024*1024);
			long maxSize = up.getSizeMax();
			
			List items = up.parseRequest(request);
			Iterator<List> iter = (Iterator<List>) items.iterator();
			while(iter.hasNext()) {
				FileItem item = (FileItem)iter.next();
				if(item.isFormField()) {		// 普通的表单域
					String name = item.getFieldName();
					String value = item.getString();
					System.out.println(name + ":" + value);
					if(value!=null) {
						value = new String(item.getString().getBytes("iso8859-1"), "UTF-8");
						if(!(ValidCheck.validCheck(value))) {
							JSONObject jo = new JSONObject();
							jo.put("ret", 1);
							jo.put("msg", "参数输入非法:" + value);
							respond(response, jo);
							return;
						}
						mapFault.put(name, value);
					}
				} else {	// 处理图片上传
					String fileName = item.getName();
					fileName = fileName.substring(fileName.lastIndexOf("\\")+1, fileName.length());
					fileName = fileName.toLowerCase();
					long fileSize = item.getSize();
					
					if(fileName.equals("") || fileSize==0) {
						mapFault.put("photoName", null);
					} else {
						if(fileName.endsWith("jpg")==false && fileName.endsWith("jpeg")==false && 
								fileName.endsWith("tif")==false && fileName.endsWith("tiff")==false &&
								fileName.endsWith("gif")==false && fileName.endsWith("png")==false) {				
							json.put("ret", 3);
							json.put("msg", "上传的图片文件格式错误！");
							respond(response, json);
							return;
						}
						
					    String fullPath = localFullPath + File.separator + fileName; 
						System.out.println("fullPath:"+fullPath);
					    File mkr = new File(fullPath);
						item.write(mkr);
						
						mapFault.put("photoName", localFilePath + File.separator + fileName);
					}					
				}
			}
			
			mapFault.put("commiter", userName);
			
			ReturnFlag rf = faultManager.ImportFaultReport(mapFault);
			/// 根据返回的标识在前端展现不同的提示语
			if(rf.getFlag()!=0) {
				json.put("ret", 2);
				json.put("msg", "导入故障上报表单失败:"+rf.getMessage());
				respond(response, json);
				return;		
			}			
		} catch (Exception e) {
			json.put("ret", 3);
			json.put("msg", "导入故障上报表单出现异常！");
			respond(response, json);
			return;		
		}
		
		json.put("ret", 0);
		respond(response, json);
		return;
	}

	protected void respond(HttpServletResponse response, JSONObject json) throws ServletException, IOException {
		String str = "<textarea>" + json.toString() + "</textarea>";
		System.out.println("UploadFaultServlet get:");
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
