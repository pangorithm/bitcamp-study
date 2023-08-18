-- 회원
DROP TABLE IF EXISTS sch_member RESTRICT;

-- 일정
DROP TABLE IF EXISTS sch_schedule RESTRICT;

-- 게시글
DROP TABLE IF EXISTS sch_board RESTRICT;

-- 댓글
DROP TABLE IF EXISTS sch_comment RESTRICT;

-- 일정참여
DROP TABLE IF EXISTS sch_part_schedule RESTRICT;

-- 회원주소
DROP TABLE IF EXISTS sch_member_addr RESTRICT;

-- 주소 유형
DROP TABLE IF EXISTS sch_addr_type RESTRICT;

-- 게시글첨부파일
DROP TABLE IF EXISTS sch_board_file RESTRICT;

-- 투표
DROP TABLE IF EXISTS sch_vote RESTRICT;

-- 투표항목
DROP TABLE IF EXISTS sch_vote_item RESTRICT;

-- 회원투표
DROP TABLE IF EXISTS sch_part_vote RESTRICT;

-- 투표첨부파일
DROP TABLE IF EXISTS sch_vote_file RESTRICT;

-- 댓글첨부파일
DROP TABLE IF EXISTS sch_comment_file RESTRICT;

-- 회원
CREATE TABLE sch_member (
  mno        INTEGER     NOT NULL COMMENT '회원번호', -- 회원번호
  name       VARCHAR(50) NOT NULL COMMENT '이름', -- 이름
  email      VARCHAR(40) NOT NULL COMMENT '이메일', -- 이메일
  password   VARCHAR(40) NOT NULL COMMENT '비밀번호', -- 비밀번호
  tel        VARCHAR(30) NULL     COMMENT '전화번호', -- 전화번호
  gender     VARCHAR(10) NULL     COMMENT '성별', -- 성별
  created_at DATE        NOT NULL DEFAULT (current_date()) COMMENT '가입일', -- 가입일
  photo      VARCHAR(255)         COMMENT '사진'
)
COMMENT '회원';

-- 회원
ALTER TABLE sch_member
  ADD CONSTRAINT PK_sch_member -- 회원 기본키
  PRIMARY KEY (
  mno -- 회원번호
  );

-- 회원 유니크 인덱스
CREATE UNIQUE INDEX UIX_sch_member
  ON sch_member ( -- 회원
    email ASC -- 이메일
  );

-- 회원 인덱스
CREATE INDEX IX_sch_member
  ON sch_member( -- 회원
    name ASC -- 이름
  );

ALTER TABLE sch_member
  MODIFY COLUMN mno INTEGER NOT NULL AUTO_INCREMENT COMMENT '회원번호';

-- 일정
CREATE TABLE sch_schedule (
  sno        INTEGER      NOT NULL COMMENT '일정번호', -- 일정번호
  title      VARCHAR(50)  NOT NULL COMMENT '제목', -- 제목
  content    VARCHAR(255) NULL     COMMENT '설명', -- 설명
  start_time DATETIME     NOT NULL DEFAULT (current_date()) COMMENT '시작일시', -- 시작일시
  end_time   DATETIME     NOT NULL DEFAULT (current_date()) COMMENT '종료일시', -- 종료일시
  mno        INTEGER      NOT NULL COMMENT '매니저 번호' -- 매니저 번호
)
COMMENT '일정';

-- 일정
ALTER TABLE sch_schedule
  ADD CONSTRAINT PK_sch_schedule -- 일정 기본키
  PRIMARY KEY (
  sno -- 일정번호
  );

-- 일정 인덱스
CREATE INDEX IX_sch_schedule
  ON sch_schedule( -- 일정
    title ASC -- 제목
  );

-- 일정 인덱스2
CREATE INDEX IX_sch_schedule2
  ON sch_schedule( -- 일정
    start_time ASC -- 시작일시
  );

-- 일정 인덱스3
CREATE INDEX IX_sch_schedule3
  ON sch_schedule( -- 일정
    end_time ASC -- 종료일시
  );

