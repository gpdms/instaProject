package com.study.springboot.like;



import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.study.springboot.post.PostDao;
import com.study.springboot.post.PostDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequiredArgsConstructor
@Log4j2
public class LikeController {
	private final LikeDao likedao;
	private final PostDao postDao;

	@GetMapping("/likes")
	@ResponseBody

	

	public int like(LikeDto postlike) {

		log.info("..........."+postlike);
		int lik = likedao.like(postlike);
		PostDto postDto = postDao.selectOnePost(postlike.getPost_id());
		int result = postDao.pluslikes(postDto);
		PostDto afterLikePost = postDao.selectOnePost(postlike.getPost_id());
		log.info("+++++++++++222"+ afterLikePost);
		return afterLikePost.getLikes();
		
	}
	
	@GetMapping("/likenum")
	@ResponseBody
	public String likenum(LikeDto postlike, Model model) {
		int lknum = likedao.unlike(postlike);
		return "feed";
	}
	
	@ResponseBody
	@GetMapping("/unlike")


	public int unlike(LikeDto postlike, Model model) {

		int unlik = likedao.unlike(postlike);
		PostDto postDto = postDao.selectOnePost(postlike.getPost_id());
		int result = postDao.minuslikes(postDto);
		PostDto afterLikePost = postDao.selectOnePost(postlike.getPost_id());
		log.info("+++++++++++222"+ afterLikePost);
		return afterLikePost.getLikes();
		
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




