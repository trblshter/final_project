package kr.or.ddit.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

import kr.or.ddit.board.UpdateGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(of= {"bo_no"})
@ToString(exclude= {"delFiles", "attachList"})
public class BoardVO implements Serializable {	
	private Long rnum; // 행번호
	@NotNull(groups=UpdateGroup.class)
	@Min(value=1)
	private String bo_no; // 게시판 번호
	@NotBlank
	private String bo_title; // 제목
	private String bo_content; // 내용
	@NotBlank
	private String bo_writer; // 작성자
	private String bo_date; // 날짜
	private Integer ctgy_no; // 강의 과목 번호
	private Integer bo_views; // 게시판 조회수
	@NotBlank
	private String bo_pass; // 비번
	
	private String type; // 게시판 구분 값
	
	private int[] delFiles; // 삭제할 첨부파일에 대한 정보
	
	public void setBo_files(MultipartFile[] bo_files) {
		if(bo_files==null) return;
		attachList = new ArrayList<>();
		for(MultipartFile tmp : bo_files) {
			if(tmp.getSize() == 0) continue;
			attachList.add(new AttachmentVO(tmp));
		}
	}
	private transient List<AttachmentVO> attachList;
}
