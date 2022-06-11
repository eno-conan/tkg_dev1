package com.eno.tkg.classSchedule;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eno.tkg.entity.StudentScheduleNormal;
import com.eno.tkg.entity.master.Lecturer;
import com.eno.tkg.entity.master.Student;
import com.eno.tkg.entity.master.TimeTableNormal;
import com.eno.tkg.repository.StudentScheduleNormalRepository;

@Service
class UpdateSelectClassScheduleService {

	@Autowired
	private StudentScheduleNormalRepository studentScheduleNormalRepository;

	private final int DAY_OF_WEEK_NUMBER_SUNDAY = 1;
	private final int MAX_ASSIGN_STUDENTS_TO_LECTURER = 2;

	/**
	 * // 振替実行
	 * 
	 * @param content 更新に必要な情報
	 * @return 更新情報
	 *
	 */
	StudentScheduleNormal updateTargetClassSchedule(final String content) throws Exception {

		// 4,2022-06-09,8
		// csvの情報を分離
		String studentScheduleId = content.split(",")[0];
		String alterClassDate = content.split(",")[1];
		String alterPeriod = content.split(",")[2];

		// DBから現在の情報を取得
		Optional<StudentScheduleNormal> updateTarget = studentScheduleNormalRepository
				.findById(Integer.parseInt(studentScheduleId));
		// TODO:もし値(studentScheduleId)がなかったら？
		Date date = validateBeforeUpdateClass(alterClassDate, alterPeriod, updateTarget);

		// 日付情報、コマ情報、振替フラグ,更新時刻、これらの項目を更新
		StudentScheduleNormal update = updateTarget.get().clone();
		int dayOfWeekNumber = checkDayOfWeekNumber(date);
		int timeTableNormalId = calcIdByPeriodAndDayOfWeek(dayOfWeekNumber, alterPeriod);
		update.setTimeTableNormal(new TimeTableNormal(timeTableNormalId));
		update.setClassDateOrigin(update.getClassDate());// 振替元日程
		update.setClassDate(date);// 振替先日程
		update.setRescheduleFlg(true);// 振替フラグ
		update.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
		return studentScheduleNormalRepository.save(update);
	}

	public int calcIdByPeriodAndDayOfWeek(final int dayOfWeekNumber, final String alterPeriod) {
		CalcTimeTableNormalId calcTimeTableNormalId = new CalcTimeTableNormalId(dayOfWeekNumber,
				Integer.parseInt(alterPeriod));
		return calcTimeTableNormalId.calcResult();
	}

	// 授業更新時のバリデーションチェック
	private Date validateBeforeUpdateClass(final String alterClassDate, final String alterPeriod,
			Optional<StudentScheduleNormal> updateTarget) throws Exception {
		// IDがない→まずありえないケースではある
		if (updateTarget.isEmpty()) {
			throw new Exception("IDが存在しません");
		}

		Date date = ClassScheduleUtil.convertStrDateToDateType(alterClassDate);
		int dayOfWeekNumber = checkDayOfWeekNumber(date);
		if (dayOfWeekNumber == DAY_OF_WEEK_NUMBER_SUNDAY) {
			throw new Exception("日曜日に授業振替は行えません");
		}

		// 振替期限の間に設定できていない場合
		if (date.before(updateTarget.get().getRescheduleDateStart())
				|| date.after(updateTarget.get().getRescheduleDateLast())) {
			throw new Exception("振替後日程が振替期間外になっています");
		}
		// 振替になっていないケース
		if (updateTarget.get().getClassDate().equals(date)
				&& updateTarget.get().getTimeTableNormal().getPeriod().equals(alterPeriod)) {
			throw new Exception("振替後日程が振替前日程と同じです");
		}

		// TimeTableNormalのIDを先に取得
		TimeTableNormal updateTargetTimeTableId = new TimeTableNormal(updateTarget.get().getTimeTableNormal().getId());

		// 振替先の日付・コマで担当する講師が既に1対2になっているケース
		List<StudentScheduleNormal> lecturerAlterClassScheduleList = studentScheduleNormalRepository
				.findAllByLecturerAndClassDateAndTimeTableNormal(new Lecturer(updateTarget.get().getLecturer().getId()),
						date, updateTargetTimeTableId);
		if (lecturerAlterClassScheduleList.size() >= MAX_ASSIGN_STUDENTS_TO_LECTURER) {
			throw new Exception("振替後日程にて、担当講師が既に2人の生徒を担当予定です");
		}

		// 振替先日程に、既に生徒の授業が組まれている場合
		Optional<StudentScheduleNormal> studentAlterClassSchedule = studentScheduleNormalRepository
				.findByStudentAndClassDateAndTimeTableNormal(new Student(updateTarget.get().getStudent().getId()), date,
						updateTargetTimeTableId);
		if (studentAlterClassSchedule.isPresent()) {
			throw new Exception("振替後日程にて、その生徒には既に別の授業が予定されています");
		}
		return date;
	}

	private int checkDayOfWeekNumber(final Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_WEEK);// Sunday:1,Saturday:7
	}

}
