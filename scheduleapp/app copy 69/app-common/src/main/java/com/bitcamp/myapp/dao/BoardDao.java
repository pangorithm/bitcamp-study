package com.bitcamp.myapp.dao;

import com.bitcamp.myapp.vo.AttachedFile;
import com.bitcamp.myapp.vo.Board;
import java.util.List;

public interface BoardDao {

  int insert(Board board);

  List<Board> findAll(int category);

  Board findBy(int boardNo);

  int update(Board board);

  int updateCount(int boardNo);

  int delete(int boardNo);

  int insertFiles(Board board);

  AttachedFile findFileBy(int fileNo);

  int deleteFile(int fileNo);

  int deleteFiles(int boardNo);
}
