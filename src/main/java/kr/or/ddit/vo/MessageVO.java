package kr.or.ddit.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
/**
 * 메시지
 * @author pc22
 *
 */
@Data
@EqualsAndHashCode(of= {"msg_no"})
@NoArgsConstructor
@ToString(exclude = {"attachList"})
public class MessageVO implements Serializable {
	private int rnum; //보일 번호
	private String msg_no; // 번호
	private String msg_writer; // 작성자
	@NotBlank
	private String msg_recipient; // 받는 사람
	private String msg_date; // 보낸 날짜
	@NotBlank
	private String msg_title; // 메시지 제목
	@NotBlank
	private String msg_content; // 메시지 내용
	private Integer msg_ok; // 메시지 확인 여부
	private Integer team_no;
	
	private MultipartFile[] msg_files;
	public void setMsg_files(MultipartFile[] msg_files) {
		if(msg_files==null) return;
		attachList = new ArrayList<>();
		for(MultipartFile tmp : msg_files) {
			if(StringUtils.isBlank(tmp.getOriginalFilename())) continue;
			attachList.add(new AttachmentVO(tmp));
		}
	}
	
	private transient List<AttachmentVO> attachList;
}
