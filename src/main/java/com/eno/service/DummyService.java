package com.eno.service;

import java.net.URISyntaxException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eno.entity.Member;
import com.eno.repository.MemberRepository;

@Service
public class DummyService {

	@Autowired
	private MemberRepository memberRepository;

//	@Autowired
//	private RestTemplate restTemplate;

	public String hello() {
		return "hello";
	}

	/**
	 * @return テーブル全件
	 *
	 */
	public List<Member> findAllMember() {
		return memberRepository.findAll();
	}

	/**
	 * @return テーブル全件
	 *
	 */
	public List<Member> findMembersDeleteFlgFalse() {
		List<Member> targetMembers = memberRepository.findByDeleteFlgFalse();
		targetMembers.forEach(member -> {
			System.out.println(member.getName());
		});
		return targetMembers;
	}

	public Member findById(final Integer id) {
		Member targetMembers = memberRepository.findByIdAndDeleteFlgFalse(id);
		targetMembers.getArticles().forEach(article -> {
			System.out.println(article.getTitle());
		});
		return targetMembers;
	}

	public void cloudFront() throws URISyntaxException {
//		RestTemplate restTemplate = new RestTemplate();
//	     
//		final String baseUrl = "https://d3sixhssfof58z.cloudfront.net/init";
//		URI uri = new URI(baseUrl);
//		 
//		ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);
//		System.out.println(result);
	}

}
