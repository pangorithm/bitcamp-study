package bitcamp.myapp.dao;

import java.util.LinkedList;
import java.util.List;
import bitcamp.myapp.vo.Board;
import bitcamp.util.JsonDataHelper;

public class BoardListDao implements BoardDao {

  String filename;
  LinkedList<Board> list = new LinkedList<>();

  public BoardListDao(String filename) {
    this.filename = filename;
    JsonDataHelper.loadJson(filename, list, Board.class);
  }

  @Override
  public void insert(Board board) {
    board.setNo(Board.boardNo++);
    board.setCreatedDate(System.currentTimeMillis());
    this.list.add(board);

    JsonDataHelper.saveJson(filename, list);
  }

  @Override
  public List<Board> list() {
    return this.list;
  }

  @Override
  public Board findBy(int no) {
    for (int i = 0; i < this.list.size(); i++) {
      Board b = this.list.get(i);
      if (b.getNo() == no) {
        return b;
      }
    }
    return null;
  }

  @Override
  public int update(Board board) {
    for (int i = 0; i < list.size(); i++) {
      if (list.get(i).getNo() == board.getNo()) {
        list.set(i, board);
        JsonDataHelper.saveJson(filename, list);
        return 1;
      }
    }
    return 0;
  }

  @Override
  public int remove(int no) {
    for (int i = 0; i < list.size(); i++) {
      if (list.get(i).getNo() == no) {
        list.remove(i);
        JsonDataHelper.saveJson(filename, list);
        return 1;
      }
    }
    return 0;
  }


}

