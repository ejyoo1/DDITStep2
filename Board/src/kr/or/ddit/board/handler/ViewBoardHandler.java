package kr.or.ddit.board.handler;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.ddit.board.service.BoardServiceImpl;
import kr.or.ddit.board.service.IBoardService;
import kr.or.ddit.board.vo.AtchFileVO;
import kr.or.ddit.board.vo.BoardVO;
import kr.or.ddit.comm.handler.CommandHandler;
import kr.or.ddit.comm.service.AtchFileServiceImpl;
import kr.or.ddit.comm.service.IAtchFileService;

public class ViewBoardHandler implements CommandHandler {
	
	private static final String VIEW_PAGE = "/WEB-INF/view/board/select.jsp"; // 한건 데이터 조회하는 페이지
	
	@Override
	public boolean isRedirect(HttpServletRequest req) {
		return false; //foward만 하기 위해
	}

	@Override
	public String process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		
		String boardNo = req.getParameter("boardNo");
		
		IBoardService service = BoardServiceImpl.getInstance();
		BoardVO bv = service.getBoard(boardNo);
		
		if(bv.getAtchFileId() > 0) { // 첨부파일 존재하면...
			// 첨부파일 정보 조회
			AtchFileVO fileVO = new AtchFileVO();
			fileVO.setAtchFileId(bv.getAtchFileId());
			
			IAtchFileService atchFileService = AtchFileServiceImpl.getInstance();
			List<AtchFileVO> atchFileList = atchFileService.getAtchFileList(fileVO); // 파일목록 저장
			
			req.setAttribute("atchFileList", atchFileList);
		}
		
		req.setAttribute("boardVo", bv);
		
		return VIEW_PAGE;
		
	}

}
