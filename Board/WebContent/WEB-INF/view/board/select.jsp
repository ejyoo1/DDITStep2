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
	<table class="table">
		<thead class="thead-dark">
			<tr>
				<th colspan="2">#<%=boardVo.getBoardNo() %>. [<%= boardVo.getBoardTitle() %>]</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>게시글 내용</td>
				<td><%= boardVo.getBoardContent() %></td>
			</tr>
			<tr>
				<td>게시글 작성일</td>
				<td><%= boardVo.getBoardDate() %></td>
			</tr>
			<tr>
				<td>게시글 작성자</td>
				<td><%= boardVo.getBoardWriter() %></td>
			</tr>
			<tr>
				<td>첨부파일</td>
				<td>
					<%if(atchFileList != null){
						for(AtchFileVO atchFileVO : atchFileList){
					%>
					<!-- id와 파일의 순번을 알아야 파일을 가져올 수 있음 (Key 역할)-->
					<!-- request.getContextPath() : 절대경로 -->
						<div><a href="<%=request.getContextPath() %>/filedownload.do?fileId=<%=atchFileVO.getAtchFileId()%>&fileSn=<%=atchFileVO.getFileSn()%>"><%=atchFileVO.getOrignlFileNm() %></a></div>
					<%
						}
					}
					%>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<a class="btn btn-dark float-sm-right btnArea" href="list.do">목록으로</a>
					<a class="btn btn-dark float-sm-right btnArea" href="update.do?boardNo=<%= boardVo.getBoardNo()%>">게시글 수정</a>
					<a class="btn btn-dark float-sm-right btnArea" href="delete.do?boardNo=<%=boardVo.getBoardNo()%>">게시글 삭제</a>
				</td>
			</tr>
		</tbody>
	</table>
</body>
</html>