ALTER TABLE sch_schedule
  MODIFY COLUMN sno INTEGER NOT NULL AUTO_INCREMENT COMMENT '일정번호';

-- 게시글
CREATE TABLE sch_board (
  bno        INTEGER     NOT NULL COMMENT '게시글번호', -- 게시글번호
  mno        INTEGER     NOT NULL COMMENT '작성자번호', -- 작성자번호
  title      VARCHAR(50) NOT NULL COMMENT '제목', -- 제목
  content    MEDIUMTEXT  NOT NULL COMMENT '내용', -- 내용
  view_count INTEGER     NOT NULL DEFAULT 0 COMMENT '조회수', -- 조회수
  created_at DATETIME    NOT NULL DEFAULT (current_date()) COMMENT '작성일시', -- 작성일시
  updated_at DATETIME    NULL     COMMENT '수정일시', -- 수정일시
  category   VARCHAR(10) NOT NULL COMMENT '게시판 분류' -- 카테고리
)
COMMENT '게시글';

-- 게시글
ALTER TABLE sch_board
  ADD CONSTRAINT PK_sch_board -- 게시글 기본키
  PRIMARY KEY (
  bno -- 게시글번호
  );

-- 게시글 인덱스
CREATE INDEX IX_sch_board
  ON sch_board( -- 게시글
    title ASC -- 제목
  );

ALTER TABLE sch_board
  MODIFY COLUMN bno INTEGER NOT NULL AUTO_INCREMENT COMMENT '게시글번호';

-- 댓글
CREATE TABLE sch_comment (
  cno        INTEGER    NOT NULL COMMENT '댓글번호', -- 댓글번호
  bno        INTEGER    NOT NULL COMMENT '게시글번호', -- 게시글번호
  mno        INTEGER    NOT NULL COMMENT '작성자번호', -- 작성자번호
  pcno       INTEGER    NULL     COMMENT '대댓글 작성시 입력', -- 부모댓글번호
  content    MEDIUMTEXT NOT NULL COMMENT '내용', -- 내용
  created_at DATETIME   NOT NULL DEFAULT (current_date()) COMMENT '작성일시', -- 작성일시
  updated_at DATETIME   NULL     COMMENT '수정일시' -- 수정일시
)
COMMENT '댓글';

-- 댓글
ALTER TABLE sch_comment
  ADD CONSTRAINT PK_sch_comment -- 댓글 기본키
  PRIMARY KEY (
  cno -- 댓글번호
  );

ALTER TABLE sch_comment
  MODIFY COLUMN cno INTEGER NOT NULL AUTO_INCREMENT COMMENT '댓글번호';

-- 일정참여
CREATE TABLE sch_part_schedule (
  pmno INTEGER NOT NULL COMMENT '참여자번호', -- 참여자번호
  sno  INTEGER NOT NULL COMMENT '일정번호' -- 일정번호
)
COMMENT '일정참여';

-- 일정참여
ALTER TABLE sch_part_schedule
  ADD CONSTRAINT PK_sch_part_schedule -- 일정참여 기본키
  PRIMARY KEY (
  pmno, -- 참여자번호
  sno   -- 일정번호
  );

-- 회원주소
CREATE TABLE sch_member_addr (
  mano        INTEGER      NOT NULL COMMENT '회원주소변호', -- 회원주소변호
  mno         INTEGER      NOT NULL COMMENT '회원번호', -- 회원번호
  atno        INTEGER      NOT NULL COMMENT '주소유형번호', -- 주소유형번호
  post_addr   VARCHAR(10)  NOT NULL COMMENT '우편번호', -- 우편번호
  basic_addr  VARCHAR(255) NOT NULL COMMENT '기본주소', -- 기본주소
  detail_addr VARCHAR(255) NULL     COMMENT '상세주소' -- 상세주소
)
COMMENT '회원주소';

-- 회원주소
ALTER TABLE sch_member_addr
  ADD CONSTRAINT PK_sch_member_addr -- 회원주소 기본키
  PRIMARY KEY (
  mano -- 회원주소변호
  );

