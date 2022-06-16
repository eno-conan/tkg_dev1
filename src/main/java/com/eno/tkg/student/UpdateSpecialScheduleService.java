package com.eno.tkg.student;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eno.tkg.entity.StudentClassSpecialSummary;
import com.eno.tkg.entity.StudentScheduleSpecial;
import com.eno.tkg.entity.master.Lecturer;
import com.eno.tkg.entity.master.Student;
import com.eno.tkg.entity.master.Subject;
import com.eno.tkg.entity.master.TimeTableSpecial;
import com.eno.tkg.repository.StudentClassSpecialSummaryRepository;
import com.eno.tkg.repository.StudentScheduleSpecialRepository;
import com.eno.tkg.repository.TimeTableSpecialRepository;
import com.eno.tkg.util.UseOverFunction;

@Service
public class UpdateSpecialScheduleService {

	@Autowired
	private StudentScheduleSpecialRepository studentScheduleSpecialRepository;

	@Autowired
	private StudentClassSpecialSummaryRepository studentClassSpecialSummaryRepository;

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
	@Transactional
	public String updateTargetStudentSpecialSchedule(final String content) throws Exception {
		System.out.println(content);

		String[] requestBoby = content.split(",");
		List<String> requestBobyListWhole = Arrays.asList(requestBoby);
		if (requestBobyListWhole.isEmpty()) {
			throw new Exception("送信内容が存在しません");
		}

		// 科目の概要取得
		int specialSummaryId = Integer.parseInt(requestBobyListWhole.get(1));// このID値から、科目名取得をすること
		Optional<StudentClassSpecialSummary> summaryInfo = studentClassSpecialSummaryRepository
				.findById(specialSummaryId);
		if (summaryInfo.isEmpty()) {
			throw new Exception("summaryが存在しません");
		}

		try {
			int studentId = Integer.parseInt(requestBobyListWhole.get(0));
			int idStartrelatedShceduleInfo = 2;
			List<String> relateTimeTableInfoList = requestBobyListWhole.subList(idStartrelatedShceduleInfo,
					requestBobyListWhole.size());// tableId一覧取得
			updateStudentScheduleSpecialTable(studentId, summaryInfo, relateTimeTableInfoList);
		} catch (Exception e) {
			throw new Exception("DB更新でエラーが発生しました");
		}

		String strJson = UseOverFunction.getDataToJsonFormat("更新処理が完了しました");
		return strJson;
	}

	/**
	 * studentScheduleSpecialテーブルの追加・更新・削除
	 * 
	 * @param studentId               生徒ID
	 * @param summaryInfo             選択科目概要
	 * @param relateTimeTableInfoList 追加削除のための情報
	 * 
	 */
	@Transactional
	private void updateStudentScheduleSpecialTable(int studentId, Optional<StudentClassSpecialSummary> summaryInfo,
			List<String> relateTimeTableInfoList) {
		int updateUnplaceFrameCount = 0;// summaryテーブルのコマ数増減を管理
		int operateDataStatus = 0;// 追加・更新/削除か管理
		List<String> relateTimeTableInfoListReadOnly = Collections.unmodifiableList(relateTimeTableInfoList);

		for (int i = 0; i < relateTimeTableInfoListReadOnly.size(); i++) {
			// save-period{N}がきたときに保存（更新）
			if (relateTimeTableInfoListReadOnly.get(i).matches("save-period.*$")) {
				operateDataStatus = 1;
				continue;
				// delete-period{N}がきたときに削除処理に変更
			} else if (relateTimeTableInfoListReadOnly.get(i).matches("delete-period.*$")) {
				operateDataStatus = 2;
				continue;
			}

			// 生徒IDとtime_table_special_IDから授業のあり・なしを判定
			int eachTimeTableSpecialId = Integer.parseInt(relateTimeTableInfoList.get(i));
			Optional<StudentScheduleSpecial> classInfo = studentScheduleSpecialRepository
					.findByStudentAndTimeTableSpecial(new Student(studentId),
							new TimeTableSpecial(eachTimeTableSpecialId));
			
			if (operateDataStatus == 1) {
				if (classInfo.isPresent()) {
					StudentScheduleSpecial updateClass = updateClassInfo(summaryInfo, classInfo);
					studentScheduleSpecialRepository.save(updateClass);
				} else {
					StudentScheduleSpecial newClass = insertClass(studentId, summaryInfo, eachTimeTableSpecialId);
					studentScheduleSpecialRepository.save(newClass);
					updateUnplaceFrameCount++;
				}
			} else if (operateDataStatus == 2) {
				studentScheduleSpecialRepository.delete(classInfo.get());
				updateUnplaceFrameCount--;
			}
		}

		// 未配置コマ数の更新
		StudentClassSpecialSummary updateSummaryInfo = summaryInfo.get().clone();
		updateSummaryInfo.setUnplaceClassCount(summaryInfo.get().getUnplaceClassCount() - updateUnplaceFrameCount);
		updateSummaryInfo.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
		studentClassSpecialSummaryRepository.save(updateSummaryInfo);
	}

	/**
	 * studentScheduleSpecialテーブルのレコード追加のためのデータ準備
	 * 
	 * @param studentId              生徒ID
	 * @param summaryInfo            選択科目概要
	 * @param eachTimeTableSpecialId eachTimeTableSpecialテーブルのID
	 * @return newClass 挿入情報
	 */
	private StudentScheduleSpecial insertClass(int studentId, Optional<StudentClassSpecialSummary> summaryInfo,
			int eachTimeTableSpecialId) {
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
		return newClass;
	}

	/**
	 * studentScheduleSpecialテーブルのレコード更新のためのデータ準備
	 * 
	 * @param summaryInfo            選択科目概要
	 * @param classInfo              現在の授業情報
	 * @param eachTimeTableSpecialId eachTimeTableSpecialテーブルのID
	 * @return updateClass 更新情報
	 */
	private StudentScheduleSpecial updateClassInfo(Optional<StudentClassSpecialSummary> summaryInfo,
			Optional<StudentScheduleSpecial> classInfo) {
		System.out.println("授業あり->更新");
		StudentScheduleSpecial updateClass = classInfo.get().clone();
		updateClass.setSubject(new Subject(summaryInfo.get().getSubject().getSubjectKey()));
		updateClass.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
		return updateClass;
	}

}
