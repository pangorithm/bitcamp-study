package bitcamp.test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class CalcServer2 {
  public static void main(String[] args) throws Exception {
    try (ServerSocket serverSocket = new ServerSocket(8888)) {
      System.out.println("서버 실행!");
      processRequest(serverSocket);


    }
  }

  static void processRequest(ServerSocket serverSocket) {
    HashMap<String, Integer> socketMap = new HashMap<>();

    while (true) {
      InetSocketAddress sockAddr = null;
      try (Socket socket = serverSocket.accept();
          DataInputStream in = new DataInputStream(socket.getInputStream());
          DataOutputStream out = new DataOutputStream(socket.getOutputStream());) {

        sockAddr = (InetSocketAddress) socket.getRemoteSocketAddress();
        System.out.printf("%s(%d) 클라이언트 접속!\n", sockAddr.getHostString(), sockAddr.getPort());

        if (!socketMap.containsKey(sockAddr.getHostString())) {
          socketMap.put(sockAddr.getHostString(), 0);
        }

        int result = socketMap.get(sockAddr.getHostString());

        String op = in.readUTF();
        if (op.equals("quit")) {
          break;
        }

        int value = in.readInt();

        switch (op) {
          case "+":
            result += value;
            break;
          case "-":
            result -= value;
            break;
          case "*":
            result *= value;
            break;
          case "/":
            result /= value;
            break;
          case "%":
            result %= value;
            break;
          default:
            out.writeUTF("지원하지 않는 연산자입니다!");
        }
        socketMap.put(sockAddr.getHostString(), result);
        out.writeUTF(String.format("%d", result));

      } catch (Exception e) {
        System.out.printf("%s(%d) 클라이언트 통신 오류!\n", sockAddr.getHostString(), sockAddr.getPort());
      }
    }
  }
}
