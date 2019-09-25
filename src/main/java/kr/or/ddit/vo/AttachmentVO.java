package kr.or.ddit.vo;

import java.io.Serializable;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
/**
 * 첨부파일 관리
 * @author pc22
 *
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = {"att_no", "bo_no", "lt_no"})
public class AttachmentVO implements Serializable {
	public AttachmentVO(MultipartFile item){
		this.item = item;
		att_filename = item.getOriginalFilename();
		att_mime = item.getContentType();
		att_filesize = item.getSize();
		att_savename = UUID.randomUUID().toString();
		att_fancysize = FileUtils.byteCountToDisplaySize(att_filesize);
	}
	private long rnum;
	private MultipartFile item;
	private Integer att_no; // 첨부파일 번호
	private String bo_no; // 게시판 번호
	private String msg_no; //메시지 번호
	private String att_filename; // 첨부파일 이름
	private String att_savename; // 첨부파일 저장 이름
	private String att_mime; // 첨부파일 타입
	private long att_filesize; // 첨부파일 파일크기
	private String att_fancysize; // 첨부파일 파일용량
	private Integer att_download; // 다운로드 수
	private String lt_no;
	private String att_desc; 
}
