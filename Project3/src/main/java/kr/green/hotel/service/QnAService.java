package kr.green.hotel.service;

import java.util.List;

import kr.green.hotel.vo.CommVO;
import kr.green.hotel.vo.PagingVO;
import kr.green.hotel.vo.QnACommentVO;
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
	
	// 댓글저장
	void insert(QnACommentVO qnACommentVO);
	// 댓글수정
	void update(QnACommentVO qnACommentVO);
	// 댓글 1개 삭제(idx)
	void deleteByIdx(int idx);
	// 댓글 전체삭제(ref) ---- 원본글을 삭제할때만 사용
	void deleteByRef(int ref);
	// 댓글 모두얻기
	List<QnACommentVO> selectList(int ref);
	// 댓글 개수
	int selectCount(int ref);
}
