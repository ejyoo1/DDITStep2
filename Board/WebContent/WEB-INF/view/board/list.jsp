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
 <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css" crossorigin="anonymous">
    <script src="https://use.fontawesome.com/releases/v5.15.1/js/all.js" crossorigin="anonymous"></script>
 <!-- js -->
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
<style>
	.textCenter {
		text-align: center;
	}
	.textleft {
		text-align: left;
	}
	.btnArea {
		margin-right: 10px;
	}
</style>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark d-flex justify-content-between">
	<a class="navbar-brand" href="list.do">유은지 게시판</a>
	<form action="search.do" method="post" class="form-inline">
    	<input class="form-control mr-sm-2" type="search" name="search" placeholder="Search" aria-label="Search">
    	<button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
  	</form>
</nav>

<div class="container">
	<div class="col-md-12">
		<h1> &nbsp; </h1>
		<table class="table">
			<thead class="thead-dark">
				<tr>
					<th scope="col" class="textCenter">#</td>
					<th scope="col" class="textCenter">게시글 제목</td>
					<th scope="col" class="textCenter">게시글 내용</td>
					<th scope="col" class="textCenter">게시글 작성일</td>
					<th scope="col" class="textCenter">게시글 작성자</td>
					<th scope="col" class="textCenter">첨부파일 번호</td>
				</tr>
			</thead>
			<tbody>
		
	<%
		int boardSize = boardList.size();
		
		if(boardSize > 0){
			for(int i = 0; i < boardSize; i++){
	%>		
				<tr>
					<td scope="row" class="textCenter"><%=boardList.get(i).getBoardNo() %></td>
					<td scope="row"><a href="select.do?boardNo=<%=boardList.get(i).getBoardNo()%>" class="boardList">
						<%=boardList.get(i).getBoardTitle() %></a></td>
					<td scope="row"><%=boardList.get(i).getBoardContent() %></td>
					<td scope="row" class="textCenter"><%=boardList.get(i).getBoardDate() %></td>
					<td scope="row" class="textCenter"><%=boardList.get(i).getBoardWriter() %></td>
					<td scope="row" class="textCenter"><%=boardList.get(i).getAtchFileId() %></td>
				</tr>
	<%
			}
		}else{
	%>
				<tr>
					<td colspan="6">게시글이 없습니다.</td>
				</tr>
	<%
		}
	%>
				<tr align="center">
					<td colspan="6"><a class="btn btn-dark float-sm-right" href="insert.do">게시글 등록</a></td>
				</tr>
			</tbody>
		</table>
	</div>
</div>
	
	
	<%
		if(msg.equals("성공")){ // 성공메시지가 전달되면...
	%>
			<script>
				alert('정상적으로 처리되었습니다.');
			</script>
	<% 		
		}
	%>
	
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
</body>
</html>