package com.bitcamp.myapp.controller;

import com.bitcamp.myapp.service.ScheduleService;
import com.bitcamp.myapp.vo.Member;
import com.bitcamp.myapp.vo.Schedule;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ScheduleController {

  @Autowired
  ScheduleService scheduleService;

  @RequestMapping("/schedule/add")
  public String add(HttpServletRequest request, HttpServletResponse response) throws Exception {
    if (request.getMethod().equals("GET")) {
      return "/WEB-INF/jsp/schedule/form.jsp";
    }

    Schedule sch = new Schedule();
    sch.setTitle(
        request.getParameter("title"));
    sch.setContent(request
        .getParameter("content"));
    sch.setStartTime(Timestamp.valueOf(LocalDateTime.parse(request.getParameter("start-time"))));
    sch.setEndTime(Timestamp.valueOf(LocalDateTime.parse(request.getParameter("end-time"))));
    sch.setOwner((Member) request.getSession().getAttribute("loginUser"));

    try {
      scheduleService.add(sch);
      // 새로 생성된 스케줄 번호를 알아야함
      scheduleService.addScheduleParticipant(sch.getNo(),
          ((Member) request.getAttribute("loginUser")).getNo());
      return "redirect:list";
    } catch (Exception e) {

      request.setAttribute("message", "스케줄 등록 오류!");
      request.setAttribute("refresh", "2;url=list");
      throw e;
    }
  }

  @RequestMapping("/schedule/delete")
  public String delete(HttpServletRequest request, HttpServletResponse response) throws Exception {

    Schedule sch = scheduleService.get(Integer.parseInt(request.getParameter("no")));
    Member loginUser = (Member) request.getSession().getAttribute("loginUser");

    try {
      if (sch == null) {
        throw new Exception("해당 번호의 스케줄이 없습니다!");
      }

      if (!sch.getOwner().equals(loginUser)) {
        throw new Exception("삭제 권한이 없습니다.");
      }

      scheduleService.delete(sch.getNo());
      return "redirect:list";
    } catch (Exception e) {
      request.setAttribute("error", e);
      request.setAttribute("message", e.getMessage());
      request.setAttribute("refresh", "2;url=list");
      throw e;
    }

  }

  @RequestMapping("/schedule/detail")
  public String detail(HttpServletRequest request, HttpServletResponse response) throws Exception {

    Schedule schedule = scheduleService.get(Integer.parseInt((String) request.getParameter("no")));

    Member loginUser = (Member) request.getSession().getAttribute("loginUser");

    if (schedule == null) {
      request.setAttribute("refresh", "2;url=list");
      throw new Exception("해당 번호의 스케줄이 없습니다!");
    } else {

      request.setAttribute("schedule", schedule);
      List<Member> participantList = scheduleService.getParticipatedMemberList(schedule.getNo());
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

  @RequestMapping("/schedule/list")
  public String list(HttpServletRequest request, HttpServletResponse response) throws Exception {

    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();

    Member loginUser = (Member) request.getSession().getAttribute("loginUser");
    if (loginUser == null) {
      return "redirect:../auth/login";
    }

    Map<String, List<Schedule>> listMap = scheduleService.listMap(loginUser.getNo());
    request.setAttribute("ownedList", listMap.get("ownedList"));
    request.setAttribute("participatedList", listMap.get("participatedList"));
    return "/WEB-INF/jsp/schedule/list.jsp";
  }

  @RequestMapping("/schedule/participantAdd")
  public String participantAdd(HttpServletRequest request, HttpServletResponse response)
      throws Exception {

    int scheduleNo = Integer.parseInt(request.getParameter("no"));
    int addParticipantNo = Integer.parseInt(request.getParameter("addParticipantNo"));

    try {
      int result = scheduleService.addScheduleParticipant(scheduleNo, addParticipantNo);

      if (result == -1) {
        throw new Exception("이미 참가중인 멤버입니다.");
      } else if (result == -2) {
        throw new Exception("존재하지 않는 멤버입니다.");
      } else {
        return "redirect:detail?no=" + scheduleNo;
      }

    } catch (Exception e) {
      request.setAttribute("message", e.getMessage());
      request.setAttribute("refresh", "2;url=detail?no=" + scheduleNo);
      throw e;
    }
  }

  @RequestMapping("/schedule/participantDelete")
  public String participantDelete(HttpServletRequest request, HttpServletResponse response)
      throws Exception {
    int scheduleNo = Integer.parseInt(request.getParameter("no"));
    int deleteParticipantNo = Integer.parseInt(request.getParameter("deleteParticipantNo"));

    try {
      int result = scheduleService.deleteScheduleParticipant(scheduleNo, deleteParticipantNo);

      if (result == -1) {
        throw new Exception("참여하지 않는 멤버입니다.");
      } else if (result == -2) {
        throw new Exception("존재하지 않는 멤버입니다.");
      } else {
        return "redirect:detail?no=" + scheduleNo;
      }

    } catch (Exception e) {
      request.setAttribute("message", e.getMessage());
      request.setAttribute("refresh", "2;url=detail?no=" + scheduleNo);
      throw e;
    }
  }

  @RequestMapping("/schedule/search")
  public String search(HttpServletRequest request, HttpServletResponse response) throws Exception {
    if (request.getMethod().equals("GET")) {
      return "/WEB-INF/jsp/schedule/searchForm.jsp";

    }

    Member loginUser = (Member) request.getSession().getAttribute("loginUser");

    String searchTitle = request.getParameter("title");
    long searchRangeStart =
        localDateTimeToLong(LocalDateTime.parse(request.getParameter("start-time")));
    long searchRangeEnd =
        localDateTimeToLong(LocalDateTime.parse(request.getParameter("end-time")));

    Map<String, List<Schedule>> listMap = scheduleService.listMap(loginUser.getNo());
    HashSet<Schedule> set = new HashSet<>(listMap.get("ownedList"));
    set.addAll(listMap.get("participatedList"));
    set.removeIf(sch ->
        !(searchTitle.length() > 0 && sch.getTitle().matches(String.format(".*%s.*", searchTitle))
            || (sch.getEndTime().getTime() > searchRangeStart
            && sch.getStartTime().getTime() < searchRangeEnd))
    );
    request.setAttribute("list", new ArrayList<Schedule>(set));
    return "/WEB-INF/jsp/schedule/search.jsp";
  }

  @RequestMapping("/schedule/update")
  public String update(HttpServletRequest request, HttpServletResponse response) throws Exception {

    Schedule sch = scheduleService.get(Integer.parseInt(request.getParameter("no")));
    if (sch == null
        || sch.getOwner().getNo()
        != ((Member) request.getSession().getAttribute("loginUser")).getNo()) {
      throw new Exception("스케줄이 없거나 변경 권한이 없습니다.");
    }

    sch.setTitle(request.getParameter("title"));
    sch.setContent(request.getParameter("content"));
    sch.setStartTime(Timestamp.valueOf(LocalDateTime.parse(request.getParameter("start-time"))));
    sch.setEndTime(Timestamp.valueOf(LocalDateTime.parse(request.getParameter("end-time"))));

    try {
      scheduleService.update(sch);
      return "redirect:list";
    } catch (Exception e) {
      request.setAttribute("message", e.getMessage());
      request.setAttribute("refresh", "2;url=detail?no=" + sch.getNo());
      throw e;
    }
  }

  private long localDateTimeToLong(LocalDateTime localDateTime) {
    return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
  }
}
