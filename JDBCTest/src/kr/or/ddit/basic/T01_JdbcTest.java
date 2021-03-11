package kr.or.ddit.basic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class T01_JdbcTest {
	/**
	 JDBC를 이용한 데이터베이스 처리 순서
	 
	 순서 : JDBC 드라이버 로딩 => 해당 DB에 접속 => 질의(SQL명령 수행) => 질의 결과를 받아서 처리 => 종료(자원반납)
	 
	 */
	
	public static void main(String[] args) {
//		DB작업에 필요한 객체 변수 선언
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null; // 쿼리문이 select 일 경우에필요
		
		try {
//			1. 드라이버 로딩
//			드라이버를 못찾는 경우가 있으므로 예외 처리를 해야됨.
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
//			2. DB에 접속 (Connection 객체 생성)
			String url = "jdbc:oracle:thin:@localhost:1521/xe";
			String user = "pc19";
			String password = "java";
			
			conn = DriverManager.getConnection(url, user, password);
			
//			3. 쿼리 실행 위한 Statement객체 생성 => Connection객체를이용한다.
			stmt = conn.createStatement();
			
//			4. SQL문을 Statement객체를 이용하여 실행하고 실행결과를 ResultSet객체에 저장한다.
			String sql = "select * from lprod"; // 실행할 SQL문
			rs = stmt.executeQuery(sql);
			
//			5. ResultSet객체에 저장되어 있는 자료를 반복문과 next()메서드를 이용하여 차례대로 읽어와 처리한다.
			System.out.println("실행한 쿼리문 : " + sql);
			System.out.println("===쿼리문 실행 결과===");
			
//			rs.next() => ResultSet객체의 데이터를 가리키는 포인터를 다음 레코드로 이동시키고 그곳에 자료가 있으면 true, 없으면 false를 반환한다.
			while(rs.next()) {
//				컬럼의 자료를 가져오는 방법
//				방법1) rs.get자료형 이름("컬럼명")
//				방법2) rs.get자료형 이름(컬럼번호) => 컬럼번호는 1부터 시작함
				System.out.println("lprod_id : " + rs.getInt("lprod_id"));
				System.out.println("lprod_gu : " + rs.getString("lprod_gu"));
				System.out.println("lprod_nm : " + rs.getString("lprod_nm"));
				System.out.println("---------------------------------------");
			}
			System.out.println("출력 끝...");
			
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//종료
			if(rs != null) try {rs.close();} catch (SQLException ex) {}
			if(stmt != null) try {stmt.close();} catch (SQLException ex) {}
			if(conn != null) try {conn.close();} catch (SQLException ex) {}
		}
	}
}
