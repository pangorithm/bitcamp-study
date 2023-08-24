<%@ page
    language="java"
    pageEncoding="utf-8"
    contentType="text/html;charset=utf-8"
    isErrorPage="true" %>

<%@ page import="org.apache.ibatis.session.SqlSessionFactory"%>

<%
    SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) this.getServletContext()
        .getAttribute("sqlSessionFactory");

    sqlSessionFactory.openSession(false).rollback();

    if (request.getAttribute("refresh") != null) {
      response.setHeader("Refresh", (String) request.getAttribute("refresh"));
    }
%>

<!DOCTYPE html>
<html>
<head>
<meta charset='UTF-8'>
<title>게시글</title>
</head>
<body>

<jsp:include page="header.jsp" />

<h1>실행 오류!</h1>

<p><%=exception%></p>


<jsp:include page="footer.jsp" />

</body>
</html>











