package com.bitcamp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import com.bitcamp.myapp.dao.ScheduleDao;
import com.bitcamp.myapp.vo.Schedule;

public class MySQLScheduleDao implements ScheduleDao {

  Connection con;

  public MySQLScheduleDao(Connection con) {
    this.con = con;
  }

  @Override
  public void insert(Schedule schedule) {
    try (PreparedStatement stmt = con.prepareStatement(
        "insert into scheduleapp_schedule(schedule_title, start_time, end_time) values(?, ?, ?)");) {
      stmt.setString(1, schedule.getScheduleTitle());
      stmt.setString(2, new Timestamp(schedule.getStartTime()).toString());
      stmt.setString(3, new Timestamp(schedule.getEndTime()).toString());
      stmt.executeUpdate();

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public List<Schedule> list() {
    try (PreparedStatement stmt = con.prepareStatement(
        "select schedule_no, schedule_title, start_time, end_time from scheduleapp_schedule order by start_time asc");
        ResultSet rs = stmt.executeQuery()) {

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

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public Schedule findBy(int no) {
    try (PreparedStatement stmt = con.prepareStatement(
        "select schedule_no, schedule_title, start_time, end_time from scheduleapp_schedule where schedule_no="
            + no);
        ResultSet rs = stmt.executeQuery()) {

      if (rs.next()) {
        Schedule schedule = new Schedule();
        schedule.setNo(rs.getInt("schedule_no"));
        schedule.setScheduleTitle(rs.getString("schedule_title"));
        schedule.setStartTime(rs.getTimestamp("start_time").getTime());
        schedule.setEndTime(rs.getTimestamp("end_time").getTime());

        return schedule;
      }

      return null;

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public int update(Schedule schedule) {
    try (PreparedStatement stmt = con.prepareStatement(
        "update scheduleapp_schedule set schedule_title=?, start_time=?, end_time=? where schedule_no=?");) {
      stmt.setString(1, schedule.getScheduleTitle());
      stmt.setString(2, new Timestamp(schedule.getStartTime()).toString());
      stmt.setString(3, new Timestamp(schedule.getEndTime()).toString());
      stmt.setInt(4, schedule.getNo());
      return stmt.executeUpdate();

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public int remove(int no) {
    try (PreparedStatement stmt =
        con.prepareStatement("delete from scheduleapp_schedule where schedule_no=?");) {
      stmt.setInt(1, no);
      return stmt.executeUpdate();

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

}
