package kr.or.ddit.member.handler;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.ddit.comm.handler.CommandHandler;
import kr.or.ddit.member.service.IMemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.member.vo.MemberVO;

public class UpdateMemberHandler_NOFILE implements CommandHandler{

	private static final String VIEW_PAGE = "/WEB-INF/view/member/updateForm.jsp";

	@Override
	public boolean isRedirect(HttpServletRequest req) {
		if(req.getMethod().equals("GET")) { // Get방식인 경우.
			return false;
		}else { // POST 방식인 경우... 
			return true;
		}
	}


	@Override
	public String process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		if(req.getMethod().equals("GET")) { // GET 방식인 경우 (update Form 으로 화면 이동)
			String memId = req.getParameter("memId"); // 웹에서 던져준 수정할 아이디 값을 가져옴
			// 1-1 회원 정보 조회
			IMemberService service = MemberServiceImpl.getInstance();
			
			MemberVO mv = service.getMember(memId);
			
			// 2. 모델 정보 등록
			req.setAttribute("memVO", mv); // View 단에서 req 내 객체 꺼내서 쓸 수 있도록 등록
			
			return VIEW_PAGE; // updateForm 으로 화면을 쏴줌
			
		} else { // POST 방식인 경우 (실제 업데이트 하는 부분) ==> submit 한 데이터를 처리함.
			
			// 1. 요청파라미터 정보 가져오기
			String memId = req.getParameter("memId");
			String memName = req.getParameter("memName");
			String memTel = req.getParameter("memTel");
			String memAddr = req.getParameter("memAddr");
			
			// 2. 서비스 객체 생성하기
			IMemberService memberService = MemberServiceImpl.getInstance();
			
			// 3. 회원정보 등록하기
			MemberVO mv = new MemberVO();
			mv.setMemId(memId);
			mv.setMemName(memName);
			mv.setMemTel(memTel);
			mv.setMemAddr(memAddr);
			
			int cnt = memberService.updateMember(mv);
			
			String msg = "";
			if(cnt > 0) {
				msg = "성공";
			}else {
				msg = "실패";
			}
			
			// 4. 목록 조회화면으로 이동
			String redirectUrl = req.getContextPath() + "/member/list.do?msg=" + URLEncoder.encode(msg, "UTF-8");
			
			return redirectUrl;
		}
	}
	
}
