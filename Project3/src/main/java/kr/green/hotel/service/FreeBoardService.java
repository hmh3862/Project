package kr.green.hotel.service;

import kr.green.hotel.vo.CommVO;
import kr.green.hotel.vo.FreeBoardVO;
import kr.green.hotel.vo.PagingVO;

public interface FreeBoardService {
	// 1. 목록보기
	PagingVO<FreeBoardVO> selectList(CommVO commVO);
	// 2. 내용보기 - 글 1개 가져오기
	FreeBoardVO selectByIdx(int idx);
	// 3. 글쓰기
	void insert(FreeBoardVO freeBoardVO);
	// 4. 글수정
	void update(FreeBoardVO freeBoardVO, String[] delFiles, String realPath);
	// 5. 글삭제
	void delete(FreeBoardVO freeBoardVO, String uploadPath);
}
