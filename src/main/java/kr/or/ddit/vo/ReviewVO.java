package kr.or.ddit.vo;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewVO implements Serializable {
	private int rnum;
	private Integer rv_no; // 리뷰 번호
	private String rv_writer; // 리뷰 학생 아이디
	private String rv_recipient; // 리뷰 선생님 아이디
	private String rv_content; // 리뷰 내용
	private String rv_date; // 리뷰 날짜
	private Integer rv_grade; // 리뷰 별점
}
