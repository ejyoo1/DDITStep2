package kr.or.ddit.basic;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class T10_ServletSessionListenerTest extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getSession().setAttribute("AAA", "AAA입니다.");
		req.getSession().setAttribute("AAA", "AAA수정합니다.");
		req.getSession().setAttribute("BBB", "BBB입니다.");
		req.getSession().removeAttribute("AAA");
		
		//HTTP 세션 영역내에서 바인딩 관련 예제
		MySessionBindingListener bindinglistener
			= new MySessionBindingListener();
		
		req.getSession().setAttribute("obj", bindinglistener); // 관리하고싶은 객체에만 리스너를 적용하고 싶을때 사용
		req.getSession().removeAttribute("obj");
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
