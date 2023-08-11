package com.bitcamp.myapp.dao;

import java.util.List;
import com.bitcamp.myapp.vo.Member;
import com.bitcamp.myapp.vo.MemberAddress;

public interface MemberDao {
  void insert(Member member);

  List<Member> findAll();

  Member findBy(int no);

  Member findByEmailAndPassword(Member member);

  int update(Member member);

  int delete(int no);

  List<MemberAddress> getMembersAddressList(int memberNo);

}
