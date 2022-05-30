package com.eno.form;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class UseInterfaceForm {

	// 初期登録ということで
	public static interface New {
	}

	// 修正ということで
	public static interface Edit {
	}

	@NotEmpty
	private String name;

	@NotEmpty(groups = { New.class })
	private String age;

	@NotEmpty(groups = { Edit.class })
	private String prize;

}
