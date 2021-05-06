package kr.or.ddit.board.handler;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.ddit.board.service.BoardServiceImpl;
import kr.or.ddit.board.service.IBoardService;
import kr.or.ddit.board.vo.BoardVO;
import kr.or.ddit.comm.handler.CommandHandler;

public class SearchBoardHandler implements CommandHandler{
	private static final String VIEW_PAGE = "/WEB-INF/view/board/list.jsp";

	@Override
	public boolean isRedirect(HttpServletRequest req) {
		return false;
	}

	@Override
	public String process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		IBoardService boardService = 
				BoardServiceImpl.getInstance();
		
		String search = req.getParameter("search");
		
		BoardVO bv = new BoardVO();
		bv.setBoardTitle(search);
		bv.setBoardWriter(search);
		bv.setBoardContent(search);
		
		// 2. 회원정보 조회
		List<BoardVO> boardList = boardService.getSearchBoard(bv);
		
		req.setAttribute("boardList", boardList);
		return VIEW_PAGE;
	}
}
