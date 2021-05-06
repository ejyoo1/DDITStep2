<%@page import="kr.or.ddit.board.vo.AtchFileVO"%>
<%@page import="java.util.List"%>
<%@page import="kr.or.ddit.board.vo.BoardVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
BoardVO boardVo = (BoardVO) request.getAttribute("boardVo");
	List<AtchFileVO> atchFileList = (List<AtchFileVO>) request.getAttribute("atchFileList"); // 파일이 있는 상태
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
	<form class="form-inline">
    	<input class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search">
    	<button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
  	</form>
</nav>
	<div class="container">
		<div class="col-md-12">
			<h1> &nbsp; </h1>
	<form action="update.do" method="post" enctype="multipart/form-data">
		<input type="hidden" name="boardNo" value="<%= boardVo.getBoardNo() %>">
		<input type="hidden" name="atchFileId" value="<%= boardVo.getAtchFileId() %>">
		<table class="table">
			<thead class="thead-dark">
				<tr>
					<th colspan="2">#<%=boardVo.getBoardNo() %>. [<%= boardVo.getBoardTitle() %>] 수정</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td scope="row" class="textleft"><label for="boardTitle">제목</label></td>
					<td scope="row">
						<input type="text" class="form-control" id="boardTitle" name="boardTitle" value="<%= boardVo.getBoardTitle() %>">
					</td>
				</tr>
				<tr>
					<td scope="row" class="textleft"><label for="boardWriter">게시판 작성자</label></td>
					<td scope="row">
						<input type="text" class="form-control" id="boardTitle" name="boardWriter" value="<%= boardVo.getBoardWriter() %>" readonly>
					</td>
				</tr>
				<tr>
					<td scope="row" class="textleft"><label for="boardContent">게시판 내용</label></td>
					<td scope="row">
						<textarea class="form-control" rows="5" id="boardContent" name="boardContent"><%= boardVo.getBoardContent() %></textarea>
					</td>
				</tr>
				<tr>
					<td scope="row" class="textleft"><label for="atchFile1">기존 첨부파일</label></td>
					<td scope="row">
						<%if(atchFileList != null){
							for(AtchFileVO atchFileVO : atchFileList){
						%>
							<div><a href="<%=request.getContextPath() %>/filedownload.do?fileId=<%=atchFileVO.getAtchFileId()%>&fileSn=<%=atchFileVO.getFileSn()%>"><%=atchFileVO.getOrignlFileNm() %></a></div>
						<%
							}
						}
						%>
					</td>
				</tr>
				<tr>
					<td scope="row" class="textleft"><label for="atchFile">새로운 첨부파일</label></td>
					<td><input type="file" id="atchFile" name="atchFile"></td>
				</tr>
				<tr align="center">
					<td colspan="2">
						<button class="btn btn-dark" type="submit">게시글수정</button>
					</td>
				</tr>
			</tbody>
		</table>
	</form>
</body>
</html>