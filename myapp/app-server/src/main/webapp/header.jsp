<%@ page
    language="java"
    pageEncoding="utf-8"
    contentType="text/html;charset=utf-8"%>

<jsp:useBean id="loginUser" class="bitcamp.myapp.vo.Member" scope="session"/>

<div style='height:50px;background-color:orange;'>
  <img src='https://www.ncloud.com/public/img/logo-m.png' style='height:40px'>
  <a href='/member/list.jsp'>회원</a>
  <a href='/board/list.jsp?category=1'>게시글</a>
  <a href='/board/list.jsp?category=2'>독서록</a>

  <%
      if (loginUser.getNo() == 0) {
        out.println("<a href='/auth/form.jsp'>로그인</a>");
      } else {
        if(loginUser.getPhoto() == null){
          out.println("<img style='height:40px' src='http://gjoxpfbmymto19010706.cdn.ntruss.com/icon/avatar.png?type=f&w=30&h=40&faceopt=true&ttype=jpg'>");
        }else{
          out.println(String.format(
              "<img src='http://mvsenqskbqzl19010704.cdn.ntruss.com/member/%s?type=f&w=30&h=40&faceopt=true&ttype=jpg'>",
              loginUser.getPhoto()));
        }
        out.println(String.format("%s <a href='/auth/logout.jsp'>로그아웃</a>", loginUser.getName()));

      }
  %>
</div>