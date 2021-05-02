package kr.or.ddit.board.dao;

import java.sql.SQLException;
import java.util.List;

import com.ibatis.sqlmap.client.SqlMapClient;

import kr.or.ddit.board.vo.BoardVO;

/**
 * 실제 DB와 연결해서 SQL문을 수행하여 결과를 작성한다.
 * @author 유은지
 *
 */
public interface IBoardDao {
	
	public int insertBoard(SqlMapClient smc, BoardVO bv) throws SQLException;
	
	public boolean checkBoard(SqlMapClient smc, String boardId) throws SQLException;
	
	public List<BoardVO> getAllBoardList(SqlMapClient smc) throws SQLException;
	
	public int updateBoard(SqlMapClient smc, BoardVO bv) throws SQLException;
	
	public int deleteBoard(SqlMapClient smc, String boardId) throws SQLException;
	
	public List<BoardVO> getSearchBoard(SqlMapClient smc, BoardVO bv) throws SQLException;
	
	public BoardVO getBoard(SqlMapClient smc, String boardId) throws SQLException;
}
