package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyRestController {
	
	
	@GetMapping("/greet")
    public String getGreet() {
		return "Good Morning";
	}
	
	@GetMapping("/admin")
    public String getGreetad() {
		return "hi admin";
	}
	
	@GetMapping("/user")
    public String getGreetus() {
		return "hi user";
	}
	
	
}
