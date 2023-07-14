package com.bitcamp.util;

public class ManagedThread extends Thread {
  ResourcePool<ManagedThread> pool;
  Job job;

  public ManagedThread(ResourcePool<ManagedThread> pool) {
    this.pool = pool;
  }

  public void setJob(Job job) {
    this.job = job;
    synchronized (this) {
      this.notify();
    }
  }

  @Override
  public void run() {
    while (true) {
      try {
        synchronized (this) {
          this.wait();
        }
        this.job.execute();
        pool.returnResource(this);

      } catch (Exception e) {
        System.out.println("스레드 실행 오류 발생");
        e.printStackTrace();
      }
    }
  }

}
