package aloha.domain;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class BoardDTO {
	
	private Board board;
	private Page page;
	private List<Board> boardList;
	private List<BoardFile> fileList;
	
	
}
