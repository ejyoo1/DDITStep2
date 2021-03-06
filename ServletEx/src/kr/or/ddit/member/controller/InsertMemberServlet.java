package kr.or.ddit.member.controller;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import kr.or.ddit.member.service.IMemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.member.vo.MemberVO;

//핸들러 구현으로 이 클래스는 사용하지 않음.
public class InsertMemberServlet extends HttpServlet{
	private static final Logger RESULT_LOGGER = Logger.getLogger(InsertMemberServlet.class);
	
	/**
	 * 화면을 그려주는 jsp로 이동
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequestDispatcher dispatcher = 
				req.getRequestDispatcher("/member/insertForm.jsp");
		dispatcher.forward(req,  resp);
	}
	
	/**
	 * 멤버를 새로 등록
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		// 1. 요청 파라미터 정보 가져오기
		String memId = req.getParameter("memId");
		String memName = req.getParameter("memName");
		String memTel = req.getParameter("memTel");
		String memAddr = req.getParameter("memAddr");
		RESULT_LOGGER.debug("★★InsertMemberServlet 결과★★ [req.getParameter]: " 
				+ memId
				+ "," + memName
				+ "," + memTel
				+ "," + memAddr);
		
		// 2. 서비스 객체 생성하기
		IMemberService memberService = MemberServiceImpl.getInstance();
		
		// 3. 회원 정보 등록하기
		MemberVO mv = new MemberVO();
		mv.setMemId(memId);
		mv.setMemName(memName);
		mv.setMemAddr(memAddr);
		mv.setMemTel(memTel);
		
		int cnt = memberService.insertMember(mv);
		
		RESULT_LOGGER.debug("★★InsertMemberServlet 결과★★ [1:삽입성공,0:삽입실패]: " + cnt);
		
		String msg = "";
		
		if(cnt > 0) {
			msg = "성공";
		}else {
			msg = "실패";
		}
		
		// 4. 목록 조회 회면으로 이동
		String redirectUrl = req.getContextPath() + // 외부에 노출되는 URL은 req.getContextPath()이 꼭 필요하다.
				"/member/list.do?msg=" + URLEncoder.encode(msg, "UTF-8"); // msg 그냥 넣으면 한글 깨지므로 인코딩 작업 필수
		
		resp.sendRedirect(redirectUrl);
		
//		req.getRequestDispatcher(
//						"/member/list.do?msg=" 
//						+ URLEncoder.encode(msg, "UTF-8")).forward(req, resp);
	}
}
