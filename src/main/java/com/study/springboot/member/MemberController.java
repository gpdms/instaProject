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
	
	//private final MemberService service;
	final MemberDao dao;
	
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
	
	@PostMapping("/signUp")
	public String addMember(MemberDto dto) {
		log.info("-------------MemberController addMember()------------");
		log.info(dto);
	//	boolean b = service.addMember(dto);
	//	log.info(dto.toString());
	//	if(b) {
	//		return "ok";
	//	}
		//아이디중복체크: 아이디 null이면 0반환  
		int idcheck = dao.getId(dto);
		if (idcheck > 0) { //아이디 중복되면
			return "signup";
		} else {
			//insert: 성공시 1반환
			int rs = dao.addMember(dto);
			log.info(rs);
			if(rs > 0) {
				return "login";
			} else {
				return "signup"; 
			}
		}
	}
	
	
	
	//로그인한 정보를 세션에 저장.
	@PostMapping("/login")
	public String login(MemberDto dto, HttpSession session, Model md) {
		log.info("------------MemberController login()-------------");
		MemberDto user= dao.login(dto);
		session.setAttribute("user", user);
		
		MemberDto ses_user = (MemberDto) session.getAttribute("user");
		log.info(ses_user);
		md.addAttribute("nickname", ses_user.getNickname());
		return "my_home";
		
	}
	
	//로그아웃. 세션 제거.
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("user");
		return "login";
		
	}
	
	
}
