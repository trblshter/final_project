package kr.or.ddit.attach.controller;

import java.io.IOException;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.or.ddit.attach.service.IAttachService;
import kr.or.ddit.vo.AttachmentVO;

@Controller
public class AttachDownloadController {
	@Inject
	IAttachService service;

	@RequestMapping("/download.do")
	public String process(
			@RequestParam(name="what",required=true) int att_no,
			Model model
			) throws IOException{
		
		AttachmentVO attach = service.download(att_no);
		model.addAttribute("attach", attach);
		
		return "downloadView";
	}
}
