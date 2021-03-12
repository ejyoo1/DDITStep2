package kr.or.ddit.member.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import kr.or.ddit.member.vo.MemberVO;

/**
 * 실제 DB와 연결해서 SQL문을 수행하여 결과를 작성한다.
 * @author 유은지
 *
 */
public interface IMemberDao {
	
	/**
	 * MemberVO 객체에 담겨진 자료를 DB에 insert하는 메서드
	 * @param conn 커넥션 객체
	 * @param mv DB에 insert할 자료가 저장된 MemberVO 객체
	 * @return DB작업이 성공하면 1이상의 값이 반환되고, 실패하면 0이 반환된다.
	 * @throws SQLException JDBC관련 예외객체 발생
	 */
	public int insertMember(Connection conn, MemberVO mv) throws SQLException;
	
	/**
	 * 주어진 회원 ID가 존재하는지 여부를 알아내는 메서드
	 * @param conn 커넥션 객체
	 * @param memId 회원 ID
	 * @return 해당 회원 ID가 존재하면 true, 존재하지 않으면 false
	 * @throws SQLException JDBC관련 예외객체 발생
	 */
	public boolean checkMember(Connection conn, String memId) throws SQLException;
	
	/**
	 * DB의 mymember 테이블의 전체 레코드를 가져와서 List에 담아 반환하는 메서드
	 * @param conn 커넥션 객체
	 * @return 회원정보를 담고있는 List 객체
	 * @throws SQLException JDBC관련 예외객체 발생
	 */
	public List<MemberVO> getAllMemberList(Connection conn) throws SQLException;
	
	/**
	 * 하나의 회원정보를 이용하여 DB를 update하는 메서드
	 * @param conn 커넥션 객체
	 * @param mv 회원정보 객체
	 * @return 작업성공 : 1, 작업 실패 : 0
	 * @throws SQLException JDBC관련 예외객체 발생
	 */
	public int updateMember(Connection conn, MemberVO mv) throws SQLException;
	
	/**
	 * 회원ID를 매개변수로 받아서 그 회원정보를 삭제하는 메서드
	 * @param conn 커넥션 객체
	 * @param memId 삭제할 회원ID
	 * @return 작업성공 : 1, 작업 실패 : 0
	 * @throws SQLException JDBC관련 예외객체 발생
	 */
	public int deleteMember(Connection conn, String memId) throws SQLException;
}
