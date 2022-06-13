package com.eno.tkg.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.eno.tkg.entity.SpecialSeasonDateList;
import com.eno.tkg.entity.master.Lecturer;
import com.eno.tkg.entity.master.SpecialSeason;

@Repository
public interface SpecialSeasonDateListRepository extends CrudRepository<SpecialSeasonDateList, Integer>{
	
	public List<Optional<SpecialSeasonDateList>> findBySpecialSeason(SpecialSeason specialSeason);


}
