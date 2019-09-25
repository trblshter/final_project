package kr.or.ddit.webRTC.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/frontend")
public class FrontEndController {
	
	@GetMapping("view")
	public String viewing() {
		return "frontend/view";
	}
	
	@GetMapping("webrtc")
	public String webrtc() {
		return "frontend/webrtc";
	}

}
