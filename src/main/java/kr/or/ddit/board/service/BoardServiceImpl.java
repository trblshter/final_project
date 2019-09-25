package kr.or.ddit.board.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.ddit.attach.dao.IAttachDAO;
import kr.or.ddit.board.dao.IBoardDAO;
import kr.or.ddit.board.exception.BoardException;
import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.vo.AttachmentVO;
import kr.or.ddit.vo.BoardVO;
import kr.or.ddit.vo.PagingVO;

@Service
public class BoardServiceImpl implements IBoardService {
	
	@Inject
	IBoardDAO iBoardDAO;
	@Inject
	IAttachDAO attachDAO;
	@Value("#{appInfo.attachPath}")
	private File saveFolder;
	@PostConstruct
	public void init() {
		if(saveFolder == null || saveFolder.isFile())
			throw new BoardException("appInfo.properties 에서 첨부 파일 저장 위치 확인.");
		if(!saveFolder.exists()) saveFolder.mkdirs();
	}
	
	private void processFiles(BoardVO board) {
		// 삭제할 파일 처리		
		int[] delFiles = board.getDelFiles();
		if(delFiles!=null && delFiles.length>0) {
			for(int delFileNo : delFiles) {
				AttachmentVO saved = attachDAO.selectAttach(delFileNo);
				attachDAO.deleteAttach(delFileNo);
				FileUtils.deleteQuietly(new File(saveFolder, saved.getAtt_savename()));
			}
		}
		// 업로드된 파일 처리
		List<AttachmentVO> attatchList = board.getAttachList();
		if(attatchList==null || attatchList.size()==0) return;
		for(AttachmentVO attatch : attatchList) {
			attatch.setBo_no(board.getBo_no());
			attachDAO.insertAttach(attatch);
			File saveFile = new File(saveFolder, attatch.getAtt_savename());
			try(
				InputStream is = attatch.getItem().getInputStream();
			){
				FileUtils.copyInputStreamToFile(is, saveFile);
			}catch(IOException e) {
				throw new BoardException(e);
			}
		}
	}
	
	// 01. 게시글 쓰기
	@Transactional
	@Override
	public ServiceResult create(BoardVO vo) throws Exception {
		String bo_title = vo.getBo_title();
		String bo_content = vo.getBo_content();
		String bo_writer = vo.getBo_writer();
		// *태그문자 처리(< ==> &lt;, > ==> &gt;)
		// replace(A, B) A를 B로 변경
		
		bo_title = bo_title.replace("<", "&lt;");
		bo_title = bo_title.replace(">", "&gt;");
		bo_writer = bo_writer.replace("<", "&lt;");				
		bo_writer = bo_writer.replace(">", "&gt;");
		// *공백 문자 처리
		bo_title = bo_title.replace(" ",	"&nbsp;&nbsp;"); 
		bo_writer = bo_writer.replace(" ",	  "&nbsp;&nbsp;"); 
		// *줄바꿈 문자 처리
//		bo_content = bo_content.replace("\n", "<br>");
		vo.setBo_title(bo_title);
		vo.setBo_content(bo_content);
		vo.setBo_writer(bo_writer);
		
		int cnt = iBoardDAO.insert(vo);		
		ServiceResult result = ServiceResult.FAILED;
		if(cnt > 0) {
			processFiles(vo); // 첨부파일 처리
			result = ServiceResult.OK;			
		}
		return result;
	}

	// 02. 게시글 상세보기
	@Override
	public BoardVO read(String bo_no) throws Exception {
		return iBoardDAO.view(bo_no);
	}

