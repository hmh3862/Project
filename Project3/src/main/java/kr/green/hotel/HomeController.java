package kr.green.hotel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import kr.green.hotel.vo.HotelVO;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, @RequestParam(required = false) String area, @RequestParam(required = false) String img, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		String formattedDate = dateFormat.format(date);
		model.addAttribute("serverTime", formattedDate );
		model.addAttribute("user", getPrincipal() );
		
		if(area==null) area="포천시";
		if(img==null) img="m008o_6";
		model.addAttribute("area", area );
		model.addAttribute("img", img );
		
		List<HotelVO> list = null;
		List<HotelVO> resultList = new ArrayList<HotelVO>();
		try{
			File file = new ClassPathResource("hotelList.json").getFile();
			System.out.println(file.getAbsolutePath());
			Reader reader = new FileReader(file);
			Gson gson = new Gson();
			list = gson.fromJson(reader, new TypeToken<List<HotelVO>>() {}.getType());
			reader.close();
			
			// 전체 정보중에서 필요한 지역만 찾아서 넣는다.
			if(!area.equals("전체")) {
				for(HotelVO vo : list) {
					if(vo.getSIGUN_NM().equals(area)) {
						resultList.add(vo);
					}
				}
			}else {
				resultList = list;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		};
		System.out.println(list);
		model.addAttribute("list",resultList);
		return "home";
	}
	
	// 권한이 없는 페이지에 접근시 처리할 내용
	@RequestMapping(value = "/403")
	public String page403(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			System.out.println("현재 로그인 정보 : " + userDetails);
			model.addAttribute("username", userDetails.getUsername());
		}
		return "403";
	}

	// 인증 정보를 시큐리트에서 얻어내는 메서드
	private String getPrincipal(){
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            userName = ((UserDetails)principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }
}
