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
 *2. 컨테이너(톰캣=WAS)는 web.xml에 정의된 url 패턴을 확인하여 어느 서블릿을 통해 처리해야 할지를 검색한다.
---> 컨테이너로부터 서블릿이 올라갈 때 init() 메서드가 호출된다.
 *3. Servlet Container(톰캣=WAS)는 요청을 처리할 개별 스레드 객체를 생성하여 
 *	해당 서블릿 객체의 service() 메서드를 호출한다.
 *(톰캣이 http프로토콜에서 사용하는 HttpServletRequest 및 HttpServletResponse 객체를 생성하여 요청정보를 가공한 뒤 생성된 개별 스레드가 파라미터로 넘겨준다) ==> 객체는 요청 응답 데이터 처리를 위해.
 *(HttpServletRequest = requestLine~body 추가 / HttpServletResponse = statusLine~body 추가)(비 연결 지향)
 *4. service() 메서드는 메서드 타입을 Request Line 에서 체크하여 적절한 메서드를 호출한다. (doGet, doPost, doPut, doDelete 등) 
 *5. 요청 처리가 완료되면, (HttpServletRequest 및 HttpServletResponse 객체는 소멸한다.)
 *6. 컨테이너(톰캣=WAS)로부터 서블릿이 제거되는 경우에 destroy() 메서드가 호출된다.
 
 *HttpServletRequest, HttpServletResponse 는 사용자에게 요청 응답이 될 때 까지 살아있다.
 */

public class T02_ServletTest extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//Post 방식으로 넘어오는 Body 데이터를 인코딩 처리함. 
		//Get방식은 톰캣의 URIEncoding 설정을 이용함.
		//반드시 request에서 값을 가져오기 전에 먼저 설정해야 적용된다.
		req.setCharacterEncoding("utf-8");//요청된 데이터에 대해서 톰캣이 body 인코딩 관련 영역(URL정보는 해당되지 않음)
		//URL을 인코딩하려면 톰캣을 세팅해야 함.
		
		//요청 정보로부터 name값을 가져옴
		String name = req.getParameter("name");
		
		System.out.println("name => " + name);
		
		//응답메시지 인코딩 설정(Content-Type의 charset=UTF-8)
		resp.setCharacterEncoding("UTF-8");//날라온 응답 데이터에 대해서 톰캣이 body 인코딩 관련 영역
		
		//응답 메시지의 컨텐트 타입 설정
		resp.setContentType("text/plain"); //text/planin(텍스트로보내겠다) ==> 마임타입 : 웹 브라우저 세팅 시 어떤형식으로 할지 ? ([html : text/html],[img : image/jpg])
		
		//실제 수행할 로직(기능)이 시작되는 부분...
//		resp.getOutputStream() ==> 응답 데이터 바이트기반 출력시 이 스트림을 사용하면 됨.
		PrintWriter out = resp.getWriter();//getWriter() 하여 필요한 write를 시켜준다.(컨텐트 타입 설정 시 텍스트로 했기 때문에 이부분을 문자기반 스트림을 가져옴)(응답데이터는 톰캣이 알아서 해줌.)
		out.println("name => " + name);
		out.println("서블릿 경로 : " + req.getServletPath());
		out.println("컨텍스트 경로 : " + req.getContextPath());
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
