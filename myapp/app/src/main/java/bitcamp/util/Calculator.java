package bitcamp.util;

public class Calculator {

  private int result = 0;

  public int getResult() {
    return this.result;
  }

  public void init(int a) {
    this.result = a;
  }

  public void plus(int a) {
    this.result += a;
  }

  public void minus(int a) {
    this.result -= a;
  }

  public void multiple(int a) {
    this.result *= a;
  }

  public void devide(int a) {
    this.result /= a;
  }

}
