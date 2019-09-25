package kr.or.ddit.vo;

import java.io.Serializable;

import lombok.Data;
/**
 * 강의 과목
 * @author pc22
 *
 */
@Data
public class CategoryVO implements Serializable {
	private Integer ctgy_no; // 강의 과목 번호
	private String ctgy_name; // 과목 이름
	private byte[] ctgy_image; //과목 이미지
	private String ctgy_type; // 과목 확장자
}
