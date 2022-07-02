package com.eno.tkg.student.specialAttendance;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eno.tkg.entity.SpecialSeasonDateList;
import com.eno.tkg.entity.StudentAttendanceSpecial;
import com.eno.tkg.entity.StudentScheduleSpecial;
import com.eno.tkg.entity.master.SpecialSeason;
import com.eno.tkg.entity.master.Student;
import com.eno.tkg.repository.SpecialSeasonDateListRepository;
import com.eno.tkg.repository.StudentAttendanceSpecialRepository;
import com.eno.tkg.repository.StudentScheduleSpecialRepository;
import com.eno.tkg.util.UseOverFunction;
import com.fasterxml.jackson.core.JsonProcessingException;

@Service
class SpecialAttendanceService {

	@Autowired
	private SpecialSeasonDateListRepository specialSeasonDateListRepository;

	@Autowired
	private StudentAttendanceSpecialRepository studentAttendanceSpecialRepository;

	@Autowired
	private StudentScheduleSpecialRepository studentScheduleSpecialRepository;

	/**
	 * // 講習期間の日程を取得
	 * 
	 * @param specialSeasonId 講習ID
	 * @return json 日程一覧
	 * @throws JsonProcessingException
	 *
	 */
	String getCurrentSpecialAttendance(final String studentId, final String specialSeasonId)
			throws JsonProcessingException {

		// 日付取得
		List<Optional<SpecialSeasonDateList>> specialSeasonDateList = getDateList(specialSeasonId);

		// 現在の出欠情報状況を取得
		List<StudentAttendanceSpecial> currentAttendanceInfo = studentAttendanceSpecialRepository
				.findBySpecialSeasonAndStudent(new SpecialSeason(Integer.parseInt(specialSeasonId)),
						new Student(Integer.parseInt(studentId)));

		// 現在の講習予定を取得（授業が入っている箇所を更新できないようにする）
		List<Optional<StudentScheduleSpecial>> currentStundentSchedule = studentScheduleSpecialRepository
				.findByStudentOrderByClassDate(new Student(Integer.parseInt(studentId)));

		List<Map<String, Object>> returnJsonLiteral = new ArrayList<>();

		int timeTableIdStart = 1;
		for (final Optional<SpecialSeasonDateList> date : specialSeasonDateList) {

//			7:1日あたりのコマ数
			final int timeTableCntInADay = 7;
			Map<String, Object> eachDateInfoMap = new LinkedHashMap<>();
			List<Map<String, Object>> eachIdAndCheckedInfo = new ArrayList<>();
//			1日のコマごとに処理
			for (int i = 0; i < timeTableCntInADay; i++) {
				final int currentTimeTableId = timeTableIdStart + i;
				Map<String, Object> eachIdInfoMap = new LinkedHashMap<>();

				eachIdInfoMap.put("timeTableId", String.valueOf(currentTimeTableId));
				// 現在チェックが入っているtimeTableId（テーブルに存在するtimeTableId）の情報を付与
				List<StudentAttendanceSpecial> currentCheckedResult = currentAttendanceInfo.stream()
						.filter(info -> String.valueOf(info.getTimeTableSpecial().getId())
								.equals(String.valueOf(currentTimeTableId)))
						.collect(Collectors.toList());

				boolean checkedFlg = false;
				if (!currentCheckedResult.isEmpty()) {
					checkedFlg = true;
				}
				eachIdInfoMap.put("checkedFlg", checkedFlg);

				// 現在の授業予定との比較
				List<Optional<StudentScheduleSpecial>> classExistResult = currentStundentSchedule.stream()
						.filter(info -> String.valueOf(info.get().getTimeTableSpecial().getId())
								.equals(String.valueOf(currentTimeTableId)))
						.collect(Collectors.toList());

				boolean notOperateFlg = false;
				// 授業がある箇所は非機能状態にする
				if (!classExistResult.isEmpty()) {
					notOperateFlg = true;
				}
				eachIdInfoMap.put("notOperateFlg", notOperateFlg);
				eachIdAndCheckedInfo.add(eachIdInfoMap);
			}
			// 翌日を処理する場合、1日の総コマ数を加算
			timeTableIdStart = timeTableIdStart + timeTableCntInADay;
			eachDateInfoMap.put("date", UseOverFunction.dateToDateStr(date.get().getClassDate()).replace("-", "/"));
			eachDateInfoMap.put("idAndCheckInfo", eachIdAndCheckedInfo);
			returnJsonLiteral.add(eachDateInfoMap);
		}

		String strJson = UseOverFunction.getDataToJsonFormat(returnJsonLiteral);
		return strJson;
	}

	private List<Optional<SpecialSeasonDateList>> getDateList(final String specialSeasonId) {
		// 日付一覧取得
		List<Optional<SpecialSeasonDateList>> specialSeasonDateList = specialSeasonDateListRepository
				.findBySpecialSeason(new SpecialSeason(Integer.parseInt(specialSeasonId)));
		return specialSeasonDateList;
	}

}