ALTER TABLE sch_member_addr
  MODIFY COLUMN mano INTEGER NOT NULL AUTO_INCREMENT COMMENT '회원주소변호';

-- 주소 유형
CREATE TABLE sch_addr_type (
  atno INTEGER     NOT NULL COMMENT '주소유형번호', -- 주소유형번호
  type VARCHAR(10) NOT NULL COMMENT '집, 직장 등' -- 유형
)
COMMENT '주소 유형';

-- 주소 유형
ALTER TABLE sch_addr_type
  ADD CONSTRAINT PK_sch_addr_type -- 주소 유형 기본키
  PRIMARY KEY (
  atno -- 주소유형번호
  );

-- 주소 유형 유니크 인덱스
CREATE UNIQUE INDEX UIX_sch_addr_type
  ON sch_addr_type ( -- 주소 유형
    type ASC -- 유형
  );

ALTER TABLE sch_addr_type
  MODIFY COLUMN atno INTEGER NOT NULL AUTO_INCREMENT COMMENT '주소유형번호';

-- 게시글첨부파일
CREATE TABLE sch_board_file (
  bfno     INTEGER      NOT NULL COMMENT '게시글첨부파일번호', -- 게시글첨부파일번호
  bno      INTEGER      NOT NULL COMMENT '게시글번호', -- 게시글번호
  filepath VARCHAR(255) NOT NULL COMMENT '첨부파일' -- 첨부파일
)
COMMENT '게시글첨부파일';

-- 게시글첨부파일
ALTER TABLE sch_board_file
  ADD CONSTRAINT PK_sch_board_file -- 게시글첨부파일 기본키
  PRIMARY KEY (
  bfno -- 게시글첨부파일번호
  );

ALTER TABLE sch_board_file
  MODIFY COLUMN bfno INTEGER NOT NULL AUTO_INCREMENT COMMENT '게시글첨부파일번호';

-- 투표
CREATE TABLE sch_vote (
  vno        INTEGER     NOT NULL COMMENT '투표번호', -- 투표번호
  mno        INTEGER     NOT NULL COMMENT '매니저번호', -- 매니저번호
  title      VARCHAR(50) NOT NULL COMMENT '제목', -- 제목
  content    MEDIUMTEXT  NULL     COMMENT '설명', -- 설명
  created_at DATETIME    NOT NULL DEFAULT (current_date()) COMMENT '작성일시', -- 작성일시
  updated_at DATETIME    NULL     COMMENT '수정일시', -- 수정일시
  eff_at     DATETIME    NOT NULL DEFAULT (current_date()) COMMENT '시작일시', -- 시작일시
  exp_at     DATETIME    NULL     DEFAULT '9999-12-31 23:59:59' COMMENT '마감일시' -- 마감일시
)
COMMENT '투표';

-- 투표
ALTER TABLE sch_vote
  ADD CONSTRAINT PK_sch_vote -- 투표 기본키
  PRIMARY KEY (
  vno -- 투표번호
  );

-- 투표 인덱스
CREATE INDEX IX_sch_vote
  ON sch_vote( -- 투표
    title ASC -- 제목
  );

-- 투표 인덱스2
CREATE INDEX IX_sch_vote2
  ON sch_vote( -- 투표
    exp_at ASC -- 마감일시
  );

ALTER TABLE sch_vote
  MODIFY COLUMN vno INTEGER NOT NULL AUTO_INCREMENT COMMENT '투표번호';

-- 투표항목
CREATE TABLE sch_vote_item (
  vino     INTEGER      NOT NULL COMMENT '투표항목번호', -- 투표항목번호
  vno      INTEGER      NULL     COMMENT '투표번호', -- 투표번호
  title    VARCHAR(50)  NOT NULL COMMENT '제목', -- 제목
  content  VARCHAR(255) NULL     COMMENT '설명', -- 설명
  filepath VARCHAR(255) NULL     COMMENT '첨부파일' -- 첨부파일
)
COMMENT '투표항목';

