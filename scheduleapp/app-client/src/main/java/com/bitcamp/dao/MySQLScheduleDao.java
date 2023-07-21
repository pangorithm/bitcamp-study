package com.bitcamp.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
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
    try (Statement stmt = con.createStatement();) {

      stmt.executeUpdate(String.format(
          "insert into scheduleapp_schedule(schedule_title, start_time, end_time) values('%s', '%s', '%s')",
          schedule.getScheduleTitle(), new Timestamp(schedule.getStartTime()).toString(),
          new Timestamp(schedule.getEndTime()).toString()));

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public List<Schedule> list() {
    try (Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(
            "select schedule_no, schedule_title, start_time, end_time from scheduleapp_schedule order by start_time asc")) {

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
    try (Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(
            "select schedule_no, schedule_title, start_time, end_time from scheduleapp_schedule where schedule_no="
                + no)) {

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
    try (Statement stmt = con.createStatement();) {

      return stmt.executeUpdate(String.format(
          "update scheduleapp_schedule set schedule_title='%s', start_time='%s', end_time='%s' where schedule_no=%d",
          schedule.getScheduleTitle(), new Timestamp(schedule.getStartTime()).toString(),
          new Timestamp(schedule.getEndTime()).toString(), schedule.getNo()));

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public int remove(int no) {
    try (Statement stmt = con.createStatement();) {

      return stmt.executeUpdate(
          String.format("delete from scheduleapp_schedule where schedule_no=%d", no));

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

}
