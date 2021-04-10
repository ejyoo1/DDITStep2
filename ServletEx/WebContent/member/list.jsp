<%@page import="org.apache.log4j.Logger"%>
<%@page import="kr.or.ddit.member.vo.MemberVO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
 	Logger RESULT_LOGGER = Logger.getLogger(this.getClass()); // 최종 결과에 대한 로거
	RESULT_LOGGER.debug("★★list.jsp 결과★★ [포워딩 상태]: 정상");

	// 서블릿 컨트롤러 단에서 저장된 리스트를 꺼내옴
	List<MemberVO> memList = (List<MemberVO>) request.getAttribute("memList");
	RESULT_LOGGER.debug("★★list.jsp 결과★★ [가져온 목록 수] : " + memList.size());
	
	String msg = request.getParameter("msg") == null ? ""
			: request.getParameter("msg");
	
	RESULT_LOGGER.debug("★★list.jsp 결과★★ [세팅한 msg 출력] : " + msg);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>회원 목록</title>
<script src="../js/jquery-3.6.0.js"></script>
<script>
 $(".memberList").click(function(){
	 alert("클릭");
	 var myForm = document.createElement("form");
     myForm.action= this.href;// the href of the link
     myForm.method= "POST";
     myForm.submit();
     return false; // cancel the actual link
 });
</script>
</head>
<body>
<!-- 
// 회원관리 프로그램 테이블 생성 스크립트 
create table mymember(
    mem_id varchar2(8) not null,  -- 회원ID
    mem_name varchar2(100) not null, -- 이름
    mem_tel varchar2(50) not null, -- 전화번호
    mem_addr varchar2(128),    -- 주소
    CONSTRAINT MYMEMBER_PK PRIMARY KEY (mem_id)
);
 -->
	<h3>전체 자료 출력</h3>
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
			<td><a href="select.do?memId=<%=memList.get(i).getMemId()%>" class="memberList">
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
		<td colspan="20"><a href="insert.do">[회원 등록]</a></td>
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