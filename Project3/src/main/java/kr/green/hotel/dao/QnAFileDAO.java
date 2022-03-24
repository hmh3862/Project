package kr.green.hotel.dao;

import java.util.List;

import kr.green.hotel.vo.QnAFileVO;

public interface QnAFileDAO {
	// <!-- 1. 첨부파일 저장 -->
	void insert(QnAFileVO vo);
	// <!-- 2. 수정시 첨부파일 저장 : 이때는 원본글의 ref가 별도로 존재한다. -->
	void updateInsert(QnAFileVO vo);
	// <!-- 3. 첨부파일 삭제 -->
	void deleteByIdx(int idx);
	// <!-- 4. 원본글의 첨부파일 모두 읽기 -->
	List<QnAFileVO> selectList(int ref);
	// <!-- 5. 원본글의 첨부파일 모두 삭제하기 -->
	void deleteByRef(int ref);
	// <!-- 6. 글 1개 가져오기 -->
	QnAFileVO selectByIdx(int idx) ;
}
