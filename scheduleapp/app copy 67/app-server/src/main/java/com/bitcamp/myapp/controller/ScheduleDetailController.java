package com.bitcamp.myapp.controller;

import com.bitcamp.myapp.dao.ScheduleDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.bitcamp.myapp.vo.Member;
import com.bitcamp.myapp.vo.Schedule;
import org.apache.ibatis.session.SqlSessionFactory;

//@WebServlet("/schedule/detail")
public class ScheduleDetailController implements PageController {

  ScheduleDao scheduleDao;

  public ScheduleDetailController(ScheduleDao scheduleDao) {
    this.scheduleDao = scheduleDao;
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

    Schedule schedule = scheduleDao.findBy(Integer.parseInt((String) request.getParameter("no")));

    Member loginUser = (Member) request.getSession().getAttribute("loginUser");

    if (schedule == null) {
      request.setAttribute("refresh", "2;url=list");
      throw new Exception("해당 번호의 스케줄이 없습니다!");
    } else {

      request.setAttribute("schedule", schedule);
      List<Member> participantList = scheduleDao.findAllParticipatedMember(schedule.getNo());
      request.setAttribute("participantList", participantList);
      HashSet<Member> memberSet = new HashSet<>();
      memberSet.add(schedule.getOwner());
      memberSet.addAll(participantList);

      if (memberSet.contains(loginUser)) {
        return "/WEB-INF/jsp/schedule/detail.jsp";
      } else {
        return "redirect:list";
      }

    }

  }


}


