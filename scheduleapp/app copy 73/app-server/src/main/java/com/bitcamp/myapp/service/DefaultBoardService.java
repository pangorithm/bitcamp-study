package com.bitcamp.myapp.service;

import com.bitcamp.myapp.dao.BoardDao;
import com.bitcamp.myapp.vo.AttachedFile;
import com.bitcamp.myapp.vo.Board;
import com.bitcamp.util.Transactional;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class DefaultBoardService implements BoardService {

  BoardDao boardDao;

  public DefaultBoardService(BoardDao boardDao) {
    this.boardDao = boardDao;
  }

  @Transactional
  @Override
  public int add(Board board) throws Exception {
    int count = boardDao.insert(board);
    if (board.getAttachedFiles().size() > 0) {
      boardDao.insertFiles(board);
    }
    return count;
  }

  @Override
  public List<Board> list(int category) throws Exception {
    return boardDao.findAll(category);
  }

  @Override
  public Board get(int boardNo) throws Exception {
    return boardDao.findBy(boardNo);
  }

  @Transactional
  @Override
  public int update(Board board) throws Exception {
    int count = boardDao.update(board);
    if (count > 0 && board.getAttachedFiles().size() > 0) {
      boardDao.insertFiles(board);
    }
    return count;
  }

  @Transactional
  @Override
  public int delete(int boardNo) throws Exception {
    boardDao.deleteFiles(boardNo);
    return boardDao.delete(boardNo);
  }

  @Transactional
  @Override
  public int increaseViewCount(int boardNo) throws Exception {
    return boardDao.updateCount(boardNo);
  }

  @Override
  public AttachedFile getAttachedFile(int fileNo) throws Exception {
    return boardDao.findFileBy(fileNo);
  }

  @Transactional
  @Override
  public int deleteAttachedFile(int fileNo) throws Exception {
    return boardDao.deleteFile(fileNo);
  }
}
