package com.application.practice2Ver2.boardAdvance.dto;



import java.util.Date;

import lombok.Data;

@Data
public class MainBoardDTO {
	
	private long boardId;
	private String writer;
	private String subject;
	private String content;
	private String passwd;
	private int readCnt;
	private Date enrollAt;
	
	

}
