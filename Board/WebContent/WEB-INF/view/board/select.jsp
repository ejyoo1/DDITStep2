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
</head>
<body>
	<table>
		<tr>
			<td>#</td>
			<td><%=boardVo.getBoardNo() %></td>
		</tr>
		<tr>
			<td>게시글 제목</td>
			<td><%= boardVo.getBoardTitle() %></td>
		</tr>
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
			<td>파일키값</td>
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
				<a href="list.do">[목록]</a>
				<a href="update.do?boardNo=<%= boardVo.getBoardNo()%>">[게시글 수정]</a>
				<a href="delete.do?boardNo=<%=boardVo.getBoardNo()%>">[게시글 삭제]</a>
			</td>
		</tr>
	</table>
</body>
</html>