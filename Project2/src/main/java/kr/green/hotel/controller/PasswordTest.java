package kr.green.hotel.controller;

import kr.green.hotel.service.MemberServiceImpl;

public class PasswordTest {
	public static void main(String[] args) {
		MemberServiceImpl memberService = new MemberServiceImpl();
		for(int i=0;i<15;i++) {
			System.out.println(memberService.makePassword(10));
		}
	}
}
