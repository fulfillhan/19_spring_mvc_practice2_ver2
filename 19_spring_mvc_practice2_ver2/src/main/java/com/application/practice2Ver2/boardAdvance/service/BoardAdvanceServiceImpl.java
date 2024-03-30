package com.application.practice2Ver2.boardAdvance.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.application.practice2Ver2.boardAdvance.dao.BoardAdanceDAO;
import com.application.practice2Ver2.boardAdvance.dto.MainBoardDTO;
import com.application.practice2Ver2.boardAdvance.dto.ReplyDTO;

@Service
public class BoardAdvanceServiceImpl implements BoardAdvanceService {
	
	@Autowired
	private BoardAdanceDAO boardAdanceDAO;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	private static Logger logger = LoggerFactory.getLogger(BoardAdvanceServiceImpl.class);

	@Override
	public int getAllBoardCnt(Map<String, String> searchCntMap) {
		
		return boardAdanceDAO.getAllBoardCnt(searchCntMap);
	}


	@Override
	public List<MainBoardDTO> getBoardList(Map<String, Object> searchMap) {
		
		return boardAdanceDAO.getBoardList(searchMap);
	}

	@Override
	public void createBoard(MainBoardDTO mainBoardDTO) {
		// 패스워드 암호화
		mainBoardDTO.setPasswd(passwordEncoder.encode(mainBoardDTO.getPasswd()));
		boardAdanceDAO.createBoard(mainBoardDTO);
	}


	@Override
	public MainBoardDTO getBoardDetail(long boardId,boolean checkReadCnt) {
		
		//조회수 증가
		if(checkReadCnt) {
			boardAdanceDAO.udpateReadCnt(boardId);
		}
		return boardAdanceDAO.getBoardDetail(boardId);
	}


	@Override
	public boolean isAuthorized(MainBoardDTO mainBoardDTO) {
		boolean isCheckAuthorize = false;
		String encodedPasswd = boardAdanceDAO.getEncodedPasswd(mainBoardDTO.getBoardId());
		if(passwordEncoder.matches(mainBoardDTO.getPasswd(), encodedPasswd)) {
			isCheckAuthorize = true;
		}
		return isCheckAuthorize;
	}


	@Override
	public void updateBoard(MainBoardDTO mainBoardDTO) {
		
		boardAdanceDAO.updateBoard(mainBoardDTO);
	}


	@Override
	public void deleteBoard(long boardId) {
		boardAdanceDAO.deleteBoard(boardId);
	}


	@Override
	public int allReplyCnt(long boardId) {
		
		return boardAdanceDAO.getReplyCnt(boardId);
	}
	
	
	@Override
	public List<ReplyDTO> getReplyList(long boardId) {
		
		return boardAdanceDAO.getReplyList(boardId);
	}
	
	@Override
	public void createReply(ReplyDTO replyDTO) {
		//passwordEncoder.encode(replyDTO.getPasswd());
		replyDTO.setPasswd(passwordEncoder.encode(replyDTO.getPasswd()));
		boardAdanceDAO.createReply(replyDTO);
		
	}


	@Override
	public ReplyDTO getReplyDetail(long replyId) {
		
		return boardAdanceDAO.getReplyDetail(replyId);
	}


	@Override
	public boolean deleteReply(ReplyDTO replyDTO) {
		String encodedPasswd = boardAdanceDAO.validateReplyUserCheck(replyDTO.getReplyId());
		if(passwordEncoder.matches(replyDTO.getPasswd(),encodedPasswd)) {
			boardAdanceDAO.deleteReply(replyDTO);
			return true;
		}
		return false;
	}


	@Override
	public boolean updateReply(ReplyDTO replyDTO) {
		//패스워드 입력 확인하기
		boolean validateReplyUserCheck = false;
		String encodedPasswd = boardAdanceDAO.validateReplyUserCheck(replyDTO.getReplyId());
		//passwordEncoder.matches(replyDTO.getPasswd(),encodedPasswd);
		if(passwordEncoder.matches(replyDTO.getPasswd(),encodedPasswd)) {
			boardAdanceDAO.updateReply(replyDTO);
			validateReplyUserCheck = true;
		}
		return validateReplyUserCheck;
	}






	
	

	
	
	
	

}
