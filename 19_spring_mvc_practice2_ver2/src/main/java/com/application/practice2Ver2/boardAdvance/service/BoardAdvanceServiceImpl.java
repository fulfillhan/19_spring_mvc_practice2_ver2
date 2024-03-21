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


	
	
	
	

}
