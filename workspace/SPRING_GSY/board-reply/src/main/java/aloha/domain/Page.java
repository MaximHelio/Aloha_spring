package aloha.domain;

import lombok.Data;

@Data
public class Page {
	
	/* �⺻�� */
	public static final int PAGE_NUM = 1;
	public static final int ROWS_PER_PAGE = 10;
	public static final int PAGE_COUNT = 10;
	public static final int FIRST_PAGE = 1;
	
	/* �ʼ� ���� */
	private int pageNum;			// ���� ������ ��ȣ
	private int rowsPerPage;		// ������ �� �Խù� ��
	private int pageCount;			// ���� ������ ����
	private int totalCount;			// ��ü ������ ����
	
	/* ���� ���� */
	private int startPage;			// (����Ǵ� ������������)���� ������ ��ȣ
	private int endPage;			// (����Ǵ� ������������)�� ������ ��ȣ

	private int firstPage;			// ù��° ������ ��ȣ
	private int	lastPage;			// ������ ������ ��ȣ
	
	private int prev;				// ���� ������ ��ȣ
	private int next;				// ���� ������ ��ȣ
	
	private int startRowIndex;		// ���� �� index
	
	private String keyword;			// �˻���
	
	private long searchTime;		// �˻� �ɸ� �ð�
	
	/* ������ */
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
