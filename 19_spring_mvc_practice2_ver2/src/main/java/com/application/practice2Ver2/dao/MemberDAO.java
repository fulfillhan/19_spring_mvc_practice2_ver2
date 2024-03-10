package com.application.practice2Ver2.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.application.practice2Ver2.dto.MemberDTO;

@Mapper
public interface MemberDAO {

	public void createMember(MemberDTO memberDTO);

	public String checkValidId(String memberId);

	public MemberDTO login(String memberId);

	public MemberDTO getMamberDetail(String memberId);

	public void updateMember(MemberDTO memberDTO);

	public void updateMember(String memberId);

	public void updateInactiveMember(String memberId);

	public void getTodayNewMemberCnt(String today);

	public List<MemberDTO> getdeleteMemberList();

	public void deleteMember(String memberId);
	
	

}
