package com.eno.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class MemberForm {

	@NotEmpty(message = "{com.eno.form.firstName.NotEmpty.message}")
	@Size(max = 10, message = "10文字以内で")
	private String firstName;

	@NotEmpty(message = "{com.eno.form.lastName.NotEmpty.message}")
	private String lastName;

	public MemberForm(final String firstName, final String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public MemberForm() {
	}

}
