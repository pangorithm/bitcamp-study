package bitcamp.test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CalcClient3 {

  static Pattern pattern = Pattern.compile("[0-9]+|\\p{Punct}");

  public static void main(String[] args) {
    String uuid = "";

    try (Scanner keyscan = new Scanner(System.in);) {

      while (true) {
        System.out.println("계산식(예 + 3)> ");
        String input = keyscan.nextLine();

        Expression expr = null;

        try {
          expr = parseExpression(input);

        } catch (ExpressionParseExeption e) {
          System.out.println("계산식이 옳지 않습니다.");
          continue;
        }

        // try (Socket socket = new Socket("localhost", 8888);
        try (Socket socket = new Socket("192.168.0.31", 8888);
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DataInputStream in = new DataInputStream(socket.getInputStream());) {

          if (input.equals("quit")) {
            out.writeUTF("quit");
            break;
          }

          out.writeUTF(uuid);
          out.writeUTF(expr.op);
          out.writeInt(expr.value);

          uuid = in.readUTF();
          String result = in.readUTF();
          System.out.printf("결과 : %s\n", result);

        } catch (Exception e) {
          System.out.println("서버 통신 오류!");
        }
      }
    }
  }

  public static Expression parseExpression(String expr) throws ExpressionParseExeption {
    try {
      Matcher matcher = pattern.matcher(expr);

      ArrayList<String> values = new ArrayList<>();
      while (matcher.find()) {
        values.add(matcher.group());
      }

      if (values.size() != 2) {
        throw new Exception("계산식이 옳지 않습니다.");
      }

      Expression obj = new Expression();
      obj.op = values.get(0);
      obj.value = Integer.parseInt(values.get(1));

      return obj;
    } catch (Exception e) {
      throw new ExpressionParseExeption(e);
    }
  }

  static class Expression {
    String op;
    int value;
  }
}
