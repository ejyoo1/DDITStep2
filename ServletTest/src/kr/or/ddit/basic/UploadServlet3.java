package kr.or.ddit.basic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 * 서블릿 3부터 지원하는 Part 인터페이스를 이용한 파일 업로드 예제 (jar 없이도 파일 업로드 가능함.)
 * @author 유은지
 */

@WebServlet(name = "UploadServlet3", urlPatterns = "/upload3") // web.xml에 적재★★★★★★★★★★★★★★★★★★★★★★★★★★
@MultipartConfig(
		fileSizeThreshold = 1024*1024, 
		maxFileSize=1024*1024*5, 
		maxRequestSize=1024*1024*5*5) // 파일 업로드 설정
public class UploadServlet3 extends HttpServlet {
	
	private static final String UPLOAD_DIR = "upload_files";
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String uploadPath = getServletContext().getRealPath("") //getServletContext().getRealPath("") : root 리턴 경로 지정 가능 getServletContext().getRealPath("/WEB-INF")
				+ File.separator + UPLOAD_DIR;// uploadTest3.html 의 method="post" enctype="multipart/form-data" 설정이 되어있어야 함.
		File uploadDir = new File(uploadPath);
		if(!uploadDir.exists()) {//폴더 없으면
			uploadDir.mkdir();//폴더가 만들어짐
		}//준비작업 완료
		
		try {
			String fileName = "";
			for(Part part : req.getParts()) { // 여러개의 파트를 담아줌 // Part가 여러개 ==> multipart ==> 파일과 필드 등 여러개 조각으로 나누어 여러개 바디를 보내는 것.
				System.out.println(part.getHeader("Content-Disposition")); // 헤더 정보인 Content-Disposition 값 가져옴
				
				fileName = getFileName(part); // 파일 이름만 추출함.
				
				if(fileName != null && !fileName.equals("")) { 
					//폼 필드가 아니거나 파일이 비어있지 않은 경우... (Content-Disposition이 아닌경우)
					//파일 저장
					part.write(uploadPath + File.separator + fileName); // .write 원하는 경로에 파일을 저장 (실제 저장하는 부분)
					System.out.println("파일명 : " + fileName + "업로드 완료!!!"); // 그냥 찍어본 것
				}
			}
		}catch(FileNotFoundException ex) {
			ex.printStackTrace();
		}
		System.out.println("파라미터 값 : " + req.getParameter("sender"));
		
		resp.setContentType("text/html");
		resp.getWriter().println("업로드 완료...!!!!");
	}

	/**
	 * Part 객체 파싱하여 파일이름 추출하기
	 * @param part
	 * @return 파일명 : 파일명이 존재하지 않으면 null 리턴(폼필드)
	 */
	private String getFileName(Part part) {
		/**
		 * multipart body를 위한 헤더 정보로 사용되는 경우 ex)파일 업로드
		 * 
		 * Content-Disposition : form-data
		 * Content-Disposition : form-data; name = "fileName"
		 * Content-Disposition : form-data; name = "fileName"; filename = "filename.jpg")
		 */
		
		for(String content : part.getHeader("Content-Disposition").split(";")) {
			return content.substring(content.indexOf("=")+1)
					.trim().replace("\"", "");//파일이름을 얻어옴
		}
		return null;// content-Disposition이 없으면 null
	}
}
