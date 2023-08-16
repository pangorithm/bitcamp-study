-- 회원 데이터 삽입
INSERT INTO sch_member (name, email, password, tel, gender)
VALUES
  ('홍길동', 'hong@example.com', sha1('1111'), '123-4567', 'M'),
  ('aaa', 'aaa@test.com', sha1('1111'), '987-6543', 'M'),
  ('김영희', 'younghee@example.com', sha1('1111'), '555-1234', 'W'),
  ('Jane Smith', 'jane@example.com', sha1('1111'), '777-8888', 'W'),
  ('이철수', 'chulsoo@example.com', sha1('1111'), '111-2222', 'M'),
  ('Michael Johnson', 'michael@example.com', sha1('1111'), '333-4444', 'M'),
  ('박미영', 'miyoung@example.com', sha1('1111'), '999-0000', 'W'),
  ('Emily Brown', 'emily@example.com', sha1('1111'), '666-7777', 'W'),
  ('정재호', 'jaeho@example.com', sha1('1111'), '222-3333', 'M'),
  ('William Lee', 'william@example.com', sha1('1111'), '444-5555', 'M');

-- 일정 데이터 삽입
INSERT INTO sch_schedule (title, content, start_time, end_time, mno)
VALUES
  ('약속1', '친구와 약속', '2023-08-15 14:00:00', '2023-08-15 17:00:00', 1),
  ('Meeting 1', 'Discuss project status', '2023-08-16 10:00:00', '2023-08-16 12:00:00', 2),
  ('모임1', '동호회 정기 모임', '2023-08-17 19:00:00', '2023-08-17 22:00:00', 3),
  ('Conference Call', 'Client presentation', '2023-08-18 15:30:00', '2023-08-18 17:00:00', 4),
  ('일정2', '가족 행사', '2023-08-19 09:00:00', '2023-08-19 12:00:00', 5),
  ('Lunch Meeting', 'Discuss partnership opportunities', '2023-08-20 12:30:00', '2023-08-20 13:30:00', 6),
  ('약속2', '데이트', '2023-08-21 18:00:00', '2023-08-21 20:00:00', 7),
  ('Project Workshop', 'Brainstorming session', '2023-08-22 14:00:00', '2023-08-22 16:00:00', 8),
  ('미팅2', '프로젝트 논의', '2023-08-23 11:00:00', '2023-08-23 13:00:00', 9),
  ('Family Gathering', 'Celebration dinner', '2023-08-24 19:00:00', '2023-08-24 21:00:00', 10);

-- 게시글 데이터 삽입
INSERT INTO sch_board (mno, title, content, created_at, category)
VALUES
  (1, '첫 번째 게시글', '안녕하세요. 첫 번째 게시글입니다.', '2023-08-11 09:00:00', '1'),
  (2, 'Hello World', 'This is my first post.', '2023-08-11 10:00:00', '1'),
  (3, '동호회 소식', '이번 주 동호회 소식을 안내합니다.', '2023-08-11 11:00:00', '2'),
  (4, 'Project Update', 'Here is the latest project update.', '2023-08-11 12:00:00', '2'),
  (5, '질문', '무엇을 물어볼까요?', '2023-08-11 13:00:00', '1'),
  (6, 'Meeting Minutes', 'Minutes of the recent meeting.', '2023-08-11 14:00:00', '1'),
  (7, '일정 공유', '다음 주 일정을 공유합니다.', '2023-08-11 15:00:00', '1'),
  (8, 'New Product Launch', 'Introducing our latest product.', '2023-08-11 16:00:00', '2'),
  (9, '한국어 게시글', '안녕하세요. 한국어 게시글입니다.', '2023-08-11 17:00:00', '2'),
  (10, 'English Post', 'Greetings from the English blog.', '2023-08-11 18:00:00', '1');

