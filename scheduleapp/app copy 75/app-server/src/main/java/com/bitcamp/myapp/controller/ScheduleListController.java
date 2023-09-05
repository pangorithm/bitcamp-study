package com.bitcamp.myapp.controller;

import com.bitcamp.myapp.service.ScheduleService;
import com.bitcamp.myapp.vo.Member;
import com.bitcamp.myapp.vo.Schedule;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller("/schedule/list")
public class ScheduleListController {

  @Autowired
  ScheduleService scheduleService;

  @RequestMapping
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

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
}
