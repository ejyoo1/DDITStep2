package kr.or.ddit.basic;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 에러(예외)처리를 위한 서블릿 예제
 * 서블릿 안에 다음과 같은 정보를 추출할 수 있다.
 * 이 데이터는 톰캣이 만들어낸 자료이다.
 * 
 * 개발자가 예외발생 시 보여주는 화면
 * @author 유은지
 *
 * - req.setAttribute("K","V"); ==> req.getAttrigute("K")
 * - setAttribute 시 톰캣이 HttpServletRequest에 추가해준다.
 */
public class T04_ErrorHandler extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 예외객체
		Throwable throwable = (Throwable) 
				req.getAttribute("javax.servlet.error.exception");
		
		// 에러 상태 코드
		Integer statusCode = (Integer) 
				req.getAttribute("javax.servlet.error.status_code");
		
		// 에러 발생한 서블릿 이름
		String servletName = (String)
				req.getAttribute("javax.servlet.error.servlet_name");
		
		if(servletName == null) {
			servletName = "알수 없는 서블릿";
		}
		
		// 에러 발생 url 정보
		String requestUri = (String)
				req.getAttribute("javax.servlet.error.request_uri");
		
		if(requestUri == null) {
			requestUri = "알수없는 URI";
		}
		
		resp.setCharacterEncoding("utf-8");
		resp.setContentType("text/html");
		
		PrintWriter out = resp.getWriter();
		String title = "Error/Exception 정보";
		
		out.println(
				"<html>\n"
				+ "<head><title>" + title + "</title></head>\n"
				+ "<body>\n"
				);
		
		if(throwable == null && statusCode == null) {
			out.println("<h2>에러정보 없음</h2>");
			out.println("홈페이지로 돌아가기 : <a href=\""
					+ "http://localhost/ServletTest/"
					+ "\">홈페이지</a>");
		}else if(statusCode != null) {
			out.println("■상태코드■ ==> " + statusCode);
		}else {
			out.println("<h2>예외/에러 정보</h2>");
			out.println("■서블릿 이름■ ==> " + servletName + "<br><br>");
			out.println("■예외 타입■ ==> " + throwable.getClass().getName() 
					+ "<br><br>");
			out.println("■요청 URI■ ==> " + requestUri + "<br><br>");
			out.println("■예외 메시지■ ==> " + throwable.getMessage());
		}
		out.println("</body>");
		out.println("</html>");
	}//끝나는 시점에 톰캣이 IO작업을 한 결과를 응답으로 쏴줌.
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
