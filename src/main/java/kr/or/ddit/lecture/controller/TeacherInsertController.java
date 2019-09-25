package kr.or.ddit.lecture.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.lecture.service.ITeacherService;
import kr.or.ddit.lecture.service.ITeamService;
import kr.or.ddit.user.service.IUserService;
import kr.or.ddit.vo.LectureUserVO;
import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.TeamVO;

@Controller
@RequestMapping("/lecture/teacherInsert.do")
public class TeacherInsertController {
	
	@Inject
	ITeamService teamService;
	
	@Inject
	IUserService userService;
	
	@Inject
	ITeacherService teacherService;
	
	@GetMapping
	public String doGet() {
		return "lecture/teacherForm";
	}
	
	@RequestMapping(produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public PagingVO<LectureUserVO> insert(
			@RequestParam(name="page", required=false, defaultValue="1") int currentPage,
			@ModelAttribute LectureUserVO lectureUserVO,
			@ModelAttribute TeamVO teamVO,
			Model model
			) {
//		String[] users = new String[]{teamVO.getTeam_member1(), teamVO.getTeam_member2(), teamVO.getTeam_member3(), teamVO.getTeam_member4()};
//		
//		for(String user_id : users) {
//			UserVO user = userService.retrieveUser(user_id);
//			if(user != null) {
//				System.out.println("있다");
//			}else {
//				System.out.println("없다");
//			}
//		}
		
		PagingVO<LectureUserVO> pagingVO = new PagingVO<>();
		pagingVO.setCurrentPage(currentPage);
		System.out.println(currentPage);
		long totalRecord = teacherService.retrieveLectureUserCount(pagingVO);
		pagingVO.setTotalRecord(totalRecord);
		System.out.println(totalRecord);
		
		teamVO.setTeam_member1(lectureUserVO.getLu_writer());
		TeamVO vo = teamService.createLectureUserTeam(teamVO);
		
		lectureUserVO.setTeam_no(vo.getTeam_no());
		
		ServiceResult result = teacherService.createLecture(lectureUserVO);
		
		List<LectureUserVO> list = teacherService.retrieveLectureUserList(pagingVO);
		pagingVO.setDataList(list);
		System.out.println(pagingVO.getDataList());
		
		System.out.println(vo);
		
		return pagingVO;
	}
	
	
}
