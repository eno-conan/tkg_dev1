package com.eno.tkg.classSchedule;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eno.tkg.entity.StudentScheduleNormal;
import com.eno.tkg.entity.master.Area;
import com.eno.tkg.entity.master.Prefecture;
import com.eno.tkg.repository.StudentScheduleNormalRepository;
import com.eno.tkg.service.TkgService1;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/tkg")
public class ClassScheduleController {

	@Autowired
	private ClassScheduleService classScheduleService;

	@GetMapping("/classSchedule")
	public String getTargetDateClassSchedule(@RequestParam(name = "targetDate") final String dateStr) {
		String strDate = dateStr.replace("-", "/");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date date = null;
		try {
			date = dateFormat.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return classScheduleService.getTargetDateClassSchedule(date);
	}
	
	@PutMapping("/updateClassSchedule")
	public String updateTargetClassSchedule(@RequestBody final String content) {
		return classScheduleService.updateTargetClassSchedule(content);
	}

}
