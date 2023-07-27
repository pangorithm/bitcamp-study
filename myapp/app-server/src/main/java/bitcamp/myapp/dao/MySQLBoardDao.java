package bitcamp.myapp.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import bitcamp.myapp.vo.Board;

public class MySQLBoardDao implements BoardDao {

  SqlSessionFactory sqlSessionFactory;
  int category;

  public MySQLBoardDao(SqlSessionFactory sqlSessionFactory, int category) {
    this.sqlSessionFactory = sqlSessionFactory;
    this.category = category;
  }

  @Override
  public void insert(Board board) {
    board.setCategory(this.category);
    SqlSession sqlSession = sqlSessionFactory.openSession(false);
    sqlSession.insert("bitcamp.myapp.dao.BoardDao.insert", board);
  }

  @Override
  public List<Board> findAll() {
    SqlSession sqlSession = sqlSessionFactory.openSession(true);
    return sqlSession.selectList("bitcamp.myapp.dao.BoardDao.findAll", this.category);
  }

  @Override
  public Board findBy(int no) {
    SqlSession sqlSession = sqlSessionFactory.openSession(true);

    Map<String, Object> paramMap = new HashMap<>();
    paramMap.put("categoryNo", this.category);
    paramMap.put("boardNo", no);

    return sqlSession.selectOne("bitcamp.myapp.dao.BoardDao.findBy", paramMap);
  }

  @Override
  public int updateCount(Board board) {
    board.setCategory(this.category);
    SqlSession sqlSession = sqlSessionFactory.openSession(false);
    return sqlSession.update("bitcamp.myapp.dao.BoardDao.updateCount", board);
  }

  @Override
  public int update(Board board) {
    board.setCategory(this.category);
    SqlSession sqlSession = sqlSessionFactory.openSession(false);
    return sqlSession.update("bitcamp.myapp.dao.BoardDao.update", board);
  }

  @Override
  public int delete(Board board) {
    board.setCategory(this.category);
    SqlSession sqlSession = sqlSessionFactory.openSession(false);
    return sqlSession.delete("bitcamp.myapp.dao.BoardDao.delete", board);
  }

}
