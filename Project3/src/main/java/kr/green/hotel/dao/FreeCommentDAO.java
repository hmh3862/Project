package kr.green.hotel.dao;

import java.util.List;

import kr.green.hotel.vo.FreeCommentVO;

public interface FreeCommentDAO {
	//	<!-- 1. 지정 번호의 댓글이 몇개? -->
	int selectCount(int ref);
	//	<!-- 2. 지정 번호의 모든 댓글 얻기 -->
	List<FreeCommentVO> selectList(int ref);
	//	<!-- 3. 댓글 저장 -->
	void insert(FreeCommentVO freeCommentVO);
	//	<!-- 4. 댓글 수정 -->
	void update(FreeCommentVO freeCommentVO);
	//	<!-- 5. 댓글 삭제 -->
	void deleteByIdx(int idx);
	//	<!-- 6. 지정 번호의 모든 댓글 삭제 -->
	void deleteByRef(int ref);
	//	<!-- 7. 지정 번호의 댓글 얻기 -->
	FreeCommentVO selectByIdx(int ref);
}
