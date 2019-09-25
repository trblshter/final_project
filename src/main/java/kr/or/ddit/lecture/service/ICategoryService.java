package kr.or.ddit.lecture.service;

import java.util.List;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.vo.CategoriesVO;
import kr.or.ddit.vo.CategoryVO;

public interface ICategoryService {
	public ServiceResult createCategory(CategoriesVO categoriesVO);
	/**
	 * 카테고리 리스트 조회
	 * @return
	 */
	public List<CategoryVO> retriveCategoryList();
}
