//package com.eno.controller;
//
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.MockitoAnnotations;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//
//import com.eno.form.MemberForm;
//import com.eno.service.DummyService;
//
//@SpringBootTest
//@ExtendWith(MockitoExtension.class)
//@EnableWebMvc
//class TestControllerTest {
//
//	private MockMvc mockMvc;
//
//	@Autowired
//	WebApplicationContext webApplicationContext;
//
//	@InjectMocks
//	private TestController targetClass;
//
//	@MockBean
//	DummyService dummyService;
//
//	@BeforeEach
//	void beforeEach() {
////		MockitoAnnotations.initMocks(this);
////		https://www.arhohuttunen.com/junit-5-mockito/
//		MockitoAnnotations.openMocks(this);
//		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext) // MockMVCをセットアップ
//				.build();
//	}
//
//	@Test
//	@DisplayName("memberForm表示＿正常系")
//	void memberFormTest() throws Exception {
//		when(dummyService.hello()).thenReturn("hello");
//
//		// check view,value setting model
//		MvcResult result = mockMvc.perform(get("/")).andExpect(status().isOk())
//				.andExpect(model().attribute("hello", "hello")).andExpect(view().name("memberForm")).andReturn();
//
//		// check form init values
//		MemberForm form = (MemberForm) result.getModelAndView().getModel().get("memberForm");
//		assertEquals(form, new MemberForm());
//
//		verify(dummyService, times(1)).hello();
//	}
//
//	@Test
//	@DisplayName("memberForm送信＿異常系＿全項目未入力")
//	void memberFormSend_notInputValueAnyItems_Error() throws Exception {
//
//		// not input any value
//		MemberForm memberForm = new MemberForm();
//
//		// check view,value setting model
//		MvcResult result = mockMvc.perform((post("/send")).flashAttr("memberForm", memberForm))
//				.andExpect(model().hasErrors()).andExpect(model().attribute("memberForm", memberForm))
//				.andExpect(view().name("memberForm")).andReturn();
//
//		MemberForm form = (MemberForm) result.getModelAndView().getModel().get("memberForm");
//		assertEquals(form, new MemberForm());
//	}
//
//	@Test
//	@DisplayName("memberForm送信＿異常系＿名前11文字異常")
//	void memberFormSend_firstNameOver10_Error() throws Exception {
//
//		// not input any value
//		MemberForm memberForm = new MemberForm();
//		memberForm.setFirstName("12345678901");//11文字
//
//		// check view,value setting model
//		MvcResult result = mockMvc.perform((post("/send")).flashAttr("memberForm", memberForm))
//				.andExpect(model().hasErrors()).andExpect(model().attribute("memberForm", memberForm))
//				.andExpect(view().name("memberForm")).andReturn();
//
//		MemberForm form = (MemberForm) result.getModelAndView().getModel().get("memberForm");
//		assertEquals(form.getFirstName().length() > 10 , true);
//	}
//
//	@Test
//	@DisplayName("memberForm送信＿正常系")
//	void memberFormSend_AllGreen() throws Exception {
//
//		// not input any value
//		MemberForm memberForm = new MemberForm();
//		memberForm.setFirstName("Mike");
//		memberForm.setLastName("Orak");
//
//		// check view,value setting model
//		MvcResult result = mockMvc.perform((post("/send")).flashAttr("memberForm", memberForm))
//				.andExpect(model().hasNoErrors()).andExpect(redirectedUrl("/dummy")).andReturn();
//
//		MemberForm form = (MemberForm) result.getModelAndView().getModel().get("memberForm");
//		assertEquals(form.getFirstName().length() < 11, true);
//	}
//
//}
