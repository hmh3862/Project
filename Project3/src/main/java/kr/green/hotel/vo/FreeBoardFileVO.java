package kr.green.hotel.vo;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;

/*
-- 해당글의 첨부파일 정보를 저장할 테이블
DROP SEQUENCE freeBoardFile_idx_seq;
CREATE SEQUENCE freeBoardFile_idx_seq;
DROP TABLE freeBoardFile;
CREATE TABLE freeBoardFile(
	idx NUMBER PRIMARY KEY,			 -- 키필드
	REF NUMBER DEFAULT 0,            -- 몇번글의 첨부파일이냐를 저장할 원본글 번호
	saveName varchar2(500) NOT NULL, -- 저장파일명
	oriName  varchar2(500) NOT NULL  -- 원본파일명
);
 */
@XmlRootElement
@Data
public class FreeBoardFileVO {
	private int 	idx;
	private int 	ref;
	private String 	saveName;
	private String 	oriName;
}
