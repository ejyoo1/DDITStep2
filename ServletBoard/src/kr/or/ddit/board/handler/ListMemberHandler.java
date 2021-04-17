package kr.or.ddit.board.handler;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.ddit.comm.handler.CommandHandler;
import kr.or.ddit.board.service.IMemberService;
import kr.or.ddit.board.service.MemberServiceImpl;
import kr.or.ddit.board.vo.MemberVO;

public class ListMemberHandler implements CommandHandler {
	
	private static final String VIEW_PAGE = "/WEB-INF/view/member/list.jsp"; // 서블릿이기에 이 경로로 접근 가능함. 웹은 접근할 수 없음.
	
	@Override
	public boolean isRedirect(HttpServletRequest req) {
		return false;
	}

	@Override
	public String process(HttpServletRequest req, HttpServletResponse resp) {
		
		// 1. 서비스 객체 생성하기
		IMemberService memberService = 
				MemberServiceImpl.getInstance();
		
		// 2. 회원정보 조회
		List<MemberVO> memList = memberService.getAllMemberList();
		
		req.setAttribute("memList", memList);
		
		return VIEW_PAGE;
	}

}
