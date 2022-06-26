package com.eno.tkg.classSchedule;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eno.tkg.entity.master.Lecturer;
import com.eno.tkg.repository.master.LecturerRepository;
import com.eno.tkg.util.UseOverFunction;
import com.fasterxml.jackson.core.JsonProcessingException;

@Service
public class GetLecturerTargetSubjectTeachService {

	@Autowired
	private LecturerRepository lecturerRepository;

	String getLecturerTargetSubjectTeach(String scheduleNormalId) throws JsonProcessingException {

		List<Lecturer> lecturers = lecturerRepository
				.findTargetSubjectTeachLecturersByScheduleId(Integer.parseInt(scheduleNormalId));
		for (Lecturer lecturer : lecturers) {
			System.out.println(lecturer.getLecturerName());
		}
		String strJson = UseOverFunction.getDataToJsonFormat(lecturers);
		return strJson;
	}

}
