package kr.or.ddit.vo;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 과제 제출
 * @author pc22
 *
 */
@Data
@NoArgsConstructor
public class HwstudentVO implements Serializable {
	private String hs_no; // 과제 제출 번호
	private String hs_writer; // 학생 아이디 
	private String ht_no; // 과제 번호
	private String hs_content; // 과제 내용
	private String hs_date; // 과제낸 날짜
	
	private String att_filename; //파일 불러옴 필요!!
	private String att_no; //파일 불러옴 필요!!
}
