package bitcamp.test.step17.vo;

public class Score {

  // 프로그래밍의 일관성을 위해 그냥 막았다.
  private String name;

  // 직접 접근을 혀용했을 때, 무효한 값을 저장할 수 있기 때문에
  // private으로 접근을 막았다.
  private int kor;
  private int eng;
  private int math;
  private int sum;
  private float aver;

  // 생성자: 인스턴스를 생성한 직후 호출하는 메서드
  public Score(String name, int kor, int eng, int math) {
    this.name = name;
    setKor(kor);
    setEng(eng);
    setMath(math);
    this.compute();
  }

  public void compute() {
    this.sum = this.kor + this.eng + this.math;
    this.aver = this.sum / 3f;
  }

  public static Score create(String name, int kor, int eng, int math) {
    Score s = new Score(name, kor, eng, math);
    s.name = name;
    s.kor = kor;
    s.eng = eng;
    s.math = math;
    s.compute();

    return s;
  }

  // getter: private 으로 접근이 막힌 변수의 값을 리턴해주는 메서드
  public int getSum() {
    return this.sum;
  }

  // getter: private 으로 접근이 막힌 변수의 값을 리턴해주는 메서드
  public float getAver() {
    return this.aver;
  }

  public String getName() {
    return this.name;
  }

  public int getKor() {
    return this.kor;
  }

  public int getEng() {
    return this.eng;
  }

  public int getMath() {
    return this.math;
  }

  public void setKor(int score) {
    if (score < 0 || score > 100) {
      return;
    }
    this.kor = score;
    this.compute();
  }

  public void setEng(int score) {
    if (score < 0 || score > 100) {
      return;
    }
    this.eng = score;
    this.compute();
  }

  public void setMath(int score) {
    if (score < 0 || score > 100) {
      return;
    }
    this.math = score;
    this.compute();
  }
}