package aloha.domain;

import lombok.Data;

@Data
public class Page {
	
	/* �⺻�� */
	private static final int PAGE_NUM = 1;
	private static final int ROWS_PER_PAGE = 10;
	private static final int PAGE_COUNT = 10;
	private static final int FIRST_PAGE = 1;
	
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
}
