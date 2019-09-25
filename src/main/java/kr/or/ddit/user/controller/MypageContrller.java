package kr.or.ddit.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MypageContrller {
	@RequestMapping("{user_type}/mypage/mypageHome")
	public String mypageHome(@PathVariable("user_type") String user_type){
		return user_type +"/mypage/mypageHome";
	}
}
