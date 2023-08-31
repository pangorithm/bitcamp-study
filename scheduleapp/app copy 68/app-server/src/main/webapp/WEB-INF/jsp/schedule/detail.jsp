<%@ page
  language="java"
  pageEncoding="utf-8"
  contentType="text/html;charset=utf-8"
  trimDirectiveWhitespaces="true"
  errorPage="/error.jsp"
  %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
  <head>
    <meta charset='UTF-8'>
    <title>스케줄</title>
  </head>
  <body>

    <jsp:include page="../header.jsp"/>

    <h1>스케줄</h1>

    <form action='update' method='post'>
      <table border='1'>
        <tr><th style='width:120px;'>번호</th>
        <td style='width:300px;'><input type='text' name='no' value='${schedule.getNo()}' readonly='readonly'></td></tr>
        <tr><th>제목</th> <td><input type='text' name='title' value='${schedule.getTitle()}'></td></tr>
        <tr><th>제목</th> <td><input type='text' name='content' value='${schedule.getContent()}'></td></tr>
        <tr><th>시작</th> <td><input type='datetime-local' name='start-time' value='${schedule.getStartTime()}'></td></tr>
        <tr><th>종료</th> <td><input type='datetime-local' name='end-time' value='${schedule.getEndTime()}'></td></tr>
        <tr><th>스캐줄 매니저</th> <td><a href='../member/detail.jsp?no=${schedule.getOwner().getNo()}'>${schedule.getOwner().getName()}</a></td></tr>
      </table>
      <div>
        <button>변경</button>
        <button type='reset'>초기화</button>
        <c:if test = "${schedule.getOwner().equals(loginUser)}">
          <a href='delete?no=${schedule.getNo()}'>삭제</a>
        </c:if>
        <a href='list'>목록</a>
      </div>
    </form>
    <div>
    </div>

    <br>
      <p1>스케줄 참가자 명단</p1><br>

      <table border='1'>
        <tr>
        <th style='width:100px;'>번호</th> <th style='width:100px;'>이름</th>
        <th>-</th>
        </tr>

        <c:forEach items="${participantList}" var="m">
          <tr>
            <form action='participantDelete' method='post'>
            <input type='hidden' name='no' value='${schedule.getNo()}'>
            <input type='hidden' name='deleteParticipantNo' value='${m.getNo()}'>
            <td>${m.getNo()}</td> <td><a href='detail?no=${m.getNo()}'>${m.getName()}</a></td>
            <td>
            <c:if test="${loginUser.equals(schedule.getOwner()) or loginUser.equals(m)}">
              <button>삭제</button>
            </c:if>
            </td>
            </form>
            </tr>
        </c:forEach>
      </table>
    <c:if test="${loginUser.getNo() == schedule.getOwner().getNo()}">
      <br>
      <div>
        추가할 참가자 번호 입력
        <form action='participantAdd' method='post'>
          <input type='hidden' name='no' value='${schedule.getNo()}'>
          <input type='number' name='addParticipantNo' min='1'>
          <button>추가</button>
        </form>
      </div>
    </c:if>
    <jsp:include page="../footer.jsp"/>

  </body>
</html>


