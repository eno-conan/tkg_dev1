package com.eno.tkg.student.specialAttendance;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eno.tkg.entity.StudentAttendanceSpecial;
import com.eno.tkg.entity.master.SpecialSeason;
import com.eno.tkg.entity.master.Student;
import com.eno.tkg.entity.master.TimeTableSpecial;
import com.eno.tkg.repository.StudentAttendanceSpecialRepository;
import com.eno.tkg.util.UseOverFunction;
import com.fasterxml.jackson.core.JsonProcessingException;

@Service
class UpdateSpecialAttendanceService {

	@Autowired
	private StudentAttendanceSpecialRepository studentAttendanceSpecialRepository;

	/**
	 * // 生徒の講習期間の出欠表更新
	 * 
	 * @param content 受信情報
	 * @throws Exception
	 *
	 */
	String updateAttendance(String content) throws JsonProcessingException {
		System.out.println(content);
		String[] requestBoby = content.split(",");
		List<String> requestBobyListWhole = Arrays.asList(requestBoby);
		String specialSeasonId = requestBobyListWhole.get(0);
		String studentId = requestBobyListWhole.get(1);

		int idStartrelatedShceduleInfo = 2;
		List<String> relateTimeTableInfoList = requestBobyListWhole.subList(idStartrelatedShceduleInfo,
				requestBobyListWhole.size());// tableId一覧取得

		// 現在の登録情報状況を取得
		List<StudentAttendanceSpecial> currentAttendanceInfo = studentAttendanceSpecialRepository
				.findBySpecialSeasonAndStudent(new SpecialSeason(Integer.parseInt(specialSeasonId)),
						new Student(Integer.parseInt(studentId)));

		// 出席→欠席、欠席→出席、両方に対応できる処理に
		List<StudentAttendanceSpecial> registCheckIds = new ArrayList<>();
		for (String timetableId : relateTimeTableInfoList) {
			List<StudentAttendanceSpecial> dbdataExistInputDataResult = currentAttendanceInfo.stream()
					.filter(info -> String.valueOf(info.getTimeTableSpecial().getId()).equals(timetableId))
					.collect(Collectors.toList());
			// 入力値がDBに存在しない：追加ケース
			if (dbdataExistInputDataResult.isEmpty()) {
				// 空っぽ：今回チェックをいれたID
				StudentAttendanceSpecial sas = new StudentAttendanceSpecial();
				sas.setStudent(new Student(Integer.parseInt(studentId)));
				sas.setSpecialSeason(new SpecialSeason(Integer.parseInt(specialSeasonId)));
				sas.setTimeTableSpecial(new TimeTableSpecial(Integer.parseInt(timetableId)));
				sas.setCreatedAt(new Timestamp(System.currentTimeMillis()));
				sas.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
				registCheckIds.add(sas);
			}
		}

		// DB値が入力値に存在しない：追加ケース
		List<Integer> deleteUnCheckIds = new ArrayList<>();
		for (StudentAttendanceSpecial dbInfo : currentAttendanceInfo) {
			List<String> inputDataExistDbResult = relateTimeTableInfoList.stream()
					.filter(info -> info.equals(String.valueOf(dbInfo.getTimeTableSpecial().getId())))
					.collect(Collectors.toList());
			if (inputDataExistDbResult.isEmpty()) {
				deleteUnCheckIds.add(dbInfo.getId());
			}

		}

		// 追加対象
		if (!registCheckIds.isEmpty()) {
			studentAttendanceSpecialRepository.saveAll(registCheckIds);
		}
		// 削除対象
		if (!deleteUnCheckIds.isEmpty()) {
			studentAttendanceSpecialRepository.deleteAllById(deleteUnCheckIds);
		}
		String strJson = UseOverFunction.getDataToJsonFormat("出欠予定表の更新が完了しました");
		return strJson;
	}

}
