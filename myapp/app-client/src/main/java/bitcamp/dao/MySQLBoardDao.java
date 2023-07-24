package bitcamp.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.vo.Board;

public class MySQLBoardDao implements BoardDao {

  Connection con;
  int category;

  public MySQLBoardDao(Connection con, int category) {
    this.con = con;
    this.category = category;
  }

  @Override
  public void insert(Board board) {
    try (Statement stmt = con.createStatement();) {

      stmt.executeUpdate(String.format(
          "insert into myapp_board(title, content, writer, password, category) values('%s', '%s', '%s', '%s', %d)",
          board.getTitle(), board.getContent(), board.getWriter(), board.getPassword(),
          this.category));

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public List<Board> list() {
    try (Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(String.format(
            "select board_no, title,  writer, view_count, created_date from myapp_board where category=%d order by board_no desc",
            this.category))) {

      List<Board> list = new ArrayList<>();

      while (rs.next()) {
        Board board = new Board();
        board.setNo(rs.getInt("board_no"));
        board.setTitle(rs.getString("title"));
        board.setWriter(rs.getString("writer"));
        board.setViewCount(rs.getInt("view_count"));
        board.setCreatedDate(rs.getTimestamp("created_date"));

        list.add(board);
      }

      return list;

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public Board findBy(int no) {
    try (Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(String.format(
            "select board_no, title, content, writer, view_count, created_date from myapp_board where board_no=%d and category=%d",
            no, this.category))) {

      if (rs.next()) {
        Board board = new Board();
        board.setNo(rs.getInt("board_no"));
        board.setTitle(rs.getString("title"));
        board.setContent(rs.getString("content"));
        board.setWriter(rs.getString("writer"));
        board.setViewCount(rs.getInt("view_count"));
        board.setCreatedDate(rs.getTimestamp("created_date"));

        stmt.executeUpdate("update myapp_board set view_count=view_count+1 where board_no=" + no);

        return board;
      }

      return null;

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public int update(Board board) {
    try (Statement stmt = con.createStatement();) {

      return stmt.executeUpdate(String.format(
          "update myapp_board set title='%s', content='%s', view_count='%d' where board_no=%d and category=%d and password='%s'",
          board.getTitle(), board.getContent(), board.getViewCount(), board.getNo(), this.category,
          board.getPassword()));

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public int remove(Board board) {
    try (Statement stmt = con.createStatement();) {

      return stmt.executeUpdate(String.format(
          "delete from myapp_board where board_no=%d and category=%d and password='%s'",
          board.getNo(), this.category, board.getPassword()));

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

}
