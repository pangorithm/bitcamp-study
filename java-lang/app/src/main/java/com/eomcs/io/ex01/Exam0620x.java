// 디렉토리에 들어있는 파일(디렉토리) 목록을 꺼낼 때 필터 적용하기 II
package com.eomcs.io.ex01;

import java.io.File;
import java.io.FileFilter;

public class Exam0620x {

  public static void main(String[] args) throws Exception {

    class JavaFilter implements FileFilter {
      @Override
      public boolean accept(File file) {
        return (file.isFile() && file.getName().endsWith(".java"));
      }
    }

    File dir = new File(".");
    JavaFilter javaFilter = new JavaFilter();
    File[] files = dir.listFiles(javaFilter);
    for (File file : files) {
      System.out.printf("%s %12d %s\n", file.isDirectory() ? "d" : "-", file.length(),
          file.getName());
    }

  }

}


