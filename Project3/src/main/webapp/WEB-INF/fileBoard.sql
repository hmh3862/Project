-- 글의 내용을 저장할 테이블
DROP SEQUENCE fileBoard_idx_seq;
CREATE SEQUENCE fileBoard_idx_seq;
DROP TABLE fileBoard;
CREATE TABLE fileBoard(
	idx NUMBER PRIMARY KEY,
	name varchar2(50) NOT NULL,
	password varchar2(50) NOT NULL,
	subject varchar2(500) NOT NULL,
	content varchar2(4000) NOT NULL,
	regDate timestamp  DEFAULT SYSDATE,
	ip varchar2(50) NOT NULL
);
-- 해당글의 첨부파일 정보를 저장할 테이블
DROP SEQUENCE fileBoardFile_idx_seq;
CREATE SEQUENCE fileBoardFile_idx_seq;
DROP TABLE fileBoardFile;
CREATE TABLE fileBoardFile(
	idx NUMBER PRIMARY KEY,			 -- 키필드
	REF NUMBER DEFAULT 0,            -- 몇번글의 첨부파일이냐를 저장할 원본글 번호
	saveName varchar2(500) NOT NULL, -- 저장파일명
	oriName  varchar2(500) NOT NULL  -- 원본파일명
);
-- 댓글의 내용을 저장할 테이블
DROP SEQUENCE board_comment_idx_seq;
CREATE SEQUENCE board_comment_idx_seq;
DROP TABLE board_comment;
create table board_comment(
	idx number primary key,
	REF NUMBER NOT NULL,  -- 원본글 번호
	name varchar2(100) not null,
	password varchar2(100) not null,
	content varchar2(2500) not null,
	regdate timestamp default sysdate,
	ip varchar2(20) not null
);

SELECT * FROM fileBoard;
SELECT * FROM fileBoardFile;
SELECT * FROM board_comment;
