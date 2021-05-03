package kr.or.ddit.board.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import com.ibatis.sqlmap.client.SqlMapClient;

import kr.or.ddit.board.vo.BoardVO;

public class BoardDaoImpl implements IBoardDao {
	
	/**
	 * 로그 객체 생성
	 */
	private static final Logger RESULT_LOGGER = Logger.getLogger(BoardDaoImpl.class); // 최종 결과에 대한 로거
	
	/**
	 * 필요한 객체 생성
	 */
	private static IBoardDao boardDao; // [private static MemberDaoImpl memDao;] 대신 인터페이스로 사용함.[유지보수 용이]
	private BoardDaoImpl() {}
	
	/**
	 * 싱글톤 패턴
	 * @return IMemberDao 리턴
	 */
	public static IBoardDao getInstance() { 
		if( boardDao == null) { boardDao = new BoardDaoImpl(); }
		return boardDao;
	}
	
	/**
	 * 멤버 등록 관련 DAO - DB 호출 및 결과 Service 로 반환
	 */
	@Override
	public int insertBoard(SqlMapClient smc, BoardVO bv) throws SQLException {
		
		int cnt = 0;
		Object obj = smc.insert("board.insertBoard", bv);
		if(obj == null) { cnt = 1;	} // 데이터 삽입 성공
		RESULT_LOGGER.debug("★★DAO 결과★★ [1:삽입성공,0:삽입실패] : " + cnt);
		
		return cnt;
	}

	/**
	 * DB에 값이 존재하는지 여부를 판단하기 위한 DAO - DB 호출 및 결과 Service 로 반환
	 */
	@Override
	public boolean checkBoard(SqlMapClient smc, String boardId) throws SQLException { 
		boolean chk = false;
		int cnt = (int) smc.queryForObject("board.getBoard", boardId);
		if(cnt > 0) { chk = true; } // 중복값이 있음.
		RESULT_LOGGER.debug("★★DAO 결과★★ [true:DB에 값이 존재함,false:DB에 값이 없음] : " + chk);
		
		return chk;
	}

	/**
	 * 전체 멤버 목록을 가져오는 DAO - DB 호출 및 결과 Service 로 반환
	 */
	@Override
	public List<BoardVO> getAllBoardList(SqlMapClient smc) throws SQLException {
		List<BoardVO> boardList = smc.queryForList("board.getBoardAll");
		RESULT_LOGGER.debug("★★DAO 결과★★ [가져온 목록 수] : " + boardList.size());
		
		return boardList;
	}

	/**
	 * 멤버를 수정하는 DAO - DB 호출 및 결과 Service 로 반환
	 */
	@Override
	public int updateBoard(SqlMapClient smc, BoardVO bv) throws SQLException {
		int cnt = 0;
		cnt = smc.update("board.updateBoard", bv);
		RESULT_LOGGER.debug("★★DAO 결과★★ [1:수정성공,0:수정실패] : " + cnt);
		
		return cnt;
	}

	/**
	 * 멤버를 삭제하는 DAO - DB 호출 및 결과 Service 로 반환
	 */
	@Override
	public int deleteBoard(SqlMapClient smc, String boardId) throws SQLException {
		int cnt = 0;
		cnt = smc.delete("board.deleteBoard", boardId);
		RESULT_LOGGER.debug("★★DAO 결과★★ [1:삭제성공,0:삭제실패] : " + cnt);
		
		return cnt;
	}
	
	/**
	 * 필터 검색을 위한 DAO [Dynamic Query] - DB 호출 및 결과 Service 로 반환
	 */
	@Override
	public List<BoardVO> getSearchBoard(SqlMapClient smc, BoardVO bv) throws SQLException {
		List<BoardVO> boardList = smc.queryForList("board.getSearchBoard", bv);
		RESULT_LOGGER.debug("★★DAO 결과★★ [가져온 목록 수] : " + boardList.size());
		
		return boardList;
	}

	@Override
	public BoardVO getBoard(SqlMapClient smc, String boardId) throws SQLException {
		BoardVO bv = (BoardVO) smc.queryForObject("board.getBoardInfo", boardId);
	
		return bv;
	}
	
}
