package kr.or.ddit.board.service;

import java.util.List;

import kr.or.ddit.board.vo.BoardVO;

/**
 * 회원정보 처리를 수행하는 서비스
 * @author 유은지
 *
 */
public interface IBoardService {
	
	public int insertBoard(BoardVO bv);
	
	public boolean checkBoard(String boardId);
	
	public List<BoardVO> getAllBoardList();
	
	public int updateBoard(BoardVO bv);
	
	public int deleteBoard(String boardId);
	
	public List<BoardVO> getSearchBoard(BoardVO bv);
	
	public BoardVO getBoard(String boardId);
}
