package com.application.practice2Ver2.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.application.practice2Ver2.dto.MemberDTO;

public interface MemberService {

	public void createMember(MultipartFile uploadProfile, MemberDTO memberDTO) throws IllegalStateException, IOException;

	public String checkValidId(String memberId);

	public String login(MemberDTO memberDTO);

	public MemberDTO getMamberDetail(String memberId);

	public void updateMember(MemberDTO memberDTO, MultipartFile uploadProfile) throws IllegalStateException, IOException;

	public void updateInactiveMember(String memberId);
	
	public void getTodayNewMemberCnt();
    public void deleteMemberScheduler();



	

}
