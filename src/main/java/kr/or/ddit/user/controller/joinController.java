package kr.or.ddit.user.controller;

import java.security.NoSuchAlgorithmException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.ddit.common.TutorGroup;
import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.exception.CommonException;
import kr.or.ddit.user.service.IUserService;
import kr.or.ddit.utils.SecurityUtils;
import kr.or.ddit.vo.UserVO;

@Controller
@RequestMapping("/join")
public class joinController {
	@Inject
	private IUserService userService;
	
	@GetMapping("joinTutee.do")
	public String joinForm(HttpServletRequest req){
		req.setAttribute("userType", "tutee");
		return "join/join";
	}
	@PostMapping("joinTutee.do")
	public String join(@Valid @ModelAttribute("user") UserVO user, Errors errors, Model model, HttpServletRequest req){
		req.setAttribute("userType", "tutee");
		boolean valid = !errors.hasErrors();
		String goPage = null;
		if(valid) {
			// 검증 통과 
			
			// 비밀번호 암호화
			user.setUser_pass(SecurityUtils.sha512(user.getUser_pass()));
			ServiceResult result = userService.createUser(user);
			switch(result) {
			case FAILED :
				// 서버 오류로 실패
				goPage = "join/join";
			case PKDUPLICATED :
				// 중복 아이디 있음
				goPage = "join/join";
			default : 
				// 회원가입 성공
				goPage = "redirect:/";
			}
		}else {
			// 검증 실패
			goPage = "join/join";
		}
		return goPage;
	}
	
	@GetMapping("joinTutor.do")
	public String joinTutorForm(HttpServletRequest req) {
		req.setAttribute("userType", "tutor");
		return "join/join";
	}
	@PostMapping("joinTutor.do")
	public String joinTutor(@Validated(TutorGroup.class) @ModelAttribute("user") UserVO user, Errors errors, Model model, HttpServletRequest req) {
		req.setAttribute("userType", "tutor");
		boolean valid = !errors.hasErrors();
		String goPage = null;
		if(valid) {
			// 검증 통과 
			// 비밀번호 암호화
			user.setUser_pass(SecurityUtils.sha512(user.getUser_pass()));
			user.setTutorCheck(true);
			user.setTt_auth(0);
			ServiceResult result = userService.createUser(user);
			switch(result) {
			case FAILED :
				// 서버 오류로 실패
				goPage = "join/join";
			case PKDUPLICATED :
				// 중복 아이디 있음
				goPage = "join/join";
			default : 
				// 회원가입 성공
				goPage = "redirect:/";
			}
		}else {
			// 검증 실패
			goPage = "join/join";
		}
		return goPage;
	}
}
