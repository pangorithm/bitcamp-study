<%@ page
language="java"
pageEncoding="utf-8"
contentType="text/html;charset=utf-8"
trimDirectiveWhitespaces="true"
errorPage="/error.jsp"%>

<%@ page import="java.io.IOException"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.List"%>
<%@ page import="com.bitcamp.myapp.dao.MemberDao"%>
<%@ page import="com.bitcamp.myapp.vo.Member"%>
<%@ page import="com.bitcamp.myapp.vo.AddressType"%>
<%@ page import="com.bitcamp.myapp.vo.MemberAddress"%>
<%@ page import="com.bitcamp.myapp.vo.AttachedFile"%>



    <!DOCTYPE html>
    <html>
    <head>
    <meta charset='UTF-8'>
    <title>회원</title>
    </head>
    <body>

<jsp:include page="../header.jsp"/>

    <h1>회원</h1>
<%
    MemberDao memberDao = (MemberDao) this.getServletContext().getAttribute("memberDao");

    Member loginUser = (Member) request.getSession().getAttribute("loginUser");
    Member m = memberDao.findBy(Integer.parseInt(request.getParameter("no")));

    if (m == null) {
%>
      해당 번호의 회원이 없습니다!
<%
    } else {
%>

<form action='/member/update.jsp' method='post' enctype='multipart/form-data'>
<table border='1'>
<tr>
<th style='width:200px;'>사진</th>
<td style='width:300px;'>

<%
if (m.getPhoto() == null) {
%>

  <img style='height:80px' src='http://gjoxpfbmymto19010706.cdn.ntruss.com/icon/avatar.png?type=f&w=30&h=40&faceopt=true&ttype=jpg'>

<%
} else {
%>

  <a href='https://kr.object.ncloudstorage.com/bitcamp-nc7-bucket-14/member/<%= m.getPhoto() %>'>
  <img src='http://gjoxpfbmymto19010706.cdn.ntruss.com/member/<%= m.getPhoto() %>?type=f&w=60&h=80&faceopt=true&ttype=jpg'>
  </a>

<%
}
%>

<input type='file' name='photo'>
</td>
</tr>

<tr>
<th style='width:200px;'>번호</th>
<td style='width:300px;'><input type='text' name='no' value='<%= m.getNo() %>' readonly='readonly'></td>
</tr>

<tr>
<th>이름</th>
<td><input type='text' name='username' value='<%= m.getName() %>'></td>
</tr>

<tr>
<th>이메일</th>
<td><input type='text' name='email' value='<%= m.getEmail() %>'></td>
</tr>

<tr>
<th>전화번호</th>
<td><input type='text' name='tel' value='<%= m.getTel() %>'></td>
</tr>

<tr>
<th>성별</th>
<td>
<select name='gender'>
<option value='M' <%= m.getGender() == 'M' ? "selected" : "" %>>남자</option>
<option value='W' <%= m.getGender() == 'W' ? "selected" : "" %>>여자</option>
</select>
</td>
</tr>

<tr>
<th>비밀번호</th>
<td><input type='text' name='password' value=''></td>
</tr>
</table>

<div>
<a href='/member/list.jsp'>목록</a>
<button type='reset'>초기화</button>

<%
if (loginUser != null && loginUser.equals(m)) {
%>

  <button>변경</button>
  <a href='/member/delete.jsp?no=<%= m.getNo() %>&password=<%= m.getPassword() %>'>삭제</a>

<%
}
%>

</div>
</form>
<div>
<table border='1'>

<%
if (loginUser != null && loginUser.equals(m)) {
  List<MemberAddress> addressList = memberDao.getMembersAddressList(m.getNo());
  %>
    <tr>
    <th>유형</th>
    <th>우편번호</th>
    <th>기본주소</th>
    <th>상세주소</th>
    </tr>

  <%
  for (MemberAddress maddr : addressList) {
  %>

    <form action='/member/addressDelete.jsp' method='post'>
    <input type='hidden' name='memberNo' value='<%= m.getNo() %>'>
    <input type='hidden' name='mano' value='<%= maddr.getNo() %>'>
    <tr>
    <td><%= maddr.getAddressType().getType() %></td>
    <td><%= maddr.getPostAddress() %></td>
    <td><%= maddr.getBasicAddress() %></td>
    <td><%= maddr.getDetailAddress() %></td>
    <td><button>삭제</button></td>
    </tr>
    </form>

    <%
    }
  }
  %>

  </table>

  <%
  if (loginUser != null && loginUser.equals(m)) {
  %>

    <form action='/member/addressAdd.jsp' method='post'><br>
    <input type='hidden' name='memberNo' value='<%= m.getNo() %>'>
    <select name='addressType'>
    <%
    List<AddressType> addressTypeList = memberDao.findAllAddressType();
    for (AddressType t : addressTypeList) {

      %>
      <option value='<%= t.getNo() %>'><%= t.getType() %></option>
      <%
    }
    %>

    </select><br>
    우편번호 <input type='text' name='postAddress'><br>
    기본주소 <input type='text' name='basicAddr'><br>
    상세주소 <input type='text' name='detailAddr'><br>
    <button>추가</button><br>
    </form>

  <%
  }
  %>

  </div>
<%
}
%>

<jsp:include page="../footer.jsp"/>

</body>
</html>


