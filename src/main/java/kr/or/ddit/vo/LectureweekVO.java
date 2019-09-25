package kr.or.ddit.vo;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;
/**
 * 강의 요일
 * @author pc22
 *
 */
@Data
public class LectureweekVO implements Serializable {
	private Integer lw_no; // 번호
	private String lt_no; // 강의 번호
	@NotBlank
	private String lw_day; // 요일
	private String lw_starttime; // 강의 시작 시간
	private String lw_endtime; // 강의 끝나는 시간
}
