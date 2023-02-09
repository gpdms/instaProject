package com.study.springboot;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class HomeController {

@GetMapping("/")
public String logIn() {
	return "login";
}

@GetMapping("/signin")
public void signIn() {
}

@GetMapping("/myHome")
public String myHome() {
	return "my_home";
}

@GetMapping("/feed")
public void feed() {
}

@GetMapping("/view")
public void view() {
}

@GetMapping("/newPost")
public String posting() {
	return "new_post";
}
  
} 
