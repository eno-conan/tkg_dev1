package com.eno.tkg.repository.master;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eno.tkg.entity.StudentScheduleNormal;
import com.eno.tkg.entity.master.Lecturer;

@Repository
public interface LecturerRepository extends JpaRepository<Lecturer, Integer> {

	public List<Lecturer> findByDeleteFlg(boolean deleteFlg);

	// ある生徒が通っている教室の講師を先頭で取得
	@Query(value = "SELECT * FROM m_lecturer ml ORDER BY FIELD( ml.classroom_id, :id) desc", nativeQuery = true)
	Optional<List<Lecturer>> findLecturerStudentWithClassroomTop(@Param("id") Integer id);
	
	@Query(value = "select m_lec.* from student_schedule_normal ssn "
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
	public List<Lecturer> findTargetSubjectTeachLecturersByScheduleId(@Param("scheduleNormalId") Integer scheduleNormalId);

}
