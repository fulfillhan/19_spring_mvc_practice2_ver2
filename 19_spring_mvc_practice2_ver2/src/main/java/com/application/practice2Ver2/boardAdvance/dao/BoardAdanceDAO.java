package com.application.practice2Ver2.boardAdvance.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.application.practice2Ver2.boardAdvance.dto.MainBoardDTO;
import com.application.practice2Ver2.boardAdvance.dto.ReplyDTO;

@Mapper
public interface BoardAdanceDAO {

	public int getAllBoardCnt(Map<String, String> searchCntMap);

	public List<MainBoardDTO> getBoardList(Map<String, Object> searchMap);
	//키워드, 검색어, currentPageNumber, onePageViewCnt

	public void createBoard(MainBoardDTO mainBoardDTO);

	public MainBoardDTO getBoardDetail(long boardId);

	public void udpateReadCnt(long boardId);

	public String getEncodedPasswd(long boardId);

	public void updateBoard(MainBoardDTO mainBoardDTO);

	public void deleteBoard(long boardId);

	public void createReply(ReplyDTO replyDTO);
	
	public int getReplyCnt(long boardId);

	public List<ReplyDTO> getReplyList(long boardId);

	public ReplyDTO getReplyDetail(long replyId);

	public void deleteReply(ReplyDTO replyDTO);

	public String validateReplyUserCheck(long replyId);

	public void updateReply(ReplyDTO replyDTO);


	

}
