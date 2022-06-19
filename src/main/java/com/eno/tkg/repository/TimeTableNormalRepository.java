package com.eno.tkg.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.eno.tkg.entity.SpecialSeasonDateList;
import com.eno.tkg.entity.master.Area;
import com.eno.tkg.entity.master.Grade;
import com.eno.tkg.entity.master.TimeTableNormal;
import com.eno.tkg.entity.master.TimeTableSpecial;

@Repository
public interface TimeTableNormalRepository extends JpaRepository<TimeTableNormal, Integer> {

//	public List<Optional<TimeTableSpecial>> findByPeriod(String period);
//
//	public Optional<TimeTableSpecial> findBySpecialSeasonDateListAndPeriod(SpecialSeasonDateList dateListId,
//			String period);

}
