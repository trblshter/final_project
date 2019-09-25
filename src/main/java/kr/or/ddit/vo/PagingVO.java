package kr.or.ddit.vo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PagingVO<T> {
	public PagingVO(int screenSize, int blockSize) {
		super();
		this.screenSize = screenSize;
		this.blockSize = blockSize;
	}
	private int screenSize=10;
	private int blockSize=5;
	private long totalRecord;
	private long totalPage;
	private long currentPage;
	private long startRow;
	private long endRow;
	private long startPage;
	private long endPage;
	private List<T> dataList;
	private String searchType;
	private String searchWord;
	private T searchVO;
	
	private String type;  // 게시판 구분
	private String bo_writer; // 게시판 작성자
	
	private String funcName="paging";
	
	
	public void setType(String type) {
		this.type = type;
	}
	public void setBo_writer(String bo_writer) {
		this.bo_writer = bo_writer;
	}
	
	public void setDataList(List<T> dataList) {
		this.dataList = dataList;
	}
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	public void setSearchWord(String searchWord) {
		this.searchWord = searchWord;
	}
	public void setSearchVO(T searchVO) {
		this.searchVO = searchVO;
	}
	public void setFuncName(String funcName) {
		this.funcName = funcName;
	}
	
	/**
	 * totalRecord 와  totalPage 를 연산
	 * @param totalRecord
	 */
	public void setTotalRecord(long totalRecord) {
		this.totalRecord = totalRecord;
		this.totalPage = (totalRecord + (screenSize-1)) / screenSize;
	}
	
	public void setCurrentPage(long currentPage) {
		this.currentPage = currentPage;
		this.endRow = screenSize * currentPage;
		this.startRow = endRow - (screenSize-1);
		this.endPage = ((currentPage+(blockSize-1))/blockSize)*blockSize;
		this.startPage = endPage - (blockSize - 1);
	}
	
	String pattern = "<a href='#' onclick='%s(%d)'>[%s]</a>";
//	@JsonProperty("paginHTML")
	public String getPagingHTML(){
		StringBuffer html = new StringBuffer();
		if(startPage>1)
			html.append(String.format(pattern, funcName, (startPage-1), "이전"));
		endPage = endPage > totalPage? totalPage : endPage;
		for(long i = startPage; i<=endPage; i++) {
			if(currentPage==i) {
				html.append(i);
			}else {
				html.append(String.format(pattern, funcName, i, i+""));
			}
		}
		if(endPage<totalPage)
			html.append(String.format(pattern, funcName, (endPage+1), "다음"));
		return html.toString();
	}
	
	String bootstrapPtrn = "<li %s><a class='page-link' href='#' onclick='%s'>%s</a></li>";
//	@JsonProperty("pagingHTMLForBS")
	public String getPagingHTMLForBS(){
		System.out.println("startPage = " + startPage);
		System.out.println("endPage = " + endPage);
		StringBuffer html = new StringBuffer();
		html.append("<nav class='d-flex justify-content-center'>");
		html.append("<ul class='pagination'>");
		
		html.append(String.format(bootstrapPtrn, startPage>1 ? "class='page-item'" : "class='disabled'", startPage>1?funcName +"("+ (startPage-1) +");" : "return false;", "<span aria-hidden='true'>«</span>"));
		endPage = endPage > totalPage? totalPage : endPage;
		for(long i = startPage; i<=endPage; i++) {
			html.append(String.format(bootstrapPtrn, currentPage==i?"class='active'":"", funcName +"(" + i + ");", i+ (currentPage==i?"<span class='sr-only'>(current)</span>":"")));
		}
		
		html.append(String.format(bootstrapPtrn, endPage<totalPage? "class='page-item'" :"class='disabled'", endPage<totalPage?funcName +"("+ (endPage+1) +");" : "return false;", "<span aria-hidden='true'>»</span>"));
		html.append("</ul>");
		html.append("</nav>");
		return html.toString();
	}
}