package com.bitcamp.myapp.service;

import com.bitcamp.myapp.dao.MemberDao;
import com.bitcamp.myapp.vo.AddressType;
import com.bitcamp.myapp.vo.Member;
import com.bitcamp.myapp.vo.MemberAddress;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Service
public class DefaultMemberService implements MemberService {

  MemberDao memberDao;
  PlatformTransactionManager txManager;

  public DefaultMemberService(MemberDao memberDao, PlatformTransactionManager txManager) {
    this.memberDao = memberDao;
    this.txManager = txManager;
  }

  @Override
  public int add(Member member) throws Exception {
    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    def.setName("tx1");
    def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
    TransactionStatus status = txManager.getTransaction(def);

    try {
      int count = memberDao.insert(member);
      txManager.commit(status);
      return count;
    } catch (Exception e) {
      txManager.rollback(status);
      throw e;
    }
  }

  @Override
  public int addAddress(MemberAddress memberAddress) throws Exception {
    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    def.setName("tx1");
    def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
    TransactionStatus status = txManager.getTransaction(def);

    try {
      int count = memberDao.insertMemberAddress(memberAddress);
      txManager.commit(status);
      return count;
    } catch (Exception e) {
      txManager.rollback(status);
      throw e;
    }
  }

  @Override
  public List<Member> list() throws Exception {
    return memberDao.findAll();
  }

  @Override
  public Member get(int memberNo) throws Exception {
    Member member = memberDao.findBy(memberNo);
//    member.setAddressList(memberDao.getMembersAddressList(member.getNo()));
    return member;
  }

  @Override
  public Member get(String email, String password) throws Exception {
    return memberDao.findByEmailAndPassword(email, password);
  }

  @Override
  public int update(Member member) throws Exception {
    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    def.setName("tx1");
    def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
    TransactionStatus status = txManager.getTransaction(def);

    try {
      int count = memberDao.update(member);
      txManager.commit(status);
      return count;
    } catch (Exception e) {
      txManager.rollback(status);
      throw e;
    }
  }

  @Override
  public int delete(int memberNo) throws Exception {
    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    def.setName("tx1");
    def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
    TransactionStatus status = txManager.getTransaction(def);

    try {
      int count = memberDao.delete(memberNo);
      txManager.commit(status);
      return count;
    } catch (Exception e) {
      txManager.rollback(status);
      throw e;
    }
  }

  @Override
  public int deleteAddress(int mano) throws Exception {
    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    def.setName("tx1");
    def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
    TransactionStatus status = txManager.getTransaction(def);

    try {
      int count = memberDao.deleteMemberAddress(mano);
      txManager.commit(status);
      return count;
    } catch (Exception e) {
      txManager.rollback(status);
      throw e;
    }
  }

  @Override
  public List<AddressType> getAddressTypeList() throws Exception {
    return memberDao.findAllAddressType();
  }
}
