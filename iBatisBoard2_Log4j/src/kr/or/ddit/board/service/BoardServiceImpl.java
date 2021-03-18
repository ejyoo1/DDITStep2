package kr.or.ddit.board.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ibatis.sqlmap.client.SqlMapClient;

import kr.or.ddit.board.dao.BoardDaoImpl;
import kr.or.ddit.board.dao.IBoardDao;
import kr.or.ddit.board.vo.BoardVO;
import kr.or.ddit.util.SqlMapClientUtil;

public class BoardServiceImpl implements IBoardService{
	private IBoardDao boardDao;
	private SqlMapClient smc;
	
//	싱글톤 패턴 적용
	private static IBoardService boardService;
	
	private BoardServiceImpl() {
		boardDao = BoardDaoImpl.getInstance();
		smc = SqlMapClientUtil.getInstance();
	}
	
	public static IBoardService getInstance() {
		if(boardService == null) {
			boardService = new BoardServiceImpl();
		}
		return boardService;
	}

	@Override
	public int insertBoard(BoardVO bv) {
		int cnt = 0;
		
		try { //Dao에서 예외처리를 넘겼으므로 Service에서 예외처리를 해야 함.
			cnt = boardDao.insertBoard(smc, bv);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cnt;
		
/****************************************************
		 트랜잭션 코드
		int cnt = 0;
		
		try {
//			트랜잭션 관리 시작 코드
			smc.startTransaction();
			cnt = memDao.insertMember(smc, mv);
//			DB에서 commit 하겠다.
			smc.commitTransaction();
		} catch (SQLException e) {//ibatis에서 처리하더라도 SQL작업에 대한 예외처리는 발생가능성이 있음.
//			Sql문을 실행하여 예외가 발생한 경우 commit 코드가 진행되지 않고 바로 catch문을 들어오게됨.
			smc.endTransaction(); //이것을 사용하여 트랜잭션을 종료하면 롤백과 같은 효과를 볼 수있음.
			try {
				smc.endTransaction();//트랜잭션관련 예외처리 필요함.
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
			e.printStackTrace();
		}//ibatis에서 리소스 관리도 자동으로 해주기 때문에 자원 반납은 하지 않음.
		return cnt;
****************************************************/
	}

	@Override
	public boolean getCheckBoard(String boardNo) {
		boolean chk = false;
		
		try {
			chk = boardDao.getCheckBoard(smc, boardNo);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return chk;
	}

	@Override
	public List<BoardVO> getBoardAll() {
		List<BoardVO> boardList = new ArrayList<>();
		
		try {
			boardList = boardDao.getBoardAll(smc);
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
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cnt;
	}

	@Override
	public int deleteBoard(String boardNo) {
		int cnt = 0;
		
		try {
			cnt = boardDao.deleteBoard(smc, boardNo);
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
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return boardList;
	}

}
