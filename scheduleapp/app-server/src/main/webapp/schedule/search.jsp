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
<form action='/schedule/search' method='get'>
<table border="1">
<tr>
  <th>검색 제목</th> <td><input type='text' name='title'></td>
</tr>
<tr>
  <th>검색 시작 시간</th> <td><input type='datetime-local' name='start-time' id='start-time'></td>
</tr>
<tr>
  <th>검색 종료 시간</th> <td><input type='datetime-local' name='end-time' id='end-time'></td>
</tr>
</table>
<button>검색</button>
</form>
<script>
  document.getElementById('start-time').value= new Date().toISOString().slice(0, 16);
  document.getElementById('end-time').value= new Date().toISOString().slice(0, 16);
</script>

</body>
</html>