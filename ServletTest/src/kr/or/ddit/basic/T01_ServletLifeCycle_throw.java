package kr.or.ddit.basic;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 서블릿의 라이프사이클을 확인하기 위한 예제
 * (서블릿이란? 웹 애플리케이션 개발 시 스펙)
 * 컨테이너(서블릿엔진)에 의해 관리되는 **자바기반 웹 컴포넌트**로서, 동적인 웹 컨텐츠를 가능하게 해준다. 
 * @author 유은지
 * 서블릿 ? 자바로 만든 웹 어플리케이션 .. 컨테이너 역할을 하는 것이 톰캣이다.
 * 서비스를 실행하기 위해서는 컨테이너 즉 톰캣(WAS)에 올려야 한다.
 * 
 * 톰캣에 프로젝트를 올려서 web.xml 설정정보 기반으로 실행
 * 
 * WebContent - root
 * WEB-INF에 web.xml이 존재해야 톰캣이 인식함.
 * WEB-INF : lib 는 외부에서 참조해서 사용하는 라이브러리를 이 폴더에 넣어야 인식한다.
 * WEB-INF : classes(클래스 Path가 모여있는 곳)
 */
public class T01_ServletLifeCycle_throw extends HttpServlet { //http 프로토콜을 사용하는 클래스
	
	@Override
	public void init() throws ServletException{
		//초기화 코드 작성
		System.out.println("init() 호출됨.");
	}
	
	@Override
	protected void service(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException{
		//실제적인 작업 수행이 시작되는 지점.(Entry Point)
		//자바의main메서드 역할
		super.service(arg0, arg1);
		System.out.println("service 호출됨.");
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//메서드 방식이 get인 경우 호출됨
		System.out.println("doGet() 호출됨.");
		throw new ServletException("서블릿 예외 발생했어여!!");
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//메서드 방식이 post인 경우에 호출됨
		System.out.println("doPost() 호출됨.");
		
//		doGet(req, resp);
	}
	
	@Override
	public void destroy() {
		//객체 소멸 시 (컨테이너로부터 서블릿 객체 제거 시) 필요한 코드 작성...
		System.out.println("destory() 호출됨.");
	}
}
