package com.application.practice2Ver2.boardAdvance.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.application.practice2Ver2.boardAdvance.dto.MainBoardDTO;
import com.application.practice2Ver2.boardAdvance.service.BoardAdvanceService;

@Controller
@RequestMapping("/boardAdvance")
public class BoardAdvanceController {
	
	@Autowired
	private BoardAdvanceService boardAdvanceService;
	
	//오류발생: (type=Internal Server Error, status=500).  You have an error in your SQL syntax; 
	// SQL: SELECT * FROM MAIN_BOARD ORDER BY ENROLL_AT DESC LIMIT ?,? 
	//해결 : 
	@GetMapping("/boardList")
	public String boardList(Model model, @RequestParam(name = "searchKeyword", defaultValue = "total") String searchKeyword, @RequestParam(name="searchWord", defaultValue = "") String searchWord,
										@RequestParam(name="onePageViewCnt", defaultValue = "10") int onePageViewCnt, @RequestParam(name="currentPageNumber", defaultValue = "1") int currentPageNumber ) {
		
		// 모든 게시글 조회 수 searchKeyword searchWord 검색 조건에 따라 숫자가 달라짐
		 Map<String, String> searchCntMap= new HashMap<String, String>();
		 searchCntMap.put("searchKeyword", searchKeyword);
		 searchCntMap.put("searchWord", searchWord);
		 //searchCntMap 조건을 파라미터로 db에서 전체 게시물의 갯수를 가지고온다.
		int allBoardCnt = boardAdvanceService.getAllBoardCnt(searchCntMap);
		
		//전체 페이지의 수(조건: 나머지가 있으면 다음장으로 넘어간다 +1)
		int allPageCnt = allBoardCnt / onePageViewCnt;
		if(allBoardCnt % onePageViewCnt != 0) {
			allPageCnt++;
		}
		
		//시작페이지(조건달기)
		int startPage = (currentPageNumber - 1) / 10 * 10 +1;
		if(startPage == 0) {
			startPage = 1;
		}
		//끝 페이지(조건달기
		int endPage = startPage + 9;
		if(endPage == 0) {
			endPage = 1;
		}
		if(endPage > allPageCnt) {
			endPage = allPageCnt; // 전체 페이지를 넘는다는 조건이라면 끝 페이지 전체페이지숫자
		}
		//게시글이 시작하는 번호 인덱스
		int startBoardIdx = (currentPageNumber -1) / onePageViewCnt;
		
		// view 로 넘겨야함
		model.addAttribute("startBoardIdx", startBoardIdx);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("allPageCnt", allPageCnt);
		model.addAttribute("allBoardCnt", allBoardCnt);
		model.addAttribute("onePageViewCnt", onePageViewCnt);
		model.addAttribute("currentPageNumber", currentPageNumber);
		model.addAttribute("searchKeyword", searchKeyword);
		model.addAttribute("searchWord", searchWord);
		
		// 넘기고 나서 boardList 메서드 호출시 데이터 확인됨
		 Map<String, Object> searchMap= new HashMap<String, Object>();
		 searchMap.put("searchKeyword", searchKeyword);
		 searchMap.put("searchWord", searchWord);
		 searchMap.put("onePageViewCnt", onePageViewCnt);
		 searchMap.put("startBoardIdx", startBoardIdx);
		 model.addAttribute("boardList", boardAdvanceService.getBoardList(searchMap));
		 
				
		return "boardAdvance/board/boardList";
	}
	
	@GetMapping("createBoard")
	public String createBoard() {
		return "boardAdvance/board/createBoard";
	}
	
	@PostMapping("createBoard")
	public String createBoard(@ModelAttribute MainBoardDTO mainBoardDTO) {
		boardAdvanceService.createBoard(mainBoardDTO);
		return "redirect:/boardAdvance/boardList";
	}
	
	@GetMapping("/boardDetail")
	public String boardDetail(@RequestParam("boardId") long boardId, Model model) {
		model.addAttribute("mainBoardDTO", boardAdvanceService.getBoardDetail(boardId, true));
		return "boardAdvance/board/boardDetail";
		
		//@{/boardAdvance/createReply(boardId=${mainBoardDTO.boardId })}'|">
		//댓글 남기기
		
	}
	
	///boardAdvance/boardAuthentication(boardId=${mainBoardDTO.boardId },menu=update)}'|">
	@GetMapping("/boardAuthentication")
	public String boardAuthentication(Model model, @RequestParam("boardId") long boardId, @RequestParam("menu") String menu) {
		model.addAttribute("mainBoardDTO", boardAdvanceService.getBoardDetail(boardId, false));
		model.addAttribute("menu", menu);
		return "boardAdvance/board/authentication";
	}
	
	@PostMapping("/boardAuthentication")
	@ResponseBody
	public String boardAuthentication(@ModelAttribute MainBoardDTO mainBoardDTO, @RequestParam("menu") String menu) {
		//name="passwd" name="boardId" name="menu"
		//boardAdvanceService.isAuthorized(); 
		String jString = "";
		if(boardAdvanceService.isAuthorized(mainBoardDTO)) {
			if (menu.equals("update")) {
				jString = "<script>";
				jString += "location.href='/boardAdvance/updateBoard?boardId="+mainBoardDTO.getBoardId()+"';";
				jString += "</sccript>";
			}
			else if (menu.equals("delete")) {
				jString = "<script>";
				jString += "location.href='/boardAdvance/deleteBoard?boardId="+mainBoardDTO.getBoardId()+"'	;";
				jString += "</sccript>";
			}
		}
		else {
			jString="""
					<script>
					alert('패스워드를 재확인해주세요!');
					history.go(-1);
					</script>
					""";
		}
			return jString;
	}
	
	//@GetMapping("/updateBoard")
	

}
