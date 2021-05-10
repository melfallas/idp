package com.mb.base.idp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
public class TestController {
	
	@GetMapping("test1")
	public String test1() {
		return "test1";
	}
	
	@GetMapping("test4")
	public String test4() {
		return "test4";
	}
	
}

