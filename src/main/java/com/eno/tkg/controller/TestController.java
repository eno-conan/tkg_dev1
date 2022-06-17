package com.eno.tkg.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class TestController {

	@GetMapping("/dummy")
	public String dummy() {
		return "dummy";
	}

}
