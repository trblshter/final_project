package kr.or.ddit.user.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.exception.UserNotFoundException;
import kr.or.ddit.user.service.IUserService;
import kr.or.ddit.utils.MailAuth;
import kr.or.ddit.utils.SecurityUtils;
import kr.or.ddit.vo.UserVO;

@Controller
@RequestMapping(value="/find")
public class FindIdPassController {
	
	@Inject
	IUserService userService;
	
	@GetMapping
	public String find() {
		return "find/findIdPass";
	}
	
	@PostMapping(value="findId", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public Map<String, Object> findId(
			@RequestParam(required=true) String user_name, 
			@RequestParam(required=true) String user_email){
		Map<String, Object> result = new HashMap<>();
		
		UserVO findUser = new UserVO();
		findUser.setUser_name(user_name);
		findUser.setUser_email(user_email);
		
		try {
			UserVO user = userService.retrieveFindUser(findUser);
			
			// 메일 보내기
			String randomCode = UUID.randomUUID().toString();
			MailAuth.sendMail(user_email, randomCode);
			
			result.put("result", true);
			result.put("authCode", randomCode);
			result.put("findUser", user);
			
		}catch(UserNotFoundException e) {
			result.put("result", false);
			result.put("message", "해당하는 정보의 유저가 없습니다.");
		}
		
		return result;
	}
	
	@PostMapping(value="findPass", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public Map<String, Object> findPass(
			@RequestParam(required=true) String user_id, 
			@RequestParam(required=true)String user_email){
		Map<String, Object> result = new HashMap<>();
		UserVO findUser = new UserVO();
		findUser.setUser_id(user_id);
		findUser.setUser_email(user_email);
		
		try {
			UserVO user = userService.retrieveFindUser(findUser);
			
			/// 메일 보내기
			String randomCode = UUID.randomUUID().toString();
			MailAuth.sendMail(user_email, randomCode);
			
			result.put("result", true);
			result.put("authCode", randomCode);
			result.put("findUser", user);
			
		}catch(UserNotFoundException e) {
			result.put("result", false);
			result.put("message", "해당하는 정보의 유저가 없습니다.");
		}
		
		return result;
	}
	
	@PostMapping(value="updatePass", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public Map<String, Object> updatePass(
			@RequestParam(required=true) String user_id,
			@RequestParam(required=true) String new_pass
			){
		Map<String, Object> result = new HashMap<>();
		
		
		try {
			UserVO user = userService.retrieveUser(user_id);
			user.setUser_pass(SecurityUtils.sha512(new_pass));
			
			ServiceResult modifyResult = userService.modifyUser(user);
			switch(modifyResult) {
			case FAILED : 
				result.put("result", false);
				result.put("message", "서버오류. 다시시도하세요.");
				break;
			default :
				result.put("result", true);
				result.put("message", "비밀번호가 변경되었습니다.");
			}
			result.put("result", true);
			
		}catch(UserNotFoundException e) {
			result.put("result", false);
			result.put("message", "해당하는 정보의 유저가 없습니다.");
		}
		
		return result;
	}
	
}
