package kr.green.hotel.service;

import kr.green.hotel.vo.CommVO;
import kr.green.hotel.vo.PagingVO;
import kr.green.hotel.vo.QnAVO;

public interface QnAService {
	// 1. 목록보기
	PagingVO<QnAVO> selectList(CommVO commVO);
	// 2. 내용보기 - 글 1개 가져오기
	QnAVO selectByIdx(int idx);
	// 3. 글쓰기
	void insert(QnAVO qnaVO);
	// 4. 글수정
	void update(QnAVO qnaVO, String[] delFiles, String realPath);
	// 5. 글삭제
	void delete(QnAVO qnaVO, String uploadPath);
}
