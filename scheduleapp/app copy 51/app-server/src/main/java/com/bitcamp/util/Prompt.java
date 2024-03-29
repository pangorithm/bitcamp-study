package com.bitcamp.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import com.bitcamp.net.NetProtocol;

public class Prompt {

  Map<String, Object> context = new HashMap<>();

  StringBuffer buf = new StringBuffer();
  DataInputStream in;
  DataOutputStream out;

  // default constructor 정의
  public Prompt(DataInputStream in, DataOutputStream out) {
    this.in = in;
    this.out = out;
  }

  public void setAttribute(String name, Object value) {
    context.put(name, value);
  }

  public Object getAttribute(String name) {
    return context.get(name);
  }

  public String inputString(String title, Object... args) throws IOException {
    this.out.writeUTF(String.format(title, args));
    this.out.writeUTF(NetProtocol.PROMPT);
    return this.in.readUTF();
  }

  public int inputInt(String title, Object... args) throws IOException {
    return Integer.parseInt(this.inputString(title, args).replaceAll("[^0-9]", ""));
  }

  public void print(String str) {
    buf.append(str);
  }

  public void println(String str) {
    buf.append(str).append("\n");
  }

  public void printf(String format, Object... args) {
    buf.append(String.format(format, args));
  }

  public void clear() {
    buf.setLength(0);
  }

  public void writeBuf() throws IOException {
    this.out.writeUTF(buf.toString());
    buf.setLength(0);
  }

  public void end() throws IOException {
    this.out.writeUTF(buf.toString());
    this.out.writeUTF(NetProtocol.RESPONSE_END);
    buf.setLength(0);
  }

  public boolean promptContinue(String inputString) {
    return inputString.equalsIgnoreCase("y");
  }
}
