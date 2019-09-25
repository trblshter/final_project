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
 * 강의 모집
 * @author pc22
 *
 */
@Data
public class LecturetutorVO implements Serializable {
	private Long rnum;
	private String lt_no; // 모집 번호
	@NotBlank
	private String lt_writer; // 모집 선생님 아이디
	private String lt_date; // 날짜
	@NotBlank
	private String lt_title; // 모집 제목
	@NotNull
	private Integer lt_price; // 모집 가격
	private String lt_content; // 모집 세부내용
	private Integer lt_secret; // 비밀글 여부
	private Integer lt_completed; // 모집 완료 여부
	private Integer lt_class_com; // 수업 완료 여부
	private Integer lt_views; // 조회수
	private MultipartFile lt_img; //이미지
	public void setLt_img(MultipartFile lt_img) throws IOException {
		this.lt_img = lt_img;
		if(lt_img!=null && StringUtils.isNotBlank(lt_img.getOriginalFilename())) {
			this.lt_image = lt_img.getBytes();
		}
	}
	
	private byte[] lt_image;
	public String getLt_imageBase64() {
		if(lt_image == null) {
			return null;
		}else {
			return Base64.encodeBase64String(lt_image);
		}
	}
	
    private String lt_start_date; // 강의 시작 날짜
    private String lt_end_date; // 강의 끝나는 날짜
    private String startDay; //import schedule TABLE
	private String endDay; //import schedule TABLE
	
	private int lt_turn; //new 강의 회차
	private int lt_recruit; //new 모집인원
	
	private int lecture_turn; // 현재 강의 회차 (꼭 필요!!)
	private String ctgy_name; // 과목 이름 설정
    
	private List<CategoryVO> categoryList; 
}
