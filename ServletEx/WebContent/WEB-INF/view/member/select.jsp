<%@page import="kr.or.ddit.member.vo.MemberVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% 
	MemberVO memVO = (MemberVO) request.getAttribute("memVO"); 
	
	String memAddr = memVO.getMemAddr()
			.replace(System.lineSeparator(), "<br>"); // tb에다가 작성하였기 때문에 BR태그로 분리
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>회원 정보 보기</title>
</head>
<body>
	<table>
		<tr>
			<td>I D:</td>
			<td><%= memVO.getMemId() %></td>
		</tr>
		<tr>
			<td>이름:</td>
			<td><%= memVO.getMemName() %></td>
		</tr>
		<tr>
			<td>전화번호:</td>
			<td><%= memVO.getMemTel() %></td>
		</tr>
		<tr>
			<td>주소:</td>
			<td><%= memVO.getMemAddr() %></td>
		</tr>
		<tr>
			<td colspan="2">
				<a href="list.do">[목록]</a>
				<a href="update.do?memId=<%= memVO.getMemId()%>">[회원정보 수정]</a>
				<a href="delete.do?memId=<%=memVO.getMemId()%>">[회원정보 삭제]</a>
			</td>
		</tr>
	</table>
</body>
</html>