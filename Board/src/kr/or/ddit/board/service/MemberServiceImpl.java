package kr.or.ddit.board.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ibatis.sqlmap.client.SqlMapClient;

import kr.or.ddit.board.dao.BoardDaoImpl;
import kr.or.ddit.board.dao.IBoardDao;
import kr.or.ddit.board.vo.BoardVO;
import kr.or.ddit.util.SqlMapClientUtil;

public class BoardServiceImpl implements IBoardService {
	
	private static final Logger RESULT_LOGGER = Logger.getLogger(BoardServiceImpl.class); // 최종 결과에 대한 로거
	
	private IBoardDao boardDao; // [private static MemberDaoImpl memDao;] 대신 인터페이스로 사용함.[유지보수 용이]
	private SqlMapClient smc; // 커넥션 객체 담기위한 메서드 객체 선언
	private static IBoardService boardService; // 서비스 객체 생성
	
	private BoardServiceImpl() {
		boardDao = BoardDaoImpl.getInstance();
		smc = SqlMapClientUtil.getInstance();
	}
	
	public static IBoardService getInstance() {
		if(boardService == null) { boardService = new BoardServiceImpl(); }
		return boardService;
	}
	
	@Override
	public int insertBoard(BoardVO bv) {
		int cnt = 0;
		
		try { // DAO throws SQLException 처리 (ibatis 에서 처리하여도 SQL 예외 발생 여지 존재함.)
			cnt = boardDao.insertBoard(smc, bv);
			RESULT_LOGGER.debug("★★Service 결과★★ [1:삽입성공,0:삽입실패] : " + cnt);
		} catch (SQLException e) {
			e.printStackTrace();
		} // ibatis 내 리소스 관리 자동으로 수행되므로, 자원반납 코드 작성하지 않음.
		return cnt;
	}

	@Override
	public boolean checkBoard(String boardId) {
		boolean chk = false;
		try {
			chk = boardDao.checkBoard(smc, boardId);
			RESULT_LOGGER.debug("★★Service 결과★★ [true:DB에 값이 존재함,false:DB에 값이 없음] : " + chk);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return chk;
	}

	@Override
	public List<BoardVO> getAllBoardList() {
		List<BoardVO> boardList = new ArrayList<>();
		try {
			boardList = boardDao.getAllBoardList(smc);
			RESULT_LOGGER.debug("★★Service 결과★★ [가져온 목록 수] : " + boardList.size());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return boardList;
	}

	@Override
	public int updateBoard(BoardVO bv) {
		int cnt = 0;
		try {
			cnt = boardDao.updateBoard(smc, bv);
			RESULT_LOGGER.debug("★★Service 결과★★ [1:수정성공,0:수정실패] : " + cnt);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cnt;
	}

	@Override
	public int deleteBoard(String boardId) {
		int cnt = 0;
		try {
			cnt = boardDao.deleteBoard(smc, boardId);
			RESULT_LOGGER.debug("★★Service 결과★★ [1:삭제성공,0:삭제실패] : " + cnt);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cnt;
	}

	@Override
	public List<BoardVO> getSearchBoard(BoardVO bv) {
		List<BoardVO> boardList = new ArrayList<>();
		
		try {
			boardList = boardDao.getSearchBoard(smc, bv);
			RESULT_LOGGER.debug("★★Service 결과★★ [가져온 목록 수] : " + boardList.size());
		}catch(SQLException ex) {
			ex.printStackTrace();
		}
		return boardList;
	}

	@Override
	public BoardVO getBoard(String boardId) {
		
		BoardVO bv = null;
		
		try {
			bv = boardDao.getBoard(smc, boardId);
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
		return bv;
	}
}
