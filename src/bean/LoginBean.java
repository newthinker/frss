package bean;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import util.*;

public class LoginBean {
	Connection conn = null;
	Statement st = null;
	ResultSet rs = null;
	ArrayList al = new ArrayList();
	
	public int login(String name,String pwd){
		// TODO 临时，懒得连接数据库了
		{
			al.add("101");
			al.add("user");
			al.add("");
			al.add("1");
			return 1;
		}
//		int temp = -1;
//		conn = DBConn.getConn();
//		try {
//			st = conn.createStatement();
//			rs = st.executeQuery("select * from admin where name='"+name+"'");
//			if(rs.next()){
//				String id = rs.getString("id");
//				String tname = rs.getString("name");
//				String tpwd = rs.getString("pwd");
//				String quanxian = rs.getString("quanxian");
//				if(pwd.equals(tpwd)){
//					al.add(id);
//					al.add(tname);
//					al.add(tpwd);
//					al.add(quanxian);
//					temp = Integer.valueOf(quanxian).intValue();
//				}else{
//					temp = -2;
//				}
//			}else{
//				temp = -3;
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally{
//			DBConn.close(conn,st,rs);
//		}
//		return temp;
	}
	
	public ArrayList getArrayLst(){
		return al;
	}

}
