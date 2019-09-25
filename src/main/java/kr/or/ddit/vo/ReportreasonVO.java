package kr.or.ddit.vo;

import java.io.IOException;
import java.io.Serializable;

import org.apache.commons.codec.binary.Base64;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 신고 신청
 * @author pc05
 *
 */
@Data		
@EqualsAndHashCode(of= {"rr_no"})
@NoArgsConstructor
public class ReportreasonVO implements Serializable {
	private int rnum; //보일 번호
	private Long rr_no; //신고 사유 번호
	private String rr_writer; //신고자
	@NotBlank
	private String rr_recipient; //신고당한사람
	@NotBlank
	private Integer rr_reason; //신고사유
	private String rr_content; //신고기타사유
	private String rr_date; //신고날짜
	private Integer rr_ok; //처리유무
	private String rrs_name; //기타사유
	
	@JsonIgnore
	private byte[] rr_image; //이미지
	
	@JsonProperty("rr_imageBase64")
	public String getRr_imageBase64() {
		String base64String = null;
		if(rr_image!=null) {
			base64String = Base64.encodeBase64String(rr_image);
		}
		return base64String;
	}
	
	@JsonIgnore
	private MultipartFile rr_img;
	
	public void setRr_img(MultipartFile rr_img) throws IOException{
		this.rr_img = rr_img;
		if(rr_img!=null && !rr_img.isEmpty()
				&& rr_img.getContentType().startsWith("image/")) {
			rr_image = rr_img.getBytes();
		}
	}
}
