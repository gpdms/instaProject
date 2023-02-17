package com.study.springboot.comment;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class CommentController {
	private final CommentDao commentdao;

	
	// 댓글 인서트 메소드
	 @PostMapping("/insertComment") 
	 public String insertComment(Model model, @RequestParam Map<String, Object> resultMap) {
	 
	 CommentDto commDto = new CommentDto();
	 
	 commDto.setMem_id(resultMap.get("mem_id").toString());
	 commDto.setPost_id(Integer.parseInt((String)(resultMap.get("post_id"))));
	 commDto.setCom_text(resultMap.get("com_text").toString());
	 
	 System.out.println("post_id :" + resultMap.get("post_id"));
	 
	 commentdao.insertComment(commDto);
	 model.addAttribute("list", commentdao.selectAllComment(commDto.getPost_id()));
	 return "/view :: #commBox"; 
	 }
	 
	
	// 대댓글 인서트 메소드
	
	 @PostMapping("/insertSubComment") 
	 public String insertSubComment(Model model, @RequestParam Map<String, Object> resultMap) {
	 
	 CommentDto commDto = new CommentDto();
	 
	 commDto.setMem_id(resultMap.get("mem_id").toString());
	 commDto.setPost_id(Integer.parseInt((String)(resultMap.get("post_id"))));
	 commDto.setCom_text(resultMap.get("com_text").toString());
	 
	 model.addAttribute("list", commentdao.selectAllComment(commDto.getPost_id()));
	 commentdao.insertComment(commDto); 
	 
	 return "/view :: #commBox"; }
	 

	@GetMapping("/selectAllComment")
	public String selectAllComment(Model model, Integer post_id) {

		List<CommentDto> cList = commentdao.selectAllComment(post_id);
		model.addAttribute("list", cList);
		System.out.println(cList);
		
		// List<CommentDto> cList = commentdao.selectAllComment(post_id);
		// model.addAttribute("list", cList);
		// int day = commentdao.calculateCommentTime(com_id);
		// model.addAttribute("day", day);

		return "view";
	}

	@GetMapping("/deleteComment")
	public String deleteComment(int com_id) {
		
		System.out.println("com_id : " + com_id);
		
		try {
			commentdao.deleteComment(com_id);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:/view";
	}

	@ResponseBody
	@PostMapping("/ajaxTest") 
	public String test1() {
		
		String content = "";
		return "테스트용 메소드";
	}

}
