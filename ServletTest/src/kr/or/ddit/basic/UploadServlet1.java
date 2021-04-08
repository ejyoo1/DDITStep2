package kr.or.ddit.basic;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

/**
 * cos JAR을 사용
 * oreilly 의 MultipartRequest를 이용한 파일 업로드 예제
 * (생성자 호출과 동시에 파일이 생성되기 때문에 선택적인 파일 생성처리 불가)
 * 특징 : 무조건적인 업로드. 사용자의 선택 옵션이 없음.
 * @author PC-19
 *
 */
public class UploadServlet1 extends HttpServlet{
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		resp.setContentType("text/html");//업로드 완료를 출력하기 위해
		
		PrintWriter out = resp.getWriter();
		
		String encType = "UTF-8";
		int maxFileSize = 5 * 1024 * 1024;
		
		// MultipartRequest(request, 저장경로, 최대허용 크기, 인코딩케릭터셋, 동일한 파일명 보호 여부)
		//DefaultFileRenamePolicy => name.zip, name1.zip, name2.zip : 중복이 있을 때 넘버링
		MultipartRequest mr = new MultipartRequest(req, "d:/D_Other/",
						maxFileSize, encType, new DefaultFileRenamePolicy());//업로드 처리 완료되는 부분.
		
		File file = mr.getFile("fname");
		System.out.println(file);// 첨부된 파일의 전체 경로 출력
		
		System.out.println(mr.getParameter("title")); // html name 가져옴
		System.out.println(mr.getParameter("text"));
		
		out.println("업로드 완료됨.");
	}
}
