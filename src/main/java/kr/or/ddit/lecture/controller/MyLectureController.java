package kr.or.ddit.lecture.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.lecture.service.IMyLectureService;
import kr.or.ddit.vo.AttachmentVO;
import kr.or.ddit.vo.CalendarVO;
import kr.or.ddit.vo.HwTutorVO;
import kr.or.ddit.vo.HwstudentVO;
import kr.or.ddit.vo.LectureVO;
import kr.or.ddit.vo.LecturetutorVO;
import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.ScheduledVO;
import kr.or.ddit.vo.TeamVO;
import kr.or.ddit.vo.UserVO;

@Controller
@RequestMapping("/myLecture")
public class MyLectureController {
	
	@Inject
	IMyLectureService service;

	@PostMapping(value="list", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public PagingVO<LectureVO> ajaxList(
			@RequestParam(name="page", required=false, defaultValue="1") int currentPage,
			HttpSession session,
 			Model model
			) {
		list(currentPage, session, model);
		return  (PagingVO<LectureVO>) model.asMap().get("pagingVO");
	}
	
	@GetMapping("list")
	public String list(
			@RequestParam(name="page", required=false, defaultValue="1") int currentPage,
				HttpSession session, Model model
			) {
		System.out.println(session.getId());
		UserVO user = (UserVO) session.getAttribute("loginUser");
		PagingVO<LecturetutorVO> pagingVO = new PagingVO<>();
		pagingVO.setSearchWord(user.getUser_id());
		pagingVO.setCurrentPage(currentPage);
		long totalRecord = 0;
		String user_id = user.getUser_id();
		List<LecturetutorVO> list;
		// 튜터 :: 자신의 강의 리스트 출력
		if(user.getUser_type().equals("tutor")) {
			totalRecord = service.retrieveMyLectureCountForTutor(user_id);
			pagingVO.setTotalRecord(totalRecord);
			list = service.retrieveMyLectureListForTutor(pagingVO);
			pagingVO.setDataList(list);
			//튜티 :: 자신의 수강하는 강의 리스트 출력
		}else if(user.getUser_type().equals("tutee")) {
			totalRecord = service.retrieveMyLectureCount(user_id);
			pagingVO.setTotalRecord(totalRecord);
			System.out.println(pagingVO.getTotalRecord());
			list = service.retrieveMyLectureList(pagingVO);
			pagingVO.setDataList(list);
		}
		model.addAttribute("category","list");
		model.addAttribute("pagingVO", pagingVO);
		return "lecture/myLecture"; 
	}
	
	@GetMapping("calendar")
	public String calendar(Model model) {
		model.addAttribute("category","calendar");
		return "lecture/myLecture"; 
	}
	
	/**
	 * TODO 튜티와 튜터의 일정을 다르게 출력할 것.
	 * 해당하는 모든 강의 일정 출력.
	 * @param session
	 * @return
	 */
	@PostMapping(value="calendar", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public CalendarVO[] renderCalendar(HttpSession session) {
		
		UserVO user = (UserVO) session.getAttribute("loginUser");
		List<ScheduledVO> scheduleList = new ArrayList<>();
		if(user.getUser_type().equals("tutee")) {
			scheduleList =  service.retrieveMyLectureSchedule(user.getUser_id());
		}else if(user.getUser_type().equals("tutor")) {
			scheduleList =  service.retrieveMyLectureScheduleTutor(user.getUser_id());
		}
		System.out.println(scheduleList.toString());
		CalendarVO[] calArry = new CalendarVO[scheduleList.size()];
		int i = 0;
		for(ScheduledVO vo : scheduleList) {
			calArry[i] = calendarPropMapper(vo);
			i++;
		}
		return calArry;
	}
	
	@GetMapping("detail/{lt_no}")
	public String detail(@PathVariable(required=true) String lt_no,
			Model model) {
		model.addAttribute("category","info");
		LecturetutorVO lecture = service.retrieveMyLecture(lt_no);
		UserVO tutor = service.retrieveMyFriendInfo(lecture.getLt_writer());
		model.addAttribute("tutor",tutor);
		model.addAttribute("lecture",lecture);
		TeamVO team = service.retrieveMyFriends(lt_no);
		model.addAttribute("team", team);
		String user_id;
		UserVO user;
		if(team != null) {
			if(StringUtils.isNotBlank(user_id = team.getTeam_member1())) {
				user = service.retrieveMyFriendInfo(user_id);
				model.addAttribute("user1", user);
			}
			if(StringUtils.isNotBlank(user_id = team.getTeam_member2())) {
				user = service.retrieveMyFriendInfo(user_id);
				model.addAttribute("user2", user);
			}
			if(StringUtils.isNotBlank(user_id = team.getTeam_member3())) {
				user = service.retrieveMyFriendInfo(user_id);
				model.addAttribute("user3", user);
			}
			if(StringUtils.isNotBlank(user_id = team.getTeam_member4())) {
				user = service.retrieveMyFriendInfo(user_id);
				model.addAttribute("user4", user);
			}
		}

		LecturetutorVO progress = service.retrieveMyLectureProgress(lt_no);
		model.addAttribute("progress", progress);
		return "lecture/lectureDetail";
	}
	
	/**
	 * 자료 공유 리스트 처리
	 * @param lt_no
	 * @param currentPage
	 * @return
	 */
	@PostMapping(value = "detail/{lt_no}", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public PagingVO<AttachmentVO> sharingAsyncProcess(@PathVariable(required=true) String lt_no,
			@RequestParam(name="page", required=false, defaultValue="1") int currentPage) {
		
		PagingVO<AttachmentVO> paging = new PagingVO<>(5,5);
		paging.setCurrentPage(currentPage);
		paging.setSearchWord(lt_no);
		long totalRecord = service.retrieveMyLectureSharingCount(paging);
		paging.setTotalRecord(totalRecord);
		
		List<AttachmentVO> attachList = service.retrieveMyLectureSharingList(paging);
		paging.setDataList(attachList);
		return paging;
	}
	
	/**
	 * 자료 공유 파일 추가
	 * @param item
	 * @param att_desc
	 * @param lt_no
	 * @return
	 */
	@PostMapping(value = "detail/addFile", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public Map<String, String> uploadFile(@RequestParam(name="item") MultipartFile item,
			@RequestParam(name="att_desc", required=false) String att_desc,
			@RequestParam(name="lt_no", required=true) String lt_no) {
		Map<String, String> resultMap = new HashMap<>();
		resultMap.put("state", "업로드 실패!!");
		if(item == null) {
			return resultMap;
		}
		AttachmentVO attach = new AttachmentVO(item);
		attach.setAtt_desc(att_desc);
		attach.setLt_no(lt_no);
		
		ServiceResult result = service.uploadSharedFile(attach);

		if(result.equals(ServiceResult.OK))
			resultMap.put("state", "업로드 성공!!");
		
		return resultMap;
	}
	
	/**
	 * 강의 세부 일정 :: 해당 강의 만.
	 * @param lt_no
	 * @param model
	 * @return
	 */
	@GetMapping("detail/calendar/{lt_no}")
	public String detailCalendar(@PathVariable(required=true) String lt_no,
			Model model) {
		model.addAttribute("category","calendar");
		LecturetutorVO lecture = service.retrieveMyLecture(lt_no);
		model.addAttribute("lecture",lecture);
		return "lecture/lectureDetail";
	}
	
	/**
	 * 강의 세부 일정 렌더링
	 * @param lt_no
	 * @param model
	 * @return
	 */
	@PostMapping(value="detail/calendar/{lt_no}", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public CalendarVO[] renderCalendar(@PathVariable(required=true) String lt_no,
			Model model) {
		List<ScheduledVO> scheList = new ArrayList<ScheduledVO>();
		ScheduledVO schedule = service.getLectureSchedulebyOne(lt_no);
		scheList.add(schedule);
		CalendarVO[] calarry = new CalendarVO[scheList.size()];
		int i = 0;
		for(ScheduledVO vo : scheList) {
			calarry[i] = calendarPropMapper(schedule);
			i++;
		}
		return calarry;
	}
	
	@GetMapping("detail/homework/{lt_no}")
	public String detailHomework(@PathVariable(required=true) String lt_no,
			Model model) {
		model.addAttribute("category","report");
		model.addAttribute("lt_no",lt_no);
		LecturetutorVO lecture = service.retrieveMyLecture(lt_no);
		model.addAttribute("lecture",lecture);
		return "lecture/lectureDetail";
	}
	
	@PostMapping(value="detail/homework/{lt_no}", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public PagingVO<HwTutorVO> listHomeWork(@PathVariable(required=true) String lt_no,
			@RequestParam(name="page", required=false, defaultValue="1") int currentPage,
			Model model){
		
		PagingVO<HwTutorVO> pagingVO = new PagingVO<>();
		pagingVO.setCurrentPage(currentPage);
		pagingVO.setSearchWord(lt_no);
		long totalRecord =  service.retrieveHomeWorkCount(lt_no);
		pagingVO.setTotalRecord(totalRecord);
		List<HwTutorVO> hwList = service.retrieveHomeWorkList(pagingVO);
		pagingVO.setDataList(hwList);
		
		return pagingVO;
	}
	
	
	/**
	 * 과제 정보 확인
	 * @param ht_no
	 * @return
	 */
	@GetMapping(value="detail/getHomework/{ht_no}", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public HwTutorVO getSelectedHomeworkInfo(@PathVariable(required=true) String ht_no) {
		HwTutorVO homework = service.retrieveHomeworkInfo(ht_no);
		return homework;
	}
	
	
	@PostMapping(value = "detail/uploadHW", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public Map<String, String> uploadHomeWork(@ModelAttribute HwTutorVO hwtutorVO) {
		Map<String, String> resultMap = new HashMap<>();
		resultMap.put("state", "업로드 실패!!");
		ServiceResult result = service.uploadHomeWork(hwtutorVO);
		if(result.equals(ServiceResult.OK))
			resultMap.put("state", "업로드 성공!!");
		return resultMap;
	}
	
	@PostMapping(value = "detail/modifyHW/{ht_no}", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public Map<String, String> modifyHomeWork(@PathVariable(required=true) String ht_no
			,@ModelAttribute HwTutorVO hwtutorVO) {
		Map<String, String> resultMap = new HashMap<>();
		hwtutorVO.setHt_no(ht_no);
		resultMap.put("state", "업데이트 실패!!");
		ServiceResult result = service.modifyHomework(hwtutorVO);
		if(result.equals(ServiceResult.OK))
			resultMap.put("state", "업데이트 성공!!");
		return resultMap;
	}
	
	/**
	 * 튜터 :: 과제 삭제
	 * @param ht_no
	 * @return
	 */
	@GetMapping(value="removeHW/{ht_no}", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public Map<String, String> removeHomeworkByTutor(@PathVariable(required=true) String ht_no) {
		Map<String, String> resultMap = new HashMap<>();
		resultMap.put("state", "삭제 실패!!");
		ServiceResult result =service.removeHomework(ht_no);
		if(result.equals(ServiceResult.OK))
			resultMap.put("state", "삭제 성공!!");
		return resultMap;
	}
	
	/**
	 * 튜티 :: 과제 삭제
	 * @param hs_no
	 * @return
	 */
	@GetMapping(value="cancleHW/{hs_no}", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public Map<String, String> removeHomeworkByTutee(@PathVariable(required=true) String hs_no) {
		Map<String, String> resultMap = new HashMap<>();
		resultMap.put("state", "삭제 실패!!");
		ServiceResult result =service.removeHomeworkByTutee(hs_no);
		if(result.equals(ServiceResult.OK))
			resultMap.put("state", "삭제 성공!!");
		return resultMap;
	}
	
	@GetMapping(value="detail/getFile/{att_no}", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public AttachmentVO getSelectedFileInfo(@PathVariable(required=true) int att_no) {
		AttachmentVO attach = service.readyForDownloadFile(att_no);
		return attach;
	}
	
	@GetMapping(value="removeFile/{att_no}", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public Map<String, String> removeSharedFile(@PathVariable(required=true) int att_no) {
		Map<String, String> resultMap = new HashMap<>();
		resultMap.put("state", "삭제 실패!!");
		ServiceResult result =service.removeSharedFile(att_no);
		if(result.equals(ServiceResult.OK))
			resultMap.put("state", "삭제 성공!!");
		
		return resultMap;
	}
	
	/**
	 * 과제 제출해버리기 
	 * @param item
	 * @param ht_no
	 * @param hwstudentVO
	 * @return
	 */
	@PostMapping(value = "/homework/submit/{ht_no}", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public Map<String, String> submitHomework(@RequestParam(name="item") MultipartFile item,
			@PathVariable(required=true) String ht_no,
			@ModelAttribute HwstudentVO hwstudentVO) {
		Map<String, String> resultMap = new HashMap<>();
		resultMap.put("state", "업로드 실패!!");
		if(item == null) {
			return resultMap;
		}
		AttachmentVO attach = new AttachmentVO(item);
		hwstudentVO.setHt_no(ht_no);
		ServiceResult result = service.submitHomework(hwstudentVO, attach);
		if(result.equals(ServiceResult.OK))
			resultMap.put("state", "업로드 성공!!");
		return resultMap;
	}
	
	/**
	 * 튜터 :: 과제 제출 현황 가져오기
	 * @param ht_no
	 * @return
	 */
	@GetMapping(value="/homework/check/{ht_no}", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public List<HwstudentVO> checkSubmittedHomeworkList(@PathVariable(required=true) String ht_no){
		List<HwstudentVO> hwstudentList = new ArrayList<>();
		hwstudentList =  service.retrieveSubmittedHomeworkList(ht_no);
		return hwstudentList;
	}
	
	
	
	/**
	 * calendarVO mapping for FullCalendar
	 * @param schedule
	 * @return
	 */
	private CalendarVO calendarPropMapper(ScheduledVO schedule) {
		if(schedule == null) {
			return null;
		}
		CalendarVO calendar = new CalendarVO();
		calendar.set_id(schedule.getLt_no());
		calendar.setTitle(schedule.getSc_title());
		calendar.setDescription(schedule.getSc_content());
		SimpleDateFormat format  = new SimpleDateFormat("yyyy-MM-ddHH:mm");
		String start = format.format(schedule.getSc_startday());
		System.out.println(start);
		start = start.substring(0, 10)+"T"+start.substring(10, start.length());
		calendar.setStart(start);
		String end = format.format(schedule.getSc_endday());
		end = end.substring(0, 10) + "T" + end.substring(10, end.length());
		calendar.setEnd(end);
		calendar.setType(schedule.getLt_no());
		calendar.setUsername(schedule.getSc_writer());
		calendar.setBackgroundColor(schedule.getSc_bgcolor());
		calendar.setTextColor(schedule.getSc_tcolor());
		calendar.setAllDay(true);
		return calendar;
	}
	
	
}
