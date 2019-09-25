package kr.or.ddit.lecture.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.ddit.attach.dao.IAttachDAO;
import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.exception.UserNotFoundException;
import kr.or.ddit.lecture.dao.IMyLectureDAO;
import kr.or.ddit.vo.AttachmentVO;
import kr.or.ddit.vo.HwTutorVO;
import kr.or.ddit.vo.HwstudentVO;
import kr.or.ddit.vo.LecturetutorVO;
import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.ScheduledVO;
import kr.or.ddit.vo.TeamVO;
import kr.or.ddit.vo.UserVO;

@Service
public class MyLectureServiceImpl implements IMyLectureService {
	
	
	@Value("#{appInfo.attachPath}")
	private File saveFolder;

	@Inject
	IMyLectureDAO dao;
	
	@Inject
	IAttachDAO attDao;
	
	@Override
	public List<LecturetutorVO> retrieveMyLectureList(PagingVO<LecturetutorVO> pagingVO) {
		return dao.selectMyLectureList(pagingVO);
	}

	@Override
	public long retrieveMyLectureCount(String user_id) {
		return dao.selectMyLectureCount(user_id);
	}

	@Override
	public LecturetutorVO retrieveMyLecture(String lt_no) {
		return dao.selectMyLecture(lt_no);
	}

	@Override
	public List<ScheduledVO>retrieveMyLectureSchedule(String user_id) {
		return dao.selectMyLectureSchedule(user_id);
	}
	
	@Override
	public List<ScheduledVO> retrieveMyLectureScheduleTutor(String user_id) {
		return dao.selectMyLectureScheduleTutor(user_id);
	}

	@Override
	public TeamVO retrieveMyFriends(String lt_no) {
		return dao.selectMyFriends(lt_no);
	}

	@Override
	public UserVO retrieveMyFriendInfo(String user_id) {
		 UserVO vo = dao.selectMyFriendInfo(user_id);
		 if(Objects.isNull(vo)) throw new UserNotFoundException("존재하지 않는 회원입니다.");
		 return vo;
	}

	@Override
	public LecturetutorVO retrieveMyLectureProgress(String lt_no) {
		return dao.selectMyLectureProgress(lt_no);
	}

	@Override
	public List<AttachmentVO> retrieveMyLectureSharingList(PagingVO<AttachmentVO> paging) {
		return dao.selectMyLectureAttachList(paging);
	}

	@Override
	public long retrieveMyLectureSharingCount(PagingVO<AttachmentVO> pagingVO) {
		return dao.selectMyLectureAttachCount(pagingVO);
	}

	@Override
	public long retrieveMyLectureCountForTutor(String user_id) {
		return dao.selectMyLectureCountForTutor(user_id);
	}

	@Override
	public List<LecturetutorVO> retrieveMyLectureListForTutor(PagingVO<LecturetutorVO> pagingVO) {
		return dao.selectMyLectureListForTutor(pagingVO);
	}

	@Override
	public ServiceResult uploadSharedFile(AttachmentVO attach) {
		int cnt = dao.insertSharedFile(attach);
		ServiceResult result = ServiceResult.FAILED;
		if(cnt>0) {
			processFiles(attach);
			result = ServiceResult.OK;
		}
		return result;
	}
	
	/**
	 * 파일 서버pc에 저장 dash
	 * @param attach
	 */
	private void processFiles(AttachmentVO attach) {
		List<AttachmentVO> attachList = new ArrayList<>();
		attachList.add(attach);
		if(attachList==null || attachList.size()==0) return;
		for(AttachmentVO attc : attachList) {
			File saveFile = new File(saveFolder, attc.getAtt_savename());
			try(
				InputStream is =  attc.getItem().getInputStream();
			){
				FileUtils.copyInputStreamToFile(is, saveFile);
			}catch (IOException e) {
				throw new RuntimeException(e); 
			}
		}
	}
	
	private void processFilesForDelete(String ltOrHs_no) {
	      // 트랜잭션 처리 주의!
	      // 삭제할 파일 처리
        AttachmentVO saved = attDao.selectAttachByLtNo(ltOrHs_no);
        FileUtils.deleteQuietly(new File(saveFolder, saved.getAtt_savename()));
	}
	

	@Override
	public AttachmentVO readyForDownloadFile(int att_no) {
		return dao.selectAttachment(att_no);
	}

	@Override
	public ServiceResult removeSharedFile(int att_no) {
		ServiceResult result = ServiceResult.FAILED;
		AttachmentVO saved = attDao.selectAttach(att_no);
	    FileUtils.deleteQuietly(new File(saveFolder, saved.getAtt_savename()));
		if(attDao.deleteAttach(att_no)>0)
			result = ServiceResult.OK;
		return result;
	}

	@Override
	public ScheduledVO getLectureSchedulebyOne(String lt_no) {
		return dao.selectMyLectureSchedulebyLecture(lt_no);
	}

	@Override
	public ServiceResult uploadHomeWork(HwTutorVO hwTutorVO) {
		ServiceResult result = ServiceResult.FAILED;
		if(dao.insertHomeWorkByTutor(hwTutorVO)>0)
			result = ServiceResult.OK;
		return result;
	}

	@Override
	public List<HwTutorVO> retrieveHomeWorkList(PagingVO<HwTutorVO> pagingVO) {
		return dao.selectHwTutorList(pagingVO);
	}

	@Override
	public long retrieveHomeWorkCount(String lt_no) {
		return dao.selectHwTutorCount(lt_no);
	}

	@Override
	public HwTutorVO retrieveHomeworkInfo(String ht_no) {
		return dao.selectHomeworkByTutor(ht_no);
	}

	@Override
	public ServiceResult removeHomework(String ht_no) {
		ServiceResult result = ServiceResult.FAILED;
		if(dao.deleteHomeworkByTutor(ht_no)>0)
			result = ServiceResult.OK;
		return result;
	}

	@Override
	public ServiceResult modifyHomework(HwTutorVO hwtutorVO) {
		ServiceResult result = ServiceResult.FAILED;
		if(dao.updateHomeworkBytutor(hwtutorVO)>0)
			result = ServiceResult.OK;
		return result;
	}

	@Override
	@Transactional
	public ServiceResult submitHomework(HwstudentVO hwstudentVO, AttachmentVO attach) {
		ServiceResult result = ServiceResult.FAILED;
		if(dao.insertHomeworkByTutee(hwstudentVO)>0) {
			String hs_no = dao.selectHomeworkByTutor(hwstudentVO.getHt_no()).getHs_no();
			attach.setLt_no(hs_no);
			if(dao.insertSharedFile(attach)>0) {
				processFiles(attach);
				result = ServiceResult.OK;
			}
		}
		return result;
	}

	@Override
	@Transactional
	public ServiceResult removeHomeworkByTutee(String hs_no) {
		ServiceResult result = ServiceResult.FAILED;
		if(dao.deleteHomeworkByTutee(hs_no)>0) {
			processFilesForDelete(hs_no);
			if(attDao.deleteAttachByLtNo(hs_no)>0)	
				result = ServiceResult.OK;
		}
		return result;
	}

	@Override
	public List<HwstudentVO> retrieveSubmittedHomeworkList(String ht_no) {
		return dao.selectSubmittedHomeworkList(ht_no);
	}

	
}
