package kr.green.hotel.service;

import kr.green.hotel.vo.CommNoticeVO;
import kr.green.hotel.vo.NoticeVO;
import kr.green.hotel.vo.PagingVO;

public interface NoticeService {
	// 1. 목록보기
	PagingVO<NoticeVO> selectList(CommNoticeVO commNoticeVO);
	// 2. 내용보기 - 글 1개 가져오기
	NoticeVO selectByIdx(int idx);
	// 3. 글쓰기
	void insert(NoticeVO noticeVO);
	// 4. 글수정
	void update(NoticeVO noticeVO, String[] delFiles, String realPath);
	// 5. 글삭제
	void delete(NoticeVO noticeVO, String uploadPath);
}
