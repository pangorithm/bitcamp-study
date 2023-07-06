package com.bitcamp.myapp.dao;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;
import com.bitcamp.myapp.vo.Schedule;
import com.bitcamp.net.RequestEntity;
import com.bitcamp.net.ResponseEntity;

public class ScheduleNetworkDao implements ScheduleDao {

  String dataName;
  DataInputStream in;
  DataOutputStream out;

  public ScheduleNetworkDao(String dataName, DataInputStream in, DataOutputStream out) {
    this.dataName = dataName;
    this.in = in;
    this.out = out;
  }

  @Override
  public void insert(Schedule sch) {
    try {
      out.writeUTF(new RequestEntity().command(dataName + "/insert").data(sch).toJson());

      ResponseEntity response = ResponseEntity.fromJson(in.readUTF());
      if (response.getStatus().equals(ResponseEntity.ERROR)) {
        throw new RuntimeException(response.getResult());
      }

    } catch (IOException e) {

      throw new RuntimeException(e);
    }
  }

  @Override
  public List<Schedule> list() {
    try {
      out.writeUTF(new RequestEntity().command(dataName + "/list").toJson());

      ResponseEntity response = ResponseEntity.fromJson(in.readUTF());
      if (response.getStatus().equals(ResponseEntity.FAILURE)) {
        throw new RuntimeException(response.getResult());
      }

      return response.getList(Schedule.class);

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public Schedule findBy(int no) {
    try {
      out.writeUTF(new RequestEntity().command(dataName + "/findBy").data(no).toJson());

      ResponseEntity response = ResponseEntity.fromJson(in.readUTF());
      if (response.getStatus().equals(ResponseEntity.ERROR)) {
        throw new RuntimeException(response.getResult());
      } else if (response.getStatus().equals(ResponseEntity.FAILURE)) {
        return null;
      }

      return response.getObject(Schedule.class);

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public int update(Schedule sch) {
    try {

      // 서버에 요청을 보낸다.
      out.writeUTF(new RequestEntity().command(dataName + "/update").data(sch).toJson());

      // 서버에서 보낸 응답을 받는다.
      ResponseEntity response = ResponseEntity.fromJson(in.readUTF());

      if (response.getStatus().equals(ResponseEntity.ERROR)) {
        throw new RuntimeException(response.getResult());
      } else if (response.getStatus().equals(ResponseEntity.FAILURE)) {
        return 0;
      }
      return response.getObject(Integer.class);

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public int remove(int no) {
    try {
      out.writeUTF(new RequestEntity().command(dataName + "/remove").data(no).toJson());

      ResponseEntity response = ResponseEntity.fromJson(in.readUTF());

      if (response.getStatus().equals(ResponseEntity.ERROR)) {
        throw new RuntimeException(response.getResult());
      } else if (response.getStatus().equals(ResponseEntity.FAILURE)) {
        return 0;
      }

      return response.getObject(Integer.class);

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
