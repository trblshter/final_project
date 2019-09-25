package kr.or.ddit.vo;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

/**
 * 
 * @author pc22
 *
 */
@Data
public class LectureCommentVO implements Serializable {
	private int lc_no;
	private String user_id;
	private String lc_date;
	private String lu_no;
	private String lc_content;
	
	private byte[] user_image;
	public String getUser_imageBase64() {
		if(user_image == null) {
			return null;
		}else {
			return Base64.encodeBase64String(user_image);
		}
	}
}
