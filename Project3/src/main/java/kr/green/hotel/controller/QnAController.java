package kr.green.hotel.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
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

import kr.green.hotel.service.QnAService;
import kr.green.hotel.vo.CommVO;
import kr.green.hotel.vo.PagingVO;
import kr.green.hotel.vo.QnAVO;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class QnAController {
	@Autowired
	private QnAService qnaService;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/board/listQnA")
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
		PagingVO<QnAVO> pv = qnaService.selectList(commVO);
		model.addAttribute("pv", pv);
		model.addAttribute("cv", commVO);
		return "listQnA";
	}
	// 입력폼 띄우기
	@RequestMapping(value = "/board/insertFormQnA")
	public String insertFormQnA(@ModelAttribute CommVO commVO, Model model) {
		model.addAttribute("cv", commVO);
		return "insertFormQnA";
	}
	// 저장하기
	@RequestMapping(value = "/board/insertOkQnA", method = RequestMethod.GET)
	public String insertOkGet() {
		return "redirect:/board/listQnA";
	}
	@RequestMapping(value = "/board/insertOkQnA", method = RequestMethod.POST)
	public String insertOkPost(
			@ModelAttribute CommVO commVO,
			@ModelAttribute QnAVO qnaVO, 
			MultipartHttpServletRequest request, Model model,
			RedirectAttributes redirectAttributes) { // redirect시 POST전송을 위해 RedirectAttributes 변수 추가
		// 일단 VO로 받고
		qnaVO.setIp(request.getRemoteAddr()); // 아이피 추가로 넣어주고 
		log.info("{}의 insertOkPost 호출 : {}", this.getClass().getName(), commVO + "\n" + qnaVO);

		// 서비스를 호출하여 저장을 수행한다.
		qnaService.insert(qnaVO);
		
		// redirect시 GET전송 하기
		// return "redirect:/board/list?p=1&s=" + commVO.getPageSize() + "&b=" + commVO.getBlockSize();
		// redirect시 POST전송 하기
		// Redirect시 POST전송 하려면 map에 넣어서 RedirectAttributes에 담아서 전송하면 된다.
		Map<String, String> map = new HashMap<>();
		map.put("p", "1");
		map.put("s", commVO.getPageSize() + "");
		map.put("b",commVO.getBlockSize() + "");
		redirectAttributes.addFlashAttribute("map", map);
		return "redirect:/board/listQnA";
	}
	
	// 내용보기 : 글 1개를 읽어서 보여준다
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/board/viewQnA")
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
		
		QnAVO qnaVO = qnaService.selectByIdx(commVO.getIdx());
		model.addAttribute("fv", qnaVO);
		model.addAttribute("cv", commVO);
		return "viewQnA";
	}
	// 수정하기
	@RequestMapping(value = "/board/updateQnA",method = RequestMethod.GET)
	public String updateGet(@ModelAttribute CommVO commVO,Model model) {
		return "redirect:/board/listQnA";
	}
	@RequestMapping(value = "/board/updateQnA",method = RequestMethod.POST)
	public String updatePost(@ModelAttribute CommVO commVO,Model model) {
		QnAVO qnaVO = qnaService.selectByIdx(commVO.getIdx());
		model.addAttribute("fv", qnaVO);
		model.addAttribute("cv", commVO);
		return "updateQnA";
	}
	
	@RequestMapping(value = "/board/updateOKQnA",method = RequestMethod.GET)
	public String updateOKGet(@ModelAttribute CommVO commVO,Model model) {
		return "redirect:/board/listQnA";
	}
	@RequestMapping(value = "/board/updateOKQnA",method = RequestMethod.POST)
	public String updateOKPost(@ModelAttribute CommVO commVO,
			@ModelAttribute QnAVO qnaVO, 
			MultipartHttpServletRequest request, Model model,
			RedirectAttributes redirectAttributes) {
		// 일단 VO로 받고
		qnaVO.setIp(request.getRemoteAddr()); // 아이피 추가로 넣어주고 
		log.info("{}의 updateOKPost 호출 : {}", this.getClass().getName(), commVO + "\n" + qnaVO);

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
		return "redirect:/board/viewQnA";
	}
	
	// 삭제하기
	@RequestMapping(value = "/board/deleteQnA",method = RequestMethod.GET)
	public String deleteGet(@ModelAttribute CommVO commVO,Model model) {
		return "redirect:/board/listQnA";
	}
	@RequestMapping(value = "/board/deleteQnA",method = RequestMethod.POST)
	public String deletePost(@ModelAttribute CommVO commVO,Model model) {
		QnAVO qnaVO = qnaService.selectByIdx(commVO.getIdx());
		model.addAttribute("fv", qnaVO);
		model.addAttribute("cv", commVO);
		return "deleteQnA";
	}

	@RequestMapping(value = "/board/deleteOKQnA",method = RequestMethod.GET)
	public String deleteOKGet(@ModelAttribute CommVO commVO,Model model) {
		return "redirect:/board/listQnA";
	}
	@RequestMapping(value = "/board/deleteOKQnA",method = RequestMethod.POST)
	public String deleteOKPost(@ModelAttribute CommVO commVO,
			@ModelAttribute QnAVO qnaVO, 
			HttpServletRequest request,
			RedirectAttributes redirectAttributes) {
		// 일단 VO로 받고
		log.info("{}의 deleteOKPost 호출 : {}", this.getClass().getName(), commVO + "\n" + qnaVO);
		// 실제 경로 구하고
		String realPath = request.getRealPath("upload");
		// 서비스를 호출하여 삭제를 수행하고
		qnaService.delete(qnaVO, realPath);
		
		// redirect시 GET전송 하기
		// return "redirect:/board/list?p=1&s=" + commVO.getPageSize() + "&b=" + commVO.getBlockSize();
		// redirect시 POST전송 하기
		// Redirect시 POST전송 하려면 map에 넣어서 RedirectAttributes에 담아서 전송하면 된다.
		Map<String, String> map = new HashMap<>();
		map.put("p", commVO.getCurrentPage() + "");
		map.put("s", commVO.getPageSize() + "");
		map.put("b",commVO.getBlockSize() + "");
		redirectAttributes.addFlashAttribute("map", map);
		return "redirect:/board/listQnA";
	}


	@RequestMapping(value = "/board/downloadQnA")
	public ModelAndView download(@RequestParam HashMap<Object, Object> params, ModelAndView mv) {
		String ofileName = (String) params.get("of"); // 원본이름
		String sfileName = (String) params.get("sf"); // 저장이름
		mv.setViewName("downloadView");
		mv.addObject("ofileName", ofileName);
		mv.addObject("sfileName", sfileName);
		return mv;
	}
	
	// 섬머노트에서 이미지 업로드를 담당하는 메서드 : 파일을 업로드하고 이미지 이름을 리턴해주면된다.
	@RequestMapping(value = "/imageUploadQnA", produces = "text/plain;charset=UTF-8")
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
