package kr.or.ddit.basic;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 서블릿 3부터 지원하는 Part 인터페이스를 이용한 파일 업로드 예제
 * @author 유은지
 */

@WebServlet(name = "UploadServlet3", urlPatterns = "/upload3") // web.xml에 적재★★★★★★★★★★★★★★★★★★★★★★★★★★
@MultipartConfig(
		fileSizeThreshold = 1024*1024, 
		maxFileSize=1024*1024*5, 
		maxRequestSize=1024*1024*5*5) // 파일 업로드 설정
public class UploadServlet3 extends HttpServlet {
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
}
