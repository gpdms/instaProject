package com.study.springboot.like;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.study.springboot.member.MemberDto;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class LikeController {

	private final LikeDao likedao;
	
	@PostMapping("/likeTest")
	@ResponseBody
	public String test2(HttpSession session, Map<String,Object> map, LikeDto likeDto) {
		
		if (session.getAttribute("user") != null) {
			MemberDto ses_user = (MemberDto) session.getAttribute("user");
			String loginUserId = ses_user.getMem_id();
			map.get("post_id");
			likeDto.setMem_id(loginUserId);
			likeDto.getPost_id();
			// 작성자 로그인 한 유저로 셋팅
			System.out.println("post_id :" + likeDto.getPost_id());
			//System.out.println(map.get("post_id"));
			System.out.println("loginUserId : " + loginUserId);
		}
		
		likedao.likeInsert(likeDto);
		//System.out.println(likeDto);
		
		return "좋아요 인서트 성공";
	}
	
	@PostMapping("/likeTest2")
	@ResponseBody
	public String test3(HttpSession session, LikeDto likeDto) {
		
		if(session.getAttribute("user") != null) {
			MemberDto ses_user = (MemberDto) session.getAttribute("user");
			String loginUserId = ses_user.getMem_id();
			likeDto.setMem_id(loginUserId);
			likeDto.getPost_id();
			// 작성자 로그인 한 유저로 셋팅
			System.out.println("post_id :" + likeDto.getPost_id());
			System.out.println("loginUserId : " + loginUserId);
		}
		
		likedao.likeDeleteTest(likeDto);
		return "좋아요 삭제 성공";
	}
	
}
