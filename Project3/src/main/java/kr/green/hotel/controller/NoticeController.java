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

import kr.green.hotel.service.NoticeService;
import kr.green.hotel.vo.CommNoticeVO;
import kr.green.hotel.vo.NoticeFileVO;
import kr.green.hotel.vo.NoticeVO;
import kr.green.hotel.vo.PagingVO;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class NoticeController {
	@Autowired
	private NoticeService noticeService;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/board/listNotice")
	// public String selectList(@ModelAttribute CommVO commVO, Model model) {
	// POST전송을 받기위한 방법
	public String selectList(@RequestParam Map<String, String> params, HttpServletRequest request,@ModelAttribute CommNoticeVO commNoticeVO, Model model) {
		// POST전송된것을 받으려면 RequestContextUtils.getInputFlashMap(request)로 맵이 존재하는지 판단해서
		// 있으면 POST처리를 하고 없으면 GET으로 받아서 처리를 한다.
		Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
		if(flashMap!=null) {
			params = (Map<String, String>) flashMap.get("map");
			commNoticeVO.setP(Integer.parseInt(params.get("p")));
			commNoticeVO.setS(Integer.parseInt(params.get("s")));
			commNoticeVO.setB(Integer.parseInt(params.get("b")));
		}
		PagingVO<NoticeVO> pv = noticeService.selectList(commNoticeVO);
		model.addAttribute("pv", pv);
		model.addAttribute("cv", commNoticeVO);
		return "listNotice";
	}
	// 입력폼 띄우기
	@RequestMapping(value = "/board/insertFormNotice")
	public String insertFormNotice(@ModelAttribute CommNoticeVO commNoticeVO, Model model) {
		model.addAttribute("cv", commNoticeVO);
		return "insertFormNotice";
	}
	// 저장하기
	@RequestMapping(value = "/board/insertOkNotice", method = RequestMethod.GET)
	public String insertOkGet() {
		return "redirect:/board/listNotice";
	}
	@RequestMapping(value = "/board/insertOkNotice", method = RequestMethod.POST)
	public String insertOkPost(
			@ModelAttribute CommNoticeVO commNoticeVO,
			@ModelAttribute NoticeVO noticeVO, 
			MultipartHttpServletRequest request, Model model,
			RedirectAttributes redirectAttributes) { // redirect시 POST전송을 위해 RedirectAttributes 변수 추가
		// 일단 VO로 받고
		noticeVO.setIp(request.getRemoteAddr()); // 아이피 추가로 넣어주고 
		log.info("{}의 insertOkPost 호출 : {}", this.getClass().getName(), commNoticeVO + "\n" + noticeVO);
		
		// 넘어온 파일 처리를 하자
		List<NoticeFileVO> fileList = new ArrayList<>(); // 파일 정보를 저장할 리스트
		
		List<MultipartFile> multipartFiles = request.getFiles("upfile"); // 넘어온 파일 리스트
		if(multipartFiles!=null && multipartFiles.size()>0) {  // 파일이 있다면
			for(MultipartFile multipartFile : multipartFiles) {
				if(multipartFile!=null && multipartFile.getSize()>0 ) { // 현재 파일이 존재한다면
					NoticeFileVO noticeFileVO = new NoticeFileVO(); // 객체 생성하고
					// 파일 저장하고
					try {
						// 저장이름
						String realPath = request.getRealPath("upload");
						String saveName = UUID.randomUUID() + "_" + multipartFile.getOriginalFilename();
						// 저장
						File target = new File(realPath, saveName);
						FileCopyUtils.copy(multipartFile.getBytes(), target);
						// vo를 채우고
						noticeFileVO.setOriName(multipartFile.getOriginalFilename());
						noticeFileVO.setSaveName(saveName);
						// 리스트에 추가하고
						fileList.add(noticeFileVO); 
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		noticeVO.setFileList(fileList);
		// 서비스를 호출하여 저장을 수행한다.
		noticeService.insert(noticeVO);
		
		// redirect시 GET전송 하기
		// return "redirect:/board/list?p=1&s=" + commVO.getPageSize() + "&b=" + commVO.getBlockSize();
		// redirect시 POST전송 하기
		// Redirect시 POST전송 하려면 map에 넣어서 RedirectAttributes에 담아서 전송하면 된다.
		Map<String, String> map = new HashMap<>();
		map.put("p", "1");
		map.put("s", commNoticeVO.getPageSize() + "");
		map.put("b",commNoticeVO.getBlockSize() + "");
		redirectAttributes.addFlashAttribute("map", map);
		return "redirect:/board/listNotice";
	}
	
	// 내용보기 : 글 1개를 읽어서 보여준다
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/board/viewNotice")
	public String view(@RequestParam Map<String, String> params, HttpServletRequest request,@ModelAttribute CommNoticeVO commNoticeVO,Model model) {
		log.info("{}의 view호출 : {}", this.getClass().getName(), commNoticeVO);
		// POST전송된것을 받으려면 RequestContextUtils.getInputFlashMap(request)로 맵이 존재하는지 판단해서
		// 있으면 POST처리를 하고 없으면 GET으로 받아서 처리를 한다.
		Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
		if(flashMap!=null) {
			params = (Map<String, String>) flashMap.get("map");
			commNoticeVO.setP(Integer.parseInt(params.get("p")));
			commNoticeVO.setS(Integer.parseInt(params.get("s")));
			commNoticeVO.setB(Integer.parseInt(params.get("b")));
			commNoticeVO.setIdx(Integer.parseInt(params.get("idx")));
		}
		
		NoticeVO noticeVO = noticeService.selectByIdx(commNoticeVO.getIdx());
		model.addAttribute("fv", noticeVO);
		model.addAttribute("cv", commNoticeVO);
		return "viewNotice";
	}
	// 수정하기
	@RequestMapping(value = "/board/updateNotice",method = RequestMethod.GET)
	public String updateGet(@ModelAttribute CommNoticeVO commNoticeVO,Model model) {
		return "redirect:/board/listNotice";
	}
	@RequestMapping(value = "/board/updateNotice",method = RequestMethod.POST)
	public String updatePost(@ModelAttribute CommNoticeVO commNoticeVO,Model model) {
		NoticeVO noticeVO = noticeService.selectByIdx(commNoticeVO.getIdx());
		model.addAttribute("fv", noticeVO);
		model.addAttribute("cv", commNoticeVO);
		return "updateNotice";
	}
	
	@RequestMapping(value = "/board/updateOKNotice",method = RequestMethod.GET)
	public String updateOKGet(@ModelAttribute CommNoticeVO commNoticeVO,Model model) {
		return "redirect:/board/listNotice";
	}
	@RequestMapping(value = "/board/updateOKNotice",method = RequestMethod.POST)
	public String updateOKPost(@ModelAttribute CommNoticeVO commNoticeVO,
			@ModelAttribute NoticeVO noticeVO, 
			MultipartHttpServletRequest request, Model model,
			RedirectAttributes redirectAttributes) {
		// 일단 VO로 받고
		noticeVO.setIp(request.getRemoteAddr()); // 아이피 추가로 넣어주고 
		log.info("{}의 updateOKPost 호출 : {}", this.getClass().getName(), commNoticeVO + "\n" + noticeVO);

		// 넘어온 파일 처리를 하자
		List<NoticeFileVO> fileList = new ArrayList<>(); // 파일 정보를 저장할 리스트
		
		List<MultipartFile> multipartFiles = request.getFiles("upfile"); // 넘어온 파일 리스트
		if(multipartFiles!=null && multipartFiles.size()>0) {  // 파일이 있다면
			for(MultipartFile multipartFile : multipartFiles) {
				if(multipartFile!=null && multipartFile.getSize()>0 ) { // 현재 파일이 존재한다면
					NoticeFileVO noticeFileVO = new NoticeFileVO(); // 객체 생성하고
					// 파일 저장하고
					try {
						// 저장이름
						String realPath = request.getRealPath("upload");
						String saveName = UUID.randomUUID() + "_" + multipartFile.getOriginalFilename();
						// 저장
						File target = new File(realPath, saveName);
						FileCopyUtils.copy(multipartFile.getBytes(), target);
						// vo를 채우고
						noticeFileVO.setOriName(multipartFile.getOriginalFilename());
						noticeFileVO.setSaveName(saveName);
						// 리스트에 추가하고
						fileList.add(noticeFileVO); 
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		noticeVO.setFileList(fileList);
		// 삭제할 파일 번호를 받아서 삭제할 파일을 삭제해 주어야 한다.
		String[] delFiles = request.getParameterValues("delfile");
		// 서비스를 호출하여 저장을 수행한다.
		String realPath = request.getRealPath("upload");
		noticeService.update(noticeVO, delFiles, realPath);
		
		// redirect시 GET전송 하기
		// return "redirect:/board/list?p=1&s=" + commVO.getPageSize() + "&b=" + commVO.getBlockSize();
		// redirect시 POST전송 하기
		// Redirect시 POST전송 하려면 map에 넣어서 RedirectAttributes에 담아서 전송하면 된다.
		Map<String, String> map = new HashMap<>();
		map.put("p", commNoticeVO.getCurrentPage() + "");
		map.put("s", commNoticeVO.getPageSize() + "");
		map.put("b",commNoticeVO.getBlockSize() + "");
		map.put("idx",commNoticeVO.getIdx() + "");
		redirectAttributes.addFlashAttribute("map", map);
		return "redirect:/board/viewNotice";
	}
	
	// 삭제하기
	@RequestMapping(value = "/board/deleteNotice",method = RequestMethod.GET)
	public String deleteGet(@ModelAttribute CommNoticeVO commNoticeVO,Model model) {
		return "redirect:/board/listNotice";
	}
	@RequestMapping(value = "/board/deleteNotice",method = RequestMethod.POST)
	public String deletePost(@ModelAttribute CommNoticeVO commNoticeVO,Model model) {
		NoticeVO noticeVO = noticeService.selectByIdx(commNoticeVO.getIdx());
		model.addAttribute("fv", noticeVO);
		model.addAttribute("cv", commNoticeVO);
		return "deleteNotice";
	}

	@RequestMapping(value = "/board/deleteOKNotice",method = RequestMethod.GET)
	public String deleteOKGet(@ModelAttribute CommNoticeVO commNoticeVO,Model model) {
		return "redirect:/board/listNotice";
	}
	@RequestMapping(value = "/board/deleteOKNotice",method = RequestMethod.POST)
	public String deleteOKPost(@ModelAttribute CommNoticeVO commNoticeVO,
			@ModelAttribute NoticeVO noticeVO, 
			HttpServletRequest request,
			RedirectAttributes redirectAttributes) {
		// 일단 VO로 받고
		log.info("{}의 deleteOKPost 호출 : {}", this.getClass().getName(), commNoticeVO + "\n" + noticeVO);
		// 실제 경로 구하고
		String realPath = request.getRealPath("upload");
		// 서비스를 호출하여 삭제를 수행하고
		noticeService.delete(noticeVO, realPath);
		
		// redirect시 GET전송 하기
		// return "redirect:/board/list?p=1&s=" + commVO.getPageSize() + "&b=" + commVO.getBlockSize();
		// redirect시 POST전송 하기
		// Redirect시 POST전송 하려면 map에 넣어서 RedirectAttributes에 담아서 전송하면 된다.
		Map<String, String> map = new HashMap<>();
		map.put("p", commNoticeVO.getCurrentPage() + "");
		map.put("s", commNoticeVO.getPageSize() + "");
		map.put("b",commNoticeVO.getBlockSize() + "");
		redirectAttributes.addFlashAttribute("map", map);
		return "redirect:/board/listNotice";
	}


	@RequestMapping(value = "/board/downloadNotice")
	public ModelAndView download(@RequestParam HashMap<Object, Object> params, ModelAndView mv) {
		String ofileName = (String) params.get("of"); // 원본이름
		String sfileName = (String) params.get("sf"); // 저장이름
		mv.setViewName("downloadView");
		mv.addObject("ofileName", ofileName);
		mv.addObject("sfileName", sfileName);
		return mv;
	}
	
	// 섬머노트에서 이미지 업로드를 담당하는 메서드 : 파일을 업로드하고 이미지 이름을 리턴해주면된다.
	@RequestMapping(value = "/imageUploadNotice", produces = "text/plain;charset=UTF-8")
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
	
}
