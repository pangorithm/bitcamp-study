package com.bitcamp.myapp.controller;

import com.bitcamp.myapp.service.ScheduleService;
import com.bitcamp.myapp.vo.Member;
import com.bitcamp.myapp.vo.Schedule;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ScheduleController {

  @Autowired
  ScheduleService scheduleService;

  {
    System.out.println("ScheduleController 생성됨!");
  }

  @RequestMapping("/schedule/form")
  public String form() {
    return "/WEB-INF/jsp/schedule/form.jsp";
  }

  @RequestMapping("/schedule/add")
  public String add(
      Schedule sch,
      Map<String, Object> model,
      HttpSession session) throws Exception {

    sch.setOwner((Member) session.getAttribute("loginUser"));

    try {
      scheduleService.add(sch);
      // 새로 생성된 스케줄 번호를 알아야함
      scheduleService.addScheduleParticipant(sch.getNo(), (sch.getOwner().getNo()));
      return "redirect:list";
    } catch (Exception e) {

      model.put("message", "스케줄 등록 오류!");
      model.put("refresh", "2;url=list");
      throw e;
    }
  }

  @RequestMapping("/schedule/delete")
  public String delete(
      Schedule sch,
      Map<String, Object> model,
      HttpSession session) throws Exception {

    Member loginUser = (Member) session.getAttribute("loginUser");

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
      model.put("error", e);
      model.put("message", e.getMessage());
      model.put("refresh", "2;url=list");
      throw e;
    }

  }

  @RequestMapping("/schedule/detail")
  public String detail(
      int no,
      Map<String, Object> model,
      HttpSession session) throws Exception {

    Schedule schedule = scheduleService.get(no);

    Member loginUser = (Member) session.getAttribute("loginUser");

    if (schedule == null) {
      model.put("refresh", "2;url=list");
      throw new Exception("해당 번호의 스케줄이 없습니다!");
    } else {

      model.put("schedule", schedule);
      List<Member> participantList = scheduleService.getParticipatedMemberList(schedule.getNo());
      model.put("participantList", participantList);
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
  public String list(Map<String, Object> model, HttpSession session) throws Exception {

    Member loginUser = (Member) session.getAttribute("loginUser");
    if (loginUser == null) {
      return "redirect:../auth/login";
    }

    Map<String, List<Schedule>> listMap = scheduleService.listMap(loginUser.getNo());
    model.put("ownedList", listMap.get("ownedList"));
    model.put("participatedList", listMap.get("participatedList"));
    return "/WEB-INF/jsp/schedule/list.jsp";
  }

  @RequestMapping("/schedule/participantAdd")
  public String participantAdd(
      int scheduleNo,
      int addParticipantNo,
      Map<String, Object> model) throws Exception {

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
      model.put("message", e.getMessage());
      model.put("refresh", "2;url=detail?no=" + scheduleNo);
      throw e;
    }
  }

  @RequestMapping("/schedule/participantDelete")
  public String participantDelete(
      int scheduleNo,
      int deleteParticipantNo,
      Map<String, Object> model
  ) throws Exception {

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
      model.put("message", e.getMessage());
      model.put("refresh", "2;url=detail?no=" + scheduleNo);
      throw e;
    }
  }

  @RequestMapping("/schedule/searchForm")
  public String searchForm() {
    return "/WEB-INF/jsp/schedule/searchForm.jsp";
  }

  @RequestMapping("/schedule/search")
  public String search(
      String title,
      String startTime,
      String endTime,
      Map<String, Object> model,
      HttpSession session) throws Exception {

    Member loginUser = (Member) session.getAttribute("loginUser");

    long searchRangeStart = localDateTimeToLong(LocalDateTime.parse(startTime));
    long searchRangeEnd = localDateTimeToLong(LocalDateTime.parse(endTime));

    Map<String, List<Schedule>> listMap = scheduleService.listMap(loginUser.getNo());
    HashSet<Schedule> set = new HashSet<>(listMap.get("ownedList"));
    set.addAll(listMap.get("participatedList"));
    set.removeIf(sch ->
        !(title.length() > 0 && sch.getTitle().matches(String.format(".*%s.*", title))
            || (sch.getEndTime().getTime() > searchRangeStart
            && sch.getStartTime().getTime() < searchRangeEnd))
    );
    model.put("list", new ArrayList<Schedule>(set));
    return "/WEB-INF/jsp/schedule/search.jsp";
  }

  @RequestMapping("/schedule/update")
  public String update(
      Schedule schedule,
      Map<String, Object> model,
      HttpSession session) throws Exception {

    Schedule sch = scheduleService.get(schedule.getNo());
    if (sch == null
        || sch.getOwner().getNo()
        != ((Member) session.getAttribute("loginUser")).getNo()) {
      throw new Exception("스케줄이 없거나 변경 권한이 없습니다.");
    }

    try {
      scheduleService.update(schedule);
      return "redirect:list";
    } catch (Exception e) {
      model.put("message", e.getMessage());
      model.put("refresh", "2;url=detail?no=" + schedule.getNo());
      throw e;
    }
  }

  private long localDateTimeToLong(LocalDateTime localDateTime) {
    return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
  }
}
