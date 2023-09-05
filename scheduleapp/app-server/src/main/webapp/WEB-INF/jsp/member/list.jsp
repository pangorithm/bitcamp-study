<%@ page
    language="java"
    pageEncoding="utf-8"
    contentType="text/html;charset=utf-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

    <!DOCTYPE html>
    <html>
    <head>
    <meta charset='UTF-8'>
    <title>회원</title>
    </head>
    <body>

<jsp:include page="../header.jsp" />

    <h1>회원 목록 </h1>
    <div style='margin:5px;'>
    <a href='form'>새 회원</a>
    </div>
    <table border='1'>
    <thead>
    <tr><th>번호</th> <th>이름</th> <th>이메일</th>
    </thead>
    <tbody>

    <c:forEach items="${list}" var="m">
      <tr>
        <td>${m.getNo()}</td>
        <td>
          <img src='http://gjoxpfbmymto19010706.cdn.ntruss.com/member/${m.getPhoto()}?type=f&w=30&h=40&faceopt=true&ttype=jpg'>
          <a href='detail?no=${m.getNo()}'>${m.getName().matches("^\\s+$") ? "이름없음" : m.getName()}</a></td>
        <td>${m.getEmail()}</td>
      </tr>
    </c:forEach>

    </tbody>
    </table>
    <a href='/'>메인</a>

<jsp:include page="../footer.jsp" />

    </body>
    </html>







