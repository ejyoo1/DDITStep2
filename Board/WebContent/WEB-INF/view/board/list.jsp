<%@page import="org.apache.log4j.Logger"%>
<%@page import="kr.or.ddit.board.vo.BoardVO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
 	Logger RESULT_LOGGER = Logger.getLogger(this.getClass()); // 최종 결과에 대한 로거
	RESULT_LOGGER.debug("★★list.jsp 결과★★ [포워딩 상태]: 정상");

	List<BoardVO> boardList = (List<BoardVO>) request.getAttribute("boardList");
	RESULT_LOGGER.debug("★★list.jsp 결과★★ [가져온 목록 수] : " + boardList.size());
	
	String msg = request.getParameter("msg") == null ? ""
			: request.getParameter("msg");
	
	RESULT_LOGGER.debug("★★list.jsp 결과★★ [세팅한 msg 출력] : " + msg);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<script src="../js/jquery-3.6.0.js"></script>
<script>
 $(".boardList").click(function(){
	 console.log("클릭");
	 var myForm = document.createElement("form");
     myForm.action= this.href;// the href of the link
     myForm.method= "POST";
     myForm.submit();
     return false; // cancel the actual link
 });
</script>
</head>
<body>
	<h3>전체 자료 출력</h3>
	<table border="1">
		<tr>
			<td>#</td>
			<td>게시글 제목</td>
			<td>게시글 내용</td>
			<td>게시글 작성일</td>
			<td>게시글 작성자</td>
			<td>파일키값</td>
		</tr>
		
	<%
		int boardSize = boardList.size();
		
		if(boardSize > 0){
			for(int i = 0; i < boardSize; i++){
	%>		
		<tr>
			<td><%=boardList.get(i).getBoardNo() %></td>
			<td><a href="select.do?boardNo=<%=boardList.get(i).getBoardNo()%>" class="boardList">
				<%=boardList.get(i).getBoardTitle() %></a></td>
			<td><%=boardList.get(i).getBoardContent() %></td>
			<td><%=boardList.get(i).getBoardDate() %></td>
			<td><%=boardList.get(i).getBoardWriter() %></td>
			<td><%=boardList.get(i).getAtchFileId() %></td>
		</tr>
	<%
			}
		}else{
	%>
		<tr>
			<td colspan="5">게시글이 없습니다.</td>
		</tr>
	<%
		}
	%>
	<tr align="center">
		<td colspan="5"><a href="insert.do">[게시글 등록]</a></td>
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