package kr.or.ddit.board.service;

import java.util.List;

import kr.or.ddit.board.vo.BoardVO;

/**
 * 게시글 정보 처리를 수행하는 서비스
 * @author 유은지
 *
 */
public interface IBoardService {

	/**
	 * 게시글 등록 메서드
	 * @param bv DB에 insert할 자료가 저장된 BoardVO 객체
	 * @return DB 작업이 성공하면 1이상의 값이 반환되고, 실패하면 0이 반환된다.
	 */
 	public int insertBoard(BoardVO bv);
 	
	/**
	 * 주어진 게시글 NO가 존재하는지 여부를 알아내는 메서드 
	 * @param boardNo 게시글NO
	 * @return 해당 게시글 NO가 존재하면 true, 존재하지 않으면 false
	 */
	public boolean getCheckBoard(String boardNo);
	
	/**
	 * 게시글 전체 목록을 가져오는 메서드
	 * @return 게시글 정보를 담고있는 List 객체
	 */
	public List<BoardVO> getBoardAll();
	
	/**
	 * 게시글 정보를 수정하는 메서드
	 * @param bv 게시글정보 객체
	 * @return 작업성공 : 1, 작업 실패 : 0
	 */
	public int updateBoard(BoardVO bv);
	
	/**
	 * 게시글정보를 삭제하는 메서드
	 * @param boardNo 삭제할 게시글NO
	 * @return 작업성공 : 1, 작업 실패 : 0
	 */
	public int deleteBoard(String boardNo);
	
	/**
	 * 게시글을 검색하는 메서드 
	 * @param bv 게시글정보 객체
	 * @return 작업성공 : 1, 작업 실패 : 0
	 */
	public List<BoardVO> getSearchBoard(BoardVO bv);
}
