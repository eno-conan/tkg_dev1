package com.eno.tkg.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eno.tkg.entity.StudentScheduleNormal;
import com.eno.tkg.entity.master.Lecturer;
import com.eno.tkg.entity.master.Student;
import com.eno.tkg.entity.master.TimeTableNormal;

@Repository
public interface StudentScheduleNormalRepository extends JpaRepository<StudentScheduleNormal, Integer> {

	// 対象日付に実施予定の全授業取得
	public List<StudentScheduleNormal> findAllByClassDateOrderByTimeTableNormalAsc(Date date);

	// あるコマの講師の授業を取得
	public List<StudentScheduleNormal> findAllByLecturerAndClassDateAndTimeTableNormal(Lecturer lecturer, Date date,
			TimeTableNormal timeTableNormal);

	// あるコマの生徒の授業予定を取得
	public Optional<StudentScheduleNormal> findByStudentAndClassDateAndTimeTableNormal(Student student, Date date,
			TimeTableNormal timeTableNormal);

	// 生徒の授業予定を取得
	public List<StudentScheduleNormal> findByStudentAndClassDateAfterOrderByClassDateAsc(Student student, Date date);

	@Query(value = "select ssn.*, m_sub.display_name,m_lec.name,m_cls.classroom_name from student_schedule_normal ssn "
			+ "left join lecturer_teach_subject lts "
			+ "on ssn.subject_key = lts.subject_key "
			+ "left join m_subject m_sub "
			+ "on lts.subject_key = m_sub.subject_key "
			+ "left join m_lecturer m_lec "
			+ "on lts.lecturer_id = m_lec.id "
			+ "left join m_classroom m_cls "
			+ "on m_lec.classroom_id = m_cls.id "
			+ "where ssn.id = :scheduleNormalId "
			+ "and lts.teach_flg = '1' "
			, nativeQuery = true)
	public List<StudentScheduleNormal> findTargetSubjectTeachLecturersByScheduleId(@Param("scheduleNormalId") Integer scheduleNormalId);

}
