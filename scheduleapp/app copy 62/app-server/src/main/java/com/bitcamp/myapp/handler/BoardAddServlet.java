package com.bitcamp.myapp.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import com.bitcamp.myapp.vo.AttachedFile;
import com.bitcamp.myapp.vo.Board;
import com.bitcamp.myapp.vo.Member;

@WebServlet("/board/add")
@MultipartConfig(maxFileSize = 1024 * 1024 * 10)
public class BoardAddServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    Member loginUser = (Member) request.getSession().getAttribute("loginUser");
    if (loginUser == null) {
      response.sendRedirect("/auth/form.html");
      return;
    }

    try {
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
              InitServlet.ncpObjectStorageService
                  .uploadFile("bitcamp-nc7-bucket-14", "board/", part);
          AttachedFile attachedFile = new AttachedFile();
          attachedFile.setFilePath(uploadFileUrl);
          attachedFiles.add(attachedFile);
        }
      }
      board.setAttachedFiles(attachedFiles);
      // System.out.println(board.getNo());
      InitServlet.boardDao.insert(board);
      // System.out.println(board.getNo());
      if (attachedFiles.size() > 0) {
        // 게시글을 정상적으로 변경했으면, 그 게시글의 첨부파일을 추가한다.
        int count = InitServlet.boardDao.insertFiles(board);
        System.out.println(count);
      }

      InitServlet.sqlSessionFactory.openSession(false).commit();
      response.sendRedirect("list?category=" + board.getCategory());

    } catch (Exception e) {
      InitServlet.sqlSessionFactory.openSession(false).rollback();

      // ErrorServlet으로 포워딩 하기 전에 ErrorServlet이 사용할 데이터를
      // ServletRequest 보관소에 저장한다.
      request.setAttribute("error", e);
      request.setAttribute("message", "게시글 등록 오류!");
      request.setAttribute("refresh", "2;url=list?category=" + request.getParameter("category"));

      request.getRequestDispatcher("/error").forward(request, response);
    }
  }

}


