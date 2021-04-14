package kr.or.ddit.comm.handler;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.ddit.comm.dao.AtchFileDaoImpl;
import kr.or.ddit.comm.dao.IAtchFileDao;
import kr.or.ddit.member.vo.AtchFileVO;



/**
 * 서블릿을 이용한 파일다운로드 처리 예제
 * 상세 페이지에서 해당하는 파일 클릭 시 다운로드처리됨
 * @author macween
 *
 */
public class FileDownloadHandler implements CommandHandler {

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {

		long fileId =  req.getParameter("fileId") != null ? Long.parseLong(req.getParameter("fileId")) : -1; // req에 파라미터 꺼내옴
		int fileSn = req.getParameter("fileSn") != null ? Integer.parseInt(req.getParameter("fileSn")) : 1;

		// 파일 정보 조회
		IAtchFileDao dao = AtchFileDaoImpl.getInstance();

		AtchFileVO fileVO = new AtchFileVO();
		fileVO.setAtchFileId(fileId);
		fileVO.setFileSn(fileSn);

		fileVO = dao.getAtchFileDetail(fileVO); // 파일 가져옴 // 숫자로된 정보 가져옴 d:/D_Other/upload_files\c5d19640adbf4084bfd3870bd03c172a

		// 파일 다운로드 처리를 위한 응답헤더 정보 설정하기
		res.setContentType("application/octet-stream");
		System.out.println("URL인코딩된 파일명 => " +  URLEncoder.encode(fileVO.getOrignlFileNm(), "UTF-8")); 
		// URLs cannot contain spaces. URL encoding normally replaces a space with a plus (+) sign or with %20.
		// +문자 공백으로 바꿔주기
//		attachment : 파일다운로드 /  filename 원하는 파일 명으로 설정 / replaceAll("\\+", "%20") 스페이스로 처리되는 경우가 있어서 그것 처리
		res.setHeader("Content-Disposition", "attachment; filename=\"" + URLEncoder.encode(fileVO.getOrignlFileNm(), "UTF-8").replaceAll("\\+", "%20") + "\"");

		
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(fileVO.getFileStreCours()));
		BufferedOutputStream bos = new BufferedOutputStream(res.getOutputStream());

		int c = -1;
		while((c = bis.read()) != -1) {
			bos.write(c);
		}

		bis.close();
		bos.close();

		return null; // 뷰페이지는 따로 설정안함(필요가 없음) (null 인건 리다이렉트 포워드를 하지않음.)
	}

	@Override
	public boolean isRedirect(HttpServletRequest req) {
		return false;
	}
}
