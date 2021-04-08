package kr.or.ddit.basic;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 특장 : 애플리케이션 설정 정보 저장(톰캣에 적제된 컨테이너)
 * 생명주기 : 서버 종료 시 까지 살아있음.
 * @author 유은지
 * 이벤트 발생 시 발생하는 시점에 대한 Listener를 구현하면 해당 시점이 발생할 때, 제어할 수 있다.
 * 프로그램 하나당 컨텍스트 서블릿 하나씩 생성됨. 이것을 관리할 수 있는 것이 컨텍스트 리스너
 */
public class T08_ServletContextListenerTest extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getServletContext().setAttribute("ATTR1", "속성1"); // 속성 값 추가
		req.getServletContext().setAttribute("ATTR1", "속성11"); // 속성 값 변경
		req.getServletContext().setAttribute("ATTR2", "속성2"); // 속성 값 추가
		
		getServletContext().removeAttribute("ATTR1"); // 속성값 제거
//		== this.getServletContext().removeAttribute("ATTR1"); // 속성값 제거
//		== req.removeAttribute("ATTR1");
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
