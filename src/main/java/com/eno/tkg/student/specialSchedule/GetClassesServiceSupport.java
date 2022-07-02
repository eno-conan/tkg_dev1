package com.eno.tkg.student.specialSchedule;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eno.tkg.entity.SpecialSeasonDateList;
import com.eno.tkg.entity.StudentAttendanceSpecial;
import com.eno.tkg.entity.StudentScheduleSpecial;
import com.eno.tkg.entity.master.SpecialSeason;
import com.eno.tkg.entity.master.TimeTableSpecial;
import com.eno.tkg.repository.SpecialSeasonDateListRepository;

@Service
class GetClassesServiceSupport {

	@Autowired
	private SpecialSeasonDateListRepository specialSeasonDateListRepository;

	/**
	 * // 生徒の講習期間の科目とコマ数の概要取得
	 * 
	 * @param filteringClassesByPeriod コマごとの授業情報
	 * @param timetableSpecialInfo     timeTableIdに紐づく情報
	 * @return divideExistClassOrNotList 授業あり/なしの情報
	 *
	 */
	List<Optional<StudentScheduleSpecial>> setInfoByClassExist(final String specialSeasonId,
			List<Optional<StudentScheduleSpecial>> filteringClassesByPeriod,
			List<Optional<TimeTableSpecial>> timetableSpecialInfo,
			List<StudentAttendanceSpecial> currentAttendanceInfo) {

		// 既に授業があるかないか、その情報を格納
		List<Optional<StudentScheduleSpecial>> divideExistClassOrNotList = new ArrayList<>();

		// 講習日程取得
		List<Optional<SpecialSeasonDateList>> specialSeasonDateList = getDateList(specialSeasonId);

		int dateListIdx = 0;
		// 日ごとに、loopのコマ数に授業があるかどうか、チェック
		// （画面に表示する内容が授業内容か、生徒・講師いずれかの都合が悪く、グレーになるかチェックボックスか決める）
		for (Optional<SpecialSeasonDateList> certainDate : specialSeasonDateList) {
			final int currentIdx = dateListIdx;
			// 日付ごとに、授業があるか判定
			List<Optional<StudentScheduleSpecial>> eachDateClass = judgeExistClassTargetDate(filteringClassesByPeriod,
					certainDate);

			// loopのコマ数の値と日付情報を利用して、time_table_specialのIDを取得

			// 出欠情報の組み込み
			if (eachDateClass.size() == 0) {
				// 授業が予定されていないtimeTableId:timetableSpecialInfo.get(currentIdx).get().getId()
				List<StudentAttendanceSpecial> resultStudentFreeOrNot = currentAttendanceInfo.stream()
						.filter(attend -> String.valueOf(attend.getTimeTableSpecial().getId())
								.equals(String.valueOf(timetableSpecialInfo.get(currentIdx).get().getId())))
						.collect(Collectors.toList());

				// 空っぽ:今処理しているtimeTableIdが、生徒の出席timetableIdに存在しない->グレーアウト
				if (resultStudentFreeOrNot.isEmpty()) {
					// specialIdに0を設定してフロントに返す
					divideExistClassOrNotList.add(Optional.ofNullable(
							new StudentScheduleSpecial(certainDate.get().getClassDate(), new TimeTableSpecial(-1))));
				} else {
					// 生徒出席可能：チェックボックスを表示する。
					divideExistClassOrNotList
							.add(Optional.ofNullable(new StudentScheduleSpecial(certainDate.get().getClassDate(),
									new TimeTableSpecial(timetableSpecialInfo.get(currentIdx).get().getId()))));
				}
			} else {
				divideExistClassOrNotList.add(eachDateClass.get(0));
			}
			dateListIdx++;
		}
		return divideExistClassOrNotList;
	}

	/**
	 * // 生徒の講習期間の科目とコマ数の概要取得
	 * 
	 * @param filteringClassesByPeriod コマ数ごとの授業予定
	 * @param certainDate              講習会の1日程
	 * @return eachDateClass 授業の情報
	 *
	 */
	List<Optional<StudentScheduleSpecial>> judgeExistClassTargetDate(
			List<Optional<StudentScheduleSpecial>> filteringClassesByPeriod,
			Optional<SpecialSeasonDateList> certainDate) {
		List<Optional<StudentScheduleSpecial>> eachDateClass = filteringClassesByPeriod.stream().filter(s -> {
			int result = s.get().getClassDate().compareTo(certainDate.get().getClassDate());
			// 「同じ日だと、0を返す」0がどうか判定
			int numberJudgeSameDay = 0;
			if (result == numberJudgeSameDay) {
				return true;
			}
			return false;
		}).collect(Collectors.toList());
		return eachDateClass;
	}

	// 日付一覧取得
	List<Optional<SpecialSeasonDateList>> getDateList(final String specialSeasonId) {
		List<Optional<SpecialSeasonDateList>> specialSeasonDateList = specialSeasonDateListRepository
				.findBySpecialSeason(new SpecialSeason(Integer.parseInt(specialSeasonId)));
		return specialSeasonDateList;
	}

}
