package kr.or.ddit.lecture.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.lecture.dao.ICategoryDAO;
import kr.or.ddit.vo.CategoriesVO;
import kr.or.ddit.vo.CategoryVO;

@Service
public class CategoryServiceImpl implements ICategoryService{
	
	@Inject
	ICategoryDAO dao;

	@Override
	public ServiceResult createCategory(CategoriesVO categoriesVO) {
		ServiceResult result = ServiceResult.FAILED;
		int cnt = dao.insertCategory(categoriesVO);
		if(cnt > 0) result = ServiceResult.OK;
		return result;
	}
	
	@Override
	public List<CategoryVO> retriveCategoryList() {
		return dao.selectCategoryList();
	}

}
