package com.bitcamp.myapp.handler;

import com.bitcamp.myapp.vo.Schedule;
import com.bitcamp.util.ActionListener;
import com.bitcamp.util.List;
import com.bitcamp.util.Prompt;

public abstract class AbstractScheduleListener implements ActionListener {
  protected List list;

  public AbstractScheduleListener(List list) {
    this.list = list;
  }


  protected Schedule inputScheduleInfo(Schedule sch, Prompt prompt) {
    sch.setScheduleTitle(prompt.inputString("스케줄 제목을 입력하세요\n> "));

    do {
      System.out.println("스케줄 시작 날짜와 시간을 입력하세요");
      System.out.println("ex)2023-06-05 08:30");
      sch.setStartTime(parseDateLong(prompt.inputString("> ")));
    } while (!checkDate(sch.getStartTime()));

    do {
      System.out.println("스케줄 종료 날짜와 시간을 입력하세요");
      System.out.println("ex)2023-06-06 20:00");
      sch.setEndTime(parseDateLong(prompt.inputString("> ")));
    } while (!checkDate(sch.getEndTime()));
    return sch;
  }

  protected void printScheduleInfo(Schedule sch) {
    System.out.print("번호: ");
    System.out.println(sch.getNo());
    System.out.print("제목: ");
    System.out.println(sch.getScheduleTitle());
    System.out.print("시작: ");
    System.out.println(dateToString(sch.getStartTime()));
    System.out.print("종료: ");
    System.out.println(dateToString(sch.getEndTime()));
    System.out.println("");
  }

  protected int findBy(int no) {
    Object[] list = this.list.toArray();
    for (int i = 0; i < this.list.size(); i++) {
      Schedule b = (Schedule) list[i];
      if (b.getNo() == no) {
        return i;
      }
    }
    return -1;
  }

  protected static long parseDateLong(String date) {
    String str = date.replaceAll("[^0-9]", "");
    if (str.equals("")) {
      System.out.println("올바르지 않은 입력입니다");
    }
    long dateLong = Long.parseLong(str);
    return dateLong;
  }

  protected static String dateToString(long date) {
    String dateString = "";

    int min = (int) (date % 100);
    date /= 100;
    int hour = (int) (date % 100);
    date /= 100;
    int day = (int) (date % 100);
    date /= 100;
    int month = (int) (date % 100);
    date /= 100;
    int year = (int) (date % 10000);

    dateString = year + "년" + month + "월" + day + "일 " + hour + ":" + min;

    return dateString;
  }

  protected static boolean checkDate(long date) {

    int min = (int) (date % 100);
    date /= 100;
    int hour = (int) (date % 100);
    date /= 100;
    int day = (int) (date % 100);
    date /= 100;
    int month = (int) (date % 100);
    date /= 100;
    // int year = (int) (date % 10000);

    if (month < 1 || month > 12) {
      System.out.println("올바르지 않은 날짜 형식입니다.");
      return false;
    } else if (day < 1 || day > 31) {
      System.out.println("올바르지 않은 날짜 형식입니다.");
      return false;
    } else if (hour < 0 || hour > 24) {
      System.out.println("올바르지 않은 날짜 형식입니다.");
      return false;
    } else if (min < 0 || min > 59) {
      System.out.println("올바르지 않은 날짜 형식입니다.");
      return false;
    } else {
      return true;
    }
  }
}
