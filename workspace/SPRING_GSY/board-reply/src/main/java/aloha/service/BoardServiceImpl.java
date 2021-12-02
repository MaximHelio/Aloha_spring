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
	
	// 업로드 경로
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
		
		
		//	글 등록 		
		mapper.insert(board);
		
		MultipartFile[] files = board.getFile();

		//	실제 파일 업로드
		List<BoardFile> fileList =  fileUtils.uploadFiles(files, uploadPath);

		//	DB 파일 업로드
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

		//	실제 파일 업로드
		List<BoardFile> fileList =  fileUtils.uploadFiles(files, uploadPath);

		//	DB 파일 업로드
		for (BoardFile boardFile : fileList) {
			boardFile.setBoardNo(boardNo);
			mapper.uploadFile(boardFile);
		}
	}

	@Override
	public void delete(Integer boardNo) throws Exception {
		
		List<BoardFile> fileList = mapper.readFileList(boardNo);
		
		// 삭제할 파일들의 경로 목록
		List<String> deletePath = new LinkedList<String>();
		
		for (BoardFile boardFile : fileList) {
			deletePath.add(boardFile.getFullName());
		}
		
		// 실제 파일들 삭제
		fileUtils.deleteFiles(deletePath);
		
		// DB 파일정보 삭제
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
		
		// 실제 파일 삭제
		fileUtils.deleteFile(deletePath);
		
		// DB 파일 정보 삭제
		mapper.deleteFile(fileNo);
		
	}

	@Override
	public BoardDTO list(Page page) throws Exception {
		
		String keyword = page.getKeyword();
		Integer totalCount = page.getTotalCount();
		Integer rowsPerPage = page.getRowsPerPage();
		Integer pageCount = page.getPageCount();
		Integer pageNum = page.getPageNum();
		
		
		log.info("검색어:" + keyword);
		// 검색어
		keyword = keyword == null ? "" : keyword;
		
		// 조회된 게시글 수
		if (totalCount == 0)
			totalCount = mapper.totalCount(keyword);
		
		// 페이지 당 노출 게시글 수 
		if (rowsPerPage == 0)
			rowsPerPage = Page.ROWS_PER_PAGE;
		
		//노출 페이지 수
		if ( pageCount == 0 )
			pageCount = Page.PAGE_COUNT;
		
		// 현재 페이지 번호
		if (pageNum == 0)
			pageNum = Page.PAGE_NUM;
		
		page = new Page(pageNum, rowsPerPage, pageCount, totalCount, keyword);
		
		// 요청 시간
		long beforeTime = System.currentTimeMillis();
		
		List<Board> list = mapper.listWithPage(page);
		
		long afterTime = System.currentTimeMillis();
		long diffTime = (afterTime - beforeTime);
		
		//log.info("afterTime: " + afterTime);
		//log.info("걸린 시간 : " + diffTime + "(ms)");
		
		page.setSearchTime(diffTime);
		
		boardDTO.setBoardList(list);
		boardDTO.setPage(page);
		
		return boardDTO;
	}

}
