package com.eno.tkg.student;

import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

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
	 * @throws Exception
	 *
	 */
	// 戻り値はMapを使用
	@Transactional
	public String updateTargetStudentSpecialSchedule(final String content) throws Exception {
		// 1,1,2,period2,8,288,302:studentId,specialSeasonId,specialSummaryId
		String[] requestBoby = content.split(",");
		List<String> requestBobyListWhole = Arrays.asList(requestBoby);
		if (requestBobyListWhole.isEmpty()) {
			return "";
//			Exception throwする
		}

		int studentId = Integer.parseInt(requestBobyListWhole.get(0));
		int specialSummaryId = Integer.parseInt(requestBobyListWhole.get(1));// このID値から、科目名取得をすること

		// 科目キー取得
		Optional<StudentClassSpecialSummary> summaryInfo = studentClassSpecialSummaryRepository
				.findById(specialSummaryId);
		if (summaryInfo.isEmpty()) {
			throw new Exception("summaryが存在しません");
		}

		int idStartrelatedShceduleInfo = 2;
		// tableId一覧取得
		List<String> relateTimeTableInfoList = requestBobyListWhole.subList(idStartrelatedShceduleInfo,
				requestBobyListWhole.size());

		for (int i = 0; i < relateTimeTableInfoList.size(); i++) {
			System.out.println(relateTimeTableInfoList.get(i));

			if (relateTimeTableInfoList.get(i).matches("^period.*$")) {
				continue;
			}

			// 生徒IDとtime_table_special_IDから授業のあり・なしを判定
			int eachTimeTableSpecialId = Integer.parseInt(relateTimeTableInfoList.get(i));
			Optional<StudentScheduleSpecial> classInfo = studentScheduleSpecialRepository
					.findByStudentAndTimeTableSpecial(new Student(studentId),
							new TimeTableSpecial(eachTimeTableSpecialId));

			if (classInfo.isPresent()) {
				System.out.println("授業あり->更新");
				StudentScheduleSpecial updateClass = classInfo.get().clone();
				updateClass.setSubject(new Subject(summaryInfo.get().getSubject().getSubjectKey()));
				updateClass.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
				studentScheduleSpecialRepository.save(updateClass);
			} else {
				System.out.println("授業なし->追加");
				StudentScheduleSpecial newClass = new StudentScheduleSpecial();
				newClass.setStudent(new Student(studentId));
				newClass.setSubject(new Subject(summaryInfo.get().getSubject().getSubjectKey()));
				newClass.setLecturer(new Lecturer(1));
				newClass.setTimeTableSpecial(new TimeTableSpecial(eachTimeTableSpecialId));

				Optional<TimeTableSpecial> getDate = timeTableSpecialRepository.findById(eachTimeTableSpecialId);
				newClass.setClassDate(getDate.get().getSpecialSeasonDateList().getClassDate());
				newClass.setRescheduleDateStart(UseOverFunction.convertStrDateToDateType("2022/09/14"));
				newClass.setRescheduleDateLast(UseOverFunction.convertStrDateToDateType("2022/09/14"));
				newClass.setRescheduleFlg(false);
				newClass.setCreatedAt(new Timestamp(System.currentTimeMillis()));
				newClass.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
				studentScheduleSpecialRepository.save(newClass);
			}
		}
		// なし：新しく追加する処理
		// あれば、新しくインスタンスを生成して、更新処理

		String strJson = UseOverFunction.getDataToJsonFormat("");
		return strJson;
	}

}
