package kr.or.ddit.member.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import kr.or.ddit.member.service.IMemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.member.vo.MemberVO;

@WebServlet(name = "SelectMemberDetailServlet", urlPatterns = "/member/select.do")
public class SelectMemberDetailServlet extends HttpServlet {
	private static final Logger RESULT_LOGGER = Logger.getLogger(SelectMemberDetailServlet.class);
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String memId = req.getParameter("memId");
		RESULT_LOGGER.debug("★★SelectMemberDetailServlet 결과★★ [req.getParameter]: " + memId);
		
		IMemberService memberService = MemberServiceImpl.getInstance();
		
		MemberVO mv = new MemberVO();
		mv.setMemId(memId);
		
		List<MemberVO> memList = memberService.getSearchMember(mv);
		
		RESULT_LOGGER.debug("★★SelectMemberDetailServlet 결과★★ [가져온 목록 수]: " + memList.size());
		
		req.setAttribute("memList", memList);
		
		RequestDispatcher dispatcher = req.getRequestDispatcher("/member/selectMember.jsp");
		dispatcher.forward(req, resp);
	}
}
