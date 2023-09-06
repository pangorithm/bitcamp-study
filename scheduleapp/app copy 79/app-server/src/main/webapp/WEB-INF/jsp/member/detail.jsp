<%@ page
language="java"
pageEncoding="utf-8"
contentType="text/html;charset=utf-8"
trimDirectiveWhitespaces="true"
errorPage="../error.jsp"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

    <!DOCTYPE html>
    <html>
    <head>
    <meta charset='UTF-8'>
    <title>회원</title>
    </head>
    <body>

<jsp:include page="../header.jsp"/>

<h1>회원</h1>
<c:if test="${empty m}">
      해당 번호의 회원이 없습니다!
</c:if>
<c:if test="${not empty m}">
  <form action='update' method='post' enctype='multipart/form-data'>
  <table border='1'>
    <tr>
    <th style='width:200px;'>사진</th>
      <td style='width:300px;'>
        <c:if test="${m.getPhoto() == null}">
          <img style='height:80px' src='http://gjoxpfbmymto19010706.cdn.ntruss.com/icon/avatar.png?type=f&w=30&h=40&faceopt=true&ttype=jpg'>
        </c:if>
        <c:if test="${m.getPhoto() != null}">
          <a href='https://kr.object.ncloudstorage.com/bitcamp-nc7-bucket-14/member/${m.getPhoto()}'>
            <img src='http://gjoxpfbmymto19010706.cdn.ntruss.com/member/${m.getPhoto()}?type=f&w=60&h=80&faceopt=true&ttype=jpg'>
          </a>
        </c:if>
        <input type='file' name='photofile'>
      </td>
    </tr>

    <tr>
      <th style='width:200px;'>번호</th>
      <td style='width:300px;'><input type='text' name='no' value='${m.getNo()}' readonly='readonly'></td>
    </tr>

    <tr>
      <th>이름</th>
      <td><input type='text' name='name' value='${m.getName()}'></td>
    </tr>

    <tr>
      <th>이메일</th>
      <td><input type='text' name='email' value='${m.getEmail()}'></td>
    </tr>

    <tr>
      <th>전화번호</th>
      <td><input type='text' name='tel' value='${m.getTel()}'></td>
    </tr>

    <tr>
      <th>성별</th>
      <td>
        <select name='gender'>
          <option value='M' ${String.valueOf(m.getGender()) == 'M' ? "selected" : ""}>남자</option>
          <option value='W' ${String.valueOf(m.getGender()) == 'W' ? "selected" : ""}>여자</option></select></td></tr>
        </select>
      </td>
    </tr>

    <tr>
      <th>비밀번호</th>
      <td><input type='text' name='password' value=''></td>
    </tr>
  </table>

  <div>
    <a href='list'>목록</a>
    <button type='reset'>초기화</button>


    <c:if test="${loginUser != null && loginUser.equals(m)}">
      <button>변경</button>
      <a href='delete?no=${ m.getNo()}&password=${ m.getPassword()}'>삭제</a>
    </c:if>
  </div>
  </form>
  <div>
    <table border='1'>
      <c:if test="${loginUser != null && loginUser.equals(m)}">
        <tr>
          <th>유형</th>
          <th>우편번호</th>
          <th>기본주소</th>
          <th>상세주소</th>
        </tr>
        <c:forEach items="${addressList}" var="maddr">
          <form action='addressDelete' method='post'>
            <input type='hidden' name='mno' value='${m.getNo()}'>
            <input type='hidden' name='no' value='${maddr.getNo()}'>
            <tr>
              <td>${maddr.getAddressType().getType()}</td>
              <td>${maddr.getPostAddress()}</td>
              <td>${maddr.getBasicAddress()}</td>
              <td>${maddr.getDetailAddress()}</td>
              <td><button>삭제</button></td>
            </tr>
          </form>
        </c:forEach>
      </c:if>
    </table>

    <c:if test="${loginUser != null && loginUser.equals(m)}">
      <form action='addressAdd' method='post'><br>
      <input type='hidden' name='mno' value='${m.getNo()}'>
      <select name='adtno'>
        <c:forEach items="${addressTypeList}" var="t">
          <option value='${t.getNo()}'>${t.getType()}</option>
        </c:forEach>
      </select><br>
      우편번호 <input type='text' name='postAddress'><br>
      기본주소 <input type='text' name='basicAddress'><br>
      상세주소 <input type='text' name='detailAddress'><br>
      <button>추가</button><br>
      </form>
    </c:if>
  </div>
</c:if>

<jsp:include page="../footer.jsp"/>

</body>
</html>


