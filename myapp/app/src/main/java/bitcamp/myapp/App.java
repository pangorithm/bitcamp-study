package bitcamp.myapp;

// 코드 본문에서 사용할 클래스가 어떤 패키지를 클래스인지 지정한다.
// import java.io.InputStream;
import java.util.Scanner;

public class App {
  public static void main(String[] args) {
    System.out.println("나의 목록 관리 시스템");
    System.out.println("--------------------------------------------");

    // 키보드 스캐너 준비
    // InputStream keyboard = System.in;
    // Scanner scanner = new java.util.Scanner(keyboard);
    Scanner scanner = new java.util.Scanner(System.in);

    int count = 3;

    int[] no = new int[count];
    String[] name = new String[count];
    int[] age = new int[count];
    boolean[] working = new boolean[count];
    char[] gender = new char[count];
    float[] leftEye = new float[count];
    float[] rightEye = new float[count];

    for (int i = 0; i < count; i++) {
      System.out.print("번호? ");
      no[i] = scanner.nextInt();

      System.out.print("이름? ");
      name[i] = scanner.next();

      System.out.print("나이? ");
      age[i] = scanner.nextInt();

      System.out.print("재직중(true/false)? ");
      working[i] = scanner.nextBoolean();

      System.out.print("성별? ");
      String str = scanner.next();
      gender[i] = str.charAt(0);

      System.out.print("시력(왼쪽, 오른쪽)? ");
      leftEye[i] = scanner.nextFloat();
      rightEye[i] = scanner.nextFloat();
    }

    System.out.println("----------------------------------------------");

    for (int i = 0; i < count; i++) {
      System.out.printf("번호: %d\n", no[i]);
      System.out.printf("이름: %s\n", name[i]);
      System.out.printf("나이: %d\n", age[i]);
      System.out.printf("재직자: %b\n", working[i]);
      System.out.printf("성별(남자(M), 여자(W)): %c\n", gender[i]);
      System.out.printf("좌우시력: %.1f,%.1f\n", leftEye[i], rightEye[i]);
    }
    scanner.close();
  }
}
