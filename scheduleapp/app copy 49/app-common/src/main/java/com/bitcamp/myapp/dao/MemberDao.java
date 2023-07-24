package com.bitcamp.myapp.dao;

import java.util.List;
import com.bitcamp.myapp.vo.Member;

public interface MemberDao {
  void insert(Member member);

  List<Member> list();

  Member findBy(int no);

  Member findByEmailAndPassword(Member member);

  int update(Member member);

  int remove(int no);

}
