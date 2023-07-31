package com.bitcamp.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import com.bitcamp.myapp.dao.BoardDao;
import com.bitcamp.myapp.dao.MemberDao;
import com.bitcamp.myapp.dao.MySQLBoardDao;
import com.bitcamp.myapp.dao.MySQLMemberDao;
import com.bitcamp.myapp.dao.MySQLScheduleDao;
import com.bitcamp.myapp.dao.ScheduleDao;
import com.bitcamp.myapp.handler.BoardAddListener;
import com.bitcamp.myapp.handler.BoardDeleteListener;
import com.bitcamp.myapp.handler.BoardDetailListener;
import com.bitcamp.myapp.handler.BoardListListener;
import com.bitcamp.myapp.handler.BoardUpdateListener;
import com.bitcamp.myapp.handler.LoginListener;
import com.bitcamp.myapp.handler.MemberAddListener;
import com.bitcamp.myapp.handler.MemberDeleteListener;
import com.bitcamp.myapp.handler.MemberDetailListener;
import com.bitcamp.myapp.handler.MemberListListener;
import com.bitcamp.myapp.handler.MemberUpdateListener;
import com.bitcamp.myapp.handler.ScheduleAddListener;
import com.bitcamp.myapp.handler.ScheduleDeleteListener;
import com.bitcamp.myapp.handler.ScheduleDetailListener;
import com.bitcamp.myapp.handler.ScheduleListListener;
import com.bitcamp.myapp.handler.ScheduleSearchListener;
import com.bitcamp.myapp.handler.ScheduleUpdateListener;

public class DispatcherListener implements ActionListener {

  private static final int BOARD_CATEGORY = 1;
  private static final int READING_CATEGORY = 2;

  // 리스너가 작업할 때 사용할 객체를 보관하는 저장소
  Map<String, Object> beanContainer = new HashMap<>();

  public DispatcherListener() throws Exception {

    // Mybatis 준비
    SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryProxy(new SqlSessionFactoryBuilder()
        .build(Resources.getResourceAsStream("com/bitcamp/myapp/config/mybatis-config.xml")));
    beanContainer.put("sqlSessionFactory", sqlSessionFactory);

    // Dao 준비
    MemberDao memberDao = new MySQLMemberDao(sqlSessionFactory);
    ScheduleDao scheduleDao = new MySQLScheduleDao(sqlSessionFactory);
    BoardDao boardDao = new MySQLBoardDao(sqlSessionFactory);
    beanContainer.put("memberDao", memberDao);
    beanContainer.put("scheduleDao", scheduleDao);
    beanContainer.put("boardDao", boardDao);

    // Listener 준비
    beanContainer.put("login", new LoginListener(memberDao));

    beanContainer.put("member/add", new MemberAddListener(memberDao, sqlSessionFactory));
    beanContainer.put("member/list", new MemberListListener(memberDao));
    beanContainer.put("member/detail", new MemberDetailListener(memberDao));
    beanContainer.put("member/update", new MemberUpdateListener(memberDao, sqlSessionFactory));
    beanContainer.put("member/delete", new MemberDeleteListener(memberDao, sqlSessionFactory));

    beanContainer.put("schedule/add", new ScheduleAddListener(scheduleDao, sqlSessionFactory));
    beanContainer.put("schedule/list", new ScheduleListListener(scheduleDao));
    beanContainer.put("schedule/search", new ScheduleSearchListener(scheduleDao));
    beanContainer.put("schedule/detail",
        new ScheduleDetailListener(scheduleDao, sqlSessionFactory));
    beanContainer.put("schedule/update",
        new ScheduleUpdateListener(scheduleDao, sqlSessionFactory));
    beanContainer.put("schedule/delete",
        new ScheduleDeleteListener(scheduleDao, sqlSessionFactory));

    beanContainer.put("board/add",
        new BoardAddListener(BOARD_CATEGORY, boardDao, sqlSessionFactory));
    beanContainer.put("board/list", new BoardListListener(BOARD_CATEGORY, boardDao));
    beanContainer.put("board/detail",
        new BoardDetailListener(BOARD_CATEGORY, boardDao, sqlSessionFactory));
    beanContainer.put("board/update",
        new BoardUpdateListener(BOARD_CATEGORY, boardDao, sqlSessionFactory));
    beanContainer.put("board/delete",
        new BoardDeleteListener(BOARD_CATEGORY, boardDao, sqlSessionFactory));

    beanContainer.put("reading/add",
        new BoardAddListener(READING_CATEGORY, boardDao, sqlSessionFactory));
    beanContainer.put("reading/list", new BoardListListener(READING_CATEGORY, boardDao));
    beanContainer.put("reading/detail",
        new BoardDetailListener(READING_CATEGORY, boardDao, sqlSessionFactory));
    beanContainer.put("reading/update",
        new BoardUpdateListener(READING_CATEGORY, boardDao, sqlSessionFactory));
    beanContainer.put("reading/delete",
        new BoardDeleteListener(READING_CATEGORY, boardDao, sqlSessionFactory));
  }

  @Override
  public void service(BreadcrumbPrompt prompt) throws IOException {
    ActionListener listener = (ActionListener) beanContainer.get(prompt.getAttribute("menuPath"));
    if (listener == null) {
      throw new RuntimeException("해당 요청을 처리할 수 없습니다.");
    }
    listener.service(prompt);
  }

  public Object getBean(String name) {
    return beanContainer.get(name);
  }
}
