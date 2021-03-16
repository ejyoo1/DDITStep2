package kr.or.ddit.board.dao;

import java.sql.SQLException;
import java.util.List;

import com.ibatis.sqlmap.client.SqlMapClient;

import kr.or.ddit.board.vo.BoardVO;

public class BoardDaoImpl implements IBoardDao{
//	boardDao 싱글톤 패턴 적용
	private static IBoardDao boardDao;
	
//	제한된 생성자 생성
	private BoardDaoImpl() {
		
	}
	
//	객체 획득 메서드 생성
	public static IBoardDao getInstance() {
		if(boardDao == null) {
			boardDao = new BoardDaoImpl();
		}
		return boardDao;
	}

	@Override
	public int insertBoard(SqlMapClient smc, BoardVO bv) throws SQLException {
		int cnt = 0;

		Object obj = smc.insert("board.insertBoard", bv);
		
		if(obj == null) {
//			데이터 삽입 성공
			cnt = 1;
		}
		return cnt;
	}

	@Override
	public boolean getCheckBoard(SqlMapClient smc, String boardNo) throws SQLException {
		boolean chk = false;
		
//		id="getCheckBoard" resultClass="int"로 인하여 int 로 형변환
		int cnt = (int) smc.queryForObject("board.getCheckBoard", boardNo);
		
		if(cnt > 0) {
			chk = true;
		}
		return chk;
	}

	@Override
	public List<BoardVO> getBoardAll(SqlMapClient smc) throws SQLException {
//		id="getBoardAll" resultMap="boardMap" class="boardVO"
		List<BoardVO> boardList = smc.queryForList("board.getBoardAll");
		
		return boardList;
	}

	@Override
	public int updateBoard(SqlMapClient smc, BoardVO bv) throws SQLException {
		int cnt = 0;
//		id="updateBoard" parameterClass="boardVO"
		cnt = smc.update("board.updateBoard", bv);
		return cnt;
	}

	@Override
	public int deleteBoard(SqlMapClient smc, String boardNo) throws SQLException {
		int cnt = 0;
//		id="deleteBoard" parameterClass="String"
		cnt = smc.delete("board.deleteBoard", boardNo);
		return cnt;
	}

	@Override
	public List<BoardVO> getSearchBoard(SqlMapClient smc, BoardVO bv) throws SQLException {
//		id="getSearchBoard" resultMap="boardMap" class="boardVO"
		List<BoardVO> boardList = smc.queryForList("board.getSearchBoard", bv);
		
		return boardList;
	}

}
