package kr.green.hotel.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class HotelVO {
	private String SIGUN_NM;
	private String BIZPLC_NM;
	private String LOCPLC_FACLT_TELNO_DTLS;
	private String ROADNM_ZIPNO;
	private String REFINE_ROADNM_ADDR;
}
