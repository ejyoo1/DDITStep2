<%@page import="kr.or.ddit.member.vo.MemberVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	MemberVO memVO = (MemberVO) request.getAttribute("memVO");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>회원 정보 변경</title>
</head>
<body>
	<form action="update.do" method="post">
		<input type="hidden" name="memId" value="<%= memVO.getMemId() %>">
		<table>
			<tr>
				<td>이름 : </td>
				<td><input type="text" name="memName" value="<%= memVO.getMemName() %>"></td>
			</tr>
			<tr>
				<td>전화번호 : </td>
				<td><input type="text" name="memTel" value="<%= memVO.getMemTel() %>"></td>
			</tr>
			<tr>
				<td>주소 : </td>
				<td><textarea rows="5" name="memAddr"><%= memVO.getMemAddr() %></textarea></td>
			</tr>
		</table>
		<input type="submit" value="회원정보 수정">
	</form>
</body>
</html>