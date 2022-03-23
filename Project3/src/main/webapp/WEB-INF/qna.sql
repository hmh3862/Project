-- 글의 내용을 저장할 테이블
DROP SEQUENCE qna_idx_seq;
CREATE SEQUENCE qna_idx_seq;
DROP TABLE qna;
CREATE TABLE qna(
	idx NUMBER PRIMARY KEY,
	name varchar2(50) NOT NULL,
	password varchar2(50) NOT NULL,
	subject varchar2(500) NOT NULL,
	content varchar2(4000) NOT NULL,
	regDate timestamp  DEFAULT SYSDATE,
	ip varchar2(50) NOT NULL
);

SELECT * FROM qna;
