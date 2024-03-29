<%@ page
language="java"
pageEncoding="utf-8"
contentType="text/html;charset=utf-8"
trimDirectiveWhitespaces="true"
errorPage="/error.jsp"%>

<%@ page import="java.io.IOException"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.List"%>
<%@ page import="com.bitcamp.myapp.dao.BoardDao"%>
<%@ page import="com.bitcamp.myapp.vo.Board"%>
<%@ page import="com.bitcamp.myapp.vo.Member"%>
<%@ page import="com.bitcamp.myapp.vo.AttachedFile"%>
<%@ page import="org.apache.ibatis.session.SqlSessionFactory"%>

<%!
  SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd a hh:mm");
%>

<%
SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) this.getServletContext()
.getAttribute("sqlSessionFactory");
BoardDao boardDao = (BoardDao) this.getServletContext().getAttribute("boardDao");

int category = Integer.parseInt(request.getParameter("category"));
int no = Integer.parseInt(request.getParameter("no"));
Board board = boardDao.findBy(category, no);

Member loginUser = (Member) request.getSession().getAttribute("loginUser");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset='UTF-8'>
<title>게시글</title>
</head>
<body>

<jsp:include page="../header.jsp"/>

<h1>게시글</h1>

<%
if (board == null) {
%>

<p>해당 번호의 게시글이 없습니다!</p>

<%
} else {
%>

<form action='/board/update.jsp' method='post' enctype='multipart/form-data'>
<input type='hidden' name='category' value='<%=board.getCategory()%>'>
<table border='1'>
<tr><th style='width:120px;'>번호</th>
<td style='width:300px;'><input type='text' name='no' value='<%=board.getNo()%>' readonly='readonly'></td></tr>
<tr><th>제목</th><td><input type='text' name='title' value='<%=board.getTitle()%>'></td></tr>

<tr><th>내용</th> <td><textarea name='content' style='height:200px; width:400px;'><%=board.getContent()%></textarea></td></tr>
<tr><th>작성자</th> <td><%=board.getWriter().getName()%></td></tr>
<tr><th>조회수</th> <td><%=board.getViewCount()%></td></tr>
<tr><th>등록일시</th> <td><%=dateFormatter.format(board.getCreatedAt())%></td></tr>
<tr><th>수정일시</th> <td><%=board.getUpdatedAt() == null ? "-" : dateFormatter.format(board.getUpdatedAt())%></td></tr>
<tr><th>첨부파일</th><td>

<%
for (AttachedFile file : board.getAttachedFiles()) {
%>

<a href='https://kr.object.ncloudstorage.com/bitcamp-nc7-bucket-14/board/<%=file.getFilePath()%>'><%=file.getFilePath()%></a>
[<a href='/board/fileDelete.jsp?category=<%=category%>&no=<%=file.getNo()%>'>삭제</a>]
<br>

<%
}
%>

<input type='file' name='files' multiple>

</td></tr>

</table>
<div>

<%
if (loginUser.getNo() == board.getWriter().getNo()) {
%>

<button>변경</button>
<button type='reset'>초기화</button>
<a href='/board/delete.jsp?category=<%=board.getCategory()%>&no=<%=board.getNo()%>'>삭제</a>
<%
}
%>
<a href='/board/list.jsp?category=<%=board.getCategory()%>'>목록</a>
</div>
</form>

<%
board.setViewCount(board.getViewCount() + 1);
boardDao.updateCount(board);
sqlSessionFactory.openSession(false).commit();
%>

<jsp:include page="../footer.jsp"/>

</body>
</html>

<%
}
%>
