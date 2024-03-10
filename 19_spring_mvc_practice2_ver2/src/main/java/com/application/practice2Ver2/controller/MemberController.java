package com.application.practice2Ver2.controller;

import java.io.IOException;
import java.net.MalformedURLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.application.practice2Ver2.dto.MemberDTO;
import com.application.practice2Ver2.service.MemberService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/member")
public class MemberController {
	
	@Value("{file.repo.path}")
	private String fileRepositoryPath;
	
	@Autowired
	private MemberService memberService;
	
	@GetMapping("/mainMember")
	public String mainMember() {
		return "member/mainMember";
	}
	
	@GetMapping("/registerMember")
	public String registerMember() {
		return "member/registerMember";
	}
	
	@PostMapping("/registerMember")
	private String registerMember(@RequestParam("uploadProfile") MultipartFile uploadProfile, @ModelAttribute MemberDTO memberDTO) throws IllegalStateException, IOException {
		memberService.createMember(uploadProfile,memberDTO);
		return "redirect:mainMember";
	}
	
	@PostMapping("/validId")
	@ResponseBody
	public String validId(@RequestParam("memberId") String memberId) {
		return memberService.checkValidId(memberId);
	}
	
	@GetMapping("/loginMember")
	public String loginMember() {
		return "member/loginMember";
	}
	
	@PostMapping("/loginMember")
	@ResponseBody
	public String loginMember(HttpServletRequest request,@ModelAttribute MemberDTO memberDTO) {
		
		HttpSession session = request.getSession();
		session.setAttribute("memberId", memberDTO.getMemberId());
		// 반환값을 String 으로 나올수있도록
		return memberService.login(memberDTO);
	}
	
	@GetMapping("/logoutMember")
	public String logoutMember(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.invalidate();
		
		return "redirect:mainMember";
	}
	
	@GetMapping("/updateMember") 
	public String updateMember(HttpServletRequest request, Model model ) {
		HttpSession session = request.getSession();
		model.addAttribute("memberDTO", memberService.getMamberDetail((String)session.getAttribute("memberId")));
		return "member/updateMember";
	}
	
	@GetMapping("/thumbnails")
	@ResponseBody
	public Resource thumbnails(@RequestParam("fileName") String fileName) throws MalformedURLException {
		return new UrlResource(fileRepositoryPath + fileName);
	}
	
	@PostMapping("/updateMember")
	public String updateMember(@ModelAttribute MemberDTO memberDTO, @RequestParam("uploadProfile") MultipartFile uploadProfile) throws IllegalStateException, IOException {
		memberService.updateMember(memberDTO, uploadProfile);
		return "redirect:mainMember";
	}
	
	@GetMapping("/deleteMember")
	public String deleteMember() {
		return "member/deleteMember";
	}
	
	@PostMapping("/deleteMember")
	public String deleteMember(HttpServletRequest request) {
		HttpSession session = request.getSession();
		memberService.updateInactiveMember((String)session.getAttribute("memberId"));
		
		session.invalidate();
		
		return "redirct:mainMember";
	}
	

}
