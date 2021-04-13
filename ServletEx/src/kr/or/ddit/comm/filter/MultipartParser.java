package kr.or.ddit.comm.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileUploadException;

import kr.or.ddit.util.FileUploadRequestWrapper;

/**
 * 내부적으로 초기화 되어있는 (파일목록 또는 폼필드 데이터 목록) requestWrapper를 Filter에 삽입
 * 멀티파트일 때, 파싱하는 기능을 담은 필터 (Util 기능)
 * @author 유은지
 *
 */
public class MultipartParser implements Filter{
	// 멤버 변수 선언
	// 메모리 임계크기(이 크기가 넘어가면 레파지토리 위치에 임시파일로 저장됨)
	private static final int MEMORY_THRESHOLD = 1024 * 1024 * 3;
	// 파일 1개당 최대 크기
	private static final long MAX_FILE_SIZE = 1024 * 1024 * 40;
	// 요청 파일 최대 크기
	private static final long MAX_REQUEST_SIZE = 1024 * 1024 * 50;
	
	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain fc)
			throws IOException, ServletException {
		
		// 리퀘스트래퍼 객체 생성
		FileUploadRequestWrapper requestWrapper;
		try {
			requestWrapper = new FileUploadRequestWrapper(
					(HttpServletRequest) req, // 기반이므로 필수!
					MEMORY_THRESHOLD,
					MAX_FILE_SIZE,
					MAX_REQUEST_SIZE,
					"");
			// 래퍼 클래스 적용.
			fc.doFilter(requestWrapper, resp); // request의 확장기능이 존재하는 requestWrapper 추가하여 호출
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void init(FilterConfig fc) throws ServletException {
		
	}

}
