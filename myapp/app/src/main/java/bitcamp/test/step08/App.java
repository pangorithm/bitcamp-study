package bitcamp.test.step08;

// 7) GRASP 패턴: Information Expert(정보를 갖고 있는 클래스가 그 정보를 다룬다)
// 8) 인스턴스 메서드 도입
public class App {

  static class Score {
    String name;
    int kor;
    int eng;
    int math;
    int sum;
    float aver;

    void compute() {
      this.sum = this.kor + this.eng + this.math;
      this.aver = this.sum / 3f;
    }
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
    s.compute();
    scores[length++] = s;

    s = new Score();
    s.name = "임꺽정";
    s.kor = 84;
    s.eng = 76;
    s.math = 98;
    s.compute();
    scores[length++] = s;

    s = new Score();
    s.name = "유관순";
    s.kor = 93;
    s.eng = 88;
    s.math = 86;
    s.compute();
    scores[length++] = s;

    for (int i = 0; i < length; i++) {
      printScore(scores[i]);
    }
  }

  static void printScore(Score s) {
    System.out.printf("%s: 합계=%d, 평균=%f\n", s.name, s.sum, s.aver);
  }

}