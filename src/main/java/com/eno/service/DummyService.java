package com.eno.service;

import java.net.URISyntaxException;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eno.form.UseInterfaceForm;

@Service
public class DummyService {


//	@Autowired
//	private RestTemplate restTemplate;

	@Autowired
	Validator validator;
	
	//implement 22/05/30
	public void tryValidatorPatternNew() {
		UseInterfaceForm uiForm = new UseInterfaceForm();
		uiForm.setName("abc");
		uiForm.setAge("12");
		for (ConstraintViolation<UseInterfaceForm> err : 
			validator.validate(uiForm,
				new Class[] { UseInterfaceForm.New.class })) {
			System.out.println("New part:");
			System.out.println(err.getMessage());
		}
	}
	
	public void tryValidatorPatternEdit() {
		UseInterfaceForm uiForm = new UseInterfaceForm();
		uiForm.setName("abc");
		uiForm.setAge("12");
		for (ConstraintViolation<UseInterfaceForm> err : 
			validator.validate(uiForm,
				new Class[] { UseInterfaceForm.Edit.class })) {
			System.out.println("=====Edit part=====");
			System.out.println(err.getPropertyPath().toString());
			System.out.println(err.getMessage());
		}
	}
	
	

	public String hello() {
		return "hello";
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
