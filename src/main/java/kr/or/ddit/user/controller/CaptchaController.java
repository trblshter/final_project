package kr.or.ddit.user.controller;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Base64OutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.or.ddit.user.captcha.APIExamCaptcha;

@RestController
@RequestMapping(value="/nCaptcha.do", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
public class CaptchaController {
	Logger logger = LoggerFactory.getLogger(CaptchaController.class);
	private APIExamCaptcha captch = new APIExamCaptcha();
	private String key;
	@GetMapping
	public Map<String, Object> printCaptcha() {
		Map<String, Object> result = new HashMap<String, Object>();
		key = captch.nkey().substring(8, 8 + 16);
		logger.info("캡차 키만 추출 : " + key);
		ByteArrayOutputStream captchImg = captch.nImage(key);
		
		byte[] fileArray = captchImg.toByteArray();

		String imgFileBase64 = new String(Base64.encodeBase64(fileArray));
		
		result.put("nCaptcha", imgFileBase64);
		return result;
	}
	
	@PostMapping
	public Map<String, Object> resultCaptcha(String userCaptchaStr){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String result = captch.nResult(key, userCaptchaStr);
		resultMap.put("result", result);
		return resultMap;
	}
}
