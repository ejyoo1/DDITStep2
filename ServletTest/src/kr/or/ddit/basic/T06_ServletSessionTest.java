package kr.or.ddit.basic;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 쿠키는 클라이언트(브라우저)에 저장되기 때문에 다른 사용자로 인해 데이터의 변경 및 손실이 있을 수 있어서 보안에 취약하다.
 * 따라서 서버단에 별도로 저장하여 웹 브라우저가 종료될 때 까지 유효한 데이터를 사용하기 위해서 '세션'이 존재한다.
 * 
 * 세션(HttpSession) 객체에 대하여...
 * - 세션을 통해서 사용자(웹브라우저) 별로 구분하여 정보를 관리할 수 있다.(세션ID 이용)
 * 	쿠키를 사용할 때보다 보안이 향상된다.( 정보가 서버에 저장되기 때문에... )
 * 
 * - 세션객체를 가져오는 방법
 *  HttpSession session = request.getSession(boolean값);
 *  boolean값 : true인 경우 	=> 세션객체가 존재하지 않으면 세션을 새로 생성함. 세션이 존재하면 기존 세션을 줌(항상 세션이 존재하는 상태임)
 *  		   false인 경우 	=> 세션객체가 존재하지 않으면 null 리턴함. 세션이 존재하면 기존 세션을 줌(세션이 존재하는지 존재하지 않는지 확인하고자 할 때 사용 )==> 로그인 처리에 사용하면 됨.
 *  
 * - 세션 삭제 처리 방법(로그아웃)
 * 1. invalidate() 메서드 호출 (당장 지우기)
 * 2. setMaxInactiveInterval(int interval) 메서드 호출
 *    => 일정시간(초)동안 요청이 없으면 세션객체 삭제됨.
 * 3. web.xml 에 <session-config> 설정하기 (분 단위)
 * 
 * @author 유은지
 *
 */
public class T06_ServletSessionTest extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		// 세션 객체 생성하기
		HttpSession session = req.getSession(true);//default : true 세션을 가져오는데 없으면 새로 생성한다.
		
		// 생성 시간 가져오기
		Date createTime = new Date(session.getCreationTime());
		
		// 마지막 접근시간 가져오기
		Date lastAccessTime = new Date(session.getLastAccessedTime());
		
		String title = "재방문을 환영합니다.";
		int visitCount = 0; // 방문횟수
		String userId = "ejyoo"; // 사용자 ID
		
		if(session.isNew()) { // 새로 만든 세션이라면...true
			title = "처음 방문을 환영합니다.";
			session.setAttribute("userId", userId);//새로운 생성일때만 userId를 세팅하면 되므로.
		}else {
			visitCount = (Integer)session.getAttribute("visitCount"); // Object 타입이므로 캐스팅 필요
			visitCount++;
			userId = (String) session.getAttribute("userId");// Object 타입이므로 캐스팅 필요
		}
		System.out.println("visitCount : " + visitCount);
		//방문횟수 설정
		session.setAttribute("visitCount", visitCount);
		
		resp.setCharacterEncoding("utf-8");
		resp.setContentType("text/html");
		
		PrintWriter out = resp.getWriter();
		
		out.println("<html><head><title>" + title
				+ "</title></head>"
				+ "<body><h1 align=\"center\">" + title
			    + "</h1>"
			    + "<h2 align=\"center\">세션정보</h2>"
			    + "<table border=\"1\" align=\"center\">"
			    + "<tr bgcolor=\"orange\">"
			    + "<th>구분</th><th>값</th>" + "</tr>"
			    + "<tr><td>세션ID</td><td>"
			    + session.getId() + "</td></tr>"//톰캣에서 관리하는 세션별 아이디 (세션을 구분하는 유일한 값 JSESSIONID)
			    + "<tr><td>생성시간</td><td>"
			    + createTime + "</td></tr>"
			    + "<tr><td>마지막 접근시간</td><td>"
			    + lastAccessTime + "</td></tr>"
			    + "<tr><td>User Id</td><td>"
			    + userId + "</td></tr>"
			    + "<tr><td>방문횟수</td><td>"
			    + visitCount + "</td></tr></table>"
			    + "</body></html>");
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
