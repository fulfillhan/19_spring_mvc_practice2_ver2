package com.application.practice2Ver2.boardAdvance.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.application.practice2Ver2.boardAdvance.dto.MainBoardDTO;

@Mapper
public interface BoardAdanceDAO {

	public int getAllBoardCnt(Map<String, String> searchCntMap);

	public List<MainBoardDTO> getBoardList(Map<String, Object> searchMap);
	//키워드, 검색어, currentPageNumber, onePageViewCnt

	public void createBoard(MainBoardDTO mainBoardDTO);

	public MainBoardDTO getBoardDetail(long boardId);

	public void udpateReadCnt(long boardId);

	public String getEncodedPasswd(long boardId);
	

}
