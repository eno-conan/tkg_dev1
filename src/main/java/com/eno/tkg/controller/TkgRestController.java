//package com.eno.tkg.controller;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.LinkedHashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.eno.tkg.entity.StudentScheduleNormal;
//import com.eno.tkg.entity.master.Area;
//import com.eno.tkg.entity.master.Prefecture;
//import com.eno.tkg.repository.StudentScheduleNormalRepository;
//import com.eno.tkg.service.ClassScheduleService;
//import com.eno.tkg.service.TkgService1;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//@RestController
//@CrossOrigin(origins = "http://localhost:3000")
//@RequestMapping("/tkg")
//public class TkgRestController {
//
//	@Autowired
//	private StudentScheduleNormalRepository studentScheduleNormalRepository;
//
//	@Autowired
//	private ClassScheduleService classScheduleService;
//	
//	@Autowired
//	private TkgService1 tkgService1;
//
//	@GetMapping("/todayClassSchedule")
//	public String getTodayClassSchedule(@RequestParam(name = "targetDate") String dateStr) {
//		String strDate = dateStr;
//		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
//		Date date = null;
//		try {
//			date = dateFormat.parse(strDate);
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//		
//		List<StudentScheduleNormal> classes = studentScheduleNormalRepository.findAllByClassDate(date);
//
//		List<Map<String, String>> returnJsonLiteral = new ArrayList<>();
//		for (StudentScheduleNormal eachClass : classes) {
//			Map<String, String> eachRowInfoMap = new LinkedHashMap<>();
//			eachRowInfoMap.put("id", String.valueOf(eachClass.getId()));
//			eachRowInfoMap.put("period", eachClass.getTimeTableNormal().getPeriod());
//			eachRowInfoMap.put("grade", eachClass.getStudent().getGrade().getDisplayName());
//			eachRowInfoMap.put("subject", eachClass.getSubject().getDisplayName());
//			eachRowInfoMap.put("studentName", eachClass.getStudent().getStudentName());
//			eachRowInfoMap.put("lecturerName", eachClass.getLecturer().getLecturerName());
//
//			returnJsonLiteral.add(eachRowInfoMap);
//		}
//		ObjectMapper mapper = new ObjectMapper();
//		String json = "";
//		try {
//			json = mapper.writeValueAsString(returnJsonLiteral);
//		} catch (JsonProcessingException e) {
//			e.printStackTrace();
//		}
//		// 返すデータの整形は必要画面に
//		return json;
//	}
//
////	@GetMapping("/v1/tkg")
////	public String getAllArea(Model model) {
//////		tkgService1.findAllMGrade();
////		List<Prefecture> allPrefecture = tkgService1.findAllMArea();
////		ObjectMapper mapper = new ObjectMapper();
////		String json = "";
////		try {
////			json = mapper.writeValueAsString(allPrefecture);
////		} catch (JsonProcessingException e) {
////			e.printStackTrace();
////		}
////		return json;
////	}
//
//}
