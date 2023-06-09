package bitcamp.test.step03;

public class App {

  public static void main(String[] args) {
    String[] name = new String[10];
    int[] kor = new int[10];
    int[] eng = new int[10];
    int[] math = new int[10];
    int[] sum = new int[10];
    float[] aver = new float[10];
    int length = 0;

    name[length] = "홍길동";
    kor[length] = 88;
    eng[length] = 77;
    math[length] = 96;
    sum[length] = kor[length] + eng[length] + math[length];
    aver[length] = sum[length] / 3f;
    length++;

    name[length] = "임꺽정";
    kor[length] = 95;
    eng[length] = 80;
    math[length] = 75;
    sum[length] = kor[length] + eng[length] + math[length];
    aver[length] = sum[length] / 3f;
    length++;

    name[length] = "유관순";
    kor[length] = 75;
    eng[length] = 90;
    math[length] = 87;
    sum[length] = kor[length] + eng[length] + math[length];
    aver[length] = sum[length] / 3f;
    length++;

    for (int i = 0; i < length; i++) {
      System.out.printf("%s: 합계=%d, 평균=%f\n", name[i], sum[i], aver[i]);
    }
  }

}