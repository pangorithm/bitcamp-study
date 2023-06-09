package bitcamp.test.step15;

import bitcamp.test.step15.vo.Score;

// 7) GRASP 패턴: Information Expert(정보를 갖고 있는 클래스가 그 정보를 다룬다)

// 8) 인스턴스 메서드 도입
// 9) 객체 생성이 번거롭고 복잡한 경우 메서드로 분리하는 것이 낫다.(디자인패턴: 팩토리 메서드)
// 11) 생성자 도입: 인스턴스 변수를 보다 쉽게 초기화 시키기
// 12) 클래스를 유지보수하기 쉽게 별도 소스 파일로 분리
// 13) 클래스를 유지보수 하기 쉽게 패키지로 분류
// 14) 외부 접근 차단과 값 꺼내기: private, getter
// 15) 프로그래밍의 일관성을 위해 보통 다른 필드에 대해서도 getter을 만드록 사용한다

public class App {

  public static void main(String[] args) {

    final int MAX_SIZE = 10;
    Score[] scores = new Score[MAX_SIZE];
    int length = 0;

    // new Score(String, int, int, int);
    // => Score 설계도에 따라 인스턴스를 생성하라.
    // => 생성한 후 String, int, int, int 파라미터 값을 받는 생성자를 호출하라.
    // => 이렇게 초기화시킨 인스턴스의 주소를 리턴하라.
    scores[length++] = new Score("홍길동", 90, 95, 100);

    scores[length++] = new Score("임꺽정", 85, 75, 80);

    scores[length++] = new Score("유관순", 88, 96, 92);

    // 변수에 직접 접근 => 합계를 임의로 조작 가능!
    // scores[0].sum = 20000;

    for (int i = 0; i < length; i++) {
      printScore(scores[i]);
    }
  }

  static void printScore(Score s) {
    System.out.printf("%s: 합계=%d, 평균=%f\n", s.getName(), s.getSum(), s.getAver());
  }

}