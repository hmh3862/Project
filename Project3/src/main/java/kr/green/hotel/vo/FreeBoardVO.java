package kr.green.hotel.vo;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;

/*
DROP SEQUENCE freeBoard_idx_seq;
CREATE SEQUENCE freeBoard_idx_seq;
DROP TABLE freeBoard;
CREATE TABLE freeBoard(
	idx NUMBER PRIMARY KEY,
	name varchar2(50) NOT NULL,
	password varchar2(50) NOT NULL,
	subject varchar2(500) NOT NULL,
	content varchar2(4000) NOT NULL,
	regDate timestamp  DEFAULT SYSDATE,
	ip varchar2(50) NOT NULL
);
 */
@XmlRootElement
@Data
public class FreeBoardVO {
	private int 	idx;
	private String 	name;
	private String 	password;
	private String 	subject;
	private String 	content;
	private Date 	regDate;
	private String 	ip;
	// 첨부파일의 정보를 저장할 필드 추가
	private List<FreeBoardFileVO> fileList;
	
	// 댓글의 개수
	private int freeCommentCount;
	private List<FreeCommentVO> freeCommentList;
}