-- 댓글 데이터 삽입
INSERT INTO sch_comment (bno, mno, content, created_at)
VALUES
  (1, 3, '댓글입니다.', '2023-08-11 09:30:00'),
  (2, 4, 'This is a comment.', '2023-08-11 10:30:00'),
  (3, 1, '소식에 대한 댓글입니다.', '2023-08-11 11:30:00'),
  (4, 5, 'Thanks for the update.', '2023-08-11 12:30:00'),
  (5, 2, '무엇을 도와드릴까요?', '2023-08-11 13:30:00'),
  (6, 6, 'Discuss further in the next meeting.', '2023-08-11 14:30:00'),
  (7, 7, '일정이 변경되었습니다.', '2023-08-11 15:30:00'),
  (8, 8, 'Looking forward to the launch!', '2023-08-11 16:30:00'),
  (9, 9, '한국어 댓글입니다.', '2023-08-11 17:30:00'),
  (10, 10, 'Your blog posts are always informative.', '2023-08-11 18:30:00');

-- 일정참여 데이터 삽입
INSERT INTO sch_part_schedule (pmno, sno)
VALUES
  (1, 1),
  (2, 3),
  (3, 5),
  (4, 7),
  (5, 9),
  (6, 2),
  (7, 4),
  (8, 6),
  (9, 8),
  (10, 10);

-- 주소 유형 데이터 삽입
INSERT INTO sch_addr_type (type)
VALUES
  ('Home'),
  ('Work');

-- 회원주소 데이터 삽입
INSERT INTO sch_member_addr (mno, atno, post_addr, basic_addr, detail_addr)
VALUES
  (1, 1, '12345', '서울시 강남구', '역삼동 123-456'),
  (2, 2, '98765', 'New York', '123 Main St'),
  (3, 1, '54321', '서울시 마포구', '망원동 789-123'),
  (4, 2, '56789', 'Los Angeles', '456 Elm St'),
  (5, 1, '11111', '서울시 종로구', '관훈동 111-222'),
  (6, 2, '22222', 'Chicago', '789 Oak St'),
  (7, 1, '33333', '서울시 서초구', '양재동 333-444'),
  (8, 2, '44444', 'San Francisco', '101 Pine St'),
  (9, 1, '55555', '서울시 영등포구', '여의도동 555-666'),
  (10, 2, '66666', 'Houston', '222 Maple St');

-- 게시글첨부파일 데이터 삽입
INSERT INTO sch_board_file (bno, filepath)
VALUES
  (1, '/path/to/file1'),
  (2, '/path/to/file2'),
  (3, '/path/to/file3'),
  (4, '/path/to/file4'),
  (5, '/path/to/file5'),
  (6, '/path/to/file6'),
  (7, '/path/to/file7'),
  (8, '/path/to/file8'),
  (9, '/path/to/file9'),
  (10, '/path/to/file10');

-- 투표 데이터 삽입
INSERT INTO sch_vote (mno, title, content, created_at, eff_at, exp_at)
VALUES
  (1, '오픈소스 프로젝트 선호도 조사', '오픈소스 프로젝트에 대한 선호도를 조사합니다.', '2023-08-11 09:00:00', '2023-08-11 10:00:00', '2023-08-18 10:00:00'),
  (2, 'Favorite Travel Destination', 'Vote for your favorite travel destination.', '2023-08-11 10:30:00', '2023-08-11 11:30:00', '2023-08-20 11:30:00'),
  (3, '음식 종류 선호도 조사', '어떤 종류의 음식을 더 선호하시나요?', '2023-08-11 12:00:00', '2023-08-11 13:00:00', '2023-08-25 13:00:00'),
  (4, 'Best Movie of the Year', 'Vote for the best movie of the year.', '2023-08-11 14:00:00', '2023-08-11 15:00:00', '2023-08-22 15:00:00'),
  (5, '일정 변경에 관한 투표', '일정 변경에 동의하십니까?', '2023-08-11 15:30:00', '2023-08-11 16:30:00', '2023-08-19 16:30:00'),
  (6, 'Lunch Menu Voting', 'Vote for the lunch menu for next week.', '2023-08-11 16:30:00', '2023-08-11 17:30:00', '2023-08-21 17:30:00'),
  (7, 'Weekend Activity Preference', 'What would you like to do this weekend?', '2023-08-11 17:30:00', '2023-08-11 18:30:00', '2023-08-23 18:30:00'),
  (8, 'Conference Theme Vote', 'Vote for the theme of our next conference.', '2023-08-11 18:30:00', '2023-08-11 19:30:00', '2023-08-24 19:30:00'),
  (9, '한국어 투표', '한국어 투표 항목입니다.', '2023-08-11 19:30:00', '2023-08-11 20:30:00', '2023-08-26 20:30:00'),
  (10, 'English Voting', 'This is an English voting topic.', '2023-08-11 20:30:00', '2023-08-11 21:30:00', '2023-08-27 21:30:00');

