package com.eno.tkg.config;

import javax.annotation.Resource;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class RoleList {

	@Resource
	Environment environment;

	public String getRoles(final String key) {
		String setRoleList = "";

		if (key == null || "".equals(key)) {
			return setRoleList;
		}

		String roleList = environment.getProperty("user.role.list");
		if (roleList == null || "".equals(roleList)) {
			return setRoleList;
		}
		String[] role = roleList.split(",", 0);

		String valueList = environment.getProperty("user.role" + key);
		if (valueList == null) {
			return setRoleList;
		}
		String[] value = valueList.split(",", 0);

		if (role.length != value.length) {
			return setRoleList;
		}

		int i = 0;

		StringBuilder sb = new StringBuilder();

		//ロール名取得
		for (String str : value) {
			if ("1".equals(str)) {
				sb.append(role[i] + ",");
			}
			i++;
		}

		setRoleList = sb.toString();

		return setRoleList;

	}

}
