package kr.or.ddit.basic;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 요청 객체로부터 파라미터 데이터 가져오는 방법
 * (참고)데이터 주고받을 떄 형식이 바이너리, 문자형태이다.(내부적으로 직렬화 일어난다.)
 * 
 * - getParameter(파라미터명) : 파라미터값이 한 개의 스트링 값을 가져올 때 사용함
 * - getParameterValues() : 파라미터 값이 여러개인 경우.
 * 							ex) checkbox
 * - getParameterNames() : request에 존재하는 모든 파라미터 정보를 가져올 때 사용함.(사용자가 뭘 던졌는지 알고싶을 때 사용)
 * @author 유은지
 *
 */
public class T03_ServletParameterTest extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String gender = req.getParameter("gender");
		String hobby = req.getParameter("hobby");
		String rlgn = req.getParameter("rlgn");//종교
		String[] foods = req.getParameterValues("food");//checkBox라
		
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html");
		
		PrintWriter out = resp.getWriter();
		out.println("<html>");
		out.println("<body>");
		out.println("<p>username : " + username + "</p>");
		out.println("<p>password : " + password + "</p>");
		out.println("<p>gender : " + gender + "</p>");
		out.println("<p>hobby : " + hobby + "</p>");
		out.println("<p>rlgn : " + rlgn + "</p>");
		
		if(foods != null) {
			for(String food : foods) {
				out.print("<p>foods : " + food + "</p> ");
			}
		}
		
		Enumeration<String> params = req.getParameterNames();
		while(params.hasMoreElements()) {
			String param = params.nextElement();
			out.println("<p>파라미터 이름 : " + param + "</p>");
		}
		out.println("</body>");
		out.println("</html>");
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
