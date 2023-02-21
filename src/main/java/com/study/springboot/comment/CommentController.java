package com.study.springboot.comment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.study.springboot.member.MemberDto;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class CommentController {
	private final CommentDao commentdao;
	// private final MemberDto memberDto;

	// 댓글 인서트 메소드
	@PostMapping("/insertComment1")
	public String insertComment(Model model, @RequestParam Map<String, Object> resultMap, HttpSession session) {

		CommentDto commDto = new CommentDto();
		int post_id = Integer.parseInt((String) (resultMap.get("post_id")));

		// session값 null 처리
		if (session.getAttribute("user") != null) {
			MemberDto ses_user = (MemberDto) session.getAttribute("user");
			String loginUserId = ses_user.getMem_id();
			commDto.setMem_id(loginUserId);
			// 작성자 로그인 한 유저로 셋팅
			System.out.println("loginUserId : " + loginUserId);
		}

		commDto.setPost_id(post_id);
		commDto.setCom_text(resultMap.get("com_text").toString());
		commentdao.insertComment(commDto);
		// db에 인서트
		model.addAttribute("cList", commentdao.selectAllComment(post_id));
		// view에 뿌려주는 용도
		return "/view :: #commentBox";
	}

	// 대댓글 인서트 메소드
	@PostMapping("/insertSubComment")
	public String insertSubComment(Model model, @RequestParam Map<String, Object> resultMap, HttpSession session) {

		// session값 null 처리
		SubCommentDto subCommDto = new SubCommentDto();
		int com_id = Integer.parseInt((String) (resultMap.get("com_id")));

		if (session.getAttribute("user") != null) {
			MemberDto ses_user = (MemberDto) session.getAttribute("user");
			String loginUserId = ses_user.getMem_id();
			subCommDto.setMem_id(loginUserId);
			// 작성자 로그인 한 유저로 셋팅
			System.out.println("loginUserId : " + loginUserId);
		}

		// 로그인 한 경우에 로그인 한 사람의 아이디를 userID로

		subCommDto.setCom_id(com_id);
		subCommDto.setSubcom_text(resultMap.get("subcom_text").toString());
		// db에 인서트
		commentdao.insertSubComment(subCommDto);
		// view에 뿌려주는 용도
		//model.addAttribute("sList", commentdao.selectAllSubComment(com_id));

		return "/view :: #subCommentArea";
	}

	
	 @GetMapping("/selectAllComment") 
	 public String selectAllComment(Model model,CommentDto commDto) {
	 
	 int post_id = commDto.getPost_id();
	 
	 Map<Integer, List<SubCommentDto>> lst = new HashMap<>(); 
	 List<CommentDto> cList = commentdao.selectAllComment(post_id);
	 
	 for(CommentDto commdto : cList) {
		 int com_id = commdto.getCom_id();
		 List<SubCommentDto> sList = commentdao.selectAllSubComment(com_id);
		 System.out.println(sList);
		 // 각 com_id로 해당 comment에 달린 subcomment list를 찾는다. 
		 lst.put(commdto.getCom_id(), sList); // 해당 Map에 
		 }
	 
	 model.addAttribute("lst", lst); 
	 model.addAttribute("list", cList);
	 System.out.println(cList);
	 
	 return "view"; 
	 }
	 

	// 댓글 삭제. return값 아직 미완성
	@ResponseBody
	@GetMapping("/deleteComment")
	public String deleteComment(int com_id) {

		commentdao.deleteComment(com_id);

		return "삭제 완료";
	}

	// feed에서 댓글 작성 시 사용 예정
	@ResponseBody
	@PostMapping("/ajaxTest")
	public String test1() {

		String content = "";
		return "테스트용 메소드";
	}

	// @GetMapping("/feed/{login_id}")
	// public String toMyFeed(@PathVariable("login_id") String login_id, Model
	// model) throws IOException{
//		List<PostDto> myPostList = postDao.selectAllMyPost(login_id);
//		log.info("-----------PostController toMyFeed()-------------");
	//
//		Map<String, List<Integer>> myImgMap = new HashMap<>();
	//
//		for (PostDto post : myPostList) {
//			//포스트 한개의 이미지Dto들
//			List<PostImgDto> imgs= postDao.selectAllMyImg(post.getPost_id());
//			//포스트 한개의 이미지Id들
//			List<Integer> imgIds = new ArrayList<>();
//			for(PostImgDto img : imgs) {
//				imgIds.add(img.getImg_id());
//			}
//			myImgMap.put(String.valueOf(post.getPost_id()), imgIds);
//			imgIds = null;
//		}
//		log.info(myImgMap);
	//
//		model.addAttribute("myImgMap", myImgMap);
//		model.addAttribute("myPostList", myPostList);
//		return "feed";
	// }

}
