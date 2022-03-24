package kr.green.hotel.service;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.green.hotel.dao.QnADAO;
import kr.green.hotel.dao.QnAFileDAO;
import kr.green.hotel.vo.CommVO;
import kr.green.hotel.vo.FileBoardFileVO;
import kr.green.hotel.vo.PagingVO;
import kr.green.hotel.vo.QnAFileVO;
import kr.green.hotel.vo.QnAVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@Service("qnaService")
public class QnAServiceImpl implements QnAService {
	
	@Autowired
	private QnADAO qnaDAO;
	@Autowired
	private QnAFileDAO qnaFileDAO;
	
	@Override
	public PagingVO<QnAVO> selectList(CommVO commVO) {
		log.info("{}의 selectList 호출 : {}", this.getClass().getName(), commVO);
		PagingVO<QnAVO> pagingVO = null;
		try {
			// 전체 개수 구하기
			int totalCount = qnaDAO.selectCount();
			// 페이지 계산
			pagingVO = new PagingVO<>(commVO.getCurrentPage(), commVO.getPageSize(), commVO.getBlockSize(), totalCount);
			// 글을 읽어오기
			HashMap<String, Integer> map = new HashMap<String, Integer>();
			map.put("startNo", pagingVO.getStartNo() );
			map.put("endNo", pagingVO.getEndNo());
			List<QnAVO> list = qnaDAO.selectList(map);
			// 해당글들의 첨부파일 정보를 넣어준다.
			if(list!=null && list.size()>0) {
				for(QnAVO vo : list) {
					// 해당글의 첨부파일 목록을 가져온다.
					List<QnAFileVO> fileList =  qnaFileDAO.selectList(vo.getIdx());
					// vo에 넣는다.
					vo.setFileList(fileList);
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
	public QnAVO selectByIdx(int idx) {
		log.info("{}의 selectByIdx 호출 : {}", this.getClass().getName(), idx);
		QnAVO qnaVO = qnaDAO.selectByIdx(idx); // 글 1개를 가져온다.
		// 그 글에 해당하는 첨부파일의 정보를 가져온다.
		if(qnaVO!= null) {
			List<QnAFileVO> list = qnaFileDAO.selectList(idx);
			qnaVO.setFileList(list);
		}
		log.info("{}의 selectByIdx 리턴 : {}", this.getClass().getName(), qnaVO);
		return qnaVO;
	}
	@Override
	public void insert(QnAVO qnaVO) {
		log.info("{}의 insert 호출 : {}", this.getClass().getName(), qnaVO);
		// 컨트롤러에 있는 파일을 저장하는 부분을 유틸리티 클래스로 빼주면 컨트롤러가 간단해 진다.
		if(qnaVO!=null) {
			// 1. 글을 저장한다.
			qnaDAO.insert(qnaVO);
			// 저장을 했으면 저장된 idx값을 얻어온다
			int ref = qnaDAO.selectSeq();
			// 2. 첨부 파일의 정보도 저장해 주어야 한다.
			List<QnAFileVO> list = qnaVO.getFileList();
			if(list!=null && list.size()>0) {
				for(QnAFileVO vo : list) {
					vo.setRef(ref); // 원본글번호
					qnaFileDAO.insert(vo);
				}
			}
		}
	}
	@Override
	public void delete(QnAVO qnaVO, String uploadPath) {
		log.info("{}의 delete 호출 : {}", this.getClass().getName(), qnaVO + "\n" + uploadPath);
		QnAVO dbVO = qnaDAO.selectByIdx(qnaVO.getIdx());
		if(dbVO!=null && dbVO.getPassword().equals(qnaVO.getPassword())) { // DB의 비번과 입력한 비번이 같은 경우에만
			// 글삭제
			qnaDAO.delete(qnaVO.getIdx());
			// 모든 첨부파일의 목록을 읽어온다.
			List<QnAFileVO> list = qnaFileDAO.selectList(qnaVO.getIdx());
			if(list!=null && list.size()>0) {
				for(QnAFileVO vo : list) {
					// DB 파일 삭제
					qnaFileDAO.deleteByIdx(vo.getIdx());
					// 실제 파일삭제
					File file = new File(uploadPath + File.separator + vo.getSaveName());
					file.delete();
				}
			}
		}
	}
	
	@Override
	public void update(QnAVO qnaVO, String[] delFiles, String realPath) {
		log.info("{}의 update 호출 : {}", this.getClass().getName(), qnaVO + "\n" + Arrays.toString(delFiles) + "\n" + realPath);
		QnAVO dbVO = qnaDAO.selectByIdx(qnaVO.getIdx());
		if(dbVO!=null && dbVO.getPassword().equals(qnaVO.getPassword())) { // DB의 비번과 입력한 비번이 같은 경우에만
			// 1. 글수정
			qnaDAO.update(qnaVO);
			// 2. 새롭게 첨부된 첨부 파일의 정보도 저장해 주어야 한다.
			List<QnAFileVO> list = qnaVO.getFileList();
			if(list!=null && list.size()>0) {
				for(QnAFileVO vo : list) {
					vo.setRef(qnaVO.getIdx()); // 원본글번호
					qnaFileDAO.insert(vo);
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
						QnAFileVO qnaFileVO = qnaFileDAO.selectByIdx(idx);
						if(qnaFileVO!=null) {
							// 2. 실제 서버의 파일을 삭제해 주어야 한다.
							File file = new File(realPath + File.separator + qnaFileVO.getSaveName());
							file.delete(); // 실제 파일삭제
							qnaFileDAO.deleteByIdx(idx); // DB에서만 삭제된다.
						}
					}
				}
			}
		}
	}
}
