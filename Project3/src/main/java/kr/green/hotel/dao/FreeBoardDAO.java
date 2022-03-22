package kr.green.hotel.dao;

import java.util.HashMap;
import java.util.List;

import kr.green.hotel.vo.FreeBoardVO;

public interface FreeBoardDAO {
	// <!-- 1. 개수얻기 -->
	int selectCount();
	// <!-- 2. 1개 얻기 -->
	FreeBoardVO selectByIdx(int idx);
	// <!-- 3. 1페이지 얻기 -->
	List<FreeBoardVO> selectList(HashMap<String, Integer> map);
	// <!-- 4. 저장하기 -->
	void insert(FreeBoardVO vo);
	// <!-- 5. 수정하기 -->
	void update(FreeBoardVO vo);
	// <!-- 6. 삭제하기 -->
	void delete(int idx);
	// <!-- 7. 현재 저장한 idx값 알아내기 (현재 Sequence값) -->
	int selectSeq();
}
