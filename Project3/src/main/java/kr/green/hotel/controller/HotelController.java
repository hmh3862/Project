package kr.green.hotel.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import kr.green.hotel.vo.HotelVO;

@RestController
public class HotelController {

	@RequestMapping(value = "/rest/hotel.json", produces = {MediaType.APPLICATION_JSON_VALUE})
	public List<HotelVO> list(){
		List<HotelVO> list = null;
		try{
			File file = new ClassPathResource("2015.json").getFile();
			System.out.println(file.getAbsolutePath());
			Reader reader = new FileReader(file);
			Gson gson = new Gson();
			list = gson.fromJson(reader, new TypeToken<List<HotelVO>>() {}.getType());
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		};
		System.out.println(list);
		return list;
	}
}
