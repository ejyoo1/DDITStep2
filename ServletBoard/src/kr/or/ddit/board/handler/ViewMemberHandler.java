package kr.or.ddit.board.handler;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.ddit.comm.handler.CommandHandler;
import kr.or.ddit.comm.service.AtchFileServiceImpl;
import kr.or.ddit.comm.service.IAtchFileService;
import kr.or.ddit.member.service.IMemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.member.vo.AtchFileVO;
import kr.or.ddit.member.vo.MemberVO;

public class ViewMemberHandler implements CommandHandler {
	
	private static final String VIEW_PAGE = "/WEB-INF/view/member/select.jsp"; // 한건 데이터 조회하는 페이지
	
	@Override
	public boolean isRedirect(HttpServletRequest req) {
		return false; //foward만 하기 위해
	}

	@Override
	public String process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		
		// 데이터 세팅
		String memId = req.getParameter("memId");
		
		// 회원정보 조회
		IMemberService memberService = MemberServiceImpl.getInstance();
		MemberVO mv = memberService.getMember(memId);
		
		if(mv.getAtchFileId() > 0) { // 첨부파일 존재하면...
			// 첨부파일 정보 조회
			AtchFileVO fileVO = new AtchFileVO();
			fileVO.setAtchFileId(mv.getAtchFileId());
			
			IAtchFileService atchFileService = AtchFileServiceImpl.getInstance();
			List<AtchFileVO> atchFileList = atchFileService.getAtchFileList(fileVO); // 파일목록 저장
			
			req.setAttribute("atchFileList", atchFileList);
		}
		
		req.setAttribute("memVO", mv);
		
		return VIEW_PAGE;
		
	}

}
