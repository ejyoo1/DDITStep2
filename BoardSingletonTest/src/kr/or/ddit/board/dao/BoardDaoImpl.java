package kr.or.ddit.board.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.or.ddit.board.vo.BoardVO;
import kr.or.ddit.util.JDBCUtil3;

public class BoardDaoImpl implements IBoardDao{
	/**
	 * DB에 접근하거나 DB에서 가져온 데이터 처리 관련 객체 생성
	 */
	private PreparedStatement pstmt;
	private ResultSet rs;
	
//	싱글톤 패턴 위한 나 자신 객체 생성
	private static IBoardDao boardDao;
	
	private BoardDaoImpl() {
		
	}
	
	public static IBoardDao getInstance() {
		if(boardDao == null) {
			boardDao = new BoardDaoImpl();
		}
		return boardDao;
	}

	@Override
	public int insertBoard(Connection conn, BoardVO bv) throws SQLException {
		conn = JDBCUtil3.getConnection();
		
		String sql = " INSERT INTO JDBC_BOARD(BOARD_NO, BOARD_TITLE, BOARD_WRITER, BOARD_DATE, BOARD_CONTENT) " + 
				" VALUES(BOARD_SEQ.NEXTVAL, ?, ?, SYSDATE, ?)";
		
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, bv.getBoardTitle());
		pstmt.setString(2, bv.getBoardWriter());
		pstmt.setString(3, bv.getBoardContent());
		
		int cnt = pstmt.executeUpdate();
		
		return cnt;
	}

	@Override
	public boolean checkBoard(Connection conn, String boardNo) throws SQLException {
		boolean chk = false;
		
		String sql = " SELECT COUNT(*) CNT FROM JDBC_BOARD WHERE BOARD_NO=? ";
		
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, boardNo);
		rs = pstmt.executeQuery();
		
		int cnt = 0;
		while(rs.next()) {
			cnt = rs.getInt("cnt");
		}
		
		if(cnt > 0) {
			chk = true;
		}
		
		return chk;
	}

	@Override
	public List<BoardVO> getAllBoardList(Connection conn) throws SQLException {
		List<BoardVO> boardList = new ArrayList<BoardVO>();
		
		String sql = " SELECT * FROM JDBC_BOARD ";
		
		pstmt = conn.prepareStatement(sql);
		rs = pstmt.executeQuery();
		
		while(rs.next()) {
			BoardVO bv = new BoardVO();
			int boardNo = rs.getInt("BOARD_NO");
			String boardTitle = rs.getString("BOARD_TITLE");
			String boardWriter = rs.getString("BOARD_WRITER");
			String boardDate = rs.getString("BOARD_DATE");
			String boardContent = rs.getString("BOARD_CONTENT");
			
			bv.setBoardNo(boardNo);
			bv.setBoardTitle(boardTitle);
			bv.setBoardWriter(boardWriter);
			bv.setBoardDate(boardDate);
			bv.setBoardContent(boardContent);
			
			boardList.add(bv);
		}
		
		return boardList;
	}

	@Override
	public int updateBoard(Connection conn, BoardVO bv) throws SQLException {
		String sql = " UPDATE JDBC_BOARD SET BOARD_TITLE = ?, BOARD_CONTENT = ? WHERE BOARD_NO = ? ";
		
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, bv.getBoardTitle());
		pstmt.setString(2, bv.getBoardContent());
		pstmt.setInt(3, bv.getBoardNo());
		
		int cnt = pstmt.executeUpdate();
		
		return cnt;
	}

	@Override
	public int deleteBoard(Connection conn, String boardNo) throws SQLException {
		String sql = " DELECT FROM JDBC_BOARD WHERE BOARD_NO=? ";
		
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, boardNo);
		int cnt = pstmt.executeUpdate();
		
		return cnt;
	}

	@Override
	public List<BoardVO> boardSerch(Connection conn, BoardVO bv) throws SQLException {
		List<BoardVO> boardList = new ArrayList<BoardVO>();
		String sql = " SELECT * FROM JDBC_BOARD \"\n"
				+ "					+ \"WHERE BOARD_TITLE LIKE '%'||?||'%' AND \"\n"
				+ "					+ \"BOARD_WRITER = ? AND \"\n"
				+ "					+ \"BOARD_CONTENT LIKE '%'||?||'%' ";
		
		pstmt = conn.prepareStatement(sql);
		rs = pstmt.executeQuery();
		
		while(rs.next()) {
			int boardNo = rs.getInt("BOARD_NO");
			String boardTitle = rs.getString("BOARD_TITLE");
			String boardWriter = rs.getString("BOARD_WRITER");
			String boardDate = rs.getString("BOARD_DATE");
			String boardContent = rs.getString("BOARD_CONTENT");
			
			bv.setBoardNo(boardNo);
			bv.setBoardTitle(boardTitle);
			bv.setBoardWriter(boardWriter);
			bv.setBoardDate(boardDate);
			bv.setBoardContent(boardContent);
			
			boardList.add(bv);
		}
		
		return boardList;
	}

}
