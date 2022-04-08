package kr.green.hotel.vo;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;

/*
-- 글의 내용을 저장할 테이블
DROP SEQUENCE free_comment_idx_seq;
CREATE SEQUENCE free_comment_idx_seq;
DROP TABLE free_comment;
create table free_comment(
	idx number primary key,
	REF NUMBER NOT NULL,  -- 원본글 번호
   		name varchar2(100) not null,
   		password varchar2(100) not null,
   		content varchar2(2500) not null,
   		regdate timestamp default sysdate,
   		ip varchar2(20) not null
); 
*/

@Data
@XmlRootElement
public class FreeCommentVO {
	private int idx;
	private int ref;
	private String name;
	private String password;
	private String content;
	private Date   regDate;
	private String ip;
}
