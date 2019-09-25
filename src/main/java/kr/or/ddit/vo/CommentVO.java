package kr.or.ddit.vo;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
/**
 * 댓글
 * @author pc22
 *
 */
@Data
public class CommentVO implements Serializable {
	private Integer cm_no; // 댓글 번호
	private String cm_writer; // 댓글 작성자
	private String bo_no; // 게시판 번호
	private String cm_content; // 내용
	private String cm_date; // 댓글 쓴 날짜
	private Integer cm_parent; // 부모 댓글 번호
	private byte[] user_image; // 회원 사진
	
	// 유저 프로필 사진 Base64
	public String getUser_imageBase64(){
		if(user_image==null) {
			return null;
		}else {
			return Base64.encodeBase64String(user_image);
		}
	}


}
