package com.study.springboot;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class HomeController {

@GetMapping("/")
public String tohome() {
	return "index";
}

} 
