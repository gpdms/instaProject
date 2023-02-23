package com.study.springboot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.study.springboot.follow.FollowDao;
import com.study.springboot.follow.FollowDto;
import com.study.springboot.member.MemberDao;
import com.study.springboot.member.MemberDto;
import com.study.springboot.post.ImgService;
import com.study.springboot.post.PostController;
import com.study.springboot.post.PostDao;
import com.study.springboot.post.PostDto;
import com.study.springboot.post.PostService;
import com.study.springboot.post.img.PostImgDto;
import com.study.springboot.post.img.PostImgRepository;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequiredArgsConstructor
@Log4j2
public class HomeController {
	private final PostDao postDao;
	private final MemberDao memberDao;
	private final FollowDao followDao;

	
@GetMapping("/")
public String toLogIn() {
	return "login";
}

@GetMapping("/signup")
public void toSignup() {
	
}


@GetMapping("/feed")
public void toFeed() {
}

@GetMapping("/updateinfo")
public void toUpdateinfo(HttpSession session, Model model) {
	MemberDto sessionUser = (MemberDto) session.getAttribute("user");
	String sessionEmail = sessionUser.getEmail();
	model.addAttribute("sessionEmail", sessionEmail);
}


@GetMapping("/myHome/{mem_id}")
public String toMyHome(@PathVariable("mem_id") String mem_id, HttpSession session, Model model) throws IOException{
	log.info("-----------PostController toMyHome()-------------");
	//홈 주인이 포스팅한 사진
	List<PostDto> postList = postDao.selectAllMyPost(mem_id);
	List<PostImgDto> firstImgs = new ArrayList<>();
	for (PostDto post : postList) {
		List<PostImgDto> myImgList  = new ArrayList<>();
		myImgList = postDao.selectAllImgByPost(post.getPost_id());
		if(!myImgList.isEmpty()) 
		firstImgs.add(myImgList.get(0));
	}
	
	//홈 주인이 쓴 게시물 수
	int postCount = postDao.countMyPost(mem_id);

	//홈 주인의 정보 (for 프로필 이미지, 닉네임) 
	MemberDto homeUser = memberDao.selectOneMember(mem_id);
	
	model.addAttribute("homeUser", homeUser);
	model.addAttribute("postCount", postCount);
	model.addAttribute("firstImgs", firstImgs);
	
	//----------------팔로워----------------------
	List<FollowDto> flwerList = followDao.selectFollower(mem_id);
	int countFlwer = flwerList.size();
	//log.info("팔로워리스트-------------:"+flwerList);
	//log.info("팔로워 수--------------:"+countFlwer);
	model.addAttribute("flwerList", flwerList);
	model.addAttribute("countFlwer", countFlwer);
	//--------------- 팔로잉----------------------
	List<FollowDto> flwingList = followDao.selectFollowing(mem_id);
	int countFlwing = flwingList.size();
	//log.info("팔로잉리스트------------:"+flwingList);
	//log.info("팔로잉 수-------------:"+countFlwing);
	model.addAttribute("flwingList", flwingList);
	model.addAttribute("countFlwing", countFlwing);
	
	//팔로우되어있는지 체크
		MemberDto sessionUser = (MemberDto) session.getAttribute("user");
		String sessionId = sessionUser.getMem_id();
		int checkFlw = followDao.checkFollow(sessionId, mem_id);
		model.addAttribute("checkFlw", checkFlw);
		
		//log.info("세션유저------"+sessionUser);
		//log.info("페이지주인 아이디--------:"+mem_id);
		//log.info("세션유저아이디-----"+sessionId);
		//log.info("팔로우되어있으면 1------------:"+checkFlw);
	
	return "my_home";
}


//나의 feed 이미지 리스트 (post_id)로
//@GetMapping("/feed/{login_id}")
//public String toMyFeed(@PathVariable("login_id") String login_id, Model model) throws IOException{
//	List<PostDto> myPostList = postDao.selectAllMyPost(login_id);
//	log.info("-----------PostController toMyFeed()-------------");
//	
//	Map<String, List<Integer>> myImgMap = new HashMap<>();
//	
//	for (PostDto post : myPostList) {
//		//포스트 한개의 이미지Dto들
//		List<PostImgDto> imgs= postDao.selectAllMyImg(post.getPost_id());
//		//포스트 한개의 이미지Id들
//		List<Integer> imgIds = new ArrayList<>();
//		for(PostImgDto img : imgs) {
//			imgIds.add(img.getImg_id());
//		}
//		myImgMap.put(String.valueOf(post.getPost_id()), imgIds);
//		imgIds = null;
//	}
//	log.info(myImgMap);
//	
//	model.addAttribute("myImgMap", myImgMap);
//	model.addAttribute("myPostList", myPostList);
//	return "feed";
//}
  
} 
