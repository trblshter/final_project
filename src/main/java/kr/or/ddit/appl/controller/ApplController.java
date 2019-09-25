package kr.or.ddit.appl.controller;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import kr.or.ddit.appl.service.IApplService;
import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.lecture.service.ILectureService;
import kr.or.ddit.lecture.service.ITeamService;
import kr.or.ddit.vo.ApplVO;
import kr.or.ddit.vo.LecturetutorVO;
import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.TeamVO;

@Controller
public class ApplController {
	
	@Inject
	IApplService service;
	@Inject
	ITeamService teamService;
	@Inject
	ILectureService lectureService;
	
	@PostMapping(value="/appl/applInsert.do",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String insert(@ModelAttribute ApplVO applVO, Model model) {
		String message = null;
		TeamVO teamVO = teamService.retrieveTeam(applVO.getLt_no());
		
		LecturetutorVO lecturetutorVO = lectureService.retrieveLectureTutor(applVO.getLt_no());
		
		if(lecturetutorVO.getLt_completed() == 1) {
			message = "모집 완료했습니다";
		}else {
			ApplVO appl = service.retrieveAppl(applVO);
			if(appl!=null) {
				message = "이미 신청된 강의입니다";
			}else {
				ServiceResult result = service.createAppl(applVO);
				if(ServiceResult.OK.equals(result)) {
					message = "신청이 완료되었습니다";
				}
			}
			
			teamVO.setLt_no(applVO.getLt_no());
			ServiceResult result = null;
			if(teamVO != null) {
				if(teamVO.getTeam_member1() == null) {
					teamVO.setTeam_member1(applVO.getAppl_user());
					
					result = teamService.updateTeam(teamVO);
				}else {
					if(applVO.getAppl_user().equals(teamVO.getTeam_member1())) {
						message = "이미 신청하셨습니다";
					}else {
						if(teamVO.getTeam_member2() == null) {
							teamVO.setTeam_member2(applVO.getAppl_user());
							
							result = teamService.updateTeam(teamVO);
						}else {
							if(applVO.getAppl_user().equals(teamVO.getTeam_member2())) {
								message = "이미 신청하셨습니다";
							}else {
								if(teamVO.getTeam_member3() == null) {
									teamVO.setTeam_member3(applVO.getAppl_user());
									
									result = teamService.updateTeam(teamVO);
								}else {
									if(applVO.getAppl_user().equals(teamVO.getTeam_member3())) {
										message = "이미 신청하셨습니다";
									}else {
										if(teamVO.getTeam_member4() == null) {
											teamVO.setTeam_member4(applVO.getAppl_user());
											
											result = teamService.updateTeam(teamVO);
										}else {
											message = "더이상 신청할 수 업습니다";
										}
									}
								}
							}
						}
					}
				}
			}
			
			teamVO = teamService.retrieveTeam(applVO.getLt_no());
			String[] teamArray = new String[] {teamVO.getTeam_member1(), teamVO.getTeam_member2(), teamVO.getTeam_member3(), teamVO.getTeam_member4()};
			
			int cnt = 0;  
			
			for(int i = 0; i < teamArray.length; i++) {
				if(teamArray[i] != null) {
					cnt += 1;
				}else if(teamArray[i] == null) {
					break;
				}
			}
			
			if(lecturetutorVO.getLt_recruit() == cnt) {
				ServiceResult result2 = lectureService.updateLectureTutor(lecturetutorVO);
			}
		}
		
//		model.addAttribute("message", message);
		
		return message;
	}
	
	@GetMapping(value="/tutee/mypage/appl")
	public String applList() {
		return "tutee/mypage/appl";
	}
	
	@GetMapping(value="/appl/applList.do",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public PagingVO<ApplVO> applList(
			Model model,
			@RequestParam(name="page", defaultValue="1", required=false) long currentPage,
			@RequestParam(required=false) String searchWord
			) {
		PagingVO<ApplVO> pagingVO = new PagingVO<>();
		pagingVO.setCurrentPage(currentPage);
		pagingVO.setSearchWord(searchWord);
		
		long totalRecord = service.retrieveApplCount(pagingVO);
		pagingVO.setTotalRecord(totalRecord);
		
		List<ApplVO> list = service.retrieveApplList(pagingVO);
		pagingVO.setDataList(list);
		
		model.addAttribute("pagingVO", pagingVO);
		
		return pagingVO;
	}
	
}
