<%@ page
    language="java"
    pageEncoding="UTF-8"
    contentType="text/html;charset=UTF-8"
    trimDirectiveWhitespaces="true"
    errorPage="/error.jsp" %>

<%@ page import="java.io.IOException"%>
<%@ page import="com.bitcamp.myapp.dao.BoardDao"%>
<%@ page import="com.bitcamp.myapp.vo.Board"%>
<%@ page import="com.bitcamp.myapp.vo.Member"%>
<%@ page import="com.bitcamp.myapp.vo.AttachedFile"%>
<%@ page import="com.bitcamp.util.NcpObjectStorageService"%>
<%@ page import="org.apache.ibatis.session.SqlSessionFactory"%>

<%

    SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) this.getServletContext()
        .getAttribute("sqlSessionFactory");
    BoardDao boardDao = (BoardDao) this.getServletContext().getAttribute("boardDao");

    Member loginUser = (Member) request.getSession().getAttribute("loginUser");
    if (loginUser == null) {
      response.sendRedirect("/auth/form.jsp");
      return;
    }

    int category = Integer.parseInt(request.getParameter("category"));
    int fileNo = Integer.parseInt(request.getParameter("no"));

    AttachedFile attachedFile = boardDao.findFileBy(fileNo);

    Board board = boardDao.findBy(category, attachedFile.getBoardNo());

    request.setAttribute("refresh", "2;url=/board/detail.jsp?category="
        + request.getParameter("category") + "&no=" + board.getNo());

    if (board.getWriter().getNo() != loginUser.getNo()) {
      throw new ServletException("게시글 변경 권한이 없습니다!");
    }


    if (boardDao.deleteFile(fileNo) == 0) {
      throw new Exception("해당 번호의 첨부파일이 없거나 삭제 권한이 없습니다.");
    } else {
      response.sendRedirect("/board/detail.jsp?category=" + category + "&no=" + board.getNo());
    }
    sqlSessionFactory.openSession(false).commit();

%>