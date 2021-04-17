package kr.or.ddit.comm.controller;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import kr.or.ddit.comm.handler.CommandHandler;
import kr.or.ddit.comm.handler.NullHandler;

/**
 * 사용자 요청을 받아서 처리하는 컨트롤러 구현하기 (대표 컨트롤러)
 * command Pattern 적용
 * @author 유은지
 *
 */
public class WebController extends HttpServlet {
	
	//log4J 객체 생성
	private static Logger LOGGER = Logger.getLogger("WebController.class");
	
	private Map<String, CommandHandler> cmmHandlerMap = new HashMap<>(); // 매핑 정보 저장 (핸들러 객체 저장용 맵)
	
	/**
	 * 핸들러 맵에 put하기 위해
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		String configFilePath = config.getInitParameter("handler-config"); // ==> handler.properties 경로를 가져옴
		
		LOGGER.info("★ configFilePath : " + configFilePath);
		
		Properties handlerProp = new Properties();
		
		// 설정 파일을 읽어서 대응되는 핸들러 객체를 생성하여 맵에 등록하기 시작
		String configFileRealPath = config.getServletContext().getRealPath(configFilePath);
		
		LOGGER.info("★ configFileRealPath : " + config.getServletContext());
		
		FileReader fr;
		
		try {
			fr = new FileReader(configFileRealPath);
			handlerProp.load(fr);
		} catch(IOException ex) {
			ex.printStackTrace();
		}
		
		for(Object key : handlerProp.keySet()) {
			String command = (String) key;
			
			try {
				Class<?> klass = Class.forName(handlerProp.getProperty(command));
				CommandHandler handler = (CommandHandler) klass.newInstance();
				cmmHandlerMap.put(command,  handler);
			} catch(Exception ex) {
				ex.printStackTrace();
				throw new ServletException();
			}
		} // 설정 파일을 읽어서 대응되는 핸들러 객체를 생성하여 맵에 등록하기 끝
		
		Set<Map.Entry<String, CommandHandler>> entrySet = cmmHandlerMap.entrySet();
		for(Map.Entry<String, CommandHandler> entry : entrySet) {
			LOGGER.info(entry.getKey() 
			+ " => " + entry.getValue());
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req,resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}
	
	/**
	 * 요청 처리 메서드
	 * @param req
	 * @param resp
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String reqURI = req.getRequestURI(); // 사용자가 친 URL 정보를 가져옴. ==> "/ServletEx/member/list.do"(사용자가 접속할 때 필요한 URI)
		
		if(reqURI.indexOf(req.getContextPath()) == 0) { // 파싱
			reqURI = reqURI.substring(req.getContextPath().length()); // ContextPath 부분을 제외한 URL 정보 가져오기(컨트롤러가 원하는 URI만 추출) ==> "/member/list.do"
		}
		
		/*
		if(reqURI.equals("/member/list.do")) { // 회원 목록 조회
			// 회원 목록 조회 기능 호출...
		} else if(reqURI.equals("/member/insert.do")) { // 회원 등록 
			if(req.getMethod().equals("GET")) { // 회원 등록 페이지 이동
				// 등록을 위한 폼 페이지 이동 
			} else if(req.getMethod().equals("POST")) { // 회원 등록
				// 회원 등록 처리
			}
		}
		// .. 등 서비스가 호출되는 문구가 이어짐. => 유지보수 적합하지 않음 => 코맨드(Command) 패턴 적용의 필요성 부각!
		 * 
		 */
		
		/**
		 * 코맨드(Command) 패턴이란 ?
		 * 사용자 요청에 대한 실제 처리 기능을 커맨드 객체로 캡슐화하여 처리하는 패턴
		 * Command : 사용자 요청을 캡슐화한 객체(실제 처리 기능을 구현한 객체)
		 * Invoker : 사용자 요청에 대응되는 적당한 커맨드 객체를 찾아 실행 해주는 역할을 하는 객체
		 * 
		 * - 장점 : 요청을 처리하는 객체(Invoker)로부터 실제 수행 기능을 분리함으로써, 새로운 기능을 추가하는데 보다 수월하다.
		 * => 새로운 기능(Command)을 추가할 때 기존 기능을 수정할 필요가 없다. (유지보수 수월함)
		 */
		
		if(LOGGER.isInfoEnabled()) { // 로그 찍기
			LOGGER.info("command : " + reqURI);
			LOGGER.info("cmmHandlerMap : " + cmmHandlerMap);
		}
		
		CommandHandler handler = cmmHandlerMap.get(reqURI); // 핸들러 객체 가져옴
		
		if(handler == null) { // 정의한 핸들러가 없는 경우 
			handler = new NullHandler(); 
		}
		
		String viewPage = "";
		try {
			viewPage = handler.process(req, resp); // 핸들러 처리 부분(핸들러 작업 위임)
		} catch (Exception e) {
			e.printStackTrace();
		} // process 호출
		
		LOGGER.info("viewPage : " + viewPage);
		
		// VIEW 화면 처리 
		if(viewPage != null) { // 뷰페이지가 존재하는 경우...
			if(handler.isRedirect(req)) { // 리다이렉트 처리가 필요한 경우
				resp.sendRedirect(viewPage);
			} else { // 리다이렉트 처리가 필요하지 않은 경우 (포워드 처리)
				RequestDispatcher dispatcher = req.getRequestDispatcher(viewPage);
				
				dispatcher.forward(req,  resp);
			}
		}
	}
}
