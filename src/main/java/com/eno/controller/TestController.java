package com.eno.controller;

import java.net.URISyntaxException;
import java.util.List;

import org.hibernate.internal.build.AllowSysOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.eno.form.MemberForm;
import com.eno.service.DummyService;

@Controller
public class TestController {

	@Autowired
	private DummyService dummyService;

	@GetMapping("/validator")
	public String tryUseValidatorInterface() {
		//fulfill values
		dummyService.tryValidatorPatternNew();
		//not fulfill values
		dummyService.tryValidatorPatternEdit();
		return "dummy";
	}

	@GetMapping("/")
	public String memberFormView(Model model) {
		String message = dummyService.hello();
//		List<Member> members = dummyService.findAllMember();
//		List<Member> members = dummyService.findMembersDeleteFlgFalse();
//		Member memberInfo = dummyService.findById(1);
//		dummyService.callApiTest();
		try {
			dummyService.cloudFront();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		model.addAttribute("hello", message);
		model.addAttribute("memberFormLabel", "Member Form");
		model.addAttribute(new MemberForm());
		return "memberForm";
	}

	@PostMapping("/send")
	public String memberFormSend(@Validated MemberForm memberForm, BindingResult errors, Model model) {
		if (errors.hasErrors()) {
			model.addAttribute("memberFormLabel", "Member Form");
			model.addAttribute("memberForm", memberForm);
			return "memberForm";
		}
		return "redirect:" + "/dummy";
	}

	@GetMapping("/dummy")
	public String dummy() {
		return "dummy";
	}

}
