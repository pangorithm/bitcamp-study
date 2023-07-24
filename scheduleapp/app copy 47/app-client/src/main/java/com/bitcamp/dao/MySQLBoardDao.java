package com.bitcamp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.bitcamp.myapp.dao.BoardDao;
import com.bitcamp.myapp.vo.Board;

public class MySQLBoardDao implements BoardDao {

  Connection con;
  int category;

  public MySQLBoardDao(Connection con, int category) {
    this.con = con;
    this.category = category;
  }

  @Override
  public void insert(Board board) {
    try (PreparedStatement stmt = con.prepareStatement(
        "insert into scheduleapp_board(title, content, writer, password, category)"
            + " values(?, ?, ?, sha1(?), ?)");) {

      stmt.setString(1, board.getTitle());
      stmt.setString(2, board.getContent());
      stmt.setString(3, board.getWriter());
      stmt.setString(4, board.getPassword());
      stmt.setInt(5, this.category);
      stmt.executeUpdate();

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public List<Board> list() {
    try (PreparedStatement stmt =
        con.prepareStatement("select board_no, title,  writer, view_count, created_date"
            + " from scheduleapp_board where category=? order by board_no desc");) {
      stmt.setInt(1, this.category);

      try (ResultSet rs = stmt.executeQuery();) {

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

      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public Board findBy(int no) {
    try (PreparedStatement stmt =
        con.prepareStatement("select board_no, title, content, writer, view_count, created_date"
            + " from scheduleapp_board where board_no=? and category=?");) {

      stmt.setInt(1, no);
      stmt.setInt(2, this.category);
      try (ResultSet rs = stmt.executeQuery()) {

        if (rs.next()) {
          Board board = new Board();
          board.setNo(rs.getInt("board_no"));
          board.setTitle(rs.getString("title"));
          board.setContent(rs.getString("content"));
          board.setWriter(rs.getString("writer"));
          board.setViewCount(rs.getInt("view_count"));
          board.setCreatedDate(rs.getTimestamp("created_date"));

          stmt.executeUpdate(
              "update scheduleapp_board set view_count=view_count+1 where board_no=" + no);

          return board;
        }
        return null;

      }

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public int update(Board board) {
    try (PreparedStatement stmt =
        con.prepareStatement("update scheduleapp_board set title=?, content=?, view_count=?"
            + " where board_no=? and category=? and password=sha1(?)");) {

      stmt.setString(1, board.getTitle());
      stmt.setString(2, board.getContent());
      stmt.setInt(3, board.getViewCount());
      stmt.setInt(4, board.getNo());
      stmt.setInt(5, this.category);
      stmt.setString(6, board.getPassword());

      return stmt.executeUpdate();

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public int remove(Board board) {
    try (PreparedStatement stmt = con.prepareStatement(
        "delete from scheduleapp_board where board_no=? and category=? and password=sha1(?)");) {

      stmt.setInt(1, board.getNo());
      stmt.setInt(2, this.category);
      stmt.setString(3, board.getPassword());
      return stmt.executeUpdate();

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

}
