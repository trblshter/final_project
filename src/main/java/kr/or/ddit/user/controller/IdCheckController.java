package kr.or.ddit.user.controller;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.or.ddit.exception.UserNotFoundException;
import kr.or.ddit.user.service.IUserService;

@RestController
public class IdCheckController {
	@Inject
	IUserService service;
	
	@RequestMapping(value="/user/idCheck.do", 
					produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Map<String, Object> process(@RequestParam(required=true) String inputId){
		
		Map<String, Object> result = new HashMap<String, Object>();
		boolean duplicated = false;
		try {
			service.retrieveUser(inputId);
			duplicated = true;
		}catch (UserNotFoundException e) {
			
		}
		result.put("success", true);
		result.put("duplicated", duplicated);
		
		return result;
	}
}
