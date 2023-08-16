package com.bitcamp.myapp.dao;

import java.util.List;
import com.bitcamp.myapp.vo.AttachedFile;
import com.bitcamp.myapp.vo.Board;

public interface BoardDao {
  void insert(Board board);

  List<Board> findAll(int category);

  Board findBy(int no, int category);

  int update(Board board);

  int updateCount(Board board);

  int delete(Board board);

  int insertFiles(Board board);

  AttachedFile findFileBy(int no);

  int deleteFile(int fileNo);

}
