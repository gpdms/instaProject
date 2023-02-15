package com.study.springboot.comment;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class CommentController {

	private final CommentDao commentdao;
	

@GetMapping("/view1")
public void view(Model model, int post_id) {
	List<CommentDto> cList = commentdao.selectAllComment(post_id);
	List<Integer> tList = new ArrayList<>();
	
	for (int i = 0 ; i<cList.size(); i++) {
		//tList.add(cList.get(i).getCom_time());
		int a = commentdao.calculateCommentTime(cList.get(i).getCom_id());
		tList.add(a);
	}
	System.out.println(tList);
	
	model.addAttribute("list", cList); 
	model.addAttribute("tList", tList);   
}


@ResponseBody
@GetMapping("/insertComment")
public String insertComment(Model model, CommentDto commDTO) {
	
	//System.out.println("commDTO : " + commDTO.toString());
	commentdao.insertComment(commDTO);
	
	List<CommentDto> cList = commentdao.selectAllComment(commDTO.getPost_id());
	model.addAttribute("list", cList); 
	
	return "asd";
}
  


@GetMapping("/selectAllComment")
public String selectAllComment(Model model, int com_id) {
	
	System.out.println("selectCommentTest");
	//List<CommentDto> cList = commentdao.selectAllComment(post_id);
	//model.addAttribute("list", cList);
	//int day = commentdao.calculateCommentTime(com_id);
	//model.addAttribute("day", day);
	
	return "/view";
}



@GetMapping("/deleteComment")
public String deleteComment(int com_id) {
	commentdao.deleteComment(com_id);
	return "redirect:/selectAllComment";
}



@GetMapping("/asd")
public String selectAllPost(Model model) {
	
	/*
	 * List<PostDTO> pList = commentdao.selectAllPost(); model.addAttribute("post",
	 * pList);
	 */
	return "my_home";
}

}
