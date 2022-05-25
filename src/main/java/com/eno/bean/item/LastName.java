package com.eno.bean.item;

import lombok.Data;

@Data
public class LastName {

	private String lastname;

	public LastName(final String lastname) {
		this.lastname = lastname;
	}

}
