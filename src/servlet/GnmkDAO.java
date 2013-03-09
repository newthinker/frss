package servlet;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GnmkDAO {
	
	public List getGnmkByParent(String gnmksj) {
		List list = new ArrayList();
		String sql = "select * from GNMK where gnmksj = '" + 
				gnmksj + "' order by gnmkdm";

		Connection c = null;
		Statement stmt = null;
		try {
			c = DBUtils.openConnection();
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				Gnmk gnmk = this.getGnmk(rs);
				list.add(gnmk);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				c.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	private Gnmk getGnmk(ResultSet rs) throws SQLException {
		Gnmk gnmk = new Gnmk();
		gnmk.setId(rs.getString("id"));
		gnmk.setGnmkdm(rs.getString("gnmkdm"));
		gnmk.setGnmkmc(rs.getString("gnmkmc"));
		gnmk.setGnmklj(rs.getString("gnmklj"));
		gnmk.setGnmktb(rs.getString("gnmktb"));
		gnmk.setGnmkbz(rs.getString("gnmkbz"));
		gnmk.setGnmksj(rs.getString("gnmksj"));
		return gnmk;
	}
	
}
