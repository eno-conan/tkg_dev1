package com.eno.tkg.repository.master;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eno.tkg.entity.master.Lecturer;

@Repository
public interface LecturerRepository extends JpaRepository<Lecturer, Integer> {

	public List<Lecturer> findByDeleteFlg(boolean deleteFlg);

	// ある生徒が通っている教室の講師を先頭で取得
	@Query(value = "SELECT * FROM m_lecturer ml ORDER BY FIELD( ml.classroom_id, :id) desc", nativeQuery = true)
	Optional<List<Lecturer>> findLecturerStudentWithClassroomTop(@Param("id") Integer id);

}
