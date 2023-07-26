package bitcamp.util;

import java.sql.Connection;
import java.sql.DriverManager;

// - DB 커넥션을 관리하고 제공하는 역할
// - 스레드 별로 DB 커넥션을 보관한다.
//
public class DataSource {

  // DBMS에 연결할 때 사용할 정보
  String jdbcUrl;
  String username;
  String password;

  // 현재 스레드 값을 넣고 꺼내는 일을 하는 객체
  ThreadLocal<Connection> connectionBox = new ThreadLocal<>();

  public DataSource(String jdbcUrl, String username, String password) {
    this.jdbcUrl = jdbcUrl;
    this.username = username;
    this.password = password;
  }

  public Connection getConnection() throws Exception {
    // connectionBox를 통해 현재 스레드의 보관소에 저장된 Connection 객체를 꺼낸다.
    Connection con = connectionBox.get();

    if (con == null) {
      // 만약 현재 스레드에 Connection 객체가 보관되어 있지 않다면 새로 생성한다.
      con = DriverManager.getConnection(jdbcUrl, username, password);

      // connectionBox를 통해 새로 생성한 Connection 객체를 현재 스레드에 보관한다.
      connectionBox.set(con);
    }

    return con;
  }

  public void clean() {
    // 스레드가 작업을 끝냈으면, 이 스레드에 보관된 Connection 객체를 제거한다.
    Connection con = connectionBox.get();
    if (con != null) {
      try {
        con.close();
      } catch (Exception e) {
        // TODO: handle exception
      }
      connectionBox.remove();
    }
  }

}
