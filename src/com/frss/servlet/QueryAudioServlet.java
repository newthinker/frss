package com.frss.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.frss.bean.AudioManagerBean;
import com.frss.bean.FactoryInfoManagerBean;
import com.frss.dao.main.AudioDAO;
import com.frss.dao.main.UserDAO;
import com.frss.model.mapping.FrssAudioInfo;
import com.frss.util.DateUtil;

public class QueryAudioServlet extends HttpServlet {

	/**
	 * @ 字段 serialVersionUID: TODO
	 * @ 字段类型: long
	 */
	private static final long serialVersionUID = 1L;
	private static String[] _arrStr = {"id", "keyid", "status", "reporter", "reporttime", "filepath"};

    /**
     * @see HttpServlet#HttpServlet()
     */
    public QueryAudioServlet() {
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
		if(userId<0 || userType!=UserDAO.Formater) {
			/// log 当前登录用户非法
			JSONObject json = new JSONObject();
			json.put("ret", 2);
			json.put("msg", "当前登录用户非法！");
			respond(response, json);
			return;
		}
		
		// 状态
		int status = 0;
		String value = request.getParameter("status");
		if(value==null || value=="") {
			status = 0;
		}
		status = Integer.parseInt(value);
		if(status<0) {
			JSONObject json = new JSONObject();
			json.put("ret", 3);
			json.put("msg", "获取状态参数非法！");
			respond(response, json);
			return;
		}
		
		// json对象
		JSONObject jo = new JSONObject();
		JSONArray ja = new JSONArray();
		
		// 获取所有离线语音记录 [zuow, 2012/05/03]
		AudioDAO audioDAO = new AudioDAO();
		ArrayList<FrssAudioInfo> arrAudio = audioDAO.queryAudio(status);
		if(arrAudio!=null && arrAudio.size()>0) {
			DateUtil dateUtil = new DateUtil();
			int id = 1;
			for (FrssAudioInfo audioInfo : arrAudio ) {
				JSONObject jso = new JSONObject();
				jso.put(_arrStr[0], id);
				jso.put(_arrStr[1], Long.toString(audioInfo.getKeyId()));
				jso.put(_arrStr[2], Integer.toString(audioInfo.getStatus()));
				jso.put(_arrStr[3], audioInfo.getReporter());
				String timeString = dateUtil.getTimeString(audioInfo.getReportTime());
				jso.put(_arrStr[4], timeString);
				jso.put(_arrStr[5], audioInfo.getFilePath());
				ja.add(jso);
				
				id++;
			}
		}

		jo.put("items", ja);
		jo.put("ret", 0);
		String str = jo.toString();
		System.out.println("QueryFactoryServlet get:");
		System.out.println(str);
		response.setContentType("text/json; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.write(str);
	}

	protected void respond(HttpServletResponse response, JSONObject json) throws ServletException, IOException {
		String str = json.toString();
		System.out.println("QueryAudioServlet get:");
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
