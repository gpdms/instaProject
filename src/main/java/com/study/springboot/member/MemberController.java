package com.study.springboot.member;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
@RequiredArgsConstructor
public class MemberController {
	
	private final MemberDao dao;
	
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
				md.addAttribute("url", "login");
				return "alert";
			} else {
				md.addAttribute("msg", "가입되지 않았습니다. 다시 입력해주세요.");
				md.addAttribute("url", "signup");
				return "alert"; 
			}
		}
	}
	
	
	//로그인. 정보를 세션에 저장.
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
			md.addAttribute("nickname", ses_user.getNickname());
			return "my_home";
		}
	}
	
	//로그아웃. 세션 제거.
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("user");
		return "login";
	}
	
	
}
