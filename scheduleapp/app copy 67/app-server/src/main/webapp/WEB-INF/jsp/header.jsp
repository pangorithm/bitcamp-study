<%@ page
    language="java"
    pageEncoding="utf-8"
    contentType="text/html;charset=utf-8"%>
<%@ page import="com.bitcamp.myapp.vo.Member"%>

<div style='height:50px;background-color:skyblue;padding:10px;'>
  <a href='/'><img src='https://www.ncloud.com/public/img/logo-m.png' style='height:40px;background-color:white;padding:5px'></a>
  <a href='/app/member/list'>회원</a>
  <a href='/app/schedule/list'>스케줄</a>
  <a href='/app/board/list?category=1'>게시글</a>
  <a href='/app/board/list?category=2'>독서록</a>

  <%
    Member loginUser = (Member) request.getSession().getAttribute("loginUser");
      if (loginUser == null) {
        out.println("<a href='/app/auth/login'>로그인</a>");
      } else {
        if(loginUser.getPhoto() == null){
          out.println("<img style='height:40px' src='http://gjoxpfbmymto19010706.cdn.ntruss.com/icon/avatar.png?type=f&w=30&h=40&faceopt=true&ttype=jpg'>");
        }else{
          out.println(String.format(
              "<img src='http://mvsenqskbqzl19010704.cdn.ntruss.com/member/%s?type=f&w=30&h=40&faceopt=true&ttype=jpg'>",
              loginUser.getPhoto()));
        }
        out.println(String.format("%s <a href='/app/auth/logout'>로그아웃</a>", loginUser.getName()));

      }
  %>
</div>