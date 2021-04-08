package kr.or.ddit.basic;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * ★★★★★★★★★자카르타 프로젝트의 fileupload 모듈을 이용한 파일업로드 예제
 */
public class UploadServlet2 extends HttpServlet {
	
	private static final String UPLOAD_DIR = "upload_files";
	// 메모리 임계크기(이 크기가 넘어가면 레파지토리 위치에 임시파일로 저장됨)
	private static final int MEMORY_THRESHOLD = 1024 * 1024 * 3; //3MB
	// 파일 1개당 최대 크기
	private static final long MAX_FILE_SIZE = 1024 * 1024 * 40; // 100MB
	// 한번 요청 시 보낼 파일 최대 크기
	private static final long MAX_REQUEST_SIZE = 1024 * 1024 * 50; // 
	
	@Override
	protected void doPost(HttpServletRequest req, 
			HttpServletResponse resp) throws ServletException, IOException {
		
		if(ServletFileUpload.isMultipartContent(req)) {//포스트일 때 multipart/form-data가 아닌 경우가 있어서 체크해야됨.
			// 인코딩 타입이 multipart/form-data 인 경우...
			
			// 폼필드 데이터 저장용...
			Map<String, String> formMap = 
					new HashMap<String, String>();
			
			/**
			 * 톰캣에서 서버 메모리로 받아내는데 
			 * 모두 받으면 메모리 부족현상이 있을 수 있어서.
			 * 임시파일로 떨구어서 나중에 저장.
			 */
			DiskFileItemFactory factory = 
					new DiskFileItemFactory();
			factory.setSizeThreshold(MEMORY_THRESHOLD); // 한게 메모리 세팅
			factory.setRepository(//한계 도달시 어디에 할거냐.
					new File(
					System.getProperty("java.io.tmpdir")));//임시경로를 타지 않을 것임.
			System.out.println("임시 경로 : " + System.getProperty("java.io.tmpdir"));
			
			ServletFileUpload upload = 
					new ServletFileUpload(factory); 
			upload.setFileSizeMax(MAX_FILE_SIZE);
			upload.setSizeMax(MAX_REQUEST_SIZE);
			
			// 웹애플리케이션 루트 디렉토리 기준 업로드 경로 설정하기
			String uploadPath = getServletContext().getRealPath("") // 운영체제의 파일 프로토콜의 실제 파일 디렉토리 경로를 얻어옴.
					+ File.separator + UPLOAD_DIR; // File.separator : 운영체제에 따라 /\ 유도리있게 변경
			System.out.println("getServletContext().getRealPath(\"\") : " + getServletContext().getRealPath(""));
			File uploadDir = new File(uploadPath);
			if(!uploadDir.exists()) { // 실제 경로 존재여부 체크
				uploadDir.mkdir();//폴더 만들기
			}
			//파싱 준비작업 끝
			
			List<FileItem> formItems;
			
			/**
			 * 파싱하는 부분
			 */
			try {
				formItems = upload.parseRequest(req); // 파일 및 홈데이터
			
				if(formItems != null && formItems.size() > 0) {
					for(FileItem item : formItems) {
						if(!item.isFormField()) {// 파일인 경우...업로드 처리
							// 전체경로를 제외한 파일명만 추출하기
							String fileName = 
									new File(item.getName()).getName();//실제 경로 : item.getName() , new File(item.getName()).getName() : 이름만 쏙 빼내오기
							String filePath = 
								uploadPath + File.separator + fileName; // ROOT 내 파일 경로 만들어줌.
							File storeFile = new File(filePath);
							item.write(storeFile); // IO 작업 수행 ( 파일로 떨구는 작업 ) 
							req.setAttribute("message", 
									"업로드 완료됨. => 파일명 : " + fileName); // 파일 정보 그냥 출력
						}else { // 폼 데이터인 경우... (Name, Address)
							// 폼필드의 값이 한글인 경우에는 해당 문자열을 적절히 변환을
							// 해주어야 한다.
							// 방법1. value = new String(fileItem.getString()
							//            .getBytes("8859-1"), "UTF-8");
							// 방법2. value = fileItem.getString("UTF-8");
							formMap.put(item.getFieldName(), 
									item.getString("UTF-8")); // K, V(인코딩 처리★)
						}
					}
				}
				
			} catch (Exception e) {
				req.setAttribute("message", "예외발생: " + e.getMessage()); 
				e.printStackTrace();
			}
			for(Entry<String, String> entry : formMap.entrySet()) {// 폼데이터 잘 들어왔나 찍음.
				System.out.println("파라미터명 : " + entry.getKey());
				System.out.println("파라미터값 : " + entry.getValue());
			}
			resp.setContentType("text/html");
			resp.getWriter().print("업로드 완료됨.!!!"); // 상대방에게 html 형식으로 쏴줌.
			
			
			
			
			
			
			
			
			
			
		}
		
		
	}
}
