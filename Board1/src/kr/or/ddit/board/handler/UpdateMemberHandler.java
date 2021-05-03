package kr.or.ddit.board.handler;

import java.net.URLEncoder;
import java.util.List;

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

public class UpdateMemberHandler implements CommandHandler{

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
			
			if(mv.getAtchFileId() > 0) { // 첨부파일 존재하면...
				// 첨부파일 정보 조회
				AtchFileVO fileVO = new AtchFileVO();
				fileVO.setAtchFileId(mv.getAtchFileId());
				
				IAtchFileService atchFileService = AtchFileServiceImpl.getInstance();
				List<AtchFileVO> atchFileList = atchFileService.getAtchFileList(fileVO); // 파일목록 저장
				
				req.setAttribute("atchFileList", atchFileList);
			}
			
			// 2. 모델 정보 등록
			req.setAttribute("memVO", mv); // View 단에서 req 내 객체 꺼내서 쓸 수 있도록 등록
			
			return VIEW_PAGE; // updateForm 으로 화면을 쏴줌
			
		} else { // POST 방식인 경우 (실제 업데이트 하는 부분) ==> submit 한 데이터를 처리함.
			// 사용자가 새로운 파일을 첨부하면 아이디를 기준으로 업데이트를 실행하는 것.
			FileItem item = ((FileUploadRequestWrapper)req).getFileItem("atchFile");
			
			AtchFileVO atchFileVO = new AtchFileVO(); // attchFileID를 담기위한 VO를 생성
			
			// 기존의 첨부파일아이디 정보 가져오기 == > Parameter로 넘겨줬던 값이용. 이것을 가지고 첨부파일 저장할 떄 사용
			atchFileVO.setAtchFileId(req.getParameter("atchFile") == null ? -1 : Long.parseLong(req.getParameter("atchFile")));
			
			if(item != null && !item.getName().equals("")) { // 새로운 파일을 첨부 했을 때만 동작하도록 수행
				IAtchFileService fileService = AtchFileServiceImpl.getInstance();
				atchFileVO = fileService.saveAtchFile(item); // 첨부파일 저장
			}
			
			// 1. 요청파라미터 정보 가져오기
			String memId = req.getParameter("memId");
			String memName = req.getParameter("memName");
			String memTel = req.getParameter("memTel");
			String memAddr = req.getParameter("memAddr");
			
			// 2. 서비스 객체 생성하기
			IMemberService memberService = MemberServiceImpl.getInstance();
			
			// 3. 회원정보 수정하기
			MemberVO mv = new MemberVO();
			mv.setMemId(memId);
			mv.setMemName(memName);
			mv.setMemTel(memTel);
			mv.setMemAddr(memAddr);
			mv.setAtchFileId(atchFileVO.getAtchFileId());
			
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
