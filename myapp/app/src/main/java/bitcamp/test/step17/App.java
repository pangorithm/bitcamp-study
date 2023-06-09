package bitcamp.test.step17;

import bitcamp.test.step17.vo.Score;

// 7) GRASP 패턴: Information Expert(정보를 갖고 있는 클래스가 그 정보를 다룬다)
// 8) 인스턴스 메서드 도입
// 9) 객체 생성이 번거롭고 복잡한 경우 메서드로 분리하는 것이 낫다.(디자인패턴: 팩토리 메서드)
// 11) 생성자 도입: 인스턴스 변수를 보다 쉽게 초기화 시키기
// 12) 클래스를 유지보수하기 쉽게 별도 소스 파일로 분리
// 13) 클래스를 유지보수 하기 쉽게 패키지로 분류
// 14) 외부 접근 차단과 값 꺼내기: private, getter
// 15) 프로그래밍의 일관성을 위해 보통 다른 필드에 대해서도 getter을 만드록 사용한다
// 16) 필드의 직접 접근을 막고 setter가 필요한 이유 
// 17) 필드의 직접 접근을 막기: 인스턴스 변수의 무효한 값이 저장되지 않게 하기 위해
// ===> getter 정의 : 값을 꺼낼 때 사용
// ===> setter 정의 : 

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

    scores[length++] = new Score("임꺽정", 85, 75, 800);

    scores[length++] = new Score("유관순", 88, 96, 92);

    // 합계와 평균 계산이 끝난 후에 국어 점수를 변경한다면
    // => 국영수 점수와 합계, 평균 점수가 일치하지 않는 문제가 발생한다.
    // => 즉, 데이터에 결함이 발생한다.
    // 국영수 점수를 변경한 후에 compute()를 호출하면 되지 않을까?
    // => 만약 개발자가 compute() 호출하는 것을 잊어 먹는다면 아무 소용이 없다.
    // 만약 유효하지 않은 국영수 점수를 입력한다면?
    // => 흠... 이건 도저히 막을 길이 없다.
    // scores[0].kor = 7000; // 접근 불가
    scores[0].setKor(70); // setter를 통해서는 값 변경 가능. 단, 유효한 숫자만 받는다.
    // scores[0].compute(); // 호출하는 것을 잊어 버릴 수 있기 때문에 setter에서 호출한다.

    for (int i = 0; i < length; i++) {
      printScore(scores[i]);
    }
  }

  static void printScore(Score s) {
    System.out.printf("%s: 국어=%d, 영어=%d, 수학=%d, 합계=%d, 평균=%.1f\n",
        s.getName(), s.getKor(), s.getEng(), s.getMath(), s.getSum(), s.getAver());
  }

}