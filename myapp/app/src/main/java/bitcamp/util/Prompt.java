package bitcamp.util;

// import java.io.InputStream;
import java.util.Scanner;

public class Prompt {

  // InputStream keyboard = System.in;
  // Scanner scanner = new java.util.Scanner(keyboard);
  static Scanner scanner = new java.util.Scanner(System.in);

  public static String inputString(String title) {
    System.out.print(title);
    return scanner.nextLine();
  }

  public static int inputInt(String title) {
    System.out.print(title);
    return Integer.parseInt(scanner.nextLine());
  }

  public static void close() {
    scanner.close();
  }
}
