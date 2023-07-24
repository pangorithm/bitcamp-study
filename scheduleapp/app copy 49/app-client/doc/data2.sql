-- myapp_member 테이블 예제 데이터
insert into scheduleapp_member(member_no, name, email, password, gender) 
	values(1, 'aaa', 'aaa@test.com', sha1('1111'), 'W');
insert into scheduleapp_member(member_no, name, email, password, gender) 
	values(2, 'bbb', 'bbb@test.com', sha1('1111'), 'M');
insert into scheduleapp_member(member_no, name, email, password, gender) 
	values(3, 'ccc', 'ccc@test.com', sha1('1111'), 'W');
insert into scheduleapp_member(member_no, name, email, password, gender) 
	values(4, 'ddd', 'ddd@test.com', sha1('1111'), 'M');
insert into scheduleapp_member(member_no, name, email, password, gender) 
	values(5, 'eee', 'eee@test.com', sha1('1111'), 'W');
insert into scheduleapp_member(member_no, name, email, password, gender) 
	values(6, 'fff', 'fff@test.com', sha1('1111'), 'M');

-- myapp_board 테이블 예제 데이터
insert into scheduleapp_board(board_no, title, content, writer, password, category) 
	values(11, '제목1', '내용', 1, sha1('1111'), 1);
insert into scheduleapp_board(board_no,title, content, writer, password, category) 
	values(12, '제목2', '내용', 1, sha1('1111'), 1);
insert into scheduleapp_board(board_no,title, content, writer, password, category) 
	values(13, '제목3', '내용', 3, sha1('1111'), 1);
insert into scheduleapp_board(board_no,title, content, writer, password, category) 
	values(14, '제목4', '내용', 4, sha1('1111'), 1);
insert into scheduleapp_board(board_no,title, content, writer, password, category) 
	values(15, '제목5', '내용', 5, sha1('1111'), 2);
insert into scheduleapp_board(board_no,title, content, writer, password, category) 
	values(16, '제목6', '내용', 5, sha1('1111'), 2);
insert into scheduleapp_board(board_no,title, content, writer, password, category) 
	values(17, '제목7', '내용', 5, sha1('1111'), 2);
	
-- myapp_schedule 테이블 예제 데이터
insert into scheduleapp_schedule(schedule_no, schedule_title, start_time, end_time, owner) 
  values(1, 'aaa', '1997-08-07 12:30:45', '1997-08-07 23:10:30', 1);
insert into scheduleapp_schedule(schedule_no, schedule_title, start_time, end_time, owner) 
  values(2, 'bbb', '2002-02-02 12:30:45', '2003-03-05 23:10:30', 2);
insert into scheduleapp_schedule(schedule_no, schedule_title, start_time, end_time, owner) 
  values(3, 'ccc', '2010-10-10 12:30:45', '2020-10-20 23:10:30', 3);
insert into scheduleapp_schedule(schedule_no, schedule_title, start_time, end_time, owner) 
  values(4, 'ddd', '5555-05-05 05:05:55', '6666-06-06 06:06:06', 3);
insert into scheduleapp_schedule(schedule_no, schedule_title, start_time, end_time, owner) 
  values(5, 'eee', '7777-07-07 07:07:07', '8888-08-08 08:08:08', 1);
