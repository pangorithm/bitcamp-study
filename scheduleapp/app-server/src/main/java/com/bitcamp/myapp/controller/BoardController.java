package com.bitcamp.myapp.controller;

import com.bitcamp.myapp.service.BoardService;
import com.bitcamp.myapp.service.NcpObjectStorageService;
import com.bitcamp.myapp.vo.AttachedFile;
import com.bitcamp.myapp.vo.Board;
import com.bitcamp.myapp.vo.Member;
import java.util.ArrayList;
import java.util.Map;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BoardController {

  @Autowired
  BoardService boardService;
  @Autowired
  NcpObjectStorageService ncpObjectStorageService;

  {
    System.out.println("BoardController 생성됨!");
  }

  @RequestMapping("/board/form")
  public String form() {
    return "/WEB-INF/jsp/board/form.jsp";
  }

  @RequestMapping("/board/add")
  public String add(
      Board board,
      int category,
      Part[] files,
      Map<String, Object> model,
      HttpSession session) throws Exception {

    Member loginUser = (Member) session.getAttribute("loginUser");
    if (loginUser == null) {
      return "redirect:../auth/form";
    }

    board.setWriter(loginUser);

    try {
      ArrayList<AttachedFile> attachedFiles = new ArrayList<>();
      for (Part part : files) {
        if (part.getSize() > 0) {
          String uploadFileUrl = ncpObjectStorageService.uploadFile(
              "bitcamp-nc7-bucket-14", "board/", part);
          AttachedFile attachedFile = new AttachedFile();
          attachedFile.setFilePath(uploadFileUrl);
          attachedFiles.add(attachedFile);
        }
      }
      board.setAttachedFiles(attachedFiles);

      boardService.add(board);
      return "redirect:list?category=" + category;

    } catch (Exception e) {
      model.put("message", "게시글 등록 오류!");
      model.put("refresh", "2;url=list?category=" + category);
      throw e;
    }
  }

  @RequestMapping("/board/delete")
  public String delete(
      int no,
      int category,
      Map<String, Object> model,
      HttpSession session) throws Exception {

    Member loginUser = (Member) session.getAttribute("loginUser");
    if (loginUser == null) {
      return "redirect:../auth/form";

    }

    try {
      Board b = boardService.get(no);

      if (b == null || b.getWriter().getNo() != loginUser.getNo()) {
        throw new Exception("해당 번호의 게시글이 없거나 삭제 권한이 없습니다.");
      } else {
        boardService.delete(b.getNo());
        return "redirect:list?category=" + category;
      }

    } catch (Exception e) {
      model.put("refresh", "2;url=list?category=" + category);
      throw e;
    }
  }

  @RequestMapping("/board/detail")
  public String detail(
      int no,
      int category,
      Map<String, Object> model) throws Exception {

    try {
      Board board = boardService.get(no);
      if (board != null) {
        boardService.increaseViewCount(no);
        model.put("board", board);
      }
      return "/WEB-INF/jsp/board/detail.jsp";

    } catch (Exception e) {
      model.put("refresh",
          "5;url=/board/list?category=" + category);
      throw e;
    }
  }

  @RequestMapping("/board/list")
  public String list(
      int category, Map<String, Object> model) throws Exception {
    try {
      model.put("list", boardService.list(category));

      return "/WEB-INF/jsp/board/list.jsp";

    } catch (Exception e) {
      model.put("refresh", "1;url=/");
      throw e;

    }
  }

  @RequestMapping("/board/update")
  public String update(
      Board board,
      Part[] files,
      Map<String, Object> model,
      HttpSession session) throws Exception {

    Member loginUser = (Member) session.getAttribute("loginUser");
    if (loginUser == null) {
      return "redirect:../auth/form";
    }

    try {
      Board b = boardService.get(board.getNo());
      if (b == null || b.getWriter().getNo() != loginUser.getNo()) {
        throw new Exception("게시글이 존재하지 않거나 변경 권한이 없습니다.");
      }

      ArrayList<AttachedFile> attachedFiles = new ArrayList<>();
      for (Part part : files) {
        if (part.getName().equals("files") && part.getSize() > 0) {
          String uploadFileUrl = ncpObjectStorageService.uploadFile(
              "bitcamp-nc7-bucket-14", "board/", part);
          AttachedFile attachedFile = new AttachedFile();
          attachedFile.setFilePath(uploadFileUrl);
          attachedFiles.add(attachedFile);
        }
      }
      board.setAttachedFiles(attachedFiles);
      boardService.update(board);
      return "redirect:list?category=" + b.getCategory();

    } catch (Exception e) {
      model.put("refresh", "2;url=detail?category=" + board.getCategory() + "&no=" + board.getNo());
      throw e;
    }
  }

  @RequestMapping("/board/fileDelete")
  public String fileDelete(@RequestParam("no") int no,
      Map<String, Object> model,
      HttpSession session)
      throws Exception {

    Member loginUser = (Member) session.getAttribute("loginUser");
    if (loginUser == null) {
      return "redirect:../auth/form";
    }

    Board board = null;

    try {
      AttachedFile attachedFile = boardService.getAttachedFile(no);
      board = boardService.get(attachedFile.getBoardNo());
      if (board.getWriter().getNo() != loginUser.getNo()) {
        throw new Exception("게시글 변경 권한이 없습니다!");
      }

      if (boardService.deleteAttachedFile(no) == 0) {
        throw new Exception("해당 번호의 첨부파일이 없습니다.");
      } else {
        return "redirect:detail?category=" + board.getCategory() + "&no=" + board.getNo();
      }

    } catch (Exception e) {
      model.put("refresh",
          "2;url=detail?category=" + board.getCategory() + "&no=" + board.getNo());
      throw e;
    }
  }

}
