package com.bitcamp.io;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class BufferedDataOutputStream extends FileOutputStream {

  byte[] buf = new byte[8192];
  int cursor;

  public BufferedDataOutputStream(String name) throws FileNotFoundException {
    super(name);
  }

  @Override
  public void write(int b) throws IOException {
    if (cursor == buf.length) {
      super.write(buf);
      cursor = 0;
    }
    buf[cursor++] = (byte) b;
  }

  @Override
  public void flush() throws IOException {
    super.write(buf, 0, cursor);
    cursor = 0;
  }

  @Override
  public void close() throws IOException {
    this.flush();
    super.close();
  }

  @Override
  public void write(byte[] arr) throws IOException {
    for (int i = 0; i < arr.length; i++) {
      this.write(arr[i]);
    }
  }

  public void writeShort(int v) throws IOException {
    this.write(v >> 8);
    this.write(v);
  }

  public void writeInt(int v) throws IOException {
    this.write(v >> 24);
    this.write(v >> 16);
    this.write(v >> 8);
    this.write(v);
  }

  public void writeLong(long v) throws IOException {
    this.write((int) (v >> 56));
    this.write((int) (v >> 48));
    this.write((int) (v >> 40));
    this.write((int) (v >> 32));
    this.write((int) (v >> 24));
    this.write((int) (v >> 16));
    this.write((int) (v >> 8));
    this.write((int) v);
  }

  public void writeUTF(String str) throws IOException {
    byte[] bytes = str.getBytes("UTF-8");
    // 출력할 바이트의 개수를 2바이트로 표시한다.
    this.write(bytes.length >> 8);
    this.write(bytes.length);
    // 문자열의 바이트를 출력한다.
    this.write(bytes);
  }

  public void writeChar(int v) throws IOException {
    this.write(v >> 8);
    this.write(v);
  }
}
