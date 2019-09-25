package kr.or.ddit.common.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.JsonNode;

import kr.or.ddit.exception.CommonException;
import kr.or.ddit.exception.UserNotFoundException;
import kr.or.ddit.user.service.IAuthenticateService;
import kr.or.ddit.utils.CookieUtils;
import kr.or.ddit.utils.SecurityUtils;
import kr.or.ddit.utils.CookieUtils.TextType;
import kr.or.ddit.utils.kakao_restapi;
import kr.or.ddit.utils.naver_restapi;
import kr.or.ddit.vo.UserVO;

@Controller
@RequestMapping("/login")
public class LoginController {
	@Inject
	WebApplicationContext container;
	
	@Inject
	IAuthenticateService authService;
	
	Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@PostMapping("login.do")
	public String login(
			@RequestParam(required=false) String user_id,
			@RequestParam(required=false) String user_pass,
			@RequestParam(required=false) String cookieSaveId,
			HttpSession session,
			HttpServletResponse resp,
			@RequestHeader(name="Referer", required=false) String referer
			) throws IOException{
		String goPage = null;
		
		
		if(StringUtils.isBlank(user_id) || StringUtils.isBlank(user_pass)) {
			// 검증 실패
			session.setAttribute("message", "아이디와 패스워드를 모두 입력해주세요.");
			goPage = "redirect:/login";
			System.out.println("ii##$!$#!$!$!@#$@#$");
			
		}else {
			// 검증 통과
			UserVO user = new UserVO(user_id, user_pass);
			// 비밀번호 암호화
			user.setUser_pass(SecurityUtils.sha512(user.getUser_pass()));
			try{
				
				Object result = authService.authenticate(user);
				if(result instanceof UserVO) {
					// 아이디 패스워드 일치
					// 쿠키 저장
					int maxAge = 0;
					if("idSave".equals(cookieSaveId)) {
						maxAge = 60*60*24*7;
					}	
					Cookie idCookie = CookieUtils.createCookie("idCookie", user.getUser_id(), container.getServletContext().getContextPath(), TextType.PATH, maxAge);
					resp.addCookie(idCookie);
					
//					if(StringUtils.isNotBlank(referer) && 
//							!StringUtils.containsIgnoreCase(referer, "loginForm")) {
//							referer = referer.substring(container.getServletContext().getContextPath().length());
//							return "redirect:"+ referer; 
//						}
					
					// 인덱스(웰컴) 페이지로 이동(redirect)
					session.setAttribute("loginUser", result);
					goPage = "redirect:/";	
					
				}else {
					// 비번 틀림
					session.setAttribute("message", "비밀번호가 맞지 않습니다.");
					session.setAttribute("failedId", user.getUser_id());
					// 로그인 폼으로 이동
					goPage = "redirect:/login";
					
				}
			}catch(UserNotFoundException e) {
				// 그런 아이디 없음
				session.setAttribute("message", "해당 아이디는 없는 아이디 입니다.");
				// 로그인 폼으로 이동
				goPage = "redirect:/login";
			}
		}
		return goPage;
	}
	
