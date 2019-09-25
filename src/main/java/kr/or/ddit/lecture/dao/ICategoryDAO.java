package kr.or.ddit.lecture.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import kr.or.ddit.vo.CategoriesVO;
import kr.or.ddit.vo.CategoryVO;

@Repository
public interface ICategoryDAO {
	public int insertCategory(CategoriesVO categoriesVO);
	public List<CategoryVO> selectCategoryList();
}	
