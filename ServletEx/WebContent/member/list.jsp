<%@page import="kr.or.ddit.member.vo.MemberVO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	// 정상 포워딩 시 수행됨
	System.out.println("정상 포워딩");

	// 서블릿 컨트롤러 단에서 저장된 리스트를 꺼내옴
	List<MemberVO> memList = (List<MemberVO>) request.getAttribute("memList");
	
	// 사용 시작 = =
	
	//
	String msg = request.getParameter("msg") == null ? ""
			: request.getParameter("msg");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>회원 목록</title>
</head>
<body>

	<table border="1">
		<tr>
			<td>ID</td>
			<td>이름</td>
			<td>전화번호</td>
			<td>주소</td>
		</tr>
		
	<%
		int memSize = memList.size();
		
		if(memSize > 0){
			for(int i = 0; i < memSize; i++){
	%>		
		<tr>
			<td><%=memList.get(i).getMemId() %></td>
			<td><a href="select.do?memId=<%=memList.get(i).getMemId()%>">
			<%=memList.get(i).getMemName() %></a></td>
			<td><%=memList.get(i).getMemTel() %></td>
			<td><%=memList.get(i).getMemAddr() %></td>
		</tr>
	<%
			}
		}else{
	%>
		<tr>
			<td colspan="4">회원정보가 없습니다.</td>
		</tr>
	<%
		}
	%>
	<tr align="center">
		<td colspan="20"><a href="insert.do">[회원 등록]</a></td><!-- 상대경로라 member/insert.do 형식으로 작성하지 않음. -->
	</tr>
	</table>
	
	<%
		if(msg.equals("성공")){ // 성공메시지가 전달되면...
	%>
			<script>
				alert('정상적으로 처리되었습니다.');
			</script>
	<% 		
		}
	%>

</body>
</html>