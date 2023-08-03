package com.bitcamp.myapp.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import com.bitcamp.myapp.vo.Member;
import com.bitcamp.myapp.vo.Schedule;
import com.bitcamp.util.Component;

@Component
public class MySQLScheduleDao implements ScheduleDao {

  SqlSessionFactory sqlSessionFactory;

  public MySQLScheduleDao(SqlSessionFactory sqlSessionFactory) {
    this.sqlSessionFactory = sqlSessionFactory;
  }

  @Override
  public void insert(Schedule schedule) {
    if (schedule == null) {
      return;
    }
    SqlSession sqlSession = sqlSessionFactory.openSession(false);
    sqlSession.insert("com.bitcamp.myapp.dao.ScheduleDao.insert", schedule);
  }

  @Override
  public List<Schedule> findAllOwnedSchedule(Member loginUser) {
    SqlSession sqlSession = sqlSessionFactory.openSession();
    return sqlSession
        .selectList("com.bitcamp.myapp.dao.ScheduleDao.findAllOwnedSchedule", loginUser);
  }

  @Override
  public List<Schedule> findAllParticipatedSchedule(Member loginUser) {
    SqlSession sqlSession = sqlSessionFactory.openSession();
    return sqlSession
        .selectList("com.bitcamp.myapp.dao.ScheduleDao.findAllParticipatedSchedule", loginUser);
  }

  @Override
  public List<Member> findAllParticipatedMember(int scheduleNo) {
    SqlSession sqlSession = sqlSessionFactory.openSession();
    return sqlSession
        .selectList("com.bitcamp.myapp.dao.ScheduleDao.findAllParticipatedMember", scheduleNo);
  }

  @Override
  public Schedule findBy(int no) {
    SqlSession sqlSession = sqlSessionFactory.openSession();
    return sqlSession.selectOne("com.bitcamp.myapp.dao.ScheduleDao.findBy", no);
  }

  @Override
  public int update(Schedule schedule) {
    Map<String, Object> paramMap = new HashMap<>();
    paramMap.put("scheduleTitle", schedule.getScheduleTitle());
    paramMap.put("startTime", schedule.getStartTime().toString());
    paramMap.put("endTime", schedule.getEndTime().toString());
    paramMap.put("scheduleNo", schedule.getNo());
    paramMap.put("ownerNo", schedule.getOwner().getNo());

    SqlSession sqlSession = sqlSessionFactory.openSession(false);
    return sqlSession.update("com.bitcamp.myapp.dao.ScheduleDao.update", paramMap);
  }

  @Override
  public int delete(Schedule schedule) {
    Map<String, Object> paramMap = new HashMap<>();
    paramMap.put("scheduleNo", schedule.getNo());
    paramMap.put("ownerNo", schedule.getOwner().getNo());

    SqlSession sqlSession = sqlSessionFactory.openSession(false);
    return sqlSession.delete("com.bitcamp.myapp.dao.ScheduleDao.delete", paramMap);
  }

  @Override
  public int scheduleAddParticipant(int scheduleNo, int memberNo) {
    Map<String, Object> paramMap = new HashMap<>();
    paramMap.put("scheduleNo", scheduleNo);
    paramMap.put("memberNo", memberNo);

    SqlSession sqlSession = sqlSessionFactory.openSession(false);
    if (sqlSession.selectOne("com.bitcamp.myapp.dao.ScheduleDao.checkParticipant", paramMap)
        == null) {
      try {
        return sqlSession.insert("com.bitcamp.myapp.dao.ScheduleDao.insertParticipant", paramMap);
      } catch (Exception sqlE) {
        return -2;
      }
    } else {
      return -1;
    }
  }


  @Override
  public int scheduleDeleteParticipant(int scheduleNo, int memberNo) {
    Map<String, Object> paramMap = new HashMap<>();
    paramMap.put("scheduleNo", scheduleNo);
    paramMap.put("memberNo", memberNo);

    SqlSession sqlSession = sqlSessionFactory.openSession(false);
    if (sqlSession.selectOne("com.bitcamp.myapp.dao.ScheduleDao.checkParticipant", paramMap)
        != null) {
      try {
        return sqlSession.delete("com.bitcamp.myapp.dao.ScheduleDao.deleteParticipant", paramMap);
      } catch (Exception sqlE) {
        return -2;
      }
    } else {
      return -1;
    }
  }


}
