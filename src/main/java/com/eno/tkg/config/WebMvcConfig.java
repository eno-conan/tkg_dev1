package com.eno.tkg.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

public class WebMvcConfig extends WebMvcConfigurationSupport {
	
//	@Autowired
//	RoleList roleList;
	
	@Override
	public Validator getValidator() {
		LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		// フォームのフィールドごとのメッセージの定義ファイル
		messageSource.setBasenames("ValidationMessages_ja"); 
		messageSource.setDefaultEncoding("UTF-8");
		validator.setValidationMessageSource(messageSource);
		return validator;
	}

	@Override
	public void addResourceHandlers(final ResourceHandlerRegistry registory) {
		// 静的ファイルを追加 auto-configuration が消えてしまうため...
		// https://memorynotfound.com/adding-static-resources-css-javascript-images-thymeleaf/
		registory.addResourceHandler("/css/**", "/js/**").addResourceLocations("classpath:/static/css/",
				"classpath:/static/js/");
	}
	
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http.authorizeRequests().antMatchers("/users/**", "/settings/**", "/countries/**", "/states/**")
//				.hasAuthority("Admin")
//				.antMatchers("/categories/**", "/brands/**").hasAnyAuthority(roleList.getRoles())
//	}
}
