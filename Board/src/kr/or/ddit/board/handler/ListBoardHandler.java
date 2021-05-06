package kr.or.ddit.board.handler;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.ddit.board.service.BoardServiceImpl;
import kr.or.ddit.board.service.IBoardService;
import kr.or.ddit.board.vo.BoardVO;
import kr.or.ddit.comm.handler.CommandHandler;

public class ListBoardHandler implements CommandHandler {
	
	private static final String VIEW_PAGE = "/WEB-INF/view/board/list.jsp"; // 서블릿이기에 이 경로로 접근 가능함. 웹은 접근할 수 없음.
	
	@Override
	public boolean isRedirect(HttpServletRequest req) {
		return false;
	}

	@Override
	public String process(HttpServletRequest req, HttpServletResponse resp) {
		IBoardService boardService = 
				BoardServiceImpl.getInstance();
		
		// 2. 회원정보 조회
		List<BoardVO> boardList = boardService.getAllBoardList();
		
		req.setAttribute("boardList", boardList);
		
		return VIEW_PAGE;
	}

}
