package com.eno.tkg.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eno.tkg.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer>{
	
	public List<Member> findAll();
	
	public List<Member> findByDeleteFlgFalse();
	
	public Member findByIdAndDeleteFlgFalse(Integer id);

}
