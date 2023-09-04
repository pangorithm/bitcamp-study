package com.bitcamp.myapp.dao;

import com.bitcamp.myapp.vo.AddressType;
import com.bitcamp.myapp.vo.Member;
import com.bitcamp.myapp.vo.MemberAddress;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MemberDao {

  int insert(Member member);

  List<Member> findAll();

  Member findBy(int memberNo);

  Member findByEmailAndPassword(@Param("email") String email, @Param("password") String password);

  int update(Member member);

  int delete(int no);

  List<MemberAddress> getMembersAddressList(int memberNo);

  List<AddressType> findAllAddressType();

  void insertMemberAddress(MemberAddress memberAddress);

  int deleteMemberAddress(int mano);

}
