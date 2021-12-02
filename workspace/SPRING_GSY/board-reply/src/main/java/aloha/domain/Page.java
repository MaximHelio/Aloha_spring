package aloha.domain;

import lombok.Data;

@Data
public class Page {
	
	/* 기본값 */
	public static final int PAGE_NUM = 1;
	public static final int ROWS_PER_PAGE = 10;
	public static final int PAGE_COUNT = 10;
	public static final int FIRST_PAGE = 1;
	
	/* 필수 정보 */
	private int pageNum;			// 현재 페이지 번호
	private int rowsPerPage;		// 페이지 당 게시물 수
	private int pageCount;			// 노출 페이지 개수
	private int totalCount;			// 전체 데이터 개수
	
	/* 수식 정보 */
	private int startPage;			// (노출되는 페이지에서의)시작 페이지 번호
	private int endPage;			// (노출되는 페이지에서의)끝 페이지 번호

	private int firstPage;			// 첫번째 페이지 번호
	private int	lastPage;			// 마지막 페이지 번호
	
	private int prev;				// 이전 페이지 번호
	private int next;				// 다음 페이지 번호
	
	private int startRowIndex;		// 시작 글 index
	
	private String keyword;			// 검색어
	
	private long searchTime;		// 검색 걸린 시간
	
	/* 생성자 */
	public Page() {
		this.pageNum = PAGE_NUM;
		this.rowsPerPage = ROWS_PER_PAGE;
		this.pageCount = PAGE_COUNT;
	}
	public Page(int pageNum, int rowsPerPage, int pageCount, int totalCount, String keyword) {
		this.pageNum = pageNum;
		this.rowsPerPage = rowsPerPage;
		this.pageCount = pageCount;
		this.totalCount = totalCount;
		this.keyword = keyword;
		
		this.prev = pageNum - 1;
		this.next = pageNum + 1;
		
		this.firstPage = FIRST_PAGE;
		
		if ( totalCount % rowsPerPage == 0)
			this.lastPage = totalCount / rowsPerPage;
		else
			this.lastPage = (totalCount / rowsPerPage) + 1;
		
		this.startPage = ( (pageNum - 1)/ pageCount ) * pageCount + 1;
		this.endPage = (((pageNum -1) / pageCount) + 1) * pageCount;
		
		if (this.endPage > this.lastPage)
			this.endPage = this.lastPage;
		
		this.startRowIndex = ( pageNum - 1 ) * rowsPerPage;
	}
}
