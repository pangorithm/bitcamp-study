package com.bitcamp.myapp.handler;

import com.bitcamp.myapp.vo.Schedule;
import com.bitcamp.util.LinkedList;
import com.bitcamp.util.Prompt;

public class ScheduleHandler implements Handler {


  Prompt prompt;
  private String title;
  LinkedList list = new LinkedList();

  public ScheduleHandler(Prompt prompt, String title) {
    this.prompt = prompt;
    this.title = title;
  }

  public void execute() {
    printMenu();

    String menuNo;
    while (true) {
      menuNo = prompt.inputString("스케줄> ");
      if (menuNo.equals("0")) {
        break;
      } else if (menuNo.equals("menu")) {
        printMenu();
      } else if (menuNo.equals("1")) {
        this.inputSchedule();
      } else if (menuNo.equals("2")) {
        this.printSchedules();
      } else if (menuNo.equals("3")) {
        this.searchSchedules();
      } else if (menuNo.equals("4")) {
        this.setSchedule();
      } else if (menuNo.equals("5")) {
        this.deleteSchdule();
      } else {
        System.out.println("올바르지 않은 메뉴 번호입니다.");
      }
    }
  }

  private void printMenu() {
    System.out.printf("1. %s 등록\n", this.title);
    System.out.printf("2. %s 목록\n", this.title);
    System.out.printf("3. %s 검색\n", this.title);
    System.out.printf("4. %s 변경\n", this.title);
    System.out.printf("5. %s 삭제\n", this.title);
    System.out.println("0. 메인");
  }

  private void inputSchedule() {
    Schedule sch = new Schedule();
    this.list.add(inputScheduleInfo(sch));
  }

  private void printSchedules() {
    for (Object obj : this.list.list()) {
      Schedule sch = (Schedule) obj;
      printScheduleInfo(sch);
    }
  }

  private void searchSchedules() {
    System.out.println("어떤 조건으로 검색하시겠습니까?");
    System.out.println("1. 스케줄 제목");
    System.out.println("2. 날짜 범위");
    String optNo = this.prompt.inputString("> ");

    if (optNo.equals("1")) {
      String searchTitle = this.prompt.inputString("제목 : ");
      for (Object obj : this.list.list()) {
        Schedule sch = (Schedule) obj;
        if (sch.getScheduleTitle().contains(searchTitle)) {
          printScheduleInfo(sch);
        }
      }
    } else if (optNo.equals("2")) {
      long searchRangeStart =
          parseDateLong(this.prompt.inputString("검색 시작 시간 ex)2023-06-05 16:30\n> "));
      long searchRangeEnd =
          parseDateLong(this.prompt.inputString("검색 종료 시간 ex)2023-06-06 20:00\n> "));
      for (Object obj : this.list.list()) {
        Schedule sch = (Schedule) obj;
        if (sch.getEndTime() > searchRangeStart && sch.getStartTime() < searchRangeEnd) {
          printScheduleInfo(sch);
        }
      }
    } else {
      System.out.println("올바르지 않은 형식입니다.");
    }
  }

  private void setSchedule() {
    Schedule sch = (Schedule) this.list.get(new Schedule(this.prompt.inputInt("번호? ")));
    if (sch == null) {
      System.out.println("일치하는 번호가 존재하지 않습니다.");
    } else {
      System.out.println("수정 전--------------");
      printScheduleInfo(sch);
      System.out.println("---------------------");
      inputScheduleInfo(sch);
    }
  }

  private void deleteSchdule() {
    Schedule sch = (Schedule) this.list.get(new Schedule(this.prompt.inputInt("번호? ")));
    if (sch == null) {
      System.out.println("해당 번호의 스케줄이 없습니다!");
      return;
    }
    printScheduleInfo(sch);
    if (this.prompt.inputString("정말로 이 스케줄을 삭제 하시겠습니까?(y/N)").equalsIgnoreCase("y")) {
      list.remove(sch);
    }
  }

  private Schedule inputScheduleInfo(Schedule sch) {
    sch.setScheduleTitle(this.prompt.inputString("스케줄 제목을 입력하세요\n> "));

    do {
      System.out.println("스케줄 시작 날짜와 시간을 입력하세요");
      System.out.println("ex)2023-06-05 08:30");
      sch.setStartTime(parseDateLong(this.prompt.inputString("> ")));
    } while (!checkDate(sch.getStartTime()));

    do {
      System.out.println("스케줄 종료 날짜와 시간을 입력하세요");
      System.out.println("ex)2023-06-06 20:00");
      sch.setEndTime(parseDateLong(this.prompt.inputString("> ")));
    } while (!checkDate(sch.getEndTime()));
    return sch;
  }

  private void printScheduleInfo(Schedule sch) {
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

  private static long parseDateLong(String date) {
    String str = date.replaceAll("[^0-9]", "");
    if (str.equals("")) {
      System.out.println("올바르지 않은 입력입니다");
    }
    long dateLong = Long.parseLong(str);
    return dateLong;
  }

  private static String dateToString(long date) {
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

  private static boolean checkDate(long date) {

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
