package kr.or.ddit.board.handler;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;

import kr.or.ddit.comm.handler.CommandHandler;
import kr.or.ddit.comm.service.AtchFileServiceImpl;
import kr.or.ddit.comm.service.IAtchFileService;
import kr.or.ddit.board.service.IMemberService;
import kr.or.ddit.board.service.MemberServiceImpl;
import kr.or.ddit.board.vo.AtchFileVO;
import kr.or.ddit.board.vo.BoardVO;
import kr.or.ddit.util.FileUploadRequestWrapper;

public class InsertBoardHandler implements CommandHandler {
	private static final String VIEW_PAGE = "/WEB-INF/view/board/insertBoardForm.jsp";

	@Override
	public boolean isRedirect(HttpServletRequest req) {
		if(req.getMethod().equals("GET")) { // Get방식인 경우. foward
			return false;
		}else { // POST 방식인 경우... redirect
			return true;
		}
	}

	@Override
	public String process(HttpServletRequest req, HttpServletResponse resp) 
			throws Exception {
		
		if(req.getMethod().equals("GET")) { //GET방식인 경우 isRedirect을 하지 않는다
			return VIEW_PAGE;
		}else { //POST 방식인 경우 isRedirect를 한다 
			
			FileItem item = ((FileUploadRequestWrapper)req).getFileItem("atchFile"); // 맵 데이터 가져옴
			
			AtchFileVO atchFileVO = new AtchFileVO();
			
			IAtchFileService fileService = AtchFileServiceImpl.getInstance();
			atchFileVO = fileService.saveAtchFile(item); // 가져온 파일 데이터 가져와서 파일 저장 서비스 실행하여 파일을 가져옴.
			
			// 1. 요청파라티터 정보 가져오기
			String memId = req.getParameter("memId");
			String memName = req.getParameter("memName");
			String memTel = req.getParameter("memTel");
			String memAddr = req.getParameter("memAddr");
			
			// 2. 서비스 객체 생성하기
			IMemberService memberService = 
					MemberServiceImpl.getInstance();
			
			// 3. 회원정보 등록하기
			BoardVO mv = new MemberVO();
			mv.setMemId(memId);
			mv.setMemName(memName);
			mv.setMemAddr(memAddr);
			mv.setMemTel(memTel);
			mv.setAtchFileId(atchFileVO.getAtchFileId());
			
			int cnt = memberService.insertMember(mv);
			
			String msg = "";
			
			if(cnt > 0) {
				msg = "성공";
			}else {
				msg = "실패";
			}
			
			// 4. 목록 조회화면으로 이동
			String redirectUrl = req.getContextPath() +
					"/member/list.do?msg=" 
					+ URLEncoder.encode(msg, "UTF-8");
		
			return redirectUrl;
		}
	}

}
