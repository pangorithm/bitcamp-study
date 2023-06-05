package bitcamp.myapp;

// import java.io.InputStream;
import java.util.Scanner;

public class Prompt {

  // InputStream keyboard = System.in;
  // Scanner scanner = new java.util.Scanner(keyboard);
  static Scanner scanner = new java.util.Scanner(System.in);

  static String inputString(String title) {
    System.out.print(title);
    return scanner.nextLine();
  }
}
