package bitcamp.test.step06;

public class App {

  static class Score {
    String name;
    int kor;
    int eng;
    int math;
    int sum;
    float aver;
  }

  public static void main(String[] args) {

    final int MAX_SIZE = 10;
    Score[] scores = new Score[MAX_SIZE];
    int length = 0;

    Score s = new Score();
    s.name = "홍길동";
    s.kor = 88;
    s.eng = 77;
    s.math = 96;
    compute(s);
    scores[length++] = s;

    s = new Score();
    s.name = "임꺽정";
    s.kor = 84;
    s.eng = 76;
    s.math = 98;
    compute(s);
    scores[length++] = s;

    s = new Score();
    s.name = "유관순";
    s.kor = 93;
    s.eng = 88;
    s.math = 86;
    compute(s);
    scores[length++] = s;

    for (int i = 0; i < length; i++) {
      printScore(scores[i]);
    }
  }

  static void compute(Score s) {
    s.sum = s.kor + s.eng + s.math;
    s.aver = s.sum / 3f;
  }

  static void printScore(Score s) {
    System.out.printf("%s: 합계=%d, 평균=%f\n", s.name, s.sum, s.aver);
  }

}