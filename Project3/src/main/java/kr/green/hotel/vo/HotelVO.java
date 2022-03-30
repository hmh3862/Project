package kr.green.hotel.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class HotelVO {
	private String city;
	private String name;
	private String phone;
	private String zipcode;
	private String address;
}
