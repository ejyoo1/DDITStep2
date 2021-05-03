package kr.or.ddit.comm.handler;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 사용자 요청한 코맨트 객체 정의 시 가져야 할 필요한 멤버가 담겨있는
 * 인터페이스 정의
 * @author 유은지
 *
 */
public interface CommandHandler {
	/**
	 * 해당 화면에 대해 포워드 및 리다이렉트 여부 결정하기 위한 메서드
	 * @param req 요청객체
	 * @return 리다이렉트 처리 여부 (true : 리다이렉트 처리, false : 포워드 처리)
	 */
	public boolean isRedirect(HttpServletRequest req);
	
	/**
	 * 요청한 내용을 실제 처리하는 메서드
	 * @param req 요청객체
	 * @param resp 응답객체
	 * @return 뷰페이지 정보
	 */
	public String process(HttpServletRequest req, HttpServletResponse resp) throws Exception;
}
