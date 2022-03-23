package kr.green.hotel.service;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.green.hotel.dao.FreeBoardDAO;
import kr.green.hotel.dao.FreeBoardFileDAO;
import kr.green.hotel.vo.CommVO;
import kr.green.hotel.vo.FileBoardVO;
import kr.green.hotel.vo.FreeBoardFileVO;
import kr.green.hotel.vo.FreeBoardVO;
import kr.green.hotel.vo.PagingVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@Service("freeBoardService")
public class FreeBoardServiceImpl implements FreeBoardService {
	
	@Autowired
	private FreeBoardDAO freeBoardDAO;
	@Autowired
	private FreeBoardFileDAO freeBoardFileDAO;
	
	@Override
	public PagingVO<FreeBoardVO> selectList(CommVO commVO) {
		log.info("{}의 selectList 호출 : {}", this.getClass().getName(), commVO);
		PagingVO<FreeBoardVO> pagingVO = null;
		try {
			// 전체 개수 구하기
			int totalCount = freeBoardDAO.selectCount();
			// 페이지 계산
			pagingVO = new PagingVO<>(commVO.getCurrentPage(), commVO.getPageSize(), commVO.getBlockSize(), totalCount);
			// 글을 읽어오기
			HashMap<String, Integer> map = new HashMap<String, Integer>();
			map.put("startNo", pagingVO.getStartNo() );
			map.put("endNo", pagingVO.getEndNo());
			List<FreeBoardVO> list = freeBoardDAO.selectList(map);
			// 해당글들의 첨부파일 정보를 넣어준다.
			if(list!=null && list.size()>0) {
				for(FreeBoardVO vo : list) {
					// 해당글의 첨부파일 목록을 가져온다.
					List<FreeBoardFileVO> fileList =  freeBoardFileDAO.selectList(vo.getIdx());
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
	public FreeBoardVO selectByIdx(int idx) {
		log.info("{}의 selectByIdx 호출 : {}", this.getClass().getName(), idx);
		FreeBoardVO freeBoardVO = freeBoardDAO.selectByIdx(idx); // 글 1개를 가져온다.
		// 그 글에 해당하는 첨부파일의 정보를 가져온다.
		if(freeBoardVO!= null) {
			List<FreeBoardFileVO> list = freeBoardFileDAO.selectList(idx);
			freeBoardVO.setFileList(list);
		}
		log.info("{}의 selectByIdx 리턴 : {}", this.getClass().getName(), freeBoardVO);
		return freeBoardVO;
	}
	@Override
	public void insert(FreeBoardVO freeBoardVO) {
		log.info("{}의 insert 호출 : {}", this.getClass().getName(), freeBoardVO);
		// 컨트롤러에 있는 파일을 저장하는 부분을 유틸리티 클래스로 빼주면 컨트롤러가 간단해 진다.
		if(freeBoardVO!=null) {
			// 1. 글을 조장한다.
			freeBoardDAO.insert(freeBoardVO);
			// 저장을 했으면 저장된 idx값을 얻어온다
			int ref = freeBoardDAO.selectSeq();
			// 2. 첨부 파일의 정보도 저장해 주어야 한다.
			List<FreeBoardFileVO> list = freeBoardVO.getFileList();
			if(list!=null && list.size()>0) {
				for(FreeBoardFileVO vo : list) {
					vo.setRef(ref); // 원본글번호
					freeBoardFileDAO.insert(vo);
				}
			}
		}
	}
	@Override
	public void delete(FreeBoardVO freeBoardVO, String uploadPath) {
		log.info("{}의 delete 호출 : {}", this.getClass().getName(), freeBoardVO + "\n" + uploadPath);
		FreeBoardVO dbVO = freeBoardDAO.selectByIdx(freeBoardVO.getIdx());
		if(dbVO!=null && dbVO.getPassword().equals(freeBoardVO.getPassword())) { // DB의 비번과 입력한 비번이 같은 경우에만
			// 글삭제
			freeBoardDAO.delete(freeBoardVO.getIdx());
			// 모든 첨부파일의 목록을 읽어온다.
			List<FreeBoardFileVO> list = freeBoardFileDAO.selectList(freeBoardVO.getIdx());
			if(list!=null && list.size()>0) {
				for(FreeBoardFileVO vo : list) {
					// DB 파일 삭제
					freeBoardFileDAO.deleteByIdx(vo.getIdx());
					// 실제 파일삭제
					File file = new File(uploadPath + File.separator + vo.getSaveName());
					file.delete();
				}
			}
		}
	}
	
	@Override
	public void update(FreeBoardVO freeBoardVO, String[] delFiles, String realPath) {
		log.info("{}의 update 호출 : {}", this.getClass().getName(), freeBoardVO + "\n" + Arrays.toString(delFiles) + "\n" + realPath);
		FreeBoardVO dbVO = freeBoardDAO.selectByIdx(freeBoardVO.getIdx());
		if(dbVO!=null && dbVO.getPassword().equals(freeBoardVO.getPassword())) { // DB의 비번과 입력한 비번이 같은 경우에만
			// 1. 글수정
			freeBoardDAO.update(freeBoardVO);
			// 2. 새롭게 첨부된 첨부 파일의 정보도 저장해 주어야 한다.
			List<FreeBoardFileVO> list = freeBoardVO.getFileList();
			if(list!=null && list.size()>0) {
				for(FreeBoardFileVO vo : list) {
					vo.setRef(freeBoardVO.getIdx()); // 원본글번호
					freeBoardFileDAO.insert(vo);
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
						FreeBoardFileVO freeBoardFileVO = freeBoardFileDAO.selectByIdx(idx);
						if(freeBoardFileVO!=null) {
							// 2. 실제 서버의 파일을 삭제해 주어야 한다.
							File file = new File(realPath + File.separator + freeBoardFileVO.getSaveName());
							file.delete(); // 실제 파일삭제
							freeBoardFileDAO.deleteByIdx(idx); // DB에서만 삭제된다.
						}
					}
				}
			}
		}
	}
}
