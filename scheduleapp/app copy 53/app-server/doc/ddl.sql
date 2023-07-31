create table scheduleapp_board(
  board_no int not null,
  title varchar(255) not null,
  content text null,
  writer varchar(20) not null,
  password varchar(100) null,
  view_count int default 0,
  created_date datetime default now()
);

alter table scheduleapp_board
  add constraint primary key (board_no),
  modify column board_no int not null auto_increment;
  
create table scheduleapp_schedule(
  schedule_no int not null,
  schedule_title varchar(20) not null,
  start_time datetime not null,
  end_time datetime not null
);

alter table scheduleapp_schedule
  add constraint primary key (schedule_no),
  modify column schedule_no int not null auto_increment;
  
  
-- 게시판 카테고리 컬럼 추가
alter table scheduleapp_board
  add column category int not null;