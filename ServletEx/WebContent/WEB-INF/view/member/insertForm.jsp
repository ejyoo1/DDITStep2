<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>신규회원 등록</title>
</head>
<body>
<!-- 
	inser영역에 첨부파일을 추가하는 경우 회원 정보를 전송할 수 없게 된다.
	기본 : method="get" enctype="application/x-www-form-urlencoded"
	★★★★★ 파일 전송 시(안지킬 시 파일전송 안됨) : method="post" enctype="multipart/form-data" (앵크타입)
	기본 인코딩타입과 파일전송할 때 사용하는 인코딩 타입이 다르므로, 하나의 작업은 못하게된다.
	
	
 -->
	<form action="insert.do" method="post" enctype="multipart/form-data">
		<table>
			<tr>
				<td>ID : </td>
				<td><input type="text" name="memId" value=""></td>
			</tr>
			<tr>
				<td>이름 : </td>
				<td><input type="text" name="memName" value=""></td>
			</tr>
			<tr>
				<td>전화번호 : </td>
				<td><input type="text" name="memTel" value=""></td>
			</tr>
			<tr>
				<td>주소 : </td>
				<td><textarea rows="5" name="memAddr"></textarea></td>
			</tr>
			<tr>
				<td>첨부파일 : </td><!-- method="post" enctype="multipart/form-data" 필수 -->
				<td><input type="file" name="atchFile"></td>
			</tr>
		</table>
		<input type="submit" value="회원 등록">
	</form>
</body>
</html>