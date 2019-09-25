package kr.or.ddit.board.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import kr.or.ddit.vo.BoardVO;
import kr.or.ddit.vo.PagingVO;

@Repository
public interface IBoardDAO {
	// 01. 게시글 작성
	public int insert(BoardVO vo);	
	
	// 02. 게시글 상세보기
	public BoardVO view(String bo_no);
	
	// 03. 게시글 수정
	public int update(BoardVO vo);
	
	// 04. 게시글 삭제
	public int delete(String bo_no);
	
	// 05. 게시글 전체 목록
	public List<BoardVO> listAll(String type);
	
	// 06. 게시글 조회 증가
	public void increaseViewcnt(String bo_no);
		
	// 페이징
	public long selectBoardCount(PagingVO<BoardVO> pagingVO);
	public List<BoardVO> selectBoardList(PagingVO<BoardVO> pagingVO);
	
	// 마이페이지
	public List<BoardVO> selectUserBoard(PagingVO<BoardVO> pagingVO); // (사용자 아이디)
	public long selectMyBoardCount(PagingVO<BoardVO> pagingVO);
}
