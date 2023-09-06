<%@ page
    language="java"
    pageEncoding="UTF-8"
    contentType="text/html;charset=UTF-8"
    trimDirectiveWhitespaces="true"
    errorPage="/error.jsp" %>

<!DOCTYPE html>
<html>
<head>
<meta charset='UTF-8'>
<title>비트캠프</title>
</head>
<body>
<h1>스케줄</h1>
<form action='add' method='post'>
<table border="1">
<tr>
  <th>제목</th> <td><input type='text' name='title'></td>
</tr>
<tr>
  <th>설명</th> <td><input type='text' name='content'></td>
</tr>
<tr>
  <th>시작</th> <td><input type='datetime-local' name='startTime' Id='startTime'></td>
</tr>
<tr>
  <th>종료</th> <td><input type='datetime-local' name='endTime' Id='endTime'></td>
</tr>
</table>
<button>등록</button>
</form>
<script>
  document.getElementById('startTime').value= new Date().toISOString().slice(0, 16);
  document.getElementById('endTime').value= new Date().toISOString().slice(0, 16);
</script>
</body>
</html>