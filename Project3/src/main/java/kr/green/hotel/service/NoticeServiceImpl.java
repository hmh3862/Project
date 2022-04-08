package kr.green.hotel.service;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.green.hotel.dao.NoticeCommentDAO;
import kr.green.hotel.dao.NoticeDAO;
import kr.green.hotel.dao.NoticeFileDAO;
import kr.green.hotel.vo.CommVO;
import kr.green.hotel.vo.CommentVO;
import kr.green.hotel.vo.NoticeCommentVO;
import kr.green.hotel.vo.NoticeFileVO;
import kr.green.hotel.vo.NoticeVO;
import kr.green.hotel.vo.PagingVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@Service("noticeService")
public class NoticeServiceImpl implements NoticeService {
	
	@Autowired
	private NoticeDAO noticeDAO;
	@Autowired
	private NoticeFileDAO noticeFileDAO;
	@Autowired
	private NoticeCommentDAO noticeCommentDAO;
	
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
			// 해당글들의 첨부파일 정보를 넣어준다.
			if(list!=null && list.size()>0) {
				for(NoticeVO vo : list) {
					// 해당글의 첨부파일 목록을 가져온다.
					List<NoticeFileVO> fileList =  noticeFileDAO.selectList(vo.getIdx());
					// vo에 넣는다.
					vo.setFileList(fileList);
					vo.setNoticeCommentCount(noticeCommentDAO.selectCount(vo.getIdx()));
				}
			}
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
		// 그 글에 해당하는 첨부파일의 정보를 가져온다.
		if(noticeVO!= null) {
			List<NoticeFileVO> list = noticeFileDAO.selectList(idx);
			noticeVO.setFileList(list);
		}
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
			// 2. 첨부 파일의 정보도 저장해 주어야 한다.
			List<NoticeFileVO> list = noticeVO.getFileList();
			if(list!=null && list.size()>0) {
				for(NoticeFileVO vo : list) {
					vo.setRef(ref); // 원본글번호
					noticeFileDAO.insert(vo);
				}
			}
		}
	}
	@Override
	public void delete(NoticeVO noticeVO, String uploadPath) {
		log.info("{}의 delete 호출 : {}", this.getClass().getName(), noticeVO + "\n" + uploadPath);
		NoticeVO dbVO = noticeDAO.selectByIdx(noticeVO.getIdx());
		if(dbVO!=null && dbVO.getPassword().equals(noticeVO.getPassword())) { // DB의 비번과 입력한 비번이 같은 경우에만
			// 글삭제
			noticeDAO.delete(noticeVO.getIdx());
			// 모든 첨부파일의 목록을 읽어온다.
			List<NoticeFileVO> list = noticeFileDAO.selectList(noticeVO.getIdx());
			if(list!=null && list.size()>0) {
				for(NoticeFileVO vo : list) {
					// DB 파일 삭제
					noticeFileDAO.deleteByIdx(vo.getIdx());
					// 실제 파일삭제
					File file = new File(uploadPath + File.separator + vo.getSaveName());
					file.delete();
				}
			}
		}
	}
	
	@Override
	public void update(NoticeVO noticeVO, String[] delFiles, String realPath) {
		log.info("{}의 update 호출 : {}", this.getClass().getName(), noticeVO + "\n" + Arrays.toString(delFiles) + "\n" + realPath);
		NoticeVO dbVO = noticeDAO.selectByIdx(noticeVO.getIdx());
		if(dbVO!=null && dbVO.getPassword().equals(noticeVO.getPassword())) { // DB의 비번과 입력한 비번이 같은 경우에만
			// 1. 글수정
			noticeDAO.update(noticeVO);
			// 2. 새롭게 첨부된 첨부 파일의 정보도 저장해 주어야 한다.
			List<NoticeFileVO> list = noticeVO.getFileList();
			if(list!=null && list.size()>0) {
				for(NoticeFileVO vo : list) {
					vo.setRef(noticeVO.getIdx()); // 원본글번호
					noticeFileDAO.insert(vo);
				}
			} 
			// 3, 이미 첨부되었던 파일 삭제
			log.info("{}의 update delFiles : {}", this.getClass().getName(), delFiles);
			if(delFiles!=null && delFiles.length>0) {
				for(String t : delFiles ) {
					int idx = Integer.parseInt(t);
					if(idx>0) {
						// 실제 파일을 삭제하려면
						// 1. 해당 글번호의 글을 읽어와서
						NoticeFileVO noticeFileVO = noticeFileDAO.selectByIdx(idx);
						if(noticeFileVO!=null) {
							// 2. 실제 서버의 파일을 삭제해 주어야 한다.
							File file = new File(realPath + File.separator + noticeFileVO.getSaveName());
							file.delete(); // 실제 파일삭제
							noticeFileDAO.deleteByIdx(idx); // DB에서만 삭제된다.
						}
					}
				}
			}
		}
	}
	@Override
	public void insert(NoticeCommentVO noticeCommentVO) {
		log.debug("insert 호출 : " + noticeCommentVO);
		if(noticeCommentVO!=null) {
			noticeCommentDAO.insert(noticeCommentVO);
		}
	}
	@Override
	public void update(NoticeCommentVO noticeCommentVO) {
		log.debug("update 호출 : " + noticeCommentVO);
		if(noticeCommentVO!=null) {
			NoticeCommentVO dbVO = noticeCommentDAO.selectByIdx(noticeCommentVO.getIdx());
			if(dbVO!=null && dbVO.getPassword().equals(noticeCommentVO.getPassword())) {
				noticeCommentDAO.update(noticeCommentVO);
			}
		}
	}
	@Override
	public void deleteByIdx(int idx) {
		log.debug("deleteByIdx 호출 : " + idx);
		noticeCommentDAO.deleteByIdx(idx);
	}
	@Override
	public void deleteByRef(int ref) {
		log.debug("deleteByRef 호출 : " + ref);
		noticeCommentDAO.deleteByRef(ref);
	}
	@Override
	public List<NoticeCommentVO> selectList(int ref) {
		log.debug("selectList 호출 : " + ref);
		List<NoticeCommentVO> commentList = noticeCommentDAO.selectList(ref);
		if(commentList!=null && commentList.size()>0) {
			for(NoticeCommentVO vo : commentList) {
			}
		}
		log.debug("selectList 리턴 : " + commentList);
		return commentList;
	}
	@Override
	public int selectCount(int ref) {
		log.debug("selectCount 호출 : " + ref);
		int count = noticeCommentDAO.selectCount(ref);
		
		log.debug("selectCount 리턴 : " + count);
		return count;
	}
}
