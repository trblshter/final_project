package kr.or.ddit.board.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.vo.AttachmentVO;
import kr.or.ddit.vo.BoardVO;
import kr.or.ddit.vo.PagingVO;

public interface IBoardService {
	// 작성
	public ServiceResult create(BoardVO vo) throws Exception;
	
	// 상세보기
	public BoardVO read(String bo_no) throws Exception;
		
	// 수정
	public ServiceResult update(BoardVO vo) throws Exception;
	
	// 삭제
	ServiceResult delete(String bo_no) throws Exception;
	
	// 전체 목록
	public List<BoardVO> listAll(String type) throws Exception;
	
	// 조회
	public void increaseViewcnt(String bo_no, HttpSession session) throws Exception;
//	public BoardVO retrieveBoard(long bo_no);
	
	/**
	 * 검색 조건에 맞는 게시글 수 조회
	 * @param pagingVO
	 * @return
	 */
	public long retrieveBoardCount(PagingVO<BoardVO> pagingVO);
	
	/**
	 * 검색 조건에 맞는 게시글 목록 조회
	 * @param pagingVO
	 * @return
	 */
	public List<BoardVO> retrieveBoardList(PagingVO<BoardVO> pagingVO);
	
	/**
	 * 다운로드할 첨부파일 조회
	 * @param att_no
	 * @return
	 */
	public AttachmentVO download(int att_no);
	
	/**
	 * 사용자 작성글
	 * @param bo_writer
	 * @return
	 */
	public List<BoardVO> retrieveMyBoardList(PagingVO<BoardVO> pagingVO);
	
	/**
	 * 사용자가 작성한 게시글의 검색 조건에 맞는 게시글 수 조회
	 * @param pagingVO
	 * @return
	 */
	public long retrieveMyBoardCount(PagingVO<BoardVO> pagingVO);
}
