package com.eno.tkg.student;

import java.sql.Timestamp;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eno.tkg.entity.SpecialSeasonDateList;
import com.eno.tkg.entity.StudentClassSpecialSummary;
import com.eno.tkg.entity.StudentScheduleNormal;
import com.eno.tkg.entity.StudentScheduleSpecial;
import com.eno.tkg.entity.master.Lecturer;
import com.eno.tkg.entity.master.SpecialSeason;
import com.eno.tkg.entity.master.Student;
import com.eno.tkg.entity.master.Subject;
import com.eno.tkg.entity.master.TimeTableSpecial;
import com.eno.tkg.repository.StudentClassSpecialSummaryRepository;
import com.eno.tkg.repository.StudentScheduleSpecialRepository;
import com.eno.tkg.repository.TimeTableSpecialRepository;
import com.eno.tkg.repository.SpecialSeasonDateListRepository;
import com.eno.tkg.util.UseOverFunction;
import com.fasterxml.jackson.core.JsonProcessingException;

@Service
public class UpdateSpecialScheduleService {

	@Autowired
	private StudentScheduleSpecialRepository studentScheduleSpecialRepository;

	@Autowired
	private StudentClassSpecialSummaryRepository studentClassSpecialSummaryRepository;

	@Autowired
	private SpecialSeasonDateListRepository specialSeasonDateListRepository;

	@Autowired
	private TimeTableSpecialRepository timeTableSpecialRepository;

	/**
	 * // 生徒の講習期間のスケジュール更新
	 * 
	 * @param studentId 生徒ID
	 * @return json コマごとに整形した授業予定一覧
	 * @throws JsonProcessingException
	 *
	 */
	// 戻り値はMapを使用
	public String updateTargetStudentSpecialSchedule(final String content) throws JsonProcessingException {
		System.out.println(content);
		String studentId = content.split(",")[0];
//		String specialSeasonId = content.split(",")[1];
//		String subjectId = content.split(",")[2];// このID値から、科目名取得をすること

		// 生徒IDとtime_table_special_IDから授業のあり・なしを判定
		Optional<StudentScheduleSpecial> classInfo = studentScheduleSpecialRepository
				.findByStudentAndTimeTableSpecial(new Student(Integer.parseInt(studentId)), new TimeTableSpecial(12));

		if (classInfo.isPresent()) {
			System.out.println("授業あり->更新");
		} else {
			System.out.println("授業なし->追加");
			StudentScheduleSpecial newClass = new StudentScheduleSpecial();
			newClass.setStudent(new Student(Integer.parseInt(studentId)));
			newClass.setSubject(new Subject("m3"));
			newClass.setLecturer(new Lecturer(1));
			newClass.setTimeTableSpecial(new TimeTableSpecial(8));

			Optional<TimeTableSpecial> getDate = timeTableSpecialRepository.findById(8);
			newClass.setClassDate(getDate.get().getSpecialSeasonDateList().getClassDate());
			newClass.setRescheduleDateStart(UseOverFunction.convertStrDateToDateType("2022/09/14"));
			newClass.setRescheduleDateLast(UseOverFunction.convertStrDateToDateType("2022/09/14"));
			newClass.setRescheduleFlg(false);
			newClass.setCreatedAt(new Timestamp(System.currentTimeMillis()));
			newClass.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
			studentScheduleSpecialRepository.save(newClass);

		}
		// なし：新しく追加する処理
		// あれば、新しくインスタンスを生成して、更新処理

		String strJson = UseOverFunction.getDataToJsonFormat("");
		return strJson;
	}

}
