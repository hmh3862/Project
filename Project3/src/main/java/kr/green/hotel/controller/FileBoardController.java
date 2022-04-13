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

import kr.green.hotel.service.FileBoardService;
import kr.green.hotel.vo.CommVO;
import kr.green.hotel.vo.CommentVO;
import kr.green.hotel.vo.FileBoardFileVO;
import kr.green.hotel.vo.FileBoardVO;
import kr.green.hotel.vo.PagingVO;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class FileBoardController {
	@Autowired
	private FileBoardService fileBoardService;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/board/listBoard")
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
		PagingVO<FileBoardVO> pv = fileBoardService.selectList(commVO);
		model.addAttribute("pv", pv);
		model.addAttribute("cv", commVO);
		return "listBoard";
	}
	// 입력폼 띄우기
	@RequestMapping(value = "/board/insertBoard")
	public String insertBoard(@ModelAttribute CommVO commVO, Model model) {
		model.addAttribute("cv", commVO);
		return "insertBoard";
	}
	// 저장하기
	@RequestMapping(value = "/board/insertOk", method = RequestMethod.GET)
	public String insertOkGet() {
		return "redirect:/board/listBoard";
	}
	@RequestMapping(value = "/board/insertOk", method = RequestMethod.POST)
	public String insertOkPost(
			@ModelAttribute CommVO commVO,
			@ModelAttribute FileBoardVO fileBoardVO, 
			@ModelAttribute CommentVO commentVO,
			MultipartHttpServletRequest request, Model model,
			RedirectAttributes redirectAttributes) { // redirect시 POST전송을 위해 RedirectAttributes 변수 추가
		// 일단 VO로 받고
		fileBoardVO.setIp(request.getRemoteAddr()); // 아이피 추가로 넣어주고 
		log.info("{}의 insertOkPost 호출 : {}", this.getClass().getName(), commVO + "\n" + fileBoardVO);

		// 넘어온 파일 처리를 하자
		List<FileBoardFileVO> fileList = new ArrayList<>(); // 파일 정보를 저장할 리스트
		
		List<MultipartFile> multipartFiles = request.getFiles("upfile"); // 넘어온 파일 리스트
		if(multipartFiles!=null && multipartFiles.size()>0) {  // 파일이 있다면
			for(MultipartFile multipartFile : multipartFiles) {
				if(multipartFile!=null && multipartFile.getSize()>0 ) { // 현재 파일이 존재한다면
					FileBoardFileVO fileBoardFileVO = new FileBoardFileVO(); // 객체 생성하고
					// 파일 저장하고
					try {
						// 저장이름
						String realPath = request.getRealPath("upload");
						String saveName = UUID.randomUUID() + "_" + multipartFile.getOriginalFilename();
						// 저장
						File target = new File(realPath, saveName);
						FileCopyUtils.copy(multipartFile.getBytes(), target);
						// vo를 채우고
						fileBoardFileVO.setOriName(multipartFile.getOriginalFilename());
						fileBoardFileVO.setSaveName(saveName);
						// 리스트에 추가하고
						fileList.add(fileBoardFileVO); 
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		fileBoardVO.setFileList(fileList);
		// 서비스를 호출하여 저장을 수행한다.
		fileBoardService.insert(fileBoardVO);
		
		// redirect시 GET전송 하기
		// return "redirect:/board/list?p=1&s=" + commVO.getPageSize() + "&b=" + commVO.getBlockSize();
		// redirect시 POST전송 하기
		// Redirect시 POST전송 하려면 map에 넣어서 RedirectAttributes에 담아서 전송하면 된다.
		Map<String, String> map = new HashMap<>();
		map.put("p", "1");
		map.put("s", commVO.getPageSize() + "");
		map.put("b",commVO.getBlockSize() + "");
		redirectAttributes.addFlashAttribute("map", map);
		return "redirect:/board/listBoard";
	}
	
	// 내용보기 : 글 1개를 읽어서 보여준다
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/board/viewBoard")
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
		
		FileBoardVO fileBoardVO = fileBoardService.selectByIdx(commVO.getIdx());

		model.addAttribute("fv", fileBoardVO);
		model.addAttribute("cv", commVO);
		return "viewBoard";
	}
	// 수정하기
	@RequestMapping(value = "/board/updateBoard",method = RequestMethod.GET)
	public String updateGet(@ModelAttribute CommVO commVO,Model model) {
		return "redirect:/board/listBoard";
	}
	@RequestMapping(value = "/board/updateBoard",method = RequestMethod.POST)
	public String updatePost(@ModelAttribute CommVO commVO,Model model) {
		FileBoardVO fileBoardVO = fileBoardService.selectByIdx(commVO.getIdx());
		model.addAttribute("fv", fileBoardVO);
		model.addAttribute("cv", commVO);
		return "updateBoard";
	}
	
	@RequestMapping(value = "/board/updateOk",method = RequestMethod.GET)
	public String updateOkGet(@ModelAttribute CommVO commVO,Model model) {
		return "redirect:/board/listBoard";
	}
	@RequestMapping(value = "/board/updateOk",method = RequestMethod.POST)
	public String updateOkPost(@ModelAttribute CommVO commVO,
			@ModelAttribute FileBoardVO fileBoardVO, 
			MultipartHttpServletRequest request, Model model,
			RedirectAttributes redirectAttributes) {
		// 일단 VO로 받고
		fileBoardVO.setIp(request.getRemoteAddr()); // 아이피 추가로 넣어주고 
		log.info("{}의 updateOkPost 호출 : {}", this.getClass().getName(), commVO + "\n" + fileBoardVO);

		// 넘어온 파일 처리를 하자
		List<FileBoardFileVO> fileList = new ArrayList<>(); // 파일 정보를 저장할 리스트
		
		List<MultipartFile> multipartFiles = request.getFiles("upfile"); // 넘어온 파일 리스트
		if(multipartFiles!=null && multipartFiles.size()>0) {  // 파일이 있다면
			for(MultipartFile multipartFile : multipartFiles) {
				if(multipartFile!=null && multipartFile.getSize()>0 ) { // 현재 파일이 존재한다면
					FileBoardFileVO fileBoardFileVO = new FileBoardFileVO(); // 객체 생성하고
					// 파일 저장하고
					try {
						// 저장이름
						String realPath = request.getRealPath("upload");
						String saveName = UUID.randomUUID() + "_" + multipartFile.getOriginalFilename();
						// 저장
						File target = new File(realPath, saveName);
						FileCopyUtils.copy(multipartFile.getBytes(), target);
						// vo를 채우고
						fileBoardFileVO.setOriName(multipartFile.getOriginalFilename());
						fileBoardFileVO.setSaveName(saveName);
						// 리스트에 추가하고
						fileList.add(fileBoardFileVO); 
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		fileBoardVO.setFileList(fileList);
		// 삭제할 파일 번호를 받아서 삭제할 파일을 삭제해 주어야 한다.
		String[] delFiles = request.getParameterValues("delfile");
		// 서비스를 호출하여 저장을 수행한다.
		String realPath = request.getRealPath("upload");
		fileBoardService.update(fileBoardVO, delFiles, realPath);
		
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
		return "redirect:/board/viewBoard";
	}
	
	// 삭제하기
	@RequestMapping(value = "/board/deleteBoard",method = RequestMethod.GET)
	public String deleteGet(@ModelAttribute CommVO commVO,Model model) {
		return "redirect:/board/listBoard";
	}
	@RequestMapping(value = "/board/deleteBoard",method = RequestMethod.POST)
	public String deletePost(@ModelAttribute CommVO commVO,Model model) {
		FileBoardVO fileBoardVO = fileBoardService.selectByIdx(commVO.getIdx());
		model.addAttribute("fv", fileBoardVO);
		model.addAttribute("cv", commVO);
		return "deleteBoard";
	}

	@RequestMapping(value = "/board/deleteOk",method = RequestMethod.GET)
	public String deleteOkGet(@ModelAttribute CommVO commVO,Model model) {
		return "redirect:/board/listBoard";
	}
	@RequestMapping(value = "/board/deleteOk",method = RequestMethod.POST)
	public String deleteOkPost(@ModelAttribute CommVO commVO,
			@ModelAttribute FileBoardVO fileBoardVO, 
			HttpServletRequest request,
			RedirectAttributes redirectAttributes) {
		// 일단 VO로 받고
		log.info("{}의 deleteOkPost 호출 : {}", this.getClass().getName(), commVO + "\n" + fileBoardVO);
		// 실제 경로 구하고
		String realPath = request.getRealPath("upload");
		// 서비스를 호출하여 삭제를 수행하고
		fileBoardService.delete(fileBoardVO, realPath);
		
		// redirect시 GET전송 하기
		// return "redirect:/board/list?p=1&s=" + commVO.getPageSize() + "&b=" + commVO.getBlockSize();
		// redirect시 POST전송 하기
		// Redirect시 POST전송 하려면 map에 넣어서 RedirectAttributes에 담아서 전송하면 된다.
		Map<String, String> map = new HashMap<>();
		map.put("p", commVO.getCurrentPage() + "");
		map.put("s", commVO.getPageSize() + "");
		map.put("b",commVO.getBlockSize() + "");
		redirectAttributes.addFlashAttribute("map", map);
		return "redirect:/board/listBoard";
	}

	@RequestMapping(value = "/board/download")
	public ModelAndView download(@RequestParam HashMap<Object, Object> params, ModelAndView mv) {
		String ofileName = (String) params.get("of"); // 원본이름
		String sfileName = (String) params.get("sf"); // 저장이름
		mv.setViewName("downloadView");
		mv.addObject("ofileName", ofileName);
		mv.addObject("sfileName", sfileName);
		return mv;
	}
	
	// 섬머노트에서 이미지 업로드를 담당하는 메서드 : 파일을 업로드하고 이미지 이름을 리턴해주면된다.
	@RequestMapping(value = "/imageUpload", produces = "text/plain;charset=UTF-8")
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
	@RequestMapping(value = "/board/commentList")
	@ResponseBody
	public List<CommentVO> commentList(@RequestParam int ref) {
		log.info("{}의 commentList 호출 : {}",this.getClass().getName(), ref);
		List<CommentVO> list = fileBoardService.selectList(ref);
		
		log.info("{}의 commentList 리턴 : {}",this.getClass().getName(), list);
		return list;
	}
	
	// 댓글 저장하기 
	@RequestMapping(value = "/board/commentInsert")
	@ResponseBody
	public String commentInsert(@ModelAttribute CommentVO commentVO, HttpServletRequest request) {
		commentVO.setIp(request.getRemoteAddr());
		log.info("{}의 commentInsert 호출 : {}",this.getClass().getName(),commentVO);
		fileBoardService.insert(commentVO);
		return null;
	}
	
	// 댓글 수정하기 
	@RequestMapping(value = "/board/commentUpdate")
	@ResponseBody
	public String commentUpdate(@ModelAttribute CommentVO commentVO, HttpServletRequest request) {
		commentVO.setIp(request.getRemoteAddr());
		log.info("{}의 commentUpdate 호출 : {}",this.getClass().getName(),commentVO);
		fileBoardService.update(commentVO);
		return null;
	}
 
	// 댓글 삭제하기 
	@RequestMapping(value = "/board/commentDelete")
	@ResponseBody
	public void commentDelete(@RequestParam int idx) {
		log.info("{}의 commentDelete 호출 : {}",this.getClass().getName(),idx);
		fileBoardService.deleteByIdx(idx);
	}
}
