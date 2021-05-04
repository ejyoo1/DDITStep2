<%@page import="kr.or.ddit.board.vo.BoardVO"%>
<%@page import="java.util.List"%>
<%@page import="org.apache.log4j.Logger"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	Logger RESULT_LOGGER = Logger.getLogger(this.getClass());
	RESULT_LOGGER.debug("★★selectMember.jsp 결과★★ [포워딩 상태]: 정상");
	
	List<BoardVO> boardList = (List<BoardVO>) request.getAttribute("boardList");
	RESULT_LOGGER.debug("★★selectMember.jsp 결과★★ [가져온 목록 수] : " + boardList.size());
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
</head>
<body>
	<form action="select.do" method="post"></form>
	select board
</body>
</html>