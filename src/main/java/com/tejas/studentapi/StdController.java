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
		return "Hello Welcome Tejass to Learn Jenkins..." ; 
	}
	
	@GetMapping("/add/{a}/{b}")
	public String sum(@PathVariable int a , @PathVariable int b) {
		return "Sum = "+(a+b) ; 
	}
	
	@GetMapping("/diff")
	public String difference(@RequestParam int a , @RequestParam int b) {
		return "Diff = "+(a-b) ; 
	}
	
}
