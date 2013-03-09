package com.frss.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import net.sf.json.JSONObject;

import com.frss.bean.ApprovalManagerBean;
import com.frss.bean.AudioManagerBean;
import com.frss.util.ReturnFlag;

public class UploadAudioServlet extends HttpServlet {

	/**
	 * @ 字段 serialVersionUID: TODO
	 * @ 字段类型: long
	 */
	private static final long serialVersionUID = 1L;

	private static String[] _arrStr = {};
	int fieldNum = _arrStr.length;		// 表格字段个数
	
	private static String localFilePath = null;		
	private static String localFullPath = null;		

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadAudioServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	public void init(ServletConfig config) throws ServletException {
        super.init(config); 
        
        if(config.getInitParameter("audiopath")!=null){ 
        	localFilePath=config.getInitParameter("audiopath");
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
		System.out.println("UploadAudioServlet get:");
		
		AudioManagerBean audioManager = new AudioManagerBean();
		Map<String, String> mapAudio = new HashMap<String, String>();
		JSONObject json = new JSONObject();		// 返回值
		
		// 获取当前登录用户Id
		HttpSession session = request.getSession();
		ArrayList<String> arrUser = (ArrayList)session.getAttribute("loginUser");			
		
		try {
			// 获取用户信息
			long userId = Long.parseLong(arrUser.get(0));
			int userType = Integer.parseInt(arrUser.get(3));
			String userName = arrUser.get(1);
			if(userType<0) {
				json.put("ret", 1);
				json.put("msg", "当前登录用户非法，请重新登录！");
				respond(response, json);
				return;
			}		
			mapAudio.put("userId", arrUser.get(0));		
			
			// 获取其它收入信息
//			for (int i=0; i<fieldNum; i++)
//			{
//				System.out.println(_arrStr[i] + ": " + request.getParameter(_arrStr[i]));
//				String value = request.getParameter(_arrStr[i]);
//				if(value!=null) {
//					value = new String(request.getParameter(_arrStr[i]).getBytes("iso8859-1"), "UTF-8");
//					mapAudio.put(_arrStr[i], value);
//				}				
//			}
			
			// 首先上传语音文件
//	        if(getServletConfig().getInitParameter("audiopath")!=null && getServletConfig().getInitParameter("audiopath").length()>0){ 
//	        	localFilePath = getServletConfig().getInitParameter("audiopath");
//	        }else{
//				json.put("ret", 2);
//				json.put("msg", "获取离线语音保存路径失败！");
//				respond(response, json);
//				return;       
//	        }
			
			// 获取其它收入信息
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload up = new ServletFileUpload(factory);
//			up.setSizeMax(8*1024*1024);
//			long maxSize = up.getSizeMax();
			
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
						mapAudio.put(name, value);
					}
				} else {	// 离线语音上传
					String fileName = item.getName();
					fileName = fileName.substring(fileName.lastIndexOf("\\")+1, fileName.length());
					fileName = fileName.toLowerCase();
					long fileSize = item.getSize();
					
					if(fileName.equals("") || fileSize==0) {
						mapAudio.put("filePath", null);
					} else {
						if(fileName.endsWith("wav")==false && fileName.endsWith("WAV")==false) {			
							json.put("ret", 3);
							json.put("msg", "上传的离线语音文件格式错误！");
							respond(response, json);
							return;
						}
						
					    String fullPath = localFullPath + File.separator + fileName;
						System.out.println("fullPath:"+fullPath);
					    File mkr = new File(fullPath);
						item.write(mkr);
						
						mapAudio.put("filePath", localFilePath + File.separator + fileName);
					}				
				}
			}
			if(mapAudio.get("filePath")!=null)
				System.out.println("AudioFilePath:" + mapAudio.get("filePath"));
			else
				System.out.println("AudioFilePath:null");
			
			// 然后插入语音记录信息
			ReturnFlag rf = audioManager.importAudioReport(mapAudio);
			/// 根据返回的标识在前端展现不同的提示语
			if(rf.getFlag()!=0) {
				json.put("ret", 1);
				json.put("msg", "导入离线语音文件失败:"+rf.getMessage());
				System.out.println("导入离线语音文件失败:"+rf.getMessage());
				respond(response, json);
				return;				
			}
			
		} catch (Exception e) {
			json.put("ret", 2);
			json.put("msg", "导入离线语音文件出现异常！");
			System.out.println("导入离线语音文件出现异常！");
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
		String str = "<textarea>" + json.toString() + "</textarea>";
		System.out.println("UploadAudioServlet get:");
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
