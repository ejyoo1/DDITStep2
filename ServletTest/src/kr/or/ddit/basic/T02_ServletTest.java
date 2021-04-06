package kr.or.ddit.basic;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 서블릿 동작 방식에 대하여..
 * @author 유은지
 *1. 사용자(클라이언트)가 URL을 클릭하면 HTTP Request를 서블릿 컨테이너로 전송(요청)한다.
 *  --> 컨테이너로부터 서블릿이 올라갈 때 init() 메서드가 호출된다.
 *2. 컨테이너(톰캣=WAS)는 web.xml에 정의된 url 패턴을 확인하여 어느 서블릿을 통해 처리해야 할지를 검색한다.
 *3. Servlet Container(톰캣=WAS)는 요청을 처리할 개별 스레드 객체를 생성한다.
 *  --> 톰캣은 http프로토콜에서 사용하는 HttpServletRequest / HttpServletResponse 객체를 생성한다.
 *3-1. 해당 서블릿 객체의 service() 메서드를 호출한다.
 *  --> 요청 정보를 가공한 후 생성된 개별 스레드가 파라미터로 넘겨준다.
 *(HttpServletRequest = requestLine~body / HttpServletResponse = statusLine~body )(비 연결 지향)
 *4. service() 메서드는 메서드 타입을 Request Line 에서 체크하여 적절한 메서드를 호출한다. (doGet, doPost, doPut, doDelete 등) 
 *5. 요청 처리가 완료되면, (HttpServletRequest 및 HttpServletResponse 객체는 소멸한다.)
 *  --> HttpServletRequest, HttpServletResponse 는 사용자에게 요청 응답이 될 때 까지 살아있다.
 *6. 컨테이너(톰캣=WAS)로부터 서블릿이 제거되는 경우에 destroy() 메서드가 호출된다.
 */

public class T02_ServletTest extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		 
		
		// 요청데이터 Body 영역 인코딩 처리부분 ==> req로부터 데이터를 가져오기 전에 설정해야 함.
		req.setCharacterEncoding("utf-8");//톰캣이 요청 body영역 인코딩함.(URL정보는 해당되지 않음)
		// URL을 인코딩하려면 톰캣의 server.xml을 세팅해야 함.(server.xml 의 encoding 검색)
		
		// 요청 정보로부터 name값을 가져옴
		String name = req.getParameter("name");
		
		System.out.println("name => " + name);
		
		// 응답메시지 인코딩 설정(Content-Type의 charset=UTF-8)
		resp.setCharacterEncoding("UTF-8");//톰캣이 응답 body 영역 인코딩함.
		
		// 응답 메시지의 컨텐트 타입(마임타입:웹 브라우저 세팅 시 형태 설정) 설정
		resp.setContentType("text/plain"); //[text/planin : 텍스트 보냄][text/html : html],[image/jpg : img]
		
		// 실제 수행할 로직(기능)이 시작되는 부분...
//		resp.getOutputStream() ==> 응답 데이터 바이트기반 출력시 이 스트림을 사용하면 됨.
		PrintWriter out = resp.getWriter();// .setContentType 시 "text/plain"으로 가져왔기에 문자기반으로 데이터를 가져옴.
		out.println("name => " + name);
		out.println("서블릿 경로 : " + req.getServletPath());
		out.println("컨텍스트 경로 : " + req.getContextPath());
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
