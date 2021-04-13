package kr.or.ddit.member.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import kr.or.ddit.member.dao.MemberDaoImpl;
import kr.or.ddit.member.service.IMemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.member.vo.MemberVO;

/**
 * 컨트롤러 단을 별도의 서블릿으로 구현
 * @author 유은지
 * 전체 조회하는 서블릿
 * 
 * 컨트롤러 URL 요청 -> 서블릿 시작 -> 필요한 회원정보 위한 서비스 객체 생성 -> 회원 목록 조회 -> jsp 토스 (setAttribute)
 */

//핸들러 구현으로 이 클래스는 사용하지 않음.
public class SelectMemberAllServlet extends HttpServlet { // list.do
	private static final Logger RESULT_LOGGER = Logger.getLogger(SelectMemberAllServlet.class);
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		IMemberService memberService = MemberServiceImpl.getInstance(); // 1. 서비스 객체 생성
		
		List<MemberVO> memList = memberService.getAllMemberList(); // 2. 회원 정보 조회
		RESULT_LOGGER.debug("★★SelectMemberAllServlet 결과★★ [가져온 목록 수] : " + memList.size());
		
		req.setAttribute("memList", memList); // 요청에 memList 저장
		
		RequestDispatcher dispatcher = req.getRequestDispatcher("/member/list.jsp"); // 작업 전달
		dispatcher.forward(req, resp); // 뷰 페이지로 전달
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
