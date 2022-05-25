package com.eno.bean;

import com.eno.bean.item.FirstName;
import com.eno.bean.item.LastName;

import lombok.Data;

@Data
public class MemberForm {

	private FirstName firstName;
	private LastName lastName;

	public MemberForm(final FirstName firstName, final LastName lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public MemberForm() {
	}

}
