package com.application.practice2Ver2.boardAdvance.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.application.practice2Ver2.boardAdvance.dto.MainBoardDTO;

public interface BoardAdvanceService {

	public int getAllBoardCnt(Map<String, String> searchCntMap);

	//public List<MainBoardDTO> getBoardList(HashMap<String, Object> searchMap);

	public List<MainBoardDTO> getBoardList(Map<String, Object> searchMap);

	public void createBoard(MainBoardDTO mainBoardDTO);

	public MainBoardDTO getBoardDetail(long boardId, boolean checkReadCnt);

	public boolean isAuthorized(MainBoardDTO mainBoardDTO);

}
