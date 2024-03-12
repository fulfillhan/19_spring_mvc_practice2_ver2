package com.application.practice2Ver2.service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.application.practice2Ver2.dao.MemberDAO;
import com.application.practice2Ver2.dto.MemberDTO;


@Service
public class MemberServiceImpl implements MemberService {
	
	@Value("${file.repo.path}")
	private String fileRepositoryPath;
	
	@Autowired
	private MemberDAO memberDAO;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	private static Logger logger = LoggerFactory.getLogger(MemberServiceImpl.class); // 로그 

	@Override
	public void createMember(MultipartFile uploadProfile, MemberDTO memberDTO) throws IllegalStateException, IOException {
		
		if(!uploadProfile.isEmpty()) { // 파일있다는 조건으로
			   String originalFilename = uploadProfile.getOriginalFilename();
			   memberDTO.setProfileOriginalName(originalFilename);
			   
			   UUID uuid = UUID.randomUUID();
			    String extenstion = originalFilename.substring(originalFilename.indexOf("."));
			    
			    String profileUUID = uuid + extenstion;
			    memberDTO.setProfileUUID(profileUUID);
			    
			    uploadProfile.transferTo(new File(fileRepositoryPath+profileUUID));
		}
		
		if(memberDTO.getSmsstsYn() == null) {
			memberDTO.setSmsstsYn("n");
		}
		if(memberDTO.getEmailstsYn() == null) {
			memberDTO.setEmailstsYn("n");
		}
		
		// 패스워드 암호화하기
		//오류 발생 :  패스워드인코드만 하고 memberDTO객체로 전송하지 않음.
		//passwordEncoder.encode(memberDTO.getPasswd());
		memberDTO.setPasswd(passwordEncoder.encode(memberDTO.getPasswd()));
		memberDAO.createMember(memberDTO);
	}

	@Override
	public String checkValidId(String memberId) {
		String isValidId = "y";  //유효한가요?
		//중복확인 
		//입력된 값을 넣었을 때 데이터베이스에 있는 
		if(memberDAO.checkValidId(memberId) != null) { // 아이디가 있다면 유효하지 않다는것
			isValidId = "n";
		}
		return isValidId;
	}

	@Override
	public String login(MemberDTO memberDTO) {
		String isValidMember = "n";
		MemberDTO getLoginData = memberDAO.login(memberDTO.getMemberId());
		if (getLoginData != null) {
			// 암호화된 아이디도 가져와서 비교하기
			if (passwordEncoder.matches(memberDTO.getPasswd(), getLoginData.getPasswd())
					&& getLoginData.getActiveYn().equals("y")) {
				isValidMember = "y";
			}
		}
		return isValidMember;

	}

	@Override
	public MemberDTO getMamberDetail(String memberId) {
		
		return memberDAO.getMamberDetail(memberId);
	}

	@Override
	public void updateMember(MemberDTO memberDTO, MultipartFile uploadProfile)
			throws IllegalStateException, IOException {

		if (!uploadProfile.isEmpty()) {
			File deleteFile = new File(fileRepositoryPath + uploadProfile);
			deleteFile.delete();

			String originalFilename = uploadProfile.getOriginalFilename();
			memberDTO.setProfileOriginalName(originalFilename);

			UUID uuid = UUID.randomUUID();
			String extension = originalFilename.substring(originalFilename.indexOf("."));
			String newUploadFile = uuid + extension;
			memberDTO.setProfileUUID(newUploadFile);

			uploadProfile.transferTo(new File(fileRepositoryPath + newUploadFile));
		}

		if (memberDTO.getSmsstsYn() == null)
			memberDTO.setSmsstsYn("n");
		if (memberDTO.getEmailstsYn() == null)
			memberDTO.setEmailstsYn("n");

		memberDAO.updateMember(memberDTO);

	}

	@Override
	public void updateInactiveMember(String memberId) {
		memberDAO.updateInactiveMember(memberId);
	}

	
	@Override
	@Scheduled(cron="0 34 22 * * *")
	public void getTodayNewMemberCnt()  {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String today = sdf.format(new Date());
		memberDAO.getTodayNewMemberCnt(today);
	}

	@Override
	@Scheduled(cron="0 34 22 * * *")
	public void deleteMemberScheduler() {
		//id와 profileUUID 가지고오기
		//가지고온 ID의 프로필 정보도 삭제하기
		List<MemberDTO> deleteMemberList = memberDAO.getdeleteMemberList();
		if(!deleteMemberList.isEmpty()) {
			for (MemberDTO memberDTO : deleteMemberList) {
				File deleteFile = new File(fileRepositoryPath+memberDTO.getProfileUUID());
				deleteFile.delete();
				memberDAO.deleteMember(memberDTO.getMemberId());
			}
			
		}
	}
	
	
	
	
	

}
