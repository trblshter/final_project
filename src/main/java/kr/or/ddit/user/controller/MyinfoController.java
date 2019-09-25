package kr.or.ddit.user.controller;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.exception.UserNotFoundException;
import kr.or.ddit.user.service.IAuthenticateService;
import kr.or.ddit.user.service.IUserService;
import kr.or.ddit.utils.SecurityUtils;
import kr.or.ddit.vo.UserVO;

@Controller
@RequestMapping("{user_type}/mypage/myinfo")
public class MyinfoController {
	@Inject
	IAuthenticateService authService;
	
	@Inject
	IUserService userService;
	
	@PostMapping(value = "passCheck", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody 
	public Map<String, Object> myinfoPassChk(
			@RequestParam(required=true) String user_pass,
			@RequestParam(required=true) String user_id
			){
		Map<String, Object> result = new HashMap<>();
		UserVO user = new UserVO(user_id, user_pass);
		// 비밀번호 암호화
		user.setUser_pass(SecurityUtils.sha512(user.getUser_pass()));
		try {
			Object auth = authService.authenticate(user);
			
			if(auth instanceof UserVO) {
				result.put("result", true);
				result.put("message", "성공");
			}else if(ServiceResult.INVALIDPASSWORD == auth) {
				result.put("message", "비밀번호가 틀렸습니다.");
				result.put("result", false);
			}
		}catch (UserNotFoundException e) {
			result.put("message", "해당 유저가 없습니다.");
			result.put("result", false);
		}
		return result;
	}
	
	@GetMapping
	public String myinfoRetrive(@PathVariable("user_type") String user_type){
		return user_type + "/mypage/myinfo";
	}
	
	@PostMapping
	public String myinfoModify(
			@PathVariable("user_type") String user_type,
			@ModelAttribute("user") UserVO user,
			@RequestParam(required=false) String new_pass,
			HttpServletRequest req,
			HttpSession session
			){
		
		String message = null;
		// 비밀번호 암호화
		user.setUser_pass(SecurityUtils.sha512(user.getUser_pass()));
		try {
			Object auth = authService.authenticate(user);
			if(auth instanceof UserVO) {
				if(StringUtils.isNotEmpty(new_pass)) {
					user.setUser_pass(SecurityUtils.sha512(new_pass));
				}
				ServiceResult result = userService.modifyUser(user);
				switch(result) {
				case FAILED : 
					
					message = "서버 오류 입니다. 다시 시도해주세요.";
					break;
				default : 
					message = "수정 완료";
					session.removeAttribute("loginUser");
					session.setAttribute("loginUser", authService.authenticate(user));
				}
				
			}else if(ServiceResult.INVALIDPASSWORD == auth) {
				message = "비밀번호가 맞지 않습니다.";
			}
		}catch (UserNotFoundException e) {
			message = "해당하는 유저가 없습니다.";
		}
		req.setAttribute("message", message);
		
		
		return user_type + "/mypage/myinfo";
	}
	
	
}
