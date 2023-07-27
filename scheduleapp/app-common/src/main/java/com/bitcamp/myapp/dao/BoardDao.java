package com.bitcamp.myapp.dao;

import java.util.List;
import com.bitcamp.myapp.vo.Board;

public interface BoardDao {
  void insert(Board board);

  List<Board> findAll();

  Board findBy(int no);

  int update(Board board);

  int updateCount(Board board);

  int delete(Board board);

}