-- 투표항목
ALTER TABLE sch_vote_item
  ADD CONSTRAINT PK_sch_vote_item -- 투표항목 기본키
  PRIMARY KEY (
  vino -- 투표항목번호
  );

-- 투표항목 유니크 인덱스
CREATE UNIQUE INDEX UIX_sch_vote_item
  ON sch_vote_item ( -- 투표항목
    title ASC -- 제목
  );

ALTER TABLE sch_vote_item
  MODIFY COLUMN vino INTEGER NOT NULL AUTO_INCREMENT COMMENT '투표항목번호';

-- 회원투표
CREATE TABLE sch_part_vote (
  mno  INTEGER NOT NULL COMMENT '회원번호', -- 회원번호
  vino INTEGER NOT NULL COMMENT '투표항목번호' -- 투표항목번호
)
COMMENT '회원투표';

-- 회원투표
ALTER TABLE sch_part_vote
  ADD CONSTRAINT PK_sch_part_vote -- 회원투표 기본키
  PRIMARY KEY (
  mno,  -- 회원번호
  vino  -- 투표항목번호
  );

-- 투표첨부파일
CREATE TABLE sch_vote_file (
  vfno     INTEGER      NOT NULL COMMENT '투표첨부파일번호', -- 투표첨부파일번호
  vno      INTEGER      NULL     COMMENT '투표번호', -- 투표번호
  filepath VARCHAR(255) NOT NULL COMMENT '첨부파일' -- 첨부파일
)
COMMENT '투표첨부파일';

-- 투표첨부파일
ALTER TABLE sch_vote_file
  ADD CONSTRAINT PK_sch_vote_file -- 투표첨부파일 기본키
  PRIMARY KEY (
  vfno -- 투표첨부파일번호
  );

ALTER TABLE sch_vote_file
  MODIFY COLUMN vfno INTEGER NOT NULL AUTO_INCREMENT COMMENT '투표첨부파일번호';

-- 댓글첨부파일
CREATE TABLE sch_comment_file (
  cfno     INTEGER      NOT NULL COMMENT '댓글첨부파일번호', -- 댓글첨부파일번호
  cno      INTEGER      NOT NULL COMMENT '댓글번호', -- 댓글번호
  filepath VARCHAR(255) NOT NULL COMMENT '첨부파일' -- 첨부파일
)
COMMENT '댓글첨부파일';

-- 댓글첨부파일
ALTER TABLE sch_comment_file
  ADD CONSTRAINT PK_sch_comment_file -- 댓글첨부파일 기본키
  PRIMARY KEY (
  cfno -- 댓글첨부파일번호
  );

ALTER TABLE sch_comment_file
  MODIFY COLUMN cfno INTEGER NOT NULL AUTO_INCREMENT COMMENT '댓글첨부파일번호';

-- 일정
ALTER TABLE sch_schedule
  ADD CONSTRAINT FK_sch_member_TO_sch_schedule -- 회원 -> 일정
  FOREIGN KEY (
  mno -- 매니저 번호
  )
  REFERENCES sch_member ( -- 회원
  mno -- 회원번호
  );

-- 게시글
ALTER TABLE sch_board
  ADD CONSTRAINT FK_sch_member_TO_sch_board -- 회원 -> 게시글
  FOREIGN KEY (
  mno -- 작성자번호
  )
  REFERENCES sch_member ( -- 회원
  mno -- 회원번호
  );

-- 댓글
ALTER TABLE sch_comment
  ADD CONSTRAINT FK_sch_board_TO_sch_comment -- 게시글 -> 댓글
  FOREIGN KEY (
  bno -- 게시글번호
  )
  REFERENCES sch_board ( -- 게시글
  bno -- 게시글번호
  );

-- 댓글
ALTER TABLE sch_comment
  ADD CONSTRAINT FK_sch_member_TO_sch_comment -- 회원 -> 댓글
  FOREIGN KEY (
  mno -- 작성자번호
  )
  REFERENCES sch_member ( -- 회원
  mno -- 회원번호
  );

