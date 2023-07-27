package com.bitcamp.myapp.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.bitcamp.myapp.vo.Member;
import com.bitcamp.util.DataSource;

public class MySQLMemberDao implements MemberDao {

  DataSource ds;

  public MySQLMemberDao(DataSource ds) {
    this.ds = ds;
  }

  @Override
  public void insert(Member member) {
    try (PreparedStatement stmt = ds.getConnection(false).prepareStatement(
        "insert into scheduleapp_member(name, email, password, gender) values(?, ?, sha1(?), ?)");) {

      stmt.setString(1, member.getName());
      stmt.setString(2, member.getEmail());
      stmt.setString(3, member.getPassword());
      stmt.setString(4, String.valueOf(member.getGender()));

      stmt.executeUpdate();

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public List<Member> list() {
    try (
        PreparedStatement stmt = ds.getConnection().prepareStatement(
            "select member_no, name, email, gender from scheduleapp_member order by name asc");
        ResultSet rs = stmt.executeQuery()) {

      List<Member> list = new ArrayList<>();

      while (rs.next()) {
        Member m = new Member();
        m.setNo(rs.getInt("member_no"));
        m.setName(rs.getString("name"));
        m.setEmail(rs.getString("email"));
        m.setGender(rs.getString("gender").charAt(0));

        list.add(m);
      }

      return list;

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public Member findBy(int no) {
    try (PreparedStatement stmt = ds.getConnection().prepareStatement(
        "select member_no, name, email, gender, created_date from scheduleapp_member where member_no=?");) {
      stmt.setInt(1, no);

      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          Member m = new Member();
          m.setNo(rs.getInt("member_no"));
          m.setName(rs.getString("name"));
          m.setEmail(rs.getString("email"));
          m.setGender(rs.getString("gender").charAt(0));
          m.setCreatedDate(rs.getDate("created_date"));

          return m;
        }
        return null;
      }

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public Member findByEmailAndPassword(Member param) {
    try (PreparedStatement stmt =
        ds.getConnection().prepareStatement("select member_no, name, email, gender, created_date"
            + " from scheduleapp_member where email=? and password=sha1(?)");) {
      stmt.setString(1, param.getEmail());
      stmt.setString(2, param.getPassword());

      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          Member m = new Member();
          m.setNo(rs.getInt("member_no"));
          m.setName(rs.getString("name"));
          m.setEmail(rs.getString("email"));
          m.setGender(rs.getString("gender").charAt(0));
          m.setCreatedDate(rs.getDate("created_date"));

          return m;
        }
        return null;
      }

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public int update(Member member) {
    try (PreparedStatement stmt = ds.getConnection(false).prepareStatement(
        "update scheduleapp_member set name=?, email=?, password=sha1(?), gender=? where member_no=?");) {
      stmt.setString(1, member.getName());
      stmt.setString(2, member.getEmail());
      stmt.setString(3, member.getPassword());
      stmt.setString(4, String.valueOf(member.getGender()));
      stmt.setInt(5, member.getNo());

      return stmt.executeUpdate();

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public int remove(int no) {
    try (PreparedStatement stmt = ds.getConnection(false)
        .prepareStatement("delete from scheduleapp_member where member_no=?");) {

      stmt.setInt(1, no);
      return stmt.executeUpdate();

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }


}
