package kr.or.ddit.lecture.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.lecture.service.ICategoryService;
import kr.or.ddit.lecture.service.ILectureService;
import kr.or.ddit.lecture.service.ILectureWeekService;
import kr.or.ddit.lecture.service.IScheduleService;
import kr.or.ddit.lecture.service.ITeamService;
import kr.or.ddit.vo.CategoriesVO;
import kr.or.ddit.vo.LecturetutorVO;
import kr.or.ddit.vo.LectureweekVO;
import kr.or.ddit.vo.ScheduledVO;
import kr.or.ddit.vo.TeamVO;

@Controller
@RequestMapping("/lecture/lectureInsert.do")
public class LectureInsertController {

	@Inject
	ILectureService service;
	
	@Inject
	ICategoryService categoryService;
	
	@Inject
	ILectureWeekService LectureWeekservice;
	
	@Inject
	ITeamService teamService;
	
	@Inject
	IScheduleService scheduleService;
	
	@GetMapping
	public String doGet(@RequestParam(name="team", required=false) String team, Model model) {
		TeamVO teamVo = teamService.retrieveTeam2(Integer.parseInt(team));
		model.addAttribute("team", teamVo);
		return "lecture/lectureForm";
	}

	@PostMapping
	public String doPost(
			@RequestParam(name="category") String category,
			@Valid @ModelAttribute("lecture") LecturetutorVO lecturetutor,
			Errors errors, Model model
			,@RequestParam(name="lw_day") @Valid String lw_day
			,@RequestParam(name="lw_starttime") String lw_starttime
			,@RequestParam(name="lw_endtime") String lw_endtime
			,@RequestParam(name="team", required=false) String team_no
			) throws ParseException {
		
		System.out.println(team_no);
		CategoriesVO categoriesVO = new CategoriesVO();
		
		switch (category) {
		case "자바":
			categoriesVO.setCtgy_no(1);
			break;
		case "C++":
			categoriesVO.setCtgy_no(2);
			break;
		case "C#":
			categoriesVO.setCtgy_no(3);
			break;
		case "Phython":
			categoriesVO.setCtgy_no(4);
			break;
		case "LINUX":
			categoriesVO.setCtgy_no(5);
			break;
		case "HTML":
			categoriesVO.setCtgy_no(6);
			break;
		default:
			break;
		}
		
		System.out.println(categoriesVO.getCtgy_no());
		boolean valid = !errors.hasErrors();
		
		String goPage = null;
		String message = null;

		if (valid) {
			ServiceResult result = service.createLecture(lecturetutor);
			if(ServiceResult.OK.equals(result)) {
				goPage = "redirect:/lecture/lectureList.do";
			}else {
				message = "서버오류, 쫌따 다시.";
				goPage = "lecture/lectureForm";
			}
			
			ServiceResult result2 = categoryService.createCategory(categoriesVO);
			if(ServiceResult.OK.equals(result2)) {
				goPage = "redirect:/lecture/lectureList.do";
			}else {
				message = "서버오류, 쫌따 다시.";
				goPage = "lecture/lectureForm";
			}
			
			String[] array = lw_day.split(",");
			for(int i = 0; i < array.length; i++) {
				LectureweekVO lectureweekVO = new LectureweekVO();
				lectureweekVO.setLw_day(array[i]);
				lectureweekVO.setLw_starttime(lw_starttime);
				lectureweekVO.setLw_endtime(lw_endtime);
				
				ServiceResult result3 = LectureWeekservice.createLectureWeek(lectureweekVO);
				if(ServiceResult.OK.equals(result3)) {
					goPage = "redirect:/lecture/lectureList.do";
				}else {
					message = "서버오류, 쫌따 다시.";
					goPage = "lecture/lectureForm";
				}
			}
			
			if(team_no == null) {
				TeamVO teamVO = new TeamVO();
				
				ServiceResult result4 = teamService.createTeam(teamVO);
				if(ServiceResult.OK.equals(result4)) {
					goPage = "redirect:/lecture/lectureList.do";
				}else {
					message = "서버오류, 쫌따 다시.";
					goPage = "lecture/lectureForm";
				}
			}else {
				TeamVO teamVO = new TeamVO();
				teamVO.setTeam_no(Integer.parseInt(team_no));
				teamVO.setLt_no(lecturetutor.getLt_no());
				
				ServiceResult result5 = teamService.updateSelectedTeam(teamVO);
				
				if(ServiceResult.OK.equals(result5)) {
					goPage = "redirect:/lecture/lectureList.do";
				}else {
					message = "서버오류, 쫌따 다시.";
					goPage = "lecture/lectureForm";
				}
				
				ServiceResult result6 = service.updateLecture(lecturetutor);
				
				if(ServiceResult.OK.equals(result6)) {
					goPage = "redirect:/lecture/lectureList.do";
				}else {
					message = "서버오류, 쫌따 다시.";
					goPage = "lecture/lectureForm";
				}
			}
			
			int cnt = (int) (Math.random() * 999999);
			String color = "#"+cnt;
			
			SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
			
			
			ScheduledVO scheduledVO = new ScheduledVO();
			scheduledVO.setSc_writer(lecturetutor.getLt_writer());
			scheduledVO.setSc_turn(lecturetutor.getLt_turn());
			Date start = transFormat.parse(lecturetutor.getLt_start_date());
			scheduledVO.setSc_startday(start);
			Date end = transFormat.parse(lecturetutor.getLt_end_date());
			scheduledVO.setSc_endday(end);
			scheduledVO.setSc_content(lecturetutor.getLt_content());
			scheduledVO.setSc_title(lecturetutor.getLt_title());
			scheduledVO.setSc_bgcolor(color);
			scheduledVO.setSc_tcolor("#ffffff");
			
			ServiceResult result7 = scheduleService.createSchedule(scheduledVO);
			if(ServiceResult.OK.equals(result7)) {
				goPage = "redirect:/lecture/lectureList.do";
			}else {
				message = "서버오류, 쫌따 다시.";
				goPage = "lecture/lectureForm";
			}
			
		} else {
			goPage = "lecture/lectureForm";
		}
		
		model.addAttribute("message", message);

		return goPage;
	}
}
