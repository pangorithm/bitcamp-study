package com.bitcamp.myapp.dao;

import java.util.HashMap;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import com.bitcamp.myapp.vo.AddressType;
import com.bitcamp.myapp.vo.Member;
import com.bitcamp.myapp.vo.MemberAddress;
import org.springframework.stereotype.Component;

@Component
public class MySQLMemberDao implements MemberDao {

  SqlSessionFactory sqlSessionFactory;

  public MySQLMemberDao(SqlSessionFactory sqlSessionFactory) {
    this.sqlSessionFactory = sqlSessionFactory;
  }

  @Override
  public void insert(Member member) {
    SqlSession sqlSession = sqlSessionFactory.openSession(false);
    sqlSession.insert("com.bitcamp.myapp.dao.MemberDao.insert", member);
  }

  @Override
  public List<Member> findAll() {
    SqlSession sqlSession = sqlSessionFactory.openSession();
    return sqlSession.selectList("com.bitcamp.myapp.dao.MemberDao.findAll");
  }

  @Override
  public Member findBy(int no) {
    SqlSession sqlSession = sqlSessionFactory.openSession();
    return sqlSession.selectOne("com.bitcamp.myapp.dao.MemberDao.findBy", no);
  }

  @Override
  public Member findByEmailAndPassword(Member param) {
    SqlSession sqlSession = sqlSessionFactory.openSession();
    return sqlSession.selectOne("com.bitcamp.myapp.dao.MemberDao.findByEmailAndPassword", param);
  }

  @Override
  public int update(Member member) {
    SqlSession sqlSession = sqlSessionFactory.openSession(false);
    return sqlSession.update("com.bitcamp.myapp.dao.MemberDao.update", member);
  }

  @Override
  public int delete(int no) {
    SqlSession sqlSession = sqlSessionFactory.openSession(false);
    return sqlSession.update("com.bitcamp.myapp.dao.MemberDao.delete", no);
  }

  @Override
  public List<MemberAddress> getMembersAddressList(int memberNo) {
    SqlSession sqlSession = sqlSessionFactory.openSession(false);
    return sqlSession.selectList("com.bitcamp.myapp.dao.MemberDao.findAllAddress", memberNo);
  }

  @Override
  public List<AddressType> findAllAddressType() {
    SqlSession sqlSession = sqlSessionFactory.openSession(false);
    return sqlSession.selectList("com.bitcamp.myapp.dao.MemberDao.findAllAddressType");
  }

  @Override
  public void insertMemberAddress(MemberAddress memberAddress) {
    SqlSession sqlSession = sqlSessionFactory.openSession(false);
    sqlSession.insert("com.bitcamp.myapp.dao.MemberDao.insertMemberAddress", memberAddress);

  }

  @Override
  public int deleteMemberAddress(int loginUserNo, int mano) {
    HashMap<String, Object> map = new HashMap<>();
    map.put("loginUserNo", loginUserNo);
    map.put("mano", mano);
    SqlSession sqlSession = sqlSessionFactory.openSession(false);
    return sqlSession.update("com.bitcamp.myapp.dao.MemberDao.deleteMemberAddress", map);

  }

}