	// 03. 게시글 수정
	@Transactional
	@Override 
	public ServiceResult update(BoardVO board) { // 수정
		BoardVO savedBoard = iBoardDAO.view(board.getBo_no());
		if(savedBoard==null) throw new BoardException(board.getBo_no() + " 파일 없음1");
		
		String bo_title = board.getBo_title();
		String bo_content = board.getBo_content();
		String bo_writer = board.getBo_writer();
		// *태그문자 처리(< ==> &lt;, > ==> &gt;)
		// replace(A, B) A를 B로 변경
		
		bo_title = bo_title.replace("<", "&lt;");
		bo_title = bo_title.replace(">", "&gt;");
		bo_writer = bo_writer.replace("<", "&lt;");				
		bo_writer = bo_writer.replace(">", "&gt;");
		// *공백 문자 처리
		bo_title = bo_title.replace(" ",	"&nbsp;&nbsp;"); 
		bo_writer = bo_writer.replace(" ",	  "&nbsp;&nbsp;"); 
		// *줄바꿈 문자 처리
//		bo_content = bo_content.replace("\n", "<br>");
		board.setBo_title(bo_title);
		board.setBo_content(bo_content);
		board.setBo_writer(bo_writer);
		
		
		ServiceResult result = ServiceResult.FAILED;
		
		int cnt = iBoardDAO.update(board);
		if(cnt>0) {
			processFiles(board);
			result = ServiceResult.OK;
		}
		return result;
	}
		
		
	// 04. 게시글 삭제
//	@Override
//	public void delete(String bo_no) throws Exception {
//		iBoardDAO.delete(bo_no);
//	}
//	
	@Transactional
	@Override
	public ServiceResult delete(String bo_no) {
		BoardVO savedBoard = iBoardDAO.view(bo_no);
		if(savedBoard==null) throw new BoardException(bo_no +" 파일 없음2.");
		ServiceResult result = ServiceResult.FAILED;
		int cnt = iBoardDAO.delete(bo_no); // 파일의 metadate 만 삭제
		if(cnt > 0) {
			List<AttachmentVO> attatchList = savedBoard.getAttachList();
			if(attatchList!=null) {
				for(AttachmentVO attatch : attatchList) {
					
					FileUtils.deleteQuietly(new File(saveFolder, attatch.getAtt_savename()));
				}
			}
			result = ServiceResult.OK;
		}
		result = ServiceResult.INVALIDPASSWORD;					
		return result;
	}
	
	
	// 05. 게시글 전체 목록
	@Override
	public List<BoardVO> listAll(String type) throws Exception {
		return iBoardDAO.listAll(type);
	}

	// 06. 게시글 조회수 증가
	@Override
	public void increaseViewcnt(String bo_no, HttpSession session) throws Exception {
		long update_time = 0;
		// 세션에 저장된 조회시간 검색
		// 최초로 조회할 경우 세션에 저장된 값이 없기 때문에 if문은 실행 안함.
		if(session.getAttribute("update_time_"+bo_no) != null) {
									// 세션에서 읽어오기
			update_time = (long)session.getAttribute("update_time_"+bo_no);
		}
		// 시스템의 현재시간을 current_time에 저장
		long current_time = System.currentTimeMillis();
		// 일정시간이 경과 후 조회수 증가 처리 24*60*60*1000(24시간)
		// 시스템현재시간 - 열람시간 > 일정시간(조회수 증가가 가능하도록 지정한 시간)
		if(current_time - update_time > 5*1000) {
			iBoardDAO.increaseViewcnt(bo_no);
			// 세션에 시간을 저장 : "update_time_"+bo_no는 다른변수와 중복되지 않게 명명한 것
			session.setAttribute("update_time_"+bo_no, current_time);
		}
		
		//===================================================================
		
//		BoardVO board = iBoardDAO.view(bo_no);
//		if(board == null) throw new BoardException(bo_no + "글이 없음.");
//		iBoardDAO.increaseViewcnt(bo_no);
//		return board;
	}

	@Override
	public long retrieveBoardCount(PagingVO<BoardVO> pagingVO) {
		return iBoardDAO.selectBoardCount(pagingVO);
	}

	@Override
	public List<BoardVO> retrieveBoardList(PagingVO<BoardVO> pagingVO) {
		return iBoardDAO.selectBoardList(pagingVO);
	}
	
	
	@Override
	public AttachmentVO download(int att_no) {
		AttachmentVO attatch = attachDAO.selectAttach(att_no);
		if(attatch==null) throw new BoardException(att_no+"파일이 없음.");
		attachDAO.incrementDownCount(att_no);
		return attatch;
	}

	@Override
	public List<BoardVO> retrieveMyBoardList(PagingVO<BoardVO> pagingVO) {
		return iBoardDAO.selectUserBoard(pagingVO);
	}

	@Override
	public long retrieveMyBoardCount(PagingVO<BoardVO> pagingVO) {
		return iBoardDAO.selectMyBoardCount(pagingVO);
	}
}