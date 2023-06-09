package bitcamp.test.step04;

public class App {

  public static void main(String[] args) {

    class Score {
      String name;
      int kor;
      int eng;
      int math;
      int sum;
      float aver;
    }

    final int MAX_SIZE = 10;
    Score[] scores = new Score[MAX_SIZE];
    int length = 0;

    Score s = new Score();
    s.name = "홍길동";
    s.kor = 88;
    s.eng = 77;
    s.math = 96;
    s.sum = s.kor + s.eng + s.math;
    s.aver = s.sum / 3f;
    scores[length++] = s;

    s = new Score();
    s.name = "임꺽정";
    s.kor = 88;
    s.eng = 77;
    s.math = 96;
    s.sum = s.kor + s.eng + s.math;
    s.aver = s.sum / 3f;
    scores[length++] = s;

    s = new Score();
    s.name = "유관순";
    s.kor = 88;
    s.eng = 77;
    s.math = 96;
    s.sum = s.kor + s.eng + s.math;
    s.aver = s.sum / 3f;
    scores[length++] = s;

    for (int i = 0; i < length; i++) {
      System.out.printf("%s: 합계=%d, 평균=%f\n", scores[i].name, scores[i].sum, scores[i].aver);
    }
  }

}