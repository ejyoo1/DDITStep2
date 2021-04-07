package kr.or.ddit.basic;

import java.io.IOException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 서블릿에서 사용할 필터 구현 ==> 서블릿과는 별개이므로 인터페이스를 구현함.
 * @author 유은지
 * 
 * 서블릿 필터에 대하여...
 * 1. 사용목적
 * - 클라이언트의 요청을 수행하기 전에 가로채 필요한 작업을 수행할 수 있다.
 * - 클라이언트에 응답정보를 제공하기 전에 응답정보에 필요한 작업을 수행할 수 있다.
 * 
 * 2. 사용 예
 * - 인증 필터(인증된 사용자인가?)
 * - 데이터 압축필터(데이터 처리 시 데이터 
 * 압축)
 * - 인코딩 필터
 * - 로깅 및 감사처리 필터(로그인 접속 시간
 * - 이미지 변환 필터 등
 */
public class T07_ServletFilter implements Filter{

	@Override
	public void destroy() {
		//필터 객체가 웹 컨테이너에 의해 서비스로부터 제거되기 전에 호출됨.
		System.out.println("Filter destroy() 호출됨.");
		
	}
	
	@Override
	public void init(FilterConfig fc) throws ServletException {
		System.out.println("Filter init() 메서드 호출됨.");
		
		//초기화 파라미터 정보 가져오기(DB 접속 정보 등 담은 파라미터들...)
		String initParam = fc.getInitParameter("init-param");
		System.out.println(initParam);
	}

	//상위 ServletRequest >> 하위 HttpServletRequest 
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain fc)
			throws IOException, ServletException {//비즈니스 로직 구성영역
		System.out.println("T07_ServletFilter 시작");
		
		//클라이언트의 IP주소 가져오기
		String ipAddress = req.getRemoteAddr();
		
		System.out.println("IP주소 : " + ipAddress
				+ "\n포트번호 : " + req.getRemotePort()
				+ "\n현재 시간 : " + new Date().toString());
		
		//필터체인을 실행한다.(req,resp 객체 전달)
		fc.doFilter(req, resp);//이후에 필터가 존재하면 필터링된 결과값을 다음필터에 넘겨주고 다음 필터가 없는경우 서블릿의 다음작업이 진행된다.
		
		System.out.println("T07_ServletFilter 완료.");
		
	}

}