-- 댓글
ALTER TABLE sch_comment
  ADD CONSTRAINT FK_sch_comment_TO_sch_comment -- 댓글 -> 댓글
  FOREIGN KEY (
  pcno -- 부모댓글번호
  )
  REFERENCES sch_comment ( -- 댓글
  cno -- 댓글번호
  );

-- 일정참여
ALTER TABLE sch_part_schedule
  ADD CONSTRAINT FK_sch_member_TO_sch_part_schedule -- 회원 -> 일정참여
  FOREIGN KEY (
  pmno -- 참여자번호
  )
  REFERENCES sch_member ( -- 회원
  mno -- 회원번호
  );

-- 일정참여
ALTER TABLE sch_part_schedule
  ADD CONSTRAINT FK_sch_schedule_TO_sch_part_schedule -- 일정 -> 일정참여
  FOREIGN KEY (
  sno -- 일정번호
  )
  REFERENCES sch_schedule ( -- 일정
  sno -- 일정번호
  );

-- 회원주소
ALTER TABLE sch_member_addr
  ADD CONSTRAINT FK_sch_member_TO_sch_member_addr -- 회원 -> 회원주소
  FOREIGN KEY (
  mno -- 회원번호
  )
  REFERENCES sch_member ( -- 회원
  mno -- 회원번호
  );

-- 회원주소
ALTER TABLE sch_member_addr
  ADD CONSTRAINT FK_sch_addr_type_TO_sch_member_addr -- 주소 유형 -> 회원주소
  FOREIGN KEY (
  atno -- 주소유형번호
  )
  REFERENCES sch_addr_type ( -- 주소 유형
  atno -- 주소유형번호
  );

-- 게시글첨부파일
ALTER TABLE sch_board_file
  ADD CONSTRAINT FK_sch_board_TO_sch_board_file -- 게시글 -> 게시글첨부파일
  FOREIGN KEY (
  bno -- 게시글번호
  )
  REFERENCES sch_board ( -- 게시글
  bno -- 게시글번호
  );

-- 투표
ALTER TABLE sch_vote
  ADD CONSTRAINT FK_sch_member_TO_sch_vote -- 회원 -> 투표
  FOREIGN KEY (
  mno -- 매니저번호
  )
  REFERENCES sch_member ( -- 회원
  mno -- 회원번호
  );

-- 투표항목
ALTER TABLE sch_vote_item
  ADD CONSTRAINT FK_sch_vote_TO_sch_vote_item -- 투표 -> 투표항목
  FOREIGN KEY (
  vno -- 투표번호
  )
  REFERENCES sch_vote ( -- 투표
  vno -- 투표번호
  );

-- 회원투표
ALTER TABLE sch_part_vote
  ADD CONSTRAINT FK_sch_member_TO_sch_part_vote -- 회원 -> 회원투표
  FOREIGN KEY (
  mno -- 회원번호
  )
  REFERENCES sch_member ( -- 회원
  mno -- 회원번호
  );

-- 회원투표
ALTER TABLE sch_part_vote
  ADD CONSTRAINT FK_sch_vote_item_TO_sch_part_vote -- 투표항목 -> 회원투표
  FOREIGN KEY (
  vino -- 투표항목번호
  )
  REFERENCES sch_vote_item ( -- 투표항목
  vino -- 투표항목번호
  );

-- 투표첨부파일
ALTER TABLE sch_vote_file
  ADD CONSTRAINT FK_sch_vote_TO_sch_vote_file -- 투표 -> 투표첨부파일
  FOREIGN KEY (
  vno -- 투표번호
  )
  REFERENCES sch_vote ( -- 투표
  vno -- 투표번호
  );

-- 댓글첨부파일
ALTER TABLE sch_comment_file
  ADD CONSTRAINT FK_sch_comment_TO_sch_comment_file -- 댓글 -> 댓글첨부파일
  FOREIGN KEY (
  cno -- 댓글번호
  )
  REFERENCES sch_comment ( -- 댓글
  cno -- 댓글번호
  );
  
  