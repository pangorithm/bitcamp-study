<%@ page
    language="java"
    pageEncoding="utf-8"
    contentType="text/html;charset=utf-8"%>

<%@ page import="java.io.IOException"%>
<%@ page import="java.util.List"%>
<%@ page import="com.bitcamp.myapp.dao.MemberDao"%>
<%@ page import="com.bitcamp.myapp.vo.Member"%>


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
    <a href='/member/form.jsp'>새 회원</a>
    </div>
    <table border='1'>
    <thead>
    <tr><th>번호</th> <th>이름</th> <th>이메일</th>
    </thead>
    <tbody>

<%
    MemberDao memberDao = (MemberDao) this.getServletContext().getAttribute("memberDao");
    // 목록에서 데이터를 대신 꺼내주는 객체를 얻는다.
    List<Member> list = memberDao.findAll();

    for (Member m : list) {
      out.println(String.format(
              "<tr>"
                  + "<td>%d</td> "
                  + "<td>"
                  + "<img src='http://gjoxpfbmymto19010706.cdn.ntruss.com/%s?type=f&w=30&h=40&faceopt=true&ttype=jpg'>"
                  + "<a href='/member/detail.jsp?no=%d'>%s</a>"
                  + "</td>"
                  + "<td>%s</td>"
                  + "</tr>\n",
              m.getNo(),
              m.getPhoto() == null ? "icon/avatar.png" : "member/" + m.getPhoto(),
              m.getNo(),
              m.getName().matches("^\\s+$") ? "이름없음" : m.getName(),
              m.getEmail()));
    }
%>

    </tbody>
    </table>
    <a href='/'>메인</a>

<jsp:include page="../footer.jsp" />

    </body>
    </html>







