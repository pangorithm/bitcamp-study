package com.bitcamp.myapp.service;

import com.bitcamp.myapp.dao.MemberDao;
import com.bitcamp.myapp.vo.AddressType;
import com.bitcamp.myapp.vo.Member;
import com.bitcamp.myapp.vo.MemberAddress;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

@Service
public class DefaultMemberService implements MemberService {

  MemberDao memberDao;
  TransactionTemplate txTemplate;

  public DefaultMemberService(MemberDao memberDao, PlatformTransactionManager txManager) {
    this.memberDao = memberDao;
    this.txTemplate = new TransactionTemplate(txManager);
  }

  @Override
  public int add(Member member) throws Exception {
    return txTemplate.execute(status -> memberDao.insert(member));
  }

  @Override
  public int addAddress(MemberAddress memberAddress) throws Exception {
    return txTemplate.execute(status -> memberDao.insertMemberAddress(memberAddress));
  }

  @Override
  public List<Member> list() throws Exception {
    return memberDao.findAll();
  }

  @Override
  public Member get(int memberNo) throws Exception {
    return txTemplate.execute(status -> {
      Member member = memberDao.findBy(memberNo);
      member.setAddressList(memberDao.findAllAddress(member.getNo()));
      return member;
    });
  }

  @Override
  public Member get(String email, String password) throws Exception {
    return memberDao.findByEmailAndPassword(email, password);
  }

  @Override
  public int update(Member member) throws Exception {
    return txTemplate.execute(status -> memberDao.update(member));
  }

  @Override
  public int delete(int memberNo) throws Exception {
    return txTemplate.execute(status -> memberDao.delete(memberNo));
  }

  @Override
  public int deleteAddress(int mano) throws Exception {
    return txTemplate.execute(status -> memberDao.deleteMemberAddress(mano));
  }

  @Override
  public List<AddressType> getAddressTypeList() throws Exception {
    return memberDao.findAllAddressType();
  }
}
