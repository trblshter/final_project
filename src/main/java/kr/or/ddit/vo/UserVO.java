package kr.or.ddit.vo;

import java.io.IOException;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;

import javax.validation.constraints.NotNull;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.springframework.web.multipart.MultipartFile;

import kr.or.ddit.common.TutorGroup;
import kr.or.ddit.common.validation.TelPattern;
import kr.or.ddit.utils.SecurityUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
/**
 * 학생
 * @author pc22
 *
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(of={"user_id", "user_pass"})
public class UserVO implements Serializable {
	public UserVO(String user_id, String user_pass){
		this.user_id = user_id;
		this.user_pass = user_pass;
	}
	private int rnum; //보일 번호
	@NotBlank
	private String user_id; // 회원 아이디
	@NotBlank
	private String user_pass; // 회원 비밀번호
	@NotBlank
	private String user_name; // 회원 이름
	@Range(max=99999,min=00000)
	private Integer user_zipcode; // 회원 우편번호
	@NotBlank
	private String user_addr1; // 회원 주소
	@NotBlank
	private String user_addr2; // 회원 상세주소
	@Email
	private String user_email; // 회원 이메일
	@TelPattern
	private String user_tel; // 회원 전화번호
	@NotBlank
	private String user_birth; // 회원 생일
	@NotNull(groups=TutorGroup.class)
	private byte[] user_image; // 회원 사진
//	@NotBlank
//	private String user_account_name; // 계좌 은행
//	@Range
//	private Long user_account_num; // 계좌 번호
	
	private char user_auth; // 유저 권한 A:admin, U:user
	// 선생님 정보
	@Range(max=99999,min=00000)
	private Integer tt_career;
	@NotBlank(groups=TutorGroup.class)
	private String tt_edu;
	@NotNull(groups=TutorGroup.class)
	private byte[] tt_ctr_image;
	
	private MultipartFile tt_ctr_img;
	
	private int tt_auth;
	
	// 회원가입시 선생님인지 아닌지 확인
	private boolean tutorCheck;
	
	private String user_type = "tutee";
	
	private MultipartFile user_img;
	
	public void setUser_auth(char user_auth) {
		this.user_auth = user_auth;
		if('A'== user_auth) {
			user_type = "admin";
		}
	}
	
	public void setTt_auth(int tt_auth) {
		this.tt_auth = tt_auth;
		if(tt_auth >= 0) {
			user_type = "tutor";
		}
	}
	// 유저 프로필 사진 Base64
	public String getUser_imageBase64(){
		if(user_image==null) {
			return null;
		}else {
			return Base64.encodeBase64String(user_image);
		}
	}

	// 유저 프로필 사진
	public void setUser_img(MultipartFile user_img) throws IOException {
		this.user_img = user_img;
		if(user_img!=null && 
			StringUtils.isNotBlank(user_img.getOriginalFilename())) {
			this.user_image = user_img.getBytes();
		}
	}
	
	// 선생님 자격증 이미지 Base64
	public String getTt_ctr_imageBase64(){
		if(tt_ctr_image==null) {
			return null;
		}else {
			return Base64.encodeBase64String(tt_ctr_image);
		}
	}
	
	
	// 선생님 자격증 이미지
	public void setTt_ctr_img(MultipartFile tt_ctr_img) throws IOException {
		this.tt_ctr_img = tt_ctr_img;
		if(tt_ctr_img!=null && 
				StringUtils.isNotBlank(tt_ctr_img.getOriginalFilename())) {
			this.tt_ctr_image = tt_ctr_img.getBytes();
		}
	}

	
}
