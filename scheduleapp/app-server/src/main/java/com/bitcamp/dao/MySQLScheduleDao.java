package com.bitcamp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import com.bitcamp.myapp.dao.ScheduleDao;
import com.bitcamp.myapp.vo.Member;
import com.bitcamp.myapp.vo.Schedule;

public class MySQLScheduleDao implements ScheduleDao {

  Connection con;

  public MySQLScheduleDao(Connection con) {
    this.con = con;
  }

  @Override
  public void insert(Schedule schedule) {
    if (schedule == null) {
      return;
    }
    try (PreparedStatement stmt = con.prepareStatement(
        "insert into scheduleapp_schedule(schedule_title, start_time, end_time, owner)"
            + " values(?, ?, ?, ?)");) {
      stmt.setString(1, schedule.getScheduleTitle());
      stmt.setString(2, new Timestamp(schedule.getStartTime()).toString());
      stmt.setString(3, new Timestamp(schedule.getEndTime()).toString());
      stmt.setInt(4, schedule.getOwner().getNo());
      stmt.executeUpdate();

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public List<Schedule> list(Member loginUser) {
    try (PreparedStatement stmt = con.prepareStatement("""
        select schedule_no, schedule_title, start_time, end_time, owner, m_owner.name as owner_name,
        ss.participant_no, m_member.name as participant_name
        from (select sch.schedule_no, sch.schedule_title, sch.start_time, sch.end_time, sch.owner,
          sp.member_no as participant_no
          from scheduleapp_schedule as sch inner join scheduleapp_schedule_participants as sp
          on sch.schedule_no=sp.schedule_no where sch.owner=? or sp.member_no=?) ss
            inner join scheduleapp_member m_owner on ss.owner=m_owner.member_no
            inner join scheduleapp_member m_member on ss.participant_no=m_member.member_no
            order by start_time asc
            """);) {
      stmt.setInt(1, loginUser.getNo());
      stmt.setInt(2, loginUser.getNo());

      try (ResultSet rs = stmt.executeQuery()) {

        List<Schedule> list = new ArrayList<>();

        while (rs.next()) {
          Schedule schedule = new Schedule();
          schedule.setNo(rs.getInt("schedule_no"));
          schedule.setScheduleTitle(rs.getString("schedule_title"));
          schedule.setStartTime(rs.getTimestamp("start_time").getTime());
          schedule.setEndTime(rs.getTimestamp("end_time").getTime());

          list.add(schedule);
        }

        return list;

      }

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public List<Member> participantList(int scheduleNo) {
    try (PreparedStatement stmt = con.prepareStatement("""
        select sp.schedule_no, m.member_no, m.name
          from scheduleapp_schedule_participants as sp inner join scheduleapp_member m
            on sp.member_no=m.member_no
          where schedule_no=?
          order by member_no
            """);) {
      stmt.setInt(1, scheduleNo);

      try (ResultSet rs = stmt.executeQuery()) {

        List<Member> list = new ArrayList<>();

        while (rs.next()) {
          Member member = new Member();
          member.setNo(rs.getInt("member_no"));
          member.setName(rs.getString("name"));

          list.add(member);
        }

        return list;

      }

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public Schedule findBy(int no, Member loginUser) {
    try (PreparedStatement stmt = con.prepareStatement("""
        select schedule_no, schedule_title, start_time, end_time, owner, m_owner.name as owner_name,
        ss.participant_no, m_member.name as participant_name
        from (select sch.schedule_no, sch.schedule_title, sch.start_time, sch.end_time, sch.owner,
          sp.member_no as participant_no
          from scheduleapp_schedule as sch inner join scheduleapp_schedule_participants as sp
          on sch.schedule_no=sp.schedule_no where sch.owner=? or sp.member_no=?) ss
            inner join scheduleapp_member m_owner on ss.owner=m_owner.member_no
            inner join scheduleapp_member m_member on ss.participant_no=m_member.member_no
            where schedule_no=?
            """);) {
      stmt.setInt(1, loginUser.getNo());
      stmt.setInt(2, loginUser.getNo());
      stmt.setInt(3, no);

      try (ResultSet rs = stmt.executeQuery()) {

        if (rs.next()) {
          Schedule schedule = new Schedule();
          schedule.setNo(rs.getInt("schedule_no"));
          schedule.setScheduleTitle(rs.getString("schedule_title"));
          schedule.setStartTime(rs.getTimestamp("start_time").getTime());
          schedule.setEndTime(rs.getTimestamp("end_time").getTime());

          Member owner = new Member();
          owner.setNo(rs.getInt("owner"));
          owner.setName(rs.getString("owner_name"));
          schedule.setOwner(owner);

          return schedule;
        }

        return null;

      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public int update(Schedule schedule) {
    try (PreparedStatement stmt = con.prepareStatement(
        "update scheduleapp_schedule set schedule_title=?, start_time=?, end_time=?"
            + " where schedule_no=? and owner=?");) {
      stmt.setString(1, schedule.getScheduleTitle());
      stmt.setString(2, new Timestamp(schedule.getStartTime()).toString());
      stmt.setString(3, new Timestamp(schedule.getEndTime()).toString());
      stmt.setInt(4, schedule.getNo());
      stmt.setInt(5, schedule.getNo());
      return stmt.executeUpdate();

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public int remove(Schedule schedule) {
    try (PreparedStatement stmt =
        con.prepareStatement("delete from scheduleapp_schedule where schedule_no=? and owner=?");) {
      stmt.setInt(1, schedule.getNo());
      stmt.setInt(2, schedule.getNo());
      return stmt.executeUpdate();

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public int scheduleAddParticipant(int scheduleNo, int memberNo) {
    try (PreparedStatement stmtSelect =
        con.prepareStatement("select * from scheduleapp_schedule_participants"
            + " where schedule_no=? and member_no=?");) {
      stmtSelect.setInt(1, scheduleNo);
      stmtSelect.setInt(2, memberNo);

      try (ResultSet rs = stmtSelect.executeQuery()) {
        if (!rs.next()) {
          try (PreparedStatement stmtInsert = con.prepareStatement(
              "insert into scheduleapp_schedule_participants(schedule_no, member_no)"
                  + " values(?, ?)");) {
            stmtInsert.setInt(1, scheduleNo);
            stmtInsert.setInt(2, memberNo);
            return stmtInsert.executeUpdate();
          }
        } else {
          return -1;
        }
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

  }

}
