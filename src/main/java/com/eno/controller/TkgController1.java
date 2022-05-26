package com.eno.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.eno.entity.StudentSubject;
import com.eno.entity.master.TimeTableNormal;
import com.eno.service.TkgService1;

@Controller
public class TkgController1 {

	@Autowired
	private TkgService1 tkgService1;

	@GetMapping("/tkg")
	public String memberFormView(Model model) {
//		tkgService1.findAllMGrade();
//		tkgService1.findAllMArea();
//		tkgService1.findAllClassroom();
//		tkgService1.findAllLecturer();
		// 該当する曜日のデータを全取得
		List<StudentSubject> allStudentsSubjects = tkgService1.findAllStundentsSubjects();
		//テーブルにデータ追加
		tkgService1.insertClassesOnTargetDayOfWeek(allStudentsSubjects);
		return "dummy";
	}

}
