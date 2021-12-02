package aloha.service;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import aloha.domain.Board;
import aloha.domain.BoardDTO;
import aloha.domain.BoardFile;
import aloha.domain.Page;
import aloha.mapper.BoardMapper;
import aloha.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BoardServiceImpl implements BoardService {
	
	// ���ε� ���
	@Value("${upload.path}")
	private String uploadPath;
	
	@Autowired
	private BoardMapper mapper;
	
	@Autowired
	private FileUtils fileUtils;
	
	@Autowired
	private BoardDTO boardDTO;
	
	@Override
	public List<Board> list() throws Exception {
		return mapper.list();
	}

	@Override
	public void insert(Board board) throws Exception {
		
		
		//	�� ��� 		
		mapper.insert(board);
		
		MultipartFile[] files = board.getFile();

		//	���� ���� ���ε�
		List<BoardFile> fileList =  fileUtils.uploadFiles(files, uploadPath);

		//	DB ���� ���ε�
		for (BoardFile boardFile : fileList) {
			mapper.uploadFile(boardFile);
		}
		
//		for (MultipartFile file : files) {
//			log.info("originalFileName : " + file.getOriginalFilename());
//			log.info("contentType : " + file.getContentType());
//			log.info("size : " + file.getSize());
//		}
		
	}

	@Override
	public Board read(Integer boardNo) throws Exception {
		return mapper.read(boardNo);
	}

	@Override
	public void update(Board board) throws Exception {
		
		Integer boardNo = board.getBoardNo();
		mapper.update(board);
		
		MultipartFile[] files = board.getFile();

		//	���� ���� ���ε�
		List<BoardFile> fileList =  fileUtils.uploadFiles(files, uploadPath);

		//	DB ���� ���ε�
		for (BoardFile boardFile : fileList) {
			boardFile.setBoardNo(boardNo);
			mapper.uploadFile(boardFile);
		}
	}

	@Override
	public void delete(Integer boardNo) throws Exception {
		
		List<BoardFile> fileList = mapper.readFileList(boardNo);
		
		// ������ ���ϵ��� ��� ���
		List<String> deletePath = new LinkedList<String>();
		
		for (BoardFile boardFile : fileList) {
			deletePath.add(boardFile.getFullName());
		}
		
		// ���� ���ϵ� ����
		fileUtils.deleteFiles(deletePath);
		
		// DB �������� ����
		mapper.delete(boardNo);
		
	}

	@Override
	public List<Board> list(String keyword) throws Exception {
		keyword = ( keyword == null ? "" : keyword);

		return mapper.search(keyword);
	}

	@Override
	public List<BoardFile> readFileList(Integer boardNo) throws Exception {
		return mapper.readFileList(boardNo);
	}

	@Override
	public BoardFile readFile(Integer fileNo) throws Exception {
		return mapper.readFile(fileNo);
	}

	@Override
	public void deleteFile(Integer fileNo) throws Exception {
		
		BoardFile boardFile = mapper.readFile(fileNo);
		String deletePath = boardFile.getFileName();
		
		// ���� ���� ����
		fileUtils.deleteFile(deletePath);
		
		// DB ���� ���� ����
		mapper.deleteFile(fileNo);
		
	}

	@Override
	public BoardDTO list(Page page) throws Exception {
		
		String keyword = page.getKeyword();
		Integer totalCount = page.getTotalCount();
		Integer rowsPerPage = page.getRowsPerPage();
		Integer pageCount = page.getPageCount();
		Integer pageNum = page.getPageNum();
		
		
		log.info("�˻���:" + keyword);
		// �˻���
		keyword = keyword == null ? "" : keyword;
		
		// ��ȸ�� �Խñ� ��
		if (totalCount == 0)
			totalCount = mapper.totalCount(keyword);
		
		// ������ �� ���� �Խñ� �� 
		if (rowsPerPage == 0)
			rowsPerPage = Page.ROWS_PER_PAGE;
		
		//���� ������ ��
		if ( pageCount == 0 )
			pageCount = Page.PAGE_COUNT;
		
		// ���� ������ ��ȣ
		if (pageNum == 0)
			pageNum = Page.PAGE_NUM;
		
		page = new Page(pageNum, rowsPerPage, pageCount, totalCount, keyword);
		
		// ��û �ð�
		long beforeTime = System.currentTimeMillis();
		
		List<Board> list = mapper.listWithPage(page);
		
		long afterTime = System.currentTimeMillis();
		long diffTime = (afterTime - beforeTime);
		
		//log.info("afterTime: " + afterTime);
		//log.info("�ɸ� �ð� : " + diffTime + "(ms)");
		
		page.setSearchTime(diffTime);
		
		boardDTO.setBoardList(list);
		boardDTO.setPage(page);
		
		return boardDTO;
	}

}
