package util;

import java.sql.*;
public class DBConn {
    static{
        try{
        	Class.forName("com.mysql.jdbc.Driver");
//        	Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//          Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
        }catch(Exception ex){
            ex.printStackTrace();
        }

    }

    public static Connection getConn(){
        try{
        	Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/qcpjgl?user=root&password=");
//        	Connection conn=DriverManager.getConnection("jdbc:mysql://localhost/csgl?useUnicode=true&characterEncoding=utf-8","root","123");
//        	Connection conn=DriverManager.getConnection("jdbc:sqlserver://localhost:1433;DataBaseName=qcpjgl","sa","sql2005");
//       	Connection conn=DriverManager.getConnection("jdbc:odbc:temp");
            return conn;
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
    }
    public static void close(Connection conn,Statement st,ResultSet rs){
    	if(rs!=null){
            try{
                rs.close();
            }catch(SQLException ex){
            }
       }
       if(st!=null){
           try {
               st.close();
           }catch(Exception ex){
           }
       }
       if(conn!=null){
           try{
               conn.close();
           }catch(Exception ex){
           }
       }
    }

}
