package com.bitcamp.myapp.handler;

import com.bitcamp.myapp.vo.Board;
import com.bitcamp.util.List;
import com.bitcamp.util.MenuPrompt;

public class BoardHandler implements Handler {

  private MenuPrompt prompt;
  private String title;
  private List list;

  public BoardHandler(MenuPrompt prompt, String title, List list) {
    this.prompt = prompt;
    this.title = title;
    this.list = list;
  }

  public void execute() {
    prompt.printMenu();
    prompt.appendBreadcrumb(title, getMenu());

    String menuNo;
    while (true) {
      menuNo = prompt.inputMenu();
      if (menuNo.equals("0")) {
        prompt.removeBreadcrumb();
        break;
      } else if (menuNo.equals("menu")) {
        prompt.printMenu();
      } else if (menuNo.equals("1")) {
        this.inputBoard();
      } else if (menuNo.equals("2")) {
        this.printBoards();
      } else if (menuNo.equals("3")) {
        this.viewBoard();
      } else if (menuNo.equals("4")) {
        this.setBoard();
      } else if (menuNo.equals("5")) {
        this.removeBoard();
      } else {
        System.out.println("올바르지 않은 메뉴 번호입니다.");
      }
    }
  }

  private String getMenu() {
    StringBuilder menu = new StringBuilder();

    menu.append("1. 등록\n");
    menu.append("2. 목록\n");
    menu.append("3. 검색\n");
    menu.append("4. 변경\n");
    menu.append("5. 삭제\n");
    menu.append("0. 메인");

    return menu.toString();
  }

  private void inputBoard() {
    Board board = new Board();
    board.setTitle(this.prompt.inputString("제목?\n"));
    board.setContent(this.prompt.inputString("내용?\n"));
    board.setWriter(this.prompt.inputString("작성자\n"));
    board.setPassword(this.prompt.inputString("암호\n"));
    this.list.add(board);
  }

  private void printBoards() {
    for (Object obj : this.list.toArray()) {
      Board board = (Board) obj;
      System.out.printf("번호: %d, 제목: %s, 작성자: %s, 조회수: %d, 작성일: %tY-%5$tm-%5$td\n", board.getNo(),
          board.getTitle(), board.getWriter(), board.getViewCount(), board.getCreatedDate());
    }
  }

  private void viewBoard() {
    Board board = (Board) this.list.get(findBy(this.prompt.inputInt("번호?")));
    if (board == null) {
      System.out.println("해당 번호의 게시글이 존재하지 않습니다.");
    } else {
      System.out.printf("제목: %s\n", board.getTitle());
      System.out.printf("내용: %s\n", board.getContent());
      System.out.printf("작성자: %s\n", board.getWriter());
      System.out.printf("조회수: %d\n", board.getViewCount());
      System.out.printf("작성일: %tY-%1$tm-%1$td\n", board.getCreatedDate());
      board.setViewCount(board.getViewCount() + 1);
    }
  }

  private void setBoard() {
    Board board = (Board) this.list.get(findBy(this.prompt.inputInt("번호?")));
    if (board == null) {
      System.out.println("해당 번호의 게시글이 존재하지 않습니다.");
    } else {
      if (board.getPassword().equals(this.prompt.inputString("암호?"))) {
        board.setTitle(this.prompt.inputString("제목? (%s)\n", board.getTitle()));
        board.setContent(this.prompt.inputString("내용? (%s)\n", board.getContent()));
        board.setCreatedDate(System.currentTimeMillis());
      } else {
        System.out.println("암호가 올바르지 않습니다.");
      }
    }
  }

  private void removeBoard() {
    Board board = (Board) this.list.get(findBy(this.prompt.inputInt("번호?")));
    if (board == null) {
      System.out.println("해당 번호의 게시글이 존재하지 않습니다.");
    } else {
      if (board.getPassword().equals(this.prompt.inputString("암호?"))) {
        this.list.remove(board);
      } else {
        System.out.println("암호가 올바르지 않습니다.");
      }
    }
  }

  private int findBy(int no) {
    Object[] list = this.list.toArray();
    for (int i = 0; i < this.list.size(); i++) {
      Board b = (Board) list[i];
      if (b.getNo() == no) {
        return i;
      }
    }
    return -1;
  }

}
