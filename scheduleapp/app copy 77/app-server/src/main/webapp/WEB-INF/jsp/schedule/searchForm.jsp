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
<form action='search' method='post'>
<table border="1">
<tr>
  <th>검색 제목</th> <td><input type='text' name='title'></td>
</tr>
<tr>
  <th>검색 시작 시간</th> <td><input type='datetime-local' name='startTime' id='startTime'></td>
</tr>
<tr>
  <th>검색 종료 시간</th> <td><input type='datetime-local' name='endTime' id='endTime'></td>
</tr>
</table>
<button>검색</button>
</form>
<script>
  document.getElementById('startTime').value= new Date().toISOString().slice(0, 16);
  document.getElementById('endTime').value= new Date().toISOString().slice(0, 16);
</script>

</body>
</html>