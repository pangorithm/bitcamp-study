package com.bitcamp.myapp.dao;

import java.util.List;
import com.bitcamp.myapp.vo.AddressType;
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

  List<AddressType> findAllAddressType();

  void insertMemberAddress(MemberAddress memberAddress);

  int deleteMemberAddress(int no, int mano);

}
