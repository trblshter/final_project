package kr.or.ddit.attach.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.AbstractView;

import kr.or.ddit.vo.AttachmentVO;

public class DownloadView extends AbstractView {
	
	@Value("#{appInfo['attachPath']}")
	private File saveFolder;

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		AttachmentVO attach = (AttachmentVO) model.get("attach");
		
		String originalFileName = attach.getAtt_filename();
		File inputFile = new File(saveFolder, attach.getAtt_savename());
		
		resp.setContentType("application/octet-stream");
		//originalFileName = URLEncoder.encode(originalFileName, "UTF-8");
		//원시 데이터를 처리하는 방식
		originalFileName = new String(originalFileName.getBytes(), "ISO-8859-1");
		resp.setHeader("Content-Disposition", "attatchment;filename=\"" + originalFileName +"\"");
//		resp.setContentLengthLong(attach.getAtt_filesize());
		resp.setHeader("Content-Length", attach.getAtt_filesize()+"");
		try(
			FileInputStream is = new FileInputStream(inputFile);
			OutputStream os = resp.getOutputStream();
		){
			FileUtils.copyFile(inputFile, os);
			System.out.println("load complete");
		}

	}

}
