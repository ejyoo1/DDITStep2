package kr.or.ddit.basic;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 파일 다운로드하기
 * @author 유은지
 *
 */
public class T12_DownloadServletTest extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String filename = "aaa.jpg";
		
		// 파일 다운로드 처리를 위한 응답헤더 정보 설정하기.
		resp.setContentType("application/octet-stream");// 모든 바이너리 마임타입 다 적용됨.(첨부파일 시 이것 사용)
		resp.setHeader("Content-Disposition", 
				"attachment; filename=\"" + filename + "\"");
		resp.setHeader("Content-Disposition", 
				"inline");
		
		/**
		 Content-Disposition 헤더에 대하여.
		 
		 1. response header 에서 사용되는 경우... ex) 파일다운로드
		 	Content-Disposition: inline (default) ==> 내용 다운받아보겠다.(웹에 띄어주는것==처리가 완료되면 Network에 보임)
		 	// 서블릿 이름으로 파일 다운로드
		 	Content-Disposition: attatchment
		 	// filename.jpg 이름으로 파일 다운로드 
		 	★★★Content-Disposition: attatchment; filename="filename.jpg"
		 	
		 2.	multipart body를 위한 헤더 정보로 사용되는 경우 ... ex) 파일 업로드
		 	Content-Disposition: form-data
		 	Content-Disposition: form-data; name="fileName"
		 	Content-Disposition: form-data; name="fileName"; filename="filename.jpg"
		 */
		
		resp.setContentType("image/jpeg"); // 컨텐츠 타입 설정(파싱할 수 있는 MIME 타입을 설정해야 함. 그렇지 않으면 파일 다운로드 됨.)
		
		ServletOutputStream out = resp.getOutputStream(); // 출력 관련은 Response , 문자 기반이 아니기에 outputStream으로.
		
		FileInputStream fis = new FileInputStream("d:/D_Other/dooly.jpeg");
		
		BufferedInputStream bis = new BufferedInputStream(fis);
		BufferedOutputStream bos = new BufferedOutputStream(out);
		
		int bytes = 0; // 읽은 바이트 수
		while((bytes = bis.read()) != -1) {
			bos.write(bytes);
		}
		
		bis.close();
		bos.close();
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
