<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
			<form action="insert.do" method="post" enctype="multipart/form-data">
				<table class="table">
					<thead class="thead-dark">
						<tr>
							<th colspan="2">게시글 등록</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td scope="row" class="textleft"><label for="boardTitle">제목</label></td>
							<td scope="row">
								<input type="text" class="form-control" id="boardTitle" name="boardTitle" placeholder="이름입력">
							</td>
						</tr>
						<tr>
							<td scope="row" class="textleft"><label for="boardTitle">게시판 작성자</label></td>
							<td scope="row">
								<input type="text" class="form-control" id="boardTitle" name="boardWriter" placeholder="작성자입력">
							</td>
						</tr>
						<tr>
							<td scope="row" class="textleft"><label for="boardContent">게시판 내용</label></td>
							<td scope="row">
								<textarea class="form-control" id="boardContent" name="boardContent" placeholder="게시판 내용입력" rows="3"></textarea>
							</td>
						</tr>
						<tr>
							<td scope="row" class="textleft"><label for="atchFile">첨부파일</label></td>
							<td scope="row"><input type="file" id="atchFile" name="atchFile"></td>
						</tr>
						<tr align="center">
							<td colspan="2"><button class="btn btn-dark" type="submit">게시글등록</button></td>
						</tr>
					</tbody>
				</table>
				
			</form>
		</div>
	</div>
</body>
</html>