package kr.or.ddit.vo;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
/**
 * 과제
 * @author pc22
 *
 */
@Data
@NoArgsConstructor
public class HwTutorVO implements Serializable {
	private long rnum;
	private String ht_no; // 과제 번호
	private String lecture_no; // 과베 강의 번호
	private String ht_title; // 과제 제목
	private String ht_content; // 과제 내용
	private String ht_submit; // 제출해야하는 날짜
	private String ht_date; // 과제 작성 일자
	
	private String hs_no; // 해당 유저 과제 유무 확인!! 
	private String hs_writer; // 해당 유저 과제 유무 확인!!
	private String hs_content; // 해당 유저 과제 유무 확인!!
	private String att_filename; // 유저가 제출한 과제 확인!!
}
