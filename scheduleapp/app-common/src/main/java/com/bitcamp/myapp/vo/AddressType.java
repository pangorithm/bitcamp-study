package com.bitcamp.myapp.vo;

public class AddressType {

  private int no;
  private String type;

  @Override
  public String toString() {
    return "AddressType{" +
        "no=" + no +
        ", type='" + type + '\'' +
        '}';
  }

  public int getNo() {
    return no;
  }

  public void setNo(int no) {
    this.no = no;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }


}
