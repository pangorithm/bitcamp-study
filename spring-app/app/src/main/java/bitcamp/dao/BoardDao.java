package bitcamp.dao;

import bitcamp.vo.Board;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

// 데이터의 입출력을 다루는 객체(퍼시스턴스 객체; persistence)인 경우 다음 애노테이션을 붙인다.
@Repository
public class BoardDao {

  ArrayList<Board> boards = new ArrayList<>();

  {
    System.out.println("boardDao 생성됨!");
  }

  public BoardDao() {
    boards.add(new Board(1, "게시물입니다.111"));
    boards.add(new Board(2, "게시물입니다.222"));
    boards.add(new Board(3, "게시물입니다.333"));
    boards.add(new Board(4, "게시물입니다.444"));
    boards.add(new Board(5, "게시물입니다.555"));

  }

  public List<Board> findAll() {
    return boards;
  }

  public void insert(Board board) {
    boards.add(board);
  }

  public Board findBy(int no) {
    for (Board board : boards) {
      if (board.getNo() == no) {
        return board;
      }
    }
    return null;
  }
}






