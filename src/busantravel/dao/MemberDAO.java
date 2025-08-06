package busantravel.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import busantravel.db.DBConnectionMgr;

public class MemberDAO {
	DBConnectionMgr pool;
	
	public MemberDAO() {
		pool = DBConnectionMgr.getInstance();
	}
	
	public boolean loginCheck(String id, String pw) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		boolean flag = false;
		
		try {
			con = pool.getConnection();
			sql = "SELECT M_NAME FROM MEMBER WHERE M_ID = ? AND M_PASSWORD = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			rs = pstmt.executeQuery();
			flag = rs.next();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		
		return flag;
	}
}
