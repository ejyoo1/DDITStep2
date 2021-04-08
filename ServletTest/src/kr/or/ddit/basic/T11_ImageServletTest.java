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
 * 이미지 웹에서 띄우기
 * @author 유은지
 *
 */
public class T11_ImageServletTest extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
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
