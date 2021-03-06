package kr.green.hotel.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import kr.green.hotel.service.FreeBoardService;
import kr.green.hotel.vo.CommVO;
import kr.green.hotel.vo.CommentVO;
import kr.green.hotel.vo.FreeBoardFileVO;
import kr.green.hotel.vo.FreeBoardVO;
import kr.green.hotel.vo.FreeCommentVO;
import kr.green.hotel.vo.PagingVO;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class FreeBoardController {
	@Autowired
	private FreeBoardService freeBoardService;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/board/listFree")
	// public String selectList(@ModelAttribute CommVO commVO, Model model) {
	// POST전송을 받기위한 방법
	public String selectList(@RequestParam Map<String, String> params, HttpServletRequest request,@ModelAttribute CommVO commVO, Model model) {
		// POST전송된것을 받으려면 RequestContextUtils.getInputFlashMap(request)로 맵이 존재하는지 판단해서
		// 있으면 POST처리를 하고 없으면 GET으로 받아서 처리를 한다.
		Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
		if(flashMap!=null) {
			params = (Map<String, String>) flashMap.get("map");
			commVO.setP(Integer.parseInt(params.get("p")));
			commVO.setS(Integer.parseInt(params.get("s")));
			commVO.setB(Integer.parseInt(params.get("b")));
		}
		PagingVO<FreeBoardVO> pv = freeBoardService.selectList(commVO);
		model.addAttribute("pv", pv);
		model.addAttribute("cv", commVO);
		return "listFree";
	}
	// 입력폼 띄우기
	@RequestMapping(value = "/board/insertFree")
	public String insertFree(@ModelAttribute CommVO commVO, Model model) {
		model.addAttribute("cv", commVO);
		return "insertFree";
	}
	// 저장하기
	@RequestMapping(value = "/board/insertOkFree", method = RequestMethod.GET)
	public String insertOkGet() {
		return "redirect:/board/listFree";
	}
	@RequestMapping(value = "/board/insertOkFree", method = RequestMethod.POST)
	public String insertOkPost(
			@ModelAttribute CommVO commVO,
			@ModelAttribute FreeBoardVO freeBoardVO, 
			MultipartHttpServletRequest request, Model model,
			RedirectAttributes redirectAttributes) { // redirect시 POST전송을 위해 RedirectAttributes 변수 추가
		// 일단 VO로 받고
		freeBoardVO.setIp(request.getRemoteAddr()); // 아이피 추가로 넣어주고 
		log.info("{}의 insertOkPost 호출 : {}", this.getClass().getName(), commVO + "\n" + freeBoardVO);

		// 넘어온 파일 처리를 하자
		List<FreeBoardFileVO> fileList = new ArrayList<>(); // 파일 정보를 저장할 리스트
		
		List<MultipartFile> multipartFiles = request.getFiles("upfile"); // 넘어온 파일 리스트
		if(multipartFiles!=null && multipartFiles.size()>0) {  // 파일이 있다면
			for(MultipartFile multipartFile : multipartFiles) {
				if(multipartFile!=null && multipartFile.getSize()>0 ) { // 현재 파일이 존재한다면
					FreeBoardFileVO freeBoardFileVO = new FreeBoardFileVO(); // 객체 생성하고
					// 파일 저장하고
					try {
						// 저장이름
						String realPath = request.getRealPath("upload");
						String saveName = UUID.randomUUID() + "_" + multipartFile.getOriginalFilename();
						// 저장
						File target = new File(realPath, saveName);
						FileCopyUtils.copy(multipartFile.getBytes(), target);
						// vo를 채우고
						freeBoardFileVO.setOriName(multipartFile.getOriginalFilename());
						freeBoardFileVO.setSaveName(saveName);
						// 리스트에 추가하고
						fileList.add(freeBoardFileVO); 
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		freeBoardVO.setFileList(fileList);
		// 서비스를 호출하여 저장을 수행한다.
		freeBoardService.insert(freeBoardVO);
		
		// redirect시 GET전송 하기
		// return "redirect:/board/list?p=1&s=" + commVO.getPageSize() + "&b=" + commVO.getBlockSize();
		// redirect시 POST전송 하기
		// Redirect시 POST전송 하려면 map에 넣어서 RedirectAttributes에 담아서 전송하면 된다.
		Map<String, String> map = new HashMap<>();
		map.put("p", "1");
		map.put("s", commVO.getPageSize() + "");
		map.put("b",commVO.getBlockSize() + "");
		redirectAttributes.addFlashAttribute("map", map);
		return "redirect:/board/listFree";
	}
	
	// 내용보기 : 글 1개를 읽어서 보여준다
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/board/viewFree")
	public String view(@RequestParam Map<String, String> params, HttpServletRequest request,@ModelAttribute CommVO commVO,Model model) {
		log.info("{}의 view호출 : {}", this.getClass().getName(), commVO);
		// POST전송된것을 받으려면 RequestContextUtils.getInputFlashMap(request)로 맵이 존재하는지 판단해서
		// 있으면 POST처리를 하고 없으면 GET으로 받아서 처리를 한다.
		Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
		if(flashMap!=null) {
			params = (Map<String, String>) flashMap.get("map");
			commVO.setP(Integer.parseInt(params.get("p")));
			commVO.setS(Integer.parseInt(params.get("s")));
			commVO.setB(Integer.parseInt(params.get("b")));
			commVO.setIdx(Integer.parseInt(params.get("idx")));
		}
		
		FreeBoardVO freeBoardVO = freeBoardService.selectByIdx(commVO.getIdx());
		model.addAttribute("fv", freeBoardVO);
		model.addAttribute("cv", commVO);
		return "viewFree";
	}
	// 수정하기
	@RequestMapping(value = "/board/updateFree",method = RequestMethod.GET)
	public String updateGet(@ModelAttribute CommVO commVO,Model model) {
		return "redirect:/board/listFree";
	}
	@RequestMapping(value = "/board/updateFree",method = RequestMethod.POST)
	public String updatePost(@ModelAttribute CommVO commVO,Model model) {
		FreeBoardVO freeBoardVO = freeBoardService.selectByIdx(commVO.getIdx());
		model.addAttribute("fv", freeBoardVO);
		model.addAttribute("cv", commVO);
		return "updateFree";
	}
	
	@RequestMapping(value = "/board/updateOkFree",method = RequestMethod.GET)
	public String updateOkGet(@ModelAttribute CommVO commVO,Model model) {
		return "redirect:/board/listFree";
	}
	@RequestMapping(value = "/board/updateOkFree",method = RequestMethod.POST)
	public String updateOkPost(@ModelAttribute CommVO commVO,
			@ModelAttribute FreeBoardVO freeBoardVO, 
			MultipartHttpServletRequest request, Model model,
			RedirectAttributes redirectAttributes) {
		// 일단 VO로 받고
		freeBoardVO.setIp(request.getRemoteAddr()); // 아이피 추가로 넣어주고 
		log.info("{}의 updateOkPost 호출 : {}", this.getClass().getName(), commVO + "\n" + freeBoardVO);

		// 넘어온 파일 처리를 하자
		List<FreeBoardFileVO> fileList = new ArrayList<>(); // 파일 정보를 저장할 리스트
		
		List<MultipartFile> multipartFiles = request.getFiles("upfile"); // 넘어온 파일 리스트
		if(multipartFiles!=null && multipartFiles.size()>0) {  // 파일이 있다면
			for(MultipartFile multipartFile : multipartFiles) {
				if(multipartFile!=null && multipartFile.getSize()>0 ) { // 현재 파일이 존재한다면
					FreeBoardFileVO freeBoardFileVO = new FreeBoardFileVO(); // 객체 생성하고
					// 파일 저장하고
					try {
						// 저장이름
						String realPath = request.getRealPath("upload");
						String saveName = UUID.randomUUID() + "_" + multipartFile.getOriginalFilename();
						// 저장
						File target = new File(realPath, saveName);
						FileCopyUtils.copy(multipartFile.getBytes(), target);
						// vo를 채우고
						freeBoardFileVO.setOriName(multipartFile.getOriginalFilename());
						freeBoardFileVO.setSaveName(saveName);
						// 리스트에 추가하고
						fileList.add(freeBoardFileVO); 
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		freeBoardVO.setFileList(fileList);
		// 삭제할 파일 번호를 받아서 삭제할 파일을 삭제해 주어야 한다.
		String[] delFiles = request.getParameterValues("delfile");
		// 서비스를 호출하여 저장을 수행한다.
		String realPath = request.getRealPath("upload");
		freeBoardService.update(freeBoardVO, delFiles, realPath);
		
		// redirect시 GET전송 하기
		// return "redirect:/board/list?p=1&s=" + commVO.getPageSize() + "&b=" + commVO.getBlockSize();
		// redirect시 POST전송 하기
		// Redirect시 POST전송 하려면 map에 넣어서 RedirectAttributes에 담아서 전송하면 된다.
		Map<String, String> map = new HashMap<>();
		map.put("p", commVO.getCurrentPage() + "");
		map.put("s", commVO.getPageSize() + "");
		map.put("b",commVO.getBlockSize() + "");
		map.put("idx",commVO.getIdx() + "");
		redirectAttributes.addFlashAttribute("map", map);
		return "redirect:/board/viewFree";
	}
	
	// 삭제하기
	@RequestMapping(value = "/board/deleteFree",method = RequestMethod.GET)
	public String deleteGet(@ModelAttribute CommVO commVO,Model model) {
		return "redirect:/board/listFree";
	}
	@RequestMapping(value = "/board/deleteFree",method = RequestMethod.POST)
	public String deletePost(@ModelAttribute CommVO commVO,Model model) {
		FreeBoardVO freeBoardVO = freeBoardService.selectByIdx(commVO.getIdx());
		model.addAttribute("fv", freeBoardVO);
		model.addAttribute("cv", commVO);
		return "deleteFree";
	}

	@RequestMapping(value = "/board/deleteOkFree",method = RequestMethod.GET)
	public String deleteOkGet(@ModelAttribute CommVO commVO,Model model) {
		return "redirect:/board/listFree";
	}
	@RequestMapping(value = "/board/deleteOkFree",method = RequestMethod.POST)
	public String deleteOkPost(@ModelAttribute CommVO commVO,
			@ModelAttribute FreeBoardVO freeBoardVO, 
			HttpServletRequest request,
			RedirectAttributes redirectAttributes) {
		// 일단 VO로 받고
		log.info("{}의 deleteOkPost 호출 : {}", this.getClass().getName(), commVO + "\n" + freeBoardVO);
		// 실제 경로 구하고
		String realPath = request.getRealPath("upload");
		// 서비스를 호출하여 삭제를 수행하고
		freeBoardService.delete(freeBoardVO, realPath);
		
		// redirect시 GET전송 하기
		// return "redirect:/board/list?p=1&s=" + commVO.getPageSize() + "&b=" + commVO.getBlockSize();
		// redirect시 POST전송 하기
		// Redirect시 POST전송 하려면 map에 넣어서 RedirectAttributes에 담아서 전송하면 된다.
		Map<String, String> map = new HashMap<>();
		map.put("p", commVO.getCurrentPage() + "");
		map.put("s", commVO.getPageSize() + "");
		map.put("b",commVO.getBlockSize() + "");
		redirectAttributes.addFlashAttribute("map", map);
		return "redirect:/board/listFree";
	}


	@RequestMapping(value = "/board/downloadFree")
	public ModelAndView download(@RequestParam HashMap<Object, Object> params, ModelAndView mv) {
		String ofileName = (String) params.get("of"); // 원본이름
		String sfileName = (String) params.get("sf"); // 저장이름
		mv.setViewName("downloadView");
		mv.addObject("ofileName", ofileName);
		mv.addObject("sfileName", sfileName);
		return mv;
	}
	
	// 섬머노트에서 이미지 업로드를 담당하는 메서드 : 파일을 업로드하고 이미지 이름을 리턴해주면된다.
	@RequestMapping(value = "/imageUploadFree", produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String imageUpload(HttpServletRequest request, MultipartFile file) {
		log.info("{}의 imageUpload 호출 : {}",this.getClass().getName(),request + "\n" + file);
		String filePath = "";
		String realPath = request.getRealPath("contentfile");
		// 파일은 MultipartFile 객체가 받아준다.
		if(file!=null && file.getSize()>0) { // 파일이 존재 한다면
			try {
				// 저장이름
				String saveName = UUID.randomUUID() + "_" + file.getOriginalFilename();
				// 저장
				File target = new File(realPath, saveName);
				FileCopyUtils.copy(file.getBytes(), target);
				filePath = request.getContextPath() + "\\contentfile\\" + 	saveName;			
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		log.info("{}의 imageUpload 리턴 : {}",this.getClass().getName(),filePath);
		return filePath;
	}
	
	// 댓글 내용보기
	@RequestMapping(value = "/board/freeCommentList")
	@ResponseBody
	public List<FreeCommentVO> freeCommentList(@RequestParam int ref) {
		log.info("{}의 freeCommentList 호출 : {}",this.getClass().getName(), ref);
		List<FreeCommentVO> list = freeBoardService.selectList(ref);
		
		log.info("{}의 freeCommentList 리턴 : {}",this.getClass().getName(), list);
		return list;
	}
	
	// 댓글 저장하기 
	@RequestMapping(value = "/board/freeCommentInsert")
	@ResponseBody
	public String freeCommentInsert(@ModelAttribute FreeCommentVO freeCommentVO, HttpServletRequest request) {
		freeCommentVO.setIp(request.getRemoteAddr());
		log.info("{}의 freeCommentInsert 호출 : {}",this.getClass().getName(),freeCommentVO);
		freeBoardService.insert(freeCommentVO);
		return null;
	}
	
	// 댓글 수정하기 
	@RequestMapping(value = "/board/freeCommentUpdate")
	@ResponseBody
	public String freeCommentUpdate(@ModelAttribute FreeCommentVO freeCommentVO, HttpServletRequest request) {
		freeCommentVO.setIp(request.getRemoteAddr());
		log.info("{}의 freeCommentUpdate 호출 : {}",this.getClass().getName(),freeCommentVO);
		freeBoardService.update(freeCommentVO);
		return null;
	}
 
	// 댓글 삭제하기 
	@RequestMapping(value = "/board/freeCommentDelete")
	@ResponseBody
	public void freeCommentDelete(@RequestParam int idx) {
		log.info("{}의 freeCommentDelete 호출 : {}",this.getClass().getName(),idx);
		freeBoardService.deleteByIdx(idx);
	}
}
