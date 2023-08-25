<%@ page
    language="java"
    pageEncoding="UTF-8"
    contentType="text/html;charset=UTF-8"
    trimDirectiveWhitespaces="true"
    errorPage="/error.jsp" %>

<%@ page import="java.io.IOException"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.bitcamp.myapp.dao.BoardDao"%>
<%@ page import="com.bitcamp.myapp.vo.AttachedFile"%>
<%@ page import="com.bitcamp.myapp.vo.Board"%>
<%@ page import="com.bitcamp.myapp.vo.Member"%>
<%@ page import="com.bitcamp.util.NcpObjectStorageService"%>
<%@ page import="org.apache.ibatis.session.SqlSessionFactory"%>

<%
    request.setAttribute("refresh", "2;url=list.jsp?category=" + request.getParameter("category"));

    SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) this.getServletContext()
        .getAttribute("sqlSessionFactory");
    BoardDao boardDao = (BoardDao) this.getServletContext().getAttribute("boardDao");
    NcpObjectStorageService ncpObjectStorageService = (NcpObjectStorageService) this.getServletContext()
        .getAttribute("ncpObjectStorageService");

    Member loginUser = (Member) request.getSession().getAttribute("loginUser");
    if (loginUser == null) {
      response.sendRedirect("/auth/form");
      return;
    }

      Board board = new Board();
      board.setWriter(loginUser);
      board.setTitle(request.getParameter("title"));
      board.setContent(request.getParameter("content"));
      board.setCategory(Integer.parseInt(request.getParameter("category")));

      ArrayList<AttachedFile> attachedFiles = new ArrayList<>();

      for (Part part : request.getParts()) {
        // System.out.println(part.getName());
        if (part.getName().equals("files") && part.getSize() > 0) {
          String uploadFileUrl =
              ncpObjectStorageService
                  .uploadFile("bitcamp-nc7-bucket-14", "board/", part);
          AttachedFile attachedFile = new AttachedFile();
          attachedFile.setFilePath(uploadFileUrl);
          attachedFiles.add(attachedFile);
        }
      }
      board.setAttachedFiles(attachedFiles);
      // System.out.println(board.getNo());
      boardDao.insert(board);
      // System.out.println(board.getNo());
      if (attachedFiles.size() > 0) {
        // 게시글을 정상적으로 변경했으면, 그 게시글의 첨부파일을 추가한다.
        int count = boardDao.insertFiles(board);
      }

      sqlSessionFactory.openSession(false).commit();
      response.sendRedirect("list?category=" + board.getCategory());
%>

