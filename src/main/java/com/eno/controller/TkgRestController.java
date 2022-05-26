package com.eno.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eno.entity.master.Area;
import com.eno.entity.master.Prefecture;
import com.eno.service.TkgService1;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@CrossOrigin(origins = "*") // ★変更点
@RestController
public class TkgRestController {

	@Autowired
	private TkgService1 tkgService1;

	@GetMapping("/v1/tkg")
	public String getAllArea(Model model) {
//		tkgService1.findAllMGrade();
		List<Prefecture> allPrefecture = tkgService1.findAllMArea();
		ObjectMapper mapper = new ObjectMapper();
		String json = "";
		try {
			json = mapper.writeValueAsString(allPrefecture);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return json;
	}

}
