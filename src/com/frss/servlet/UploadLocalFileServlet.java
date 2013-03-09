package com.frss.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.frss.bean.FaultReportManagerBean;
import com.frss.bean.LocalFileManagerBean;
import com.frss.dao.main.UserDAO;
import com.frss.model.main.FaultRepairReport;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class UploadLocalFileServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static String[] _arrStr = {"filename"};
	
	private static String localFilePath = ".";
	private String localFileFullPath = null; 
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadLocalFileServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
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
		int userType = Integer.parseInt(arrUser.get(3));
		if(userId<0 || userType!=UserDAO.Regiment) {
			/// log 当前登录用户非法
			JSONObject json = new JSONObject();
			json.put("ret", 2);
			json.put("msg", "当前登录用户非法！");
			respond(response, json);
			return;
		}
		
		boolean upload=false;		// 上传成功标识
		try {
			// 生成DiskFileItemFactory工厂
	    	DiskFileItemFactory factory = new DiskFileItemFactory();
    	 
	    	// 将DiskFileItemFactory对象传给ServletFileUpload构造方法，生成上传类ServletFileUpload的对象
    	    ServletFileUpload up = new ServletFileUpload(factory);
	    	// 设置允许用户上传文件大小,单位:字节，这里设为2M
	    	up.setSizeMax(2 * 1024 * 1024);
	    	// 设置编码，解决上传文件名乱码
//	    	up.setHeaderEncoding("GB2312");/**按照JDK的介绍，此方法是解决上传文件名中文乱码问题，但是后面测试发现不能实现*/
	    	//获得允许用户上传文件大小
	    	long maxSize = up.getSizeMax();
	    	
    	    List<FileItem> ls = up.parseRequest(request);	    	    
            if(ls!=null&&ls.size()>0){
           	FileItem fileItem=ls.get(0);
				if (fileItem != null && fileItem.getName() != null && !fileItem.getName().trim().equals("")) {
					String name = fileItem.getName().substring(fileItem.getName().lastIndexOf("\\") + 1);
					if (name.endsWith(".xml") == false) {
						upload = false;
					}
					if (fileItem.isFormField() == false) {
				        File mkr = new File(localFilePath);		    	       
					    if(mkr.exists()==false){
						    mkr.mkdir();			
					    }
					    
					    localFileFullPath = localFilePath + File.separator + name; 
					    mkr = new File(localFileFullPath);
						fileItem.write(mkr);
						upload = true;
					}
				}
	    	 }
		} catch (Exception e) {
			JSONObject json = new JSONObject();
			json.put("ret", 2);
			json.put("msg", "上传故障文件出现异常！");
			respond(response, json);
			return;
		}
		
		// 判断上传文件是否成功
		if(!upload) {
			JSONObject json = new JSONObject();
			json.put("ret", 2);
			json.put("msg", "上传故障文件失败！");
			respond(response, json);
			return;			
		}
		
		/// 获取本地文件名
		String fileName = localFileFullPath;
		
		// 进行解析
		LocalFileManagerBean localFile = new LocalFileManagerBean();
		LinkedHashMap<Integer, LinkedHashMap<String, String>> mapFault = localFile.importLocalFile(fileName);
		if(mapFault==null) {
			JSONObject json = new JSONObject();
			json.put("ret", 3);
			json.put("msg", "本地文件格式不正确或者导入本地文件错误！");
			respond(response, json);		
			return;
		}
				
		// json对象
		JSONObject jo = new JSONObject();
		JSONArray jaFaults = new JSONArray();
		JSONObject jaFault = new JSONObject();
		Iterator itKey = mapFault.entrySet().iterator();
		while(itKey.hasNext()) {
			Map.Entry<Integer, Map<String, String>> entrySet = (Map.Entry<Integer, Map<String, String>>)itKey.next();
			int index = entrySet.getKey();
			Map<String, String> fault = entrySet.getValue();
			
			Iterator itField = fault.entrySet().iterator();
			while(itField.hasNext()) {
				Map.Entry<String, String> entryFault = (Map.Entry<String, String>)itField.next();
				String field = entryFault.getKey();
				String value = entryFault.getValue();
				
				jaFault.put(field, value);				
			}
			
			jaFaults.add(jaFault);
		}
		
		jo.put("items", jaFaults);
		jo.put("ret", 0);
		respond(response, jo);
	}

	protected void respond(HttpServletResponse response, JSONObject json) throws ServletException, IOException {
		String str = "<textarea>" + json.toString() + "</textarea>";
		System.out.println("UploadLocalFileServlet get:");
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
