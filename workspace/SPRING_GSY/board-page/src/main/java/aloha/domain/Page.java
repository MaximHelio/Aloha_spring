package aloha.domain;

import lombok.Data;

@Data
public class Page {
	
	/* 기본값 */
	private static final int PAGE_NUM = 1;
	private static final int ROWS_PER_PAGE = 10;
	private static final int PAGE_COUNT = 10;
	private static final int FIRST_PAGE = 1;
	
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
}
