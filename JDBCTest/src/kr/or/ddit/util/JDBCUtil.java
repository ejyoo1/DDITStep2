package kr.or.ddit.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author 유은지
 *JDBC 드라이버를 로딩하고 Connection 객체를 생성하는 메서드로 구성된 클래스
 */
public class JDBCUtil {
//	JDBCUtil클래스 실행할때 static 블럭이 실행됨
	static {
//		드라이버 로딩
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패!!");
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 커넥션 객체 가져오기
	 * @return Connection 객체
	 */
	public static Connection getConnection() {
		try {
			return DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:1521/xe", 
					"pc19", 
					"java");
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 자원반납
	 * @param conn Connection
	 * @param stmt Statement
	 * @param pstmt PreparedStatement
	 * @param rs
	 */
	public static void disConnect(Connection conn, Statement stmt, PreparedStatement pstmt, ResultSet rs) {
		if(rs != null) try {rs.close();} catch (SQLException ex) {}
		if(stmt != null) try {stmt.close();} catch (SQLException ex) {}
		if(pstmt != null) try {pstmt.close();} catch (SQLException ex) {}
		if(conn != null) try {conn.close();} catch (SQLException ex) {}
	}
}
