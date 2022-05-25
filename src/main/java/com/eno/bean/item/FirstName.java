package com.eno.bean.item;

import lombok.Data;

@Data
public class FirstName {

	private String firstname;

	public FirstName(final String firstname) {
		this.firstname = firstname;
	}

}
