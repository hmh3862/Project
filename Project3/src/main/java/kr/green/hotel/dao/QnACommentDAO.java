package kr.green.hotel.dao;

import java.util.List;

import kr.green.hotel.vo.QnACommentVO;

public interface QnACommentDAO {
	//	<!-- 1. 지정 번호의 댓글이 몇개? -->
	int selectCount(int ref);
	//	<!-- 2. 지정 번호의 모든 댓글 얻기 -->
	List<QnACommentVO> selectList(int ref);
	//	<!-- 3. 댓글 저장 -->
	void insert(QnACommentVO qnACommentVO);
	//	<!-- 4. 댓글 수정 -->
	void update(QnACommentVO qnACommentVO);
	//	<!-- 5. 댓글 삭제 -->
	void deleteByIdx(int idx);
	//	<!-- 6. 지정 번호의 모든 댓글 삭제 -->
	void deleteByRef(int ref);
	//	<!-- 7. 지정 번호의 댓글 얻기 -->
	QnACommentVO selectByIdx(int ref);
}
