package com.tejas.studentapi;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class StdController {

	@GetMapping("/hello")
	public String hellomsg() {
		return "Hello Welcome Tejas Saraf.. to Learn Jenkins..." ; 
	}
	
	@GetMapping("/add/{a}/{b}")
	public int sum(@PathVariable int a , @PathVariable int b) {
		return a+b ; 
	}
	
	@GetMapping("/diff")
	public int difference(@RequestParam int a , @RequestParam int b) {
		return a-b ; 
	}
	
	@GetMapping("/mul")
	public int multiply(@RequestParam int a , @RequestParam int b) {
		return a*b ; 
	}
	
}
