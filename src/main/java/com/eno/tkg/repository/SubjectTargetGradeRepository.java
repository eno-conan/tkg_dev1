package com.eno.tkg.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eno.tkg.entity.master.Grade;
import com.eno.tkg.entity.master.SubjectTargetGrade;


@Repository
public interface SubjectTargetGradeRepository extends JpaRepository<SubjectTargetGrade, Integer>{
		
//	生徒の学年で受講できる科目、かつ、まだ受講登録していない科目のみ取得
	@Query(value = "SELECT  "
			+"mstg.subject_key , m_sub.display_name , mstg.id , mstg.grade_key "
			+"FROM "
			+"m_subject_target_grade AS mstg "
			+"LEFT JOIN "
			+"m_student AS m_stu ON mstg.grade_key = m_stu.grade_key "
			+"LEFT JOIN "
			+"m_subject AS m_sub ON mstg.subject_key = m_sub.subject_key "
			+"WHERE "
			+"m_stu.id = :studentId "
			+"and not mstg.subject_key in "
			+"(select stu_sub2.subject_key "
			+"FROM student_subject AS stu_sub2 "
			+"LEFT JOIN m_subject m_sub2 "
			+"ON not stu_sub2.subject_key = m_sub2.subject_key  "
			+") ",
			  nativeQuery = true)
	Optional<List<SubjectTargetGrade>> getSubjectsByGradeAndNotRegistered(@Param("studentId") String studentId);
	
	Optional<List<SubjectTargetGrade>> findByGrade(Grade grade);

}
