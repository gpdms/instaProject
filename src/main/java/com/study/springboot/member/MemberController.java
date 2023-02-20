package com.study.springboot.member;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.study.springboot.post.PostDao;
import com.study.springboot.post.PostDto;
import com.study.springboot.post.img.PostImgDto;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
@RequiredArgsConstructor
public class MemberController {
	
	private final MemberDao dao;
	private final PostDao postDao;
	//private final MemberService service;
	
	//서비스에서의 getId로부터 온 값이 true이면 아이디 중복이므로 no를 리턴.
	
//	@PostMapping("/getId")
//	public String getId(MemberDto dto) {
//		log.info("MemberController getId()");
//		boolean b = service.getId(dto);
//		if(b) {
//			return "no";
//		}
//		return "ok";
//	}
	
	//회원가입 완료되어 카운트된 숫자가 0보다 클 경우 true반환. true일때 회원가입 완료이므로 "ok"반환.
	
	//회원가입
	@PostMapping("/signUp")
	public String addMember(MemberDto dto, Model md) {
		log.info("-------------MemberController addMember()------------");
		log.info(dto);
		
		//입력폼 비어있을 시 alert창 띄우고 회원가입 페이지로.
		String mem_id = dto.getMem_id();
		String mem_pw = dto.getMem_pw();
		String nickname = dto.getNickname();
		String email = dto.getEmail();
		log.info("-----------------------------mem_id:"+mem_id);
		if(mem_id=="" || mem_pw==""||nickname==""||email=="") {
			md.addAttribute("msg", "입력양식을 모두 작성해주세요.");
			md.addAttribute("url", "signup");
			return "alert";
		}
		
		//아이디중복체크: 아이디 null이면 0반환  
		int idcheck = dao.getId(dto);
		if (idcheck > 0) { //아이디 중복되면
			md.addAttribute("msg", "이미 존재하는 아이디입니다.");
			md.addAttribute("url", "signup");
			return "alert";
		} else {
			//회원가입 insert 성공시 1반환
			int rs = dao.addMember(dto);
			log.info(rs);
			if(rs > 0) {
				md.addAttribute("msg", "가입되었습니다.");
				md.addAttribute("url", "/");
				return "alert";
			} else {
				md.addAttribute("msg", "가입되지 않았습니다. 다시 입력해주세요.");
				md.addAttribute("url", "signup");
				return "alert"; 
			}
		}
	}
	
	
	//로그인한 정보를 세션에 저장.
	@PostMapping("/login")
	public String login(MemberDto dto, HttpSession session, Model md) {
		log.info("------------MemberController login()-------------");
		MemberDto user= dao.login(dto);
		if(user == null) {
			md.addAttribute("msg", "아이디 및 비밀번호를 확인하세요.");
			md.addAttribute("url", "/");
			return "alert";
		} else {
			session.setAttribute("user", user);
			MemberDto ses_user = (MemberDto) session.getAttribute("user");
			log.info(ses_user);
			//md.addAttribute("nickname", ses_user.getNickname());
			
			//------------myhome에 이미지 깔아주기-------------//
			//홈 주인이 포스팅한 사진
		  	List<PostDto> postList = postDao.selectAllMyPost(ses_user.getMem_id());
		  	List<PostImgDto> firstImgIds = new ArrayList<>();
		  	for (PostDto post : postList) {
		  		List<PostImgDto> myImgList  = new ArrayList<>();
		  		myImgList = postDao.selectAllImgByPost(post.getPost_id());
		  		if(!myImgList.isEmpty()) 
		  		firstImgIds.add(myImgList.get(0));
		  	}
		  	//홈 주인이 쓴 게시물 수
		  	int postCount = postDao.countMyPost(ses_user.getMem_id());
		  	md.addAttribute("homeUser", ses_user);
		  	md.addAttribute("postCount", postCount);
		  	md.addAttribute("firstImgs", firstImgIds);
		  	
			return "my_home";
		}
		
		

	}
	
	//로그아웃. 세션 제거.
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("user");
		return "login";
	}
	
	//프로필 이미지 제거  
	@PostMapping("/deletePfImg")
	public String deleteProfileImg(HttpSession session, Model model) {
		MemberDto user = (MemberDto) session.getAttribute("user");
		int del_result = dao.deleteProfimg(user);
		model.addAttribute("homeUser",	dao.selectOneMember(user.getMem_id()));
		return "my_home :: #profile_img";
	}
	
//	@PostMapping("/updatePfImg")
//	@ResponseBody
//	public String updateProfileImg(HttpSession session) {
//		int up_result = dao.updateProfimg((MemberDto) session.getAttribute("user"));
//		if(up_result>0)
//		return "프로필 이미지가 변경되었습니다.";
//		return "프로필 이미지를 변경할 수 없습니다.";
//	}
	
	//프로필 닉네임 업뎃 
	@PostMapping("/updateNickname")
	public String updateMemNickname(MemberDto member, Model model) {
		int up_result = dao.updateNickname(member);
		model.addAttribute("homeUser", dao.selectOneMember(member.getMem_id()));
	   return "my_home :: #user_nick";
	}
	
}