-- 투표항목 데이터 삽입
INSERT INTO sch_vote_item (vno, title, content, filepath)
VALUES
  (1, '오픈소스 A', '오픈소스 프로젝트 A', '/path/to/item1'),
  (1, '오픈소스 B', '오픈소스 프로젝트 B', '/path/to/item2'),
  (1, '오픈소스 C', '오픈소스 프로젝트 C', '/path/to/item3'),
  (2, 'Europe', 'European destinations', '/path/to/item4'),
  (2, 'Asia', 'Asian destinations', '/path/to/item5'),
  (2, 'North America', 'North American destinations', '/path/to/item6'),
  (3, '한식', '한식 음식', '/path/to/item7'),
  (3, '중식', '중식 음식', '/path/to/item8'),
  (3, '양식', '양식 음식', '/path/to/item9'),
  (4, 'Movie A', 'Description for Movie A', '/path/to/item10'),
  (4, 'Movie B', 'Description for Movie B', '/path/to/item11'),
  (4, 'Movie C', 'Description for Movie C', '/path/to/item12'),
  (5, '예', NULL, NULL),
  (5, '아니오', NULL, NULL),
  (6, 'Burger', NULL, NULL),
  (6, 'Pizza', NULL, NULL),
  (6, 'Salad', NULL, NULL),
  (7, 'Outdoor Activity', NULL, NULL),
  (7, 'Indoor Activity', NULL, NULL),
  (7, 'Relaxation', NULL, NULL),
  (8, 'Technology', NULL, NULL),
  (8, 'Nature', NULL, NULL),
  (8, 'Creativity', NULL, NULL),
  (9, '항목 1', NULL, NULL),
  (9, '항목 2', NULL, NULL),
  (9, '항목 3', NULL, NULL),
  (10, 'Option 1', NULL, NULL),
  (10, 'Option 2', NULL, NULL),
  (10, 'Option 3', NULL, NULL);

-- 회원투표 데이터 삽입
INSERT INTO sch_part_vote (mno, vino)
VALUES
  (1, 1),
  (1, 2),
  (1, 3),
  (2, 4),
  (2, 5),
  (2, 6),
  (3, 7),
  (3, 8),
  (3, 9),
  (4, 10),
  (4, 11),
  (4, 12),
  (5, 13),
  (5, 14),
  (6, 15),
  (6, 16),
  (6, 17),
  (7, 18),
  (7, 19),
  (7, 20),
  (8, 21),
  (8, 22),
  (8, 23),
  (9, 24),
  (9, 25),
  (9, 26),
  (10, 27),
  (10, 28),
  (10, 29);

-- 투표첨부파일 데이터 삽입
INSERT INTO sch_vote_file (vno, filepath)
VALUES
  (1, '/path/to/vote_file1'),
  (2, '/path/to/vote_file2'),
  (3, '/path/to/vote_file3'),
  (4, '/path/to/vote_file4'),
  (5, '/path/to/vote_file5'),
  (6, '/path/to/vote_file6'),
  (7, '/path/to/vote_file7'),
  (8, '/path/to/vote_file8'),
  (9, '/path/to/vote_file9'),
  (10, '/path/to/vote_file10');

-- 댓글첨부파일 데이터 삽입
INSERT INTO sch_comment_file (cno, filepath)
VALUES
  (1, '/path/to/comment_file1'),
  (2, '/path/to/comment_file2'),
  (3, '/path/to/comment_file3'),
  (4, '/path/to/comment_file4'),
  (5, '/path/to/comment_file5'),
  (6, '/path/to/comment_file6'),
  (7, '/path/to/comment_file7'),
  (8, '/path/to/comment_file8'),
  (9, '/path/to/comment_file9'),
  (10, '/path/to/comment_file10');
  
