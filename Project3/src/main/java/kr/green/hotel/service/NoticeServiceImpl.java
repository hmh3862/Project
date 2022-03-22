package kr.green.hotel.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.green.hotel.dao.NoticeDAO;
import kr.green.hotel.vo.CommVO;
import kr.green.hotel.vo.NoticeVO;
import kr.green.hotel.vo.PagingVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@Service("noticeService")
public class NoticeServiceImpl implements NoticeService {
	
	@Autowired
	private NoticeDAO noticeDAO;
	
	@Override
	public PagingVO<NoticeVO> selectList(CommVO commVO) {
		log.info("{}의 selectList 호출 : {}", this.getClass().getName(), commVO);
		PagingVO<NoticeVO> pagingVO = null;
		try {
			// 전체 개수 구하기
			int totalCount = noticeDAO.selectCount();
			// 페이지 계산
			pagingVO = new PagingVO<>(commVO.getCurrentPage(), commVO.getPageSize(), commVO.getBlockSize(), totalCount);
			// 글을 읽어오기
			HashMap<String, Integer> map = new HashMap<String, Integer>();
			map.put("startNo", pagingVO.getStartNo() );
			map.put("endNo", pagingVO.getEndNo());
			List<NoticeVO> list = noticeDAO.selectList(map);
			// 완성된 리스트를 페이징 객체에 넣는다.
			pagingVO.setList(list);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return pagingVO;
	}
	@Override
	public NoticeVO selectByIdx(int idx) {
		log.info("{}의 selectByIdx 호출 : {}", this.getClass().getName(), idx);
		NoticeVO noticeVO = noticeDAO.selectByIdx(idx); // 글 1개를 가져온다.
		log.info("{}의 selectByIdx 리턴 : {}", this.getClass().getName(), noticeVO);
		return noticeVO;
	}
	@Override
	public void insert(NoticeVO noticeVO) {
		log.info("{}의 insert 호출 : {}", this.getClass().getName(), noticeVO);
		// 컨트롤러에 있는 파일을 저장하는 부분을 유틸리티 클래스로 빼주면 컨트롤러가 간단해 진다.
		if(noticeVO!=null) {
			// 1. 글을 저장한다.
			noticeDAO.insert(noticeVO);
			// 저장을 했으면 저장된 idx값을 얻어온다
			int ref = noticeDAO.selectSeq();
		}
	}
	@Override
	public void delete(NoticeVO noticeVO, String uploadPath) {
		log.info("{}의 update 호출 : {}", this.getClass().getName(), noticeVO + "\n" + uploadPath);
		NoticeVO dbVO = noticeDAO.selectByIdx(noticeVO.getIdx());
		if(dbVO!=null && dbVO.getPassword().equals(noticeVO.getPassword())) { // DB의 비번과 입력한 비번이 같은 경우에만
			// 글삭제
			noticeDAO.delete(noticeVO.getIdx());
		}
	}
	
	@Override
	public void update(NoticeVO noticeVO, String[] delFiles, String realPath) {
		log.info("{}의 update 호출 : {}", this.getClass().getName(), noticeVO + "\n" + Arrays.toString(delFiles) + "\n" + realPath);
		NoticeVO dbVO = noticeDAO.selectByIdx(noticeVO.getIdx());
		if(dbVO!=null && dbVO.getPassword().equals(noticeVO.getPassword())) { // DB의 비번과 입력한 비번이 같은 경우에만
			// 1. 글수정
			noticeDAO.update(noticeVO);
		}
	}
}
