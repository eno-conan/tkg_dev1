package com.eno.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eno.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer>{
	
	public List<Member> findAll();
	
	public List<Member> findByDeleteFlgFalse();
	
	public Member findByIdAndDeleteFlgFalse(Integer id);

}
