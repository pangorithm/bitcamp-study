-- myapp_schedule 테이블 예제 데이터
insert into scheduleapp_schedule(schedule_no, schedule_title, start_time, end_time) 
	values(1, 'aaa', '1997-08-07 12:30:45', '1997-08-07 23:10:30');
insert into scheduleapp_schedule(schedule_no, schedule_title, start_time, end_time) 
  values(2, 'bbb', '2002-02-02 12:30:45', '2003-03-05 23:10:30');
insert into scheduleapp_schedule(schedule_no, schedule_title, start_time, end_time) 
  values(3, 'ccc', '2010-10-10 12:30:45', '2020-10-20 23:10:30');
insert into scheduleapp_schedule(schedule_no, schedule_title, start_time, end_time) 
  values(4, 'ddd', '5555-05-05 05:05:55', '6666-06-06 06:06:06');
insert into scheduleapp_schedule(schedule_no, schedule_title, start_time, end_time) 
  values(5, 'eee', '7777-07-07 07:07:07', '8888-08-08 08:08:08');

-- myapp_board 테이블 예제 데이터
insert into scheduleapp_board(board_no, title, content, writer, password, category) 
	values(11, '제목1', '내용', '홍길동', sha1('1111'), 1);
insert into scheduleapp_board(board_no,title, content, writer, password, category) 
	values(12, '제목2', '내용', '임꺽정', sha1('1111'), 1);
insert into scheduleapp_board(board_no,title, content, writer, password, category) 
	values(13, '제목3', '내용', '유관순', sha1('1111'), 1);
insert into scheduleapp_board(board_no,title, content, writer, password, category) 
	values(14, '제목4', '내용', '이순신', sha1('1111'), 1);
insert into scheduleapp_board(board_no,title, content, writer, password, category) 
	values(15, '제목5', '내용', '윤봉길', sha1('1111'), 2);
insert into scheduleapp_board(board_no,title, content, writer, password, category) 
	values(16, '제목6', '내용', '안중근', sha1('1111'), 2);
insert into scheduleapp_board(board_no,title, content, writer, password, category) 
	values(17, '제목7', '내용', '김구', sha1('1111'), 2);