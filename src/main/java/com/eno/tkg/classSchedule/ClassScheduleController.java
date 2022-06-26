package com.eno.tkg.classSchedule;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eno.tkg.entity.StudentScheduleNormal;
import com.eno.tkg.util.UseOverFunction;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/tkg")
public class ClassScheduleController {

	@Autowired
	private GetTargetDateClassesService getTargetDateClassesService;

	@Autowired
	private GetSelectStudentClassesService getSelectStudentClassesService;

	@Autowired
	private UpdateSelectClassScheduleService updateSelectClassScheduleService;

	@Autowired
	private GetLecturerTargetSubjectTeachService getLecturerTargetSubjectTeachService;

	/**
	 * 授業一覧取得
	 * 
	 * @param dateStr 日付（基本敵に当日の情報）
	 * @return 更新情報
	 *
	 */
	@GetMapping("/class-schedule")
	String getTargetDateClassSchedule(@RequestParam(name = "targetDate") final String dateStr) {
		Date date = UseOverFunction.convertStrDateToDateType(dateStr);
		try {
			return getTargetDateClassesService.getTargetDateClassSchedule(date);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 生徒授業予定取得
	 * 
	 * @param studentId 生徒ID
	 * @return 更新情報
	 *
	 */
	@GetMapping("/class-schedule/student-schedule/{studentId}")
	String getStudentClassSchedule(@PathVariable(name = "studentId") final String studentId) {
		try {
			return getSelectStudentClassesService.getSelectStudentClassSchedule(studentId);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 講師変更（特定科目対応可能講師取得）
	 * 
	 * @param studentId 生徒ID
	 * @return 更新情報
	 *
	 */
	@GetMapping("/class-schedule/subject-teach-lecturer/{scheduleNormalId}")
	String getLecturerTargetSubjectTeach(@PathVariable(name = "scheduleNormalId") final String scheduleNormalId) {
		try {
			return getLecturerTargetSubjectTeachService.getLecturerTargetSubjectTeach(scheduleNormalId);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 振替実行
	 * 
	 * @param content 更新情報
	 * @return 更新結果
	 *
	 */
	@PutMapping("/class-schedule/update")
	StudentScheduleNormal updateTargetClassSchedule(@RequestBody final String content) {
		try {
			return updateSelectClassScheduleService.updateTargetClassSchedule(content);
		} catch (Exception e) {
			return new StudentScheduleNormal(e.getMessage());
		}
	}

}
