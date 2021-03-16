package kr.or.ddit.board.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import kr.or.ddit.board.vo.BoardVO;

/**
 * 실제 DB와 연결해서 SQL문을 수행하여 결과를 작성한다.
 * @author 유은지
 *
 */
public interface IBoardDao {
	
	/**
	 * BoardVO 객체에 담겨진 자료를 DB에 insert하는 메서드이다.
	 * @param conn 커넥션 객체
	 * @param bv DB에 insert할 자료가 저장된 BoardVO 객체
	 * @return DB 작업이 성공하면 1이상의 값이 반환되고, 실패하면 0이 반환된다.
	 * @throws SQLException JDBC관련 예외 객체 발생
	 */
	public int insertBoard(Connection conn, BoardVO bv) throws SQLException;
	
	/**
	 * 주어진 BoardNo가 존재하는지 여부를 알아내는 메서드
	 * @param conn 커넥션 객체
	 * @param boardNo 게시판 번호
	 * @return 해당 게시판이 존재하면 true, 존재하지 않으면 false
	 * @throws SQLException JDBC관련 예외객체 발생
	 */
	public boolean checkBoard(Connection conn, String boardNo) throws SQLException;
	
	/**
	 * DB의 jdbc_board 테이블의  전체 레코드를 가져와서 List에 담아 반환하는 메서드
	 * @param conn 커넥션 객체
	 * @return 게시글 정보를 담고있는 List 객체
	 * @throws SQLException JDBC관련 예외객체 발생
	 */
	public List<BoardVO> getAllBoardList(Connection conn) throws SQLException;
	
	/**
	 * 하나의 게시글 정보를 이용하여 DB를 업데이트 하는 메서드
	 * @param conn 커넥션 객체
	 * @param bv 게시글 정보 객체
	 * @return 작업성공 : 1, 작업 실패 : 0
	 * @throws SQLException JDBC관련 예외객체 발생
	 */
	public int updateBoard(Connection conn, BoardVO bv) throws SQLException;
	
	/**
	 * 게시글번호를 매개변수로 받아서 그 게시글을 삭제하는 메서드
	 * @param conn 커넥션 객체
	 * @param boardNo 삭제할 게시글NO
	 * @return 작업성공 : 1, 작업 실패 : 0
	 * @throws SQLException JDBC관련 예외객체 발생
	 */
	public int deleteBoard(Connection conn, String boardNo) throws SQLException;
	
	/**
	 * 검색할 게시글 정보를 매개변수로 받아서 그 게시글을 검색하는 메서드 
	 * @param conn 커넥션 객체 
	 * @param bv 게시글 정보 객체 
	 * @return 게시글 정보를 담고있는 객체 
	 * @throws SQLException JDBC 관련 예외객체 발생 
	 */
	public List<BoardVO> boardSerch(Connection conn, BoardVO bv) throws SQLException;

}
