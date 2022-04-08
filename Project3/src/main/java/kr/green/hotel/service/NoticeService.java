package kr.green.hotel.service;

import java.util.List;

import kr.green.hotel.vo.CommVO;
import kr.green.hotel.vo.NoticeCommentVO;
import kr.green.hotel.vo.NoticeVO;
import kr.green.hotel.vo.PagingVO;

public interface NoticeService {
	// 1. 목록보기
	PagingVO<NoticeVO> selectList(CommVO commVO);
	// 2. 내용보기 - 글 1개 가져오기
	NoticeVO selectByIdx(int idx);
	// 3. 글쓰기
	void insert(NoticeVO noticeVO);
	// 4. 글수정
	void update(NoticeVO noticeVO, String[] delFiles, String realPath);
	// 5. 글삭제
	void delete(NoticeVO noticeVO, String uploadPath);
	
	// 댓글저장
	void insert(NoticeCommentVO noticeCommentVO);
	// 댓글수정
	void update(NoticeCommentVO noticeCommentVO);
	// 댓글 1개 삭제(idx)
	void deleteByIdx(int idx);
	// 댓글 전체삭제(ref) ---- 원본글을 삭제할때만 사용
	void deleteByRef(int ref);
	// 댓글 모두얻기
	List<NoticeCommentVO> selectList(int ref);
	// 댓글 개수
	int selectCount(int ref);
}
