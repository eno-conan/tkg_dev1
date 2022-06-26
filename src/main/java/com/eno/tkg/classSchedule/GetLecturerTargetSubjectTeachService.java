package com.eno.tkg.classSchedule;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eno.tkg.entity.StudentScheduleNormal;
import com.eno.tkg.repository.StudentScheduleNormalRepository;
import com.eno.tkg.util.UseOverFunction;
import com.fasterxml.jackson.core.JsonProcessingException;

@Service
public class GetLecturerTargetSubjectTeachService {

	@Autowired
	private StudentScheduleNormalRepository studentScheduleNormalRepository;

	String getLecturerTargetSubjectTeach(String scheduleNormalId) throws JsonProcessingException {
		// チェックした授業のIDから、対象の科目を指導可能な講師を取得
//		Optional<StudentScheduleNormal> classInfo = studentScheduleNormalRepository
//				.findById(Integer.parseInt(scheduleNormalId));
		List<StudentScheduleNormal> lecturers = studentScheduleNormalRepository
				.findTargetSubjectTeachLecturersByScheduleId(Integer.parseInt(scheduleNormalId));
		for (StudentScheduleNormal lecturer : lecturers) {
			System.out.println(lecturer.getSubject().getSubjectKey());
		}
		String strJson = UseOverFunction.getDataToJsonFormat(lecturers);
		return strJson;
	}

}
