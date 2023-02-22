package com.study.springboot.like;



import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequiredArgsConstructor
@Log4j2
public class LikeController {
	
	private final LikeDao likedao;

	@GetMapping("/like")
	@ResponseBody
	public String like(LikeDto postlike, Model model ) {
		log.info("..........."+postlike);
		int lik = likedao.like(postlike);
		return "ok";
	}
	
	@GetMapping("/likenum")
	@ResponseBody
	public String likenum(LikeDto postlike, Model model) {
		int lknum = likedao.unlike(postlike);
		return "feed";
	}
	
	@ResponseBody
	@GetMapping("/unlike")
	public String unlike(LikeDto postlike, Model model) {
		int unlik = likedao.unlike(postlike);
		return "delete";
	}
}	
	
	
//	@RequestMapping("/like/insert/{id}")
//	@ResponseBody
//	private int like_insert(@PathVariable int id) throws Exception {
//		String userId = SecurityContextHolder.getContext().getAuthentication().getName();
//		mem_id m = userService.findByUserId(userId);
//		post_id p = postService.findById(id);
//
//		heartService.save(p, m);
//		return 1;
//	}
//
//}
