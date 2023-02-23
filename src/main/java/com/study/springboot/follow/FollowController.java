package com.study.springboot.follow;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.study.springboot.member.MemberDto;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequiredArgsConstructor
@Log4j2
public class FollowController {
	private final FollowDao followDao;
	
	@ResponseBody
	@PostMapping("/follow")
	public void follow(HttpSession session, @RequestBody MemberDto homeUser) {
		//log.info("/follow 홈유저아이디-------:"+homeUser);
		MemberDto sessionUser = (MemberDto) session.getAttribute("user");
		String sessionId = sessionUser.getMem_id();
		String mem_id = homeUser.getMem_id();
		//log.info(homeUser.getMem_id());
		int res = followDao.addFollow(sessionId, homeUser.getMem_id());
		
		//log.info("/follow 세션아이디---------:"+sessionId);
		//log.info("/follow 홈유저아이디-------:"+homeUser);
		//log.info("/follow add팔로우됐는지 확인---------:"+res);
	}
	
	@ResponseBody
	@PostMapping("/unfollow")
	public void unfollow(HttpSession session, @RequestBody MemberDto homeUser) {
		log.info("/unfollow json으로 넘어온 홈유저-------:"+homeUser);
		MemberDto sessionUser = (MemberDto) session.getAttribute("user");
		String sessionId = sessionUser.getMem_id();
		
		String mem_id = homeUser.getMem_id();
		int res = followDao.unfollow(sessionId, mem_id);
		
//		log.info("/unfollow 세션아이디---------:"+sessionId);
//		log.info("/unfollow 홈유저아이디-------:"+mem_id);
//		log.info("/unfollow add팔로우됐는지 확인---------:"+res);
		
	}
	
	//아이디 검색
		@GetMapping("/search")
		public String search(String searchWord, Model model) {
			if(!searchWord.isEmpty()) {
			log.info(searchWord);
			
			List<MemberDto> list = followDao.searchId("%"+searchWord+"%");
			model.addAttribute("searchList", list);
			return "search";
			}
			else {
				model.addAttribute("msg", "검색어를 입력하세요.");
				model.addAttribute("url", "location.href");

				return "alert";
			}
			
		}
		
	
	
		
}
