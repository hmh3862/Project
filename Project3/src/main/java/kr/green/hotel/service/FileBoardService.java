package kr.green.hotel.service;

import java.util.List;

import kr.green.hotel.vo.CommVO;
import kr.green.hotel.vo.CommentVO;
import kr.green.hotel.vo.FileBoardVO;
import kr.green.hotel.vo.PagingVO;

public interface FileBoardService {
	// 1. 목록보기
	PagingVO<FileBoardVO> selectList(CommVO commVO);
	// 2. 내용보기 - 글 1개 가져오기
	FileBoardVO selectByIdx(int idx);
	// 3. 글쓰기
	void insert(FileBoardVO fileBoardVO);
	// 4. 글수정
	void update(FileBoardVO fileBoardVO, String[] delFiles, String realPath);
	// 5. 글삭제
	void delete(FileBoardVO fileBoardVO, String uploadPath);
	
	// 댓글저장
	void insert(CommentVO commentVO);
	// 댓글수정
	void update(CommentVO commentVO);
	// 댓글 1개 삭제(idx)
	void deleteByIdx(int idx);
	// 댓글 전체삭제(ref) ---- 원본글을 삭제할때만 사용
	void deleteByRef(int ref);
	// 댓글 모두얻기
	List<CommentVO> selectList(int ref);
	// 댓글 개수
	int selectCount(int ref);
}
