package com.study.springboot;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

@GetMapping("/")
public String toLogIn() {
	return "login";
}

@GetMapping("/signup")
public void toSignUp() {
}

//@GetMapping("/myHome")
//public String toMyHome() {
//	return "my_home";
//}

@GetMapping("/feed")
public void toFeed() {
}

@GetMapping("/view")
public void toView() {
}

//@GetMapping("/newPost") --> postController에
//public String posting() {
//	return "new_post";
//}

@GetMapping("/profile_edit")
public void profile_Edit() {
	
}
  
} 
