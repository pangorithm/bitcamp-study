create table scheduleapp_board(
  board_no int not null,
  title varchar(255) not null,
  content text null,
  writer int not null,
  password varchar(100) null,
  view_count int default 0,
  created_date datetime default now(),
  category int not null
);

alter table scheduleapp_board
  add constraint primary key (board_no),
  modify column board_no int not null auto_increment;
  
create table scheduleapp_schedule(
  schedule_no int not null,
  schedule_title varchar(20) not null,
  start_time datetime not null,
  end_time datetime not null,
  owner int not null
);

alter table scheduleapp_schedule
  add constraint primary key (schedule_no),
  modify column schedule_no int not null auto_increment;
  
create table scheduleapp_member(
  member_no int not null,
  name varchar(20) not null,
  email varchar(50) not null,
  password varchar(100) not null,
  gender char(1) not null,
  created_date date default (current_date())
);

alter table scheduleapp_member
  add constraint primary key (member_no),
  modify column member_no int not null auto_increment;
  
alter table scheduleapp_member
  add constraint scheduleapp_member_uk unique (email);
  
-- 게시판 작성자에 대해 외부키 설정
alter table scheduleapp_board
  add constraint scheduleapp_board_fk foreign key (writer) references scheduleapp_member (member_no);
  
-- 스케줄 소유자에 대해 외부키 설정
alter table scheduleapp_schedule
  add constraint scheduleapp_schedule_fk foreign key (owner) references scheduleapp_member (member_no);
  
create table scheduleapp_schedule_participants(
  schedule_no int not null,
  member_no int not null
);

alter table scheduleapp_schedule_participants
  add constraint scheduleapp_schedule_participants_schedule_fk foreign key (schedule_no) references scheduleapp_schedule (schedule_no);
  
alter table scheduleapp_schedule_participants
  add constraint scheduleapp_schedule_participants_member_fk foreign key (member_no) references scheduleapp_member (member_no);
  
  