	/**
	 * 카카오 로그인
	 * @throws IOException 
	 */
	@RequestMapping(value = "kakao", produces = "application/json", method = { RequestMethod.GET, RequestMethod.POST })
    public String kakaoLogin(@RequestParam("code") String code, Model model, HttpSession session,
    		@RequestParam(required=false) String cookieSaveId,
    		HttpServletRequest req,
    		HttpServletResponse resp,
			@RequestHeader(name="Referer", required=false) String referer) throws IOException {
        System.out.println("로그인 할때 임시 코드값");
        //카카오 홈페이지에서 받은 결과 코드
        System.out.println(code);
        System.out.println("로그인 후 결과값");
        
        
        System.out.println(session.getId());
        
        //카카오 rest api 객체 선언
        kakao_restapi kr = new kakao_restapi();
        //결과값을 node에 담아줌
        JsonNode node = kr.getAccessToken(code);
        //결과값 출력
        System.out.println(node);
        //노드 안에 있는 access_token값을 꺼내 문자열로 변환
        String token = node.get("access_token").toString().trim();
        //세션에 담아준다.
        session.setAttribute("token", token);
        //유저 정보를 가져온다.
        JsonNode userInfo = kr.getAccessUser(token);
        String user_id = userInfo.get("id").toString();
        logger.info("kakaoID : " + user_id);
        //유저가 우리 유저인지 db에서 찾아본다.
        UserVO user = new UserVO("kakao" + user_id, "kakao" + user_id);
        String goPage = null;
        try{
        	user.setUser_pass(SecurityUtils.sha512(user.getUser_pass()));
        	Object result =authService.authenticate(user);
			if(result instanceof UserVO) {
				// 비밀번호 암호화
				//우리 유저일 경우 로그인 세션에 (loginUser추가)
				int maxAge = 0;
				System.out.println(session.getId());
				Cookie remove = CookieUtils.createCookie("idCookie", user.getUser_id(), container.getServletContext().getContextPath(), TextType.PATH, maxAge);
				resp.addCookie(remove);
				if("idSave".equals(cookieSaveId)) {
					maxAge = 60*60*24*7;
				}	
				Cookie idCookie = CookieUtils.createCookie("idCookie", user.getUser_id(), container.getServletContext().getContextPath(), TextType.PATH, maxAge);
				resp.addCookie(idCookie);
//				if(StringUtils.isNotBlank(referer) && 
//						!StringUtils.containsIgnoreCase(referer, "loginForm")) {
//						referer = referer.substring(container.getServletContext().getContextPath().length());
//						return "redirect:"+ referer; 
//					}
				// 인덱스(웰컴) 페이지로 이동(redirect)
				session.setAttribute("loginUser", result);
				System.out.println(session.getId());
				System.out.println("#!@$!$!@$$!@$");
				goPage = "redirect:/";	
			}
		}catch(UserNotFoundException e) {
			//우리 유저가 아닐 경우 회원가입 폼으로 인도. 
			//회원가입 폼으로 넘길 데이터 :
			user.setUser_pass("kakao" + user_id);
			session.setAttribute("socialUser", user);
			//kakao회원이라는 표시
			session.setAttribute("social", "kakao");
			//전달받은 아이디
			//아이디를 이용해 조합한 패스워드
			
			goPage = "join/joinType";
		}
        return goPage;
    }
	/**
	 * 네이버 로그인
	 */
	@RequestMapping(value = "naver", produces = "application/json", method = { RequestMethod.GET, RequestMethod.POST })
    public String naverLogin(@RequestParam("code") String code, Model model, HttpSession session) {
        System.out.println("로그인 할때 임시 코드값");
        //네이버 홈페이지에서 받은 결과 코드
        System.out.println(code);
        System.out.println("로그인 후 결과값");
        
        //네이버 rest api 객체 선언
        naver_restapi kr = new naver_restapi();
        //결과값을 node에 담아줌
        JsonNode node = kr.getAccessToken(code);
        //결과값 출력
        System.out.println(node);
        //노드 안에 있는 access_token값을 꺼내 문자열로 변환
        String token = node.get("access_token").toString();
        //세션에 담아준다.
        session.setAttribute("token", token);
     	//유저 정보를 가져온다.
        JsonNode userInfo = kr.getAccessUser(token);
        System.out.println(userInfo);
        String user_id = userInfo.get("response").get("id").toString().replace("\"", "").trim();
        logger.info("naverID : " + user_id);
        //유저가 우리 유저인지 db에서 찾아본다.
        UserVO user = new UserVO("naver" + user_id, "naver" + user_id);
        // 비밀번호 암호화
        String goPage = null;
        try{
        	user.setUser_pass(SecurityUtils.sha512(user.getUser_pass()));
        	Object result =authService.authenticate(user);
			if(result instanceof UserVO) {
				//우리 유저일 경우 로그인 세션에 (loginUser추가)
				// 인덱스(웰컴) 페이지로 이동(redirect)
				session.setAttribute("loginUser", result);
				goPage = "redirect:/";	
			}
		}catch(UserNotFoundException e) {
			//우리 유저가 아닐 경우 회원가입 폼으로 인도. 
			//회원가입 폼으로 넘길 데이터 :
			user.setUser_pass("naver" + user_id);
			session.setAttribute("socialUser", user);
			//kakao회원이라는 표시
			session.setAttribute("social", "naver");
			//전달받은 아이디
			//아이디를 이용해 조합한 패스워드
			
			goPage = "join/joinType";
		}
        return goPage;
    }
	
	@GetMapping("logout.do")
	public String logout(HttpSession session, HttpServletRequest req, HttpServletResponse resp) throws IOException{
		String goPage = null;
		String referer = null;
		if(session==null || session.isNew()) {
			session.setAttribute("message", "로그인부터 하지!!");
			goPage = "redirect:/login";
		}else {
			session.invalidate();
			referer = req.getHeader("Referer");
			goPage = "redirect:/";
		}
		return goPage;
	}
}
