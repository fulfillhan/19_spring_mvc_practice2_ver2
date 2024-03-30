package com.application.practice2Ver2.boardAdvance.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.application.practice2Ver2.boardAdvance.dto.MainBoardDTO;
import com.application.practice2Ver2.boardAdvance.dto.ReplyDTO;

public interface BoardAdvanceService {

	public int getAllBoardCnt(Map<String, String> searchCntMap);

	//public List<MainBoardDTO> getBoardList(HashMap<String, Object> searchMap);

	public List<MainBoardDTO> getBoardList(Map<String, Object> searchMap);

	public void createBoard(MainBoardDTO mainBoardDTO);

	public MainBoardDTO getBoardDetail(long boardId, boolean checkReadCnt);

	public boolean isAuthorized(MainBoardDTO mainBoardDTO);

	public void updateBoard(MainBoardDTO mainBoardDTO);

	public void deleteBoard(long boardId);

	public void createReply(ReplyDTO replyDTO);

	public int allReplyCnt(long boardId);

	public List<ReplyDTO> getReplyList(long boardId);

	public ReplyDTO getReplyDetail(long replyId);

	public boolean deleteReply(ReplyDTO replyDTO);

	public boolean updateReply(ReplyDTO replyDTO);

}
