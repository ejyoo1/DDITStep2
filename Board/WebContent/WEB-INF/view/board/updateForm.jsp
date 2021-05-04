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
	<form action="update.do" method="post" enctype="multipart/form-data">
		<input type="hidden" name="boardNo" value="<%= boardVo.getBoardNo() %>">
		<input type="hidden" name="atchFileId" value="<%= boardVo.getAtchFileId() %>">
		<table>
			<tr>
				<td>게시판 제목 : </td>
				<td><input type="text" name="boardTitle" value="<%= boardVo.getBoardTitle() %>"></td>
			</tr>
			<tr>
				<td>게시판 작성자 : </td>
				<td><input type="text" name="boardWriter" value="<%= boardVo.getBoardWriter() %>" readonly></td>
			</tr>
			<tr>
				<td>게시판 내용 : </td>
				<td><textarea rows="5" name="boardContent"><%= boardVo.getBoardContent() %></textarea></td>
			</tr>
			<tr>
				<td>기존 첨부파일</td>
				<td>
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
				<td>새로운 첨부파일</td>
				<td><input type="file" name="atchFile"></td>
			</tr>
		</table>
		<input type="submit" value="게시글 수정">
	</form>
</body>
</html>