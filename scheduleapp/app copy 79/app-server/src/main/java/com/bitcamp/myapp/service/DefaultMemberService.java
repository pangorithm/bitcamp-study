package com.bitcamp.myapp.service;

import com.bitcamp.myapp.dao.MemberDao;
import com.bitcamp.myapp.vo.AddressType;
import com.bitcamp.myapp.vo.Member;
import com.bitcamp.myapp.vo.MemberAddress;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DefaultMemberService implements MemberService {

  MemberDao memberDao;

  {
    System.out.println("DefaultMemberService 생성됨!");
  }

  public DefaultMemberService(MemberDao memberDao) {
    this.memberDao = memberDao;
  }

  @Transactional
  @Override
  public int add(Member member) throws Exception {
    return memberDao.insert(member);
  }

  @Transactional
  @Override
  public int addAddress(MemberAddress memberAddress) throws Exception {
    return memberDao.insertMemberAddress(memberAddress);
  }

  @Override
  public List<Member> list() throws Exception {
    return memberDao.findAll();
  }

  @Override
  public Member get(int memberNo) throws Exception {
    Member member = memberDao.findBy(memberNo);
    member.setAddressList(memberDao.findAllAddress(member.getNo()));
    return member;
  }

  @Override
  public Member get(String email, String password) throws Exception {
    return memberDao.findByEmailAndPassword(email, password);
  }

  @Transactional
  @Override
  public int update(Member member) throws Exception {
    return memberDao.update(member);
  }

  @Transactional
  @Override
  public int delete(int memberNo) throws Exception {
    return memberDao.delete(memberNo);
  }

  @Transactional
  @Override
  public int deleteAddress(int mano) throws Exception {
    return memberDao.deleteMemberAddress(mano);
  }

  @Override
  public List<AddressType> getAddressTypeList() throws Exception {
    return memberDao.findAllAddressType();
  }
